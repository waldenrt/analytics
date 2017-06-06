package com.brierley.balor

import java.time.LocalDateTime
import java.util

import com.brierley.avro.schemas.{Balor, Error, TimePeriodData, exception}
import com.brierley.utils.BalorUDFs._
import com.brierley.utils.{BalorProducer, _}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.{AnalysisException, DataFrame, Row}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.{Failure, Success, Try}

/**
  * Created by amerrill on 1/30/17.
  */
object BalorApp {

  def loadFile(sqlCtx: HiveContext, delimiter: String, fileLocation: String): Try[DataFrame] = Try {

    val orgFile = sqlCtx
      .read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("delimiter", delimiter)
      .load(fileLocation)

    val cols = orgFile.columns

    if (cols.contains("DISC_AMT") && cols.contains("ITEM_QTY")) {
      orgFile
        .select("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "DISC_AMT", "ITEM_QTY")
        .withColumn("ITEM_QTY", orgFile("ITEM_QTY").cast(IntegerType))
        .na.fill(0, Seq("DISC_AMT", "ITEM_QTY"))
        .na.drop(Seq("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT"))
    } else if (cols.contains("DISC_AMT")) {
      orgFile
        .select("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "DISC_AMT")
        .withColumn("ITEM_QTY", lit(0))
        .na.drop(Seq("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT"))
    } else if (cols.contains("ITEM_QTY")) {
      orgFile
        .select("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY")
        .withColumn("DISC_AMT", lit(0.toDouble))
        .withColumn("ITEM_QTY", orgFile("ITEM_QTY").cast(IntegerType))
        .na.drop(Seq("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT"))
    } else {
      orgFile
        .select("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT")
        .withColumn("ITEM_QTY", lit(0))
        .withColumn("DISC_AMT", lit(0.toDouble))
        .na.drop(Seq("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT"))
    }

  }

  def calcTimePeriod(dateDF: DataFrame, cadence: CadenceValues): Try[DataFrame] = Try {

    def weekTimePeriod(dateDF: DataFrame): DataFrame = {
      dateDF
        .select("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "Date", "max(Date)")
        .withColumn("TimePeriod", (datediff(dateDF("max(Date)"), dateDF("Date")) / cadence.periodDivisor).cast(IntegerType) + 1)
        .sort("TimePeriod")
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date", "TimePeriod")

    }

    def monthTimePeriod(trimDF: DataFrame): DataFrame = {
      val maxDateDF = trimDF.select(max("Date"))

      trimDF
        .select("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "Date")
        .withColumn("max(Date)", lit(maxDateDF.select("max(Date)").first().getDate(0)))
        .withColumn("Month", month(dateDF("Date")))
        .withColumn("Year", year(dateDF("Date")))
        .withColumn("MaxMonth", month(col("max(Date)")))
        .withColumn("MaxYear", year(col("max(Date)")))
        .withColumn("TimePeriod", ((col("MaxMonth") - col("Month")) + (col("MaxYear") - col("Year")) * 12).cast(IntegerType) + 1)
        .sort("TimePeriod")
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date", "TimePeriod")
    }

    val timePeriodDF = cadence match {
      case OneWeek => weekTimePeriod(DateUtils.trimWeeks(dateDF, cadence.periodDivisor))
      case TwoWeeks => weekTimePeriod(DateUtils.trimWeeks(dateDF, cadence.periodDivisor))
      case _ => monthTimePeriod(DateUtils.trimToWholeMonth(dateDF))
    }

    timePeriodDF.persist(StorageLevel.MEMORY_AND_DISK)
    timePeriodDF

  }

  def minMaxDates(timePeriodDF: DataFrame): Try[DataFrame] = Try {
    timePeriodDF
      .select(min("Date"), max("Date"))

  }

  def countTxns(timePeriodDF: DataFrame): Try[Long] = Try {
    timePeriodDF.count()
  }

  def assignSegmentLabel(timePeriodDF: DataFrame): Try[DataFrame] = Try {

    val returnDF = timePeriodDF
      .groupBy("TimePeriod", "CUST_ID")
      .agg(count("TXN_ID").as("TXN_COUNT"),
        sum("TXN_AMT").as("TXN_AMT"),
        sum("ITEM_QTY").as("ITEM_QTY"),
        sum("DISC_AMT").as("DISC_AMT"))

    val custWindow = Window
      .partitionBy("CUST_ID")
      .orderBy("TimePeriod")
      .rangeBetween(0, 2)

    val labelDF = returnDF
      .withColumn("Label", nonLapsedLabel(returnDF("TimePeriod"), sum("TimePeriod").over(custWindow)))
      .sort("TimePeriod")
      .select("TimePeriod", "Label", "CUST_ID", "TXN_COUNT", "TXN_AMT", "DISC_AMT", "ITEM_QTY")

    val lapsedWindow = Window
      .partitionBy("CUST_ID")
      .orderBy("TimePeriod")

    val lapsedDF = labelDF
      .withColumn("TPDiff", labelDF("TimePeriod") - lag(("TimePeriod"), 1).over(lapsedWindow))
      .filter((col("TPDiff") > 1) || (col("TPDiff").isNull && labelDF("TimePeriod") > 1))
      .withColumn("Label", lit("Lapsed"))
      .withColumn("LapsedTimePeriod", labelDF("TimePeriod") - 1)
      .drop("TimePeriod")
      .withColumnRenamed("LapsedTimePeriod", "TimePeriod")
      .select("TimePeriod", "Label", "CUST_ID", "TXN_COUNT", "TXN_AMT", "DISC_AMT", "ITEM_QTY")

    val completeDF = labelDF
      .unionAll(lapsedDF)
      .sort("TimePeriod")
      .na.fill(0)

    completeDF.persist(StorageLevel.MEMORY_AND_DISK)
    completeDF
  }

  def counts(labelDF: DataFrame): Try[DataFrame] = Try {

    val maxTP = labelDF
      .select(max("TimePeriod")).head().getInt(0)

    val pivotDF = labelDF
      .filter(labelDF("TimePeriod") < (maxTP - 1))
      .groupBy("TimePeriod")
      .pivot("Label", Seq("New", "Reactivated", "Returning", "Lapsed"))
      .agg(count("CUST_ID"), sum("TXN_COUNT"), sum("TXN_AMT"), sum("DISC_AMT"), sum("ITEM_QTY"))

    pivotDF
      .withColumnRenamed("New_count(CUST_ID)", "newCustCount")
      .withColumnRenamed("New_sum(TXN_COUNT)", "newTxnCount")
      .withColumnRenamed("New_sum(TXN_AMT)", "newTxnAmt")
      .withColumnRenamed("New_sum(DISC_AMT)", "newDiscAmt")
      .withColumnRenamed("New_sum(ITEM_QTY)", "newItemCount")
      .withColumnRenamed("Reactivated_count(CUST_ID)", "reactCustCount")
      .withColumnRenamed("Reactivated_sum(TXN_COUNT)", "reactTxnCount")
      .withColumnRenamed("Reactivated_sum(TXN_AMT)", "reactTxnAmt")
      .withColumnRenamed("Reactivated_sum(DISC_AMT)", "reactDiscAmt")
      .withColumnRenamed("Reactivated_sum(ITEM_QTY)", "reactItemCount")
      .withColumnRenamed("Lapsed_count(CUST_ID)", "lapsedCustCount")
      .withColumnRenamed("Lapsed_sum(TXN_COUNT)", "lapsedTxnCount")
      .withColumnRenamed("Lapsed_sum(TXN_AMT)", "lapsedTxnAmt")
      .withColumnRenamed("Lapsed_sum(DISC_AMT)", "lapsedDiscAmt")
      .withColumnRenamed("Lapsed_sum(ITEM_QTY)", "lapsedItemCount")
      .withColumnRenamed("Returning_count(CUST_ID)", "returnCustCount")
      .withColumnRenamed("Returning_sum(TXN_COUNT)", "returnTxnCount")
      .withColumnRenamed("Returning_sum(TXN_AMT)", "returnTxnAmt")
      .withColumnRenamed("Returning_sum(DISC_AMT)", "returnDiscAmt")
      .withColumnRenamed("Returning_sum(ITEM_QTY)", "returnItemCount")
  }

  def calcSegAvg(countsDF: DataFrame): Try[DataFrame] = Try {

    countsDF
      .withColumn("newCustSpendAvg", doubleAvgCalc(countsDF("newTxnAmt"), countsDF("newCustCount")))
      .withColumn("newCustVisitAvg", longAvgCalc(countsDF("newTxnCount"), countsDF("newCustCount")))
      .withColumn("newCustItemAvg", longAvgCalc(countsDF("newItemCount"), countsDF("newCustCount")))
      .withColumn("newCustDiscAvg", doubleAvgCalc(countsDF("newDiscAmt"), countsDF("newCustCount")))
      .withColumn("newVisitSpendAvg", doubleAvgCalc(countsDF("newTxnAmt"), countsDF("newTxnCount")))
      .withColumn("newVisitItemAvg", longAvgCalc(countsDF("newItemCount"), countsDF("newTxnCount")))
      .withColumn("newVisitDiscAvg", doubleAvgCalc(countsDF("newDiscAmt"), countsDF("newTxnCount")))
      .withColumn("reactCustSpendAvg", doubleAvgCalc(countsDF("reactTxnAmt"), countsDF("reactCustCount")))
      .withColumn("reactCustVisitAvg", longAvgCalc(countsDF("reactTxnCount"), countsDF("reactCustCount")))
      .withColumn("reactCustItemAvg", longAvgCalc(countsDF("reactItemCount"), countsDF("reactCustCount")))
      .withColumn("reactCustDiscAvg", doubleAvgCalc(countsDF("reactDiscAmt"), countsDF("reactCustCount")))
      .withColumn("reactVisitSpendAvg", doubleAvgCalc(countsDF("reactTxnAmt"), countsDF("reactTxnCount")))
      .withColumn("reactVisitItemAvg", longAvgCalc(countsDF("reactItemCount"), countsDF("reactTxnCount")))
      .withColumn("reactVisitDiscAvg", doubleAvgCalc(countsDF("reactDiscAmt"), countsDF("reactTxnCount")))
      .withColumn("returnCustSpendAvg", doubleAvgCalc(countsDF("returnTxnAmt"), countsDF("returnCustCount")))
      .withColumn("returnCustVisitAvg", longAvgCalc(countsDF("returnTxnCount"), countsDF("returnCustCount")))
      .withColumn("returnCustItemAvg", longAvgCalc(countsDF("returnItemCount"), countsDF("returnCustCount")))
      .withColumn("returnCustDiscAvg", doubleAvgCalc(countsDF("returnDiscAmt"), countsDF("returnCustCount")))
      .withColumn("returnVisitSpendAvg", doubleAvgCalc(countsDF("returnTxnAmt"), countsDF("returnTxnCount")))
      .withColumn("returnVisitItemAvg", longAvgCalc(countsDF("returnItemCount"), countsDF("returnTxnCount")))
      .withColumn("returnVisitDiscAvg", doubleAvgCalc(countsDF("returnDiscAmt"), countsDF("returnTxnCount")))
      .withColumn("lapsedCustSpendAvg", doubleAvgCalc(countsDF("lapsedTxnAmt"), countsDF("lapsedCustCount")))
      .withColumn("lapsedCustVisitAvg", longAvgCalc(countsDF("lapsedTxnCount"), countsDF("lapsedCustCount")))
      .withColumn("lapsedCustItemAvg", longAvgCalc(countsDF("lapsedItemCount"), countsDF("lapsedCustCount")))
      .withColumn("lapsedCustDiscAvg", doubleAvgCalc(countsDF("lapsedDiscAmt"), countsDF("lapsedCustCount")))
      .withColumn("lapsedVisitSpendAvg", doubleAvgCalc(countsDF("lapsedTxnAmt"), countsDF("lapsedTxnCount")))
      .withColumn("lapsedVisitItemAvg", longAvgCalc(countsDF("lapsedItemCount"), countsDF("lapsedTxnCount")))
      .withColumn("lapsedVisitDiscAvg", doubleAvgCalc(countsDF("lapsedDiscAmt"), countsDF("lapsedTxnCount")))

  }

  def calcBalorRatios(countsDF: DataFrame): Try[DataFrame] = Try {

    val segWindow = Window.orderBy("TimePeriod")

    val balorDF = countsDF
      .withColumn("custBalor", balorCount(countsDF("newCustCount"), countsDF("reactCustCount"),
        countsDF("lapsedCustCount")))
      .withColumn("txnBalor", balorCount(countsDF("newTxnCount"), countsDF("reactTxnCount"),
        countsDF("lapsedTxnCount")))
      .withColumn("spendBalor", balorMoney(countsDF("newTxnAmt"), countsDF("reactTxnAmt"),
        countsDF("lapsedTxnAmt")))
      .withColumn("retention", retention(countsDF("returnCustCount"), lead("reactCustCount", 1).over(segWindow),
        lead("newCustCount", 1).over(segWindow), lead("returnCustCount", 1).over(segWindow)))
      .na.fill(0)

    balorDF
  }

  def createBalorAvro(jobKey: String, txnCount: Long, minMaxDateDF: DataFrame, balorDF: DataFrame): Try[Balor] = Try {

    val balor = new Balor()
    balor.setJobKey(jobKey)
    balor.setNumRecords(txnCount)
    balor.put("completionTime", LocalDateTime.now().toString)

    val minDate = minMaxDateDF
      .select("min(Date)")
      .head()
      .getDate(0)
      .toString

    val maxDate = minMaxDateDF
      .select("max(Date)")
      .head()
      .getDate(0)
      .toString

    balor.setMinDateBalor(minDate)
    balor.setMaxDateBalor(maxDate)

    val tempList = new java.util.ArrayList[TimePeriodData]

    def mapTimePeriodData(tpdRow: Row): Unit = {
      val tpd = new TimePeriodData()
      tpd.setTimePeriod(tpdRow.getInt(0))
      tpd.setNewCustCount(tpdRow.getLong(1))
      tpd.setNewTxnCount(tpdRow.getLong(2))
      tpd.setNewTxnAmt(tpdRow.getDouble(3))
      tpd.setNewDiscAmt(tpdRow.getDouble(4))
      tpd.setNewItemQty(tpdRow.getLong(5))

      tpd.setReactCustCount(tpdRow.getLong(6))
      tpd.setReactTxnCount(tpdRow.getLong(7))
      tpd.setReactTxnAmt(tpdRow.getDouble(8))
      tpd.setReactDiscAmt(tpdRow.getDouble(9))
      tpd.setReactItemQty(tpdRow.getLong(10))

      tpd.setReturnCustCount(tpdRow.getLong(11))
      tpd.setReturnTxnCount(tpdRow.getLong(12))
      tpd.setReturnTxnAmt(tpdRow.getDouble(13))
      tpd.setReturnDiscAmt(tpdRow.getDouble(14))
      tpd.setReturnItemQty(tpdRow.getLong(15))

      tpd.setLapsedCustCount(tpdRow.getLong(16))
      tpd.setLapsedTxnCount(tpdRow.getLong(17))
      tpd.setLapsedTxnAmt(tpdRow.getDouble(18))
      tpd.setLapsedDiscAmt(tpdRow.getDouble(19))
      tpd.setLapsedItemQty(tpdRow.getLong(20))

      tpd.setNewCustSpendAvg(tpdRow.getDouble(21))
      tpd.setNewCustVisitAvg(tpdRow.getDouble(22))
      tpd.setNewCustItemAvg(tpdRow.getDouble(23))
      tpd.setNewCustDiscAvg(tpdRow.getDouble(24))
      tpd.setNewVisitSpendAvg(tpdRow.getDouble(25))
      tpd.setNewVisitItemAvg(tpdRow.getDouble(26))
      tpd.setNewVisitDiscAvg(tpdRow.getDouble(27))

      tpd.setReactCustSpendAvg(tpdRow.getDouble(28))
      tpd.setReactCustVisitAvg(tpdRow.getDouble(29))
      tpd.setReactCustItemAvg(tpdRow.getDouble(30))
      tpd.setReactCustDiscAvg(tpdRow.getDouble(31))
      tpd.setReactVisitSpendAvg(tpdRow.getDouble(32))
      tpd.setReactVisitItemAvg(tpdRow.getDouble(33))
      tpd.setReactVisitDiscAvg(tpdRow.getDouble(34))

      tpd.setReturnCustSpendAvg(tpdRow.getDouble(35))
      tpd.setReturnCustVisitAvg(tpdRow.getDouble(36))
      tpd.setReturnCustItemAvg(tpdRow.getDouble(37))
      tpd.setReturnCustDiscAvg(tpdRow.getDouble(38))
      tpd.setReturnVisitSpendAvg(tpdRow.getDouble(39))
      tpd.setReturnVisitItemAvg(tpdRow.getDouble(40))
      tpd.setReturnVisitDiscAvg(tpdRow.getDouble(41))

      tpd.setLapsedCustSpendAvg(tpdRow.getDouble(42))
      tpd.setLapsedCustVisitAvg(tpdRow.getDouble(43))
      tpd.setLapsedCustItemAvg(tpdRow.getDouble(44))
      tpd.setLapsedCustDiscAvg(tpdRow.getDouble(45))
      tpd.setLapsedVisitSpendAvg(tpdRow.getDouble(46))
      tpd.setLapsedVisitItemAvg(tpdRow.getDouble(47))
      tpd.setLapsedVisitDiscAvg(tpdRow.getDouble(48))

      tpd.setCustBalor(tpdRow.getDouble(49))
      tpd.setTxnBalor(tpdRow.getDouble(50))
      tpd.setSpendBalor(tpdRow.getDouble(51))
      tpd.setRetention(tpdRow.getDouble(52))

      tempList.add(tpd)
    }

    balorDF.collect().foreach(e => mapTimePeriodData(e))

    balor.setBalorSets(tempList)

    balor
  }

  def sendBalorError(jobKey: String, className: String, methodName: String, msg: String, exType: String, propsList: RDD[(String, String)]): Unit = {
    val error = new Error()
    error.setJobKey(jobKey)
    error.setJobType("Balor")

    val ex = new exception()
    ex.setClassName(className)
    ex.setMethodName(methodName)
    ex.setExceptionMsg(msg)
    ex.setExceptionType(exType)

    val tempList = new util.ArrayList[exception]
    tempList.add(ex)

    error.setErrorInfo(tempList)

    BalorProducer.sendError(error, propsList)

  }

  def main(args: Array[String]): Unit = {

    val jobName = "BalorApp"
    val conf = new SparkConf().setAppName(jobName)
      .set("spark.driver.maxResultSize", "3g")
      .set("spark.sql.autoBroadcastJoinThreshold", "500000000")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.speclation", "true")

    val sc = new SparkContext(conf)
    val sqlCtx = new HiveContext(sc)

    val kafkaProps = sc.textFile("kafkaProps.txt")
    val propsOnly = kafkaProps.filter(_.contains("analytics"))
      .map(_.split(" = "))
      .keyBy(_(0))
      .mapValues(_(1))

    if (args.length < 4) {
      sendBalorError("Unknown", "BalorApp", "MainMethod", "Incorrect Usage, not enough args.", "User", propsOnly)
      System.exit(-1)
    }

    val fileLocation = args(0)
    val delimiter = args(1)
    val jobKey = args(2)
    val cadenceIndex = args(3).toInt

    val finalDF = for {
      cadence <- Try(cadenceIndex match {
        case 7 => OneYear
        case 6 => SixMonths
        case 5 => ThreeMonths
        case 4 => TwoMonths
        case 3 => OneMonth
        case 2 => TwoWeeks
        case 1 => OneWeek
      })
      orgFile <- loadFile(sqlCtx, delimiter, fileLocation)

      dateDF <- DateUtils.determineFormat(orgFile)

      timePeriodDF <- calcTimePeriod(dateDF, cadence)

      minMaxDF <- minMaxDates(timePeriodDF)

      txnCount <- countTxns(timePeriodDF)

      labelDF <- assignSegmentLabel(timePeriodDF)

      countsDF <- counts(labelDF)

      avgDF <- calcSegAvg(countsDF)

      balorDF <- calcBalorRatios(avgDF)

      avro <- createBalorAvro(jobKey, txnCount, minMaxDF, balorDF)
    } yield avro

    finalDF match {
      case Success(avro) => {
        println(s"was a success: $avro")
        BalorProducer.sendBalor("balor", propsOnly, avro)

        sc.stop()
      }
      case Failure(ex) => {
        println(s"something went wrong: $ex")
        ex match {
          case i: MatchError => sendBalorError(jobKey, "BalorApp", "MainMethod", "Invalid CadenceValue, must be 1-7", "User", propsOnly)
          case j: AnalysisException => sendBalorError(jobKey, "BalorApp", "loadFile", "Incorrect File Format, check column names and delimiter", "User", propsOnly)
          case k: NumberFormatException => sendBalorError(jobKey, "DateUtils", "determineFormat", k.getMessage, "User", propsOnly)
          case ex => sendBalorError(jobKey, "BalorApp", "unknown", ex.toString, "System", propsOnly)
        }

        sc.stop()
        System.exit(-1)
      }
    }
  }

}
