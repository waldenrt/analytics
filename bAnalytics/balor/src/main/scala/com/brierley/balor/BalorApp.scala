package com.brierley.balor

import com.brierley.utils.{CadenceValues, DateUtils, OneWeek, TwoWeeks}
import com.brierley.utils.BalorUDFs._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.storage.StorageLevel

import com.databricks.spark.avro._

/**
  * Created by amerrill on 1/30/17.
  */
object BalorApp {

  def calcTimePeriod(dateDF: DataFrame, cadence: CadenceValues): DataFrame = {

    def weekTimePeriod(dateDF: DataFrame): DataFrame = {
      val maxDateDF = dateDF.select(max("Date"))
      dateDF
        .select("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "Date")
        .join(maxDateDF)
        .withColumn("TimePeriod", (datediff(col("max(Date)"), dateDF("Date")) / cadence.periodDivisor).cast(IntegerType) + 1)
        .sort("TimePeriod")
        .select("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date", "TimePeriod")
    }

    def monthTimePeriod(dateDF: DataFrame): DataFrame = {
      val maxDateDF = dateDF.select(max("Date"))
        .withColumn("MaxMonth", month(col("max(Date)")))
        .withColumn("MaxYear", year(col("max(Date)")))

      dateDF
        .select("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "Date")
        .withColumn("Month", month(dateDF("Date")))
        .withColumn("Year", year(dateDF("Date")))
        .join(maxDateDF)
        .withColumn("TimePeriod", (((col("MaxMonth") - col("Month")) + (col("MaxYear") - col("Year")) * 12) / cadence.periodDivisor).cast(IntegerType) + 1)
        .sort("TimePeriod")
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "TimePeriod")
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

    returnDF
  }

  def assignSegmentLabel(timePeriodDF: DataFrame): DataFrame = {

    val custWindow = Window
      .partitionBy("CUST_ID")
      .orderBy("TimePeriod")
      .rangeBetween(0, 2)

    val labelDF = timePeriodDF
      .withColumn("Label", nonLapsedLabel(timePeriodDF("TimePeriod"), sum("TimePeriod").over(custWindow)))
      .sort("TimePeriod")
      .select("CUST_ID", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "TimePeriod", "Label")

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
      .select("CUST_ID", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "TimePeriod", "Label")

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
      .filter(labelDF("TimePeriod") < (maxTP-1))
      .groupBy("TimePeriod")
      .pivot("Label", Seq("New", "Reactivated", "Returning", "Lapsed"))
      .agg(sum("TXN_AMT"), sum("DISC_AMT"), sum("ITEM_QTY"), count("CUST_ID"), sum("TXN_COUNT"))

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
      .withColumn("CustBalor", balorCount(countsDF("newCustCount"),countsDF("reactCustCount"),
        countsDF("lapsedCustCount")))
      .withColumn("TxnBalor", balorCount(countsDF("newTxnCount"), countsDF("reactTxnCount"),
        countsDF("lapsedTxnCount")))
      .withColumn("SpendBalor", balorMoney(countsDF("newTxnAmt"), countsDF("reactTxnAmt"),
        countsDF("lapsedTxnAmt")))

    balorDF
  }

  def main(args: Array[String]): Unit = {

    if (args.length < 4) {
      //TODO return exception
    }

    val fileLocation = args(0)
    val delimiter = args(1)
    val cadenceIndex = args(2).toInt
    //val cadenceval = CadenceValues
    val cadence = OneWeek
    //TODO figure out how to cast this to CadenceValues
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

    val timePeriodDF = calcTimePeriod(dateDF, cadence)

    //want to count num of transactions that are actually being included in the BALOR calcs
    val txnCount = timePeriodDF.count()

    val labelDF = assignSegmentLabel(timePeriodDF)

    val countsDF = counts(labelDF)

    val balorDF = calcBalorRatios(countsDF)

    val avroDF = balorDF.write.avro("tmp/balor")
    val readDF = sqlCtx.read.avro("tmp/balor")
    readDF.show()
    readDF.printSchema()
  }


}
