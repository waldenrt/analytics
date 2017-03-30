package com.brierley.balor

import java.io.File
import java.sql.Date

import com.brierley.avro.schemas.{Balor, TimePeriodData}
import com.brierley.utils._
import com.brierley.utils.BalorUDFs._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.storage.StorageLevel
import org.apache.avro.Schema
import org.apache.avro.file.{DataFileReader, DataFileWriter}
import org.apache.avro.generic.{GenericData, GenericDatumWriter, GenericRecord}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}

/**
  * Created by amerrill on 1/30/17.
  */
object BalorApp {

  def calcTimePeriod(dateDF: DataFrame, cadence: CadenceValues): (DataFrame, DataFrame) = {

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
        .withColumn("TimePeriod", (((col("MaxMonth") - col("Month")) + (col("MaxYear") - col("Year")) * 12) / cadence.periodDivisor).cast(IntegerType) + 1)
        .sort("TimePeriod")
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date", "TimePeriod")
    }

    val timePeriodsDF = cadence match {
      case OneWeek => weekTimePeriod(DateUtils.trimWeeks(dateDF, cadence.periodDivisor))
      case TwoWeeks => weekTimePeriod(DateUtils.trimWeeks(dateDF, cadence.periodDivisor))
      case _ => monthTimePeriod(DateUtils.trimToWholeMonth(dateDF))
    }

    val returnDF = timePeriodsDF
      .groupBy("TimePeriod", "CUST_ID")
      .agg(count("TXN_ID").as("TXN_COUNT"),
        sum("TXN_AMT").as("TXN_AMT"),
        sum("ITEM_QTY").as("ITEM_QTY"),
        sum("DISC_AMT").as("DISC_AMT"))

    val begEndDateDF = timePeriodsDF
      .select(min("Date"), max("Date"))

    (returnDF, begEndDateDF)
  }

  def assignSegmentLabel(timePeriodDF: DataFrame): DataFrame = {

    val custWindow = Window
      .partitionBy("CUST_ID")
      .orderBy("TimePeriod")
      .rangeBetween(0, 2)

    val labelDF = timePeriodDF
      .withColumn("Label", nonLapsedLabel(timePeriodDF("TimePeriod"), sum("TimePeriod").over(custWindow)))
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

  def counts(labelDF: DataFrame): DataFrame = {

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

  def calcBalorRatios(countsDF: DataFrame): DataFrame = {
    val balorDF = countsDF
      .withColumn("custBalor", balorCount(countsDF("newCustCount"), countsDF("reactCustCount"),
        countsDF("lapsedCustCount")))
      .withColumn("txnBalor", balorCount(countsDF("newTxnCount"), countsDF("reactTxnCount"),
        countsDF("lapsedTxnCount")))
      .withColumn("spendBalor", balorMoney(countsDF("newTxnAmt"), countsDF("reactTxnAmt"),
        countsDF("lapsedTxnAmt")))

    balorDF
  }

  def createBalorAvro(jobKey: String, txnCount: Long, minMaxDateDF: DataFrame, balorDF: DataFrame): GenericRecord = {
   // val schema = new Schema.Parser().parse(new File("src/main/avro/balorAvroSchema.avsc"))

    val balor = new Balor()
    balor.setJobKey(jobKey)
    balor.setNumRecords(txnCount)
    balor.put("completionTime", "fakeTime")

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
        tpd.setNewItemQty(tpdRow.getDouble(5))

        tpd.setReactCustCount(tpdRow.getLong(6))
        tpd.setReactTxnCount(tpdRow.getLong(7))
        tpd.setReactTxnAmt(tpdRow.getDouble(8))
        tpd.setReactDiscAmt(tpdRow.getDouble(9))
        tpd.setReactItemQty(tpdRow.getDouble(10))

        tpd.setReturnCustCount(tpdRow.getLong(11))
        tpd.setReturnTxnCount(tpdRow.getLong(12))
        tpd.setReturnTxnAmt(tpdRow.getDouble(13))
        tpd.setReturnDiscAmt(tpdRow.getDouble(14))
        tpd.setReturnItemQty(tpdRow.getDouble(15))

        tpd.setLapsedCustCount(tpdRow.getLong(16))
        tpd.setLapsedTxnCount(tpdRow.getLong(17))
        tpd.setLapsedTxnAmt(tpdRow.getDouble(18))
        tpd.setLapsedDiscAmt(tpdRow.getDouble(19))
        tpd.setLapsedItemQty(tpdRow.getDouble(20))

        tpd.setCustBalor(tpdRow.getDouble(21))
        tpd.setTxnBalor(tpdRow.getDouble(22))
        tpd.setSpendBalor(tpdRow.getDouble(23))

        tempList.add(tpd)
    }

    balorDF.collect().foreach(e => mapTimePeriodData(e))

    balor.setBalorSets(tempList)

/*
    val file = new File("tmp/balor.avro")
    val datumWriter = new SpecificDatumWriter[Balor](schema)
    val fileWriter = new DataFileWriter[Balor](datumWriter)
    fileWriter.create(schema, file)
    fileWriter.append(balor)
    fileWriter.close()
*/

    balor
  }

  def main(args: Array[String]): Unit = {

    if (args.length < 4) {
      //TODO return exception
    }

    val fileLocation = args(0)
    val delimiter = args(1)
    val jobKey = args(2)
    val cadenceIndex = args(3).toInt

    val cadence = cadenceIndex match {
      case 7 => OneYear
      case 6 => SixMonths
      case 5 => ThreeMonths
      case 4 => TwoMonths
      case 3 => OneMonth
      case 2 => TwoWeeks
      case 1 => OneWeek
      //TODO throw exception for any other case
        // case _ => Throw me!
    }

    val jobName = "BalorApp"
    val conf = new SparkConf().setAppName(jobName)
      .set("spark.driver.maxResultSize", "3g")
      .set("spark.sql.autoBroadcastJoinThreshold", "500000000")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.speclation", "true")

    val sc = new SparkContext(conf)
    val sqlCtx = new HiveContext(sc)

    val orgFile = sqlCtx
      .read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("delimiter", delimiter)
      .load(fileLocation)

    val dateDF = DateUtils.determineFormat(orgFile)
    dateDF.persist(StorageLevel.MEMORY_AND_DISK)


    val (timePeriodDF, minMaxDF) = calcTimePeriod(dateDF, cadence)
    timePeriodDF.persist(StorageLevel.MEMORY_AND_DISK)

    //want to count num of transactions that are actually being included in the BALOR calcs
    val txnCount = timePeriodDF.count()

    val labelDF = assignSegmentLabel(timePeriodDF)

    val countsDF = counts(labelDF)

    val balorDF = calcBalorRatios(countsDF)

    val avro = createBalorAvro(jobKey, txnCount, minMaxDF, balorDF)

    //TODO write to kafka, for now this will be used to QA results
    //balorDF.write.parquet("tmp/balor")

    println(s"Balor avro output: $avro")

    sc.stop()

  }

}
