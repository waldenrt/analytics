package com.brierley.lifecycle

import com.brierley.general.utils.DateUtils
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.{Success, Try}

/**
  * Created by amerrill on 7/24/17.
  */
object Lifecycle {
  def loadFile(sqlCtx: HiveContext, delimiter: String, fileLocation: String): Try[DataFrame] = Try {
    val orgFile = sqlCtx
      .read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("delimiter", delimiter)
      .load(fileLocation)

    val cols = orgFile.columns

    if (cols.contains("ENROLL_DATE")) {
      orgFile.select("CUST_ID", "ENROLL_DATE", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "PROD_CAT")
    }
    else
      orgFile.select("CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "PROD_CAT")


  }

  //follow quantile method for trimming dates....
  def calcTimePeriod(dateDF: DataFrame, numMonths: Int): Try[DataFrame] = Try {
    if (numMonths == 0) {
      return Success(dateDF.withColumn("TimePeriod", lit(1)))
    }

    val trimDF = DateUtils.trimMonths(dateDF)
    val maxDateDF = trimDF.select(max("Date"))
      .withColumn("lastDay", last_day(col("max(Date)")))
      .head()

    val maxDate = maxDateDF.getAs[java.sql.Date](0)
    val lastDate = maxDateDF.getAs[java.sql.Date](1)

    if (maxDate != lastDate) {
      val result = trimDF
        .select("*")
        .withColumn("max(Date)", lit(maxDate))
        .withColumn("period", lit(numMonths))
        .withColumn("TimePeriod", LifecycleUDFs.periodCalc(col("max(Date)"), trimDF("Date"), col("period")))
        .sort("TimePeriod")
        .drop(col("period"))
        .drop(col("max(Date)"))

      (result)
    } else {
      val result = trimDF
        .select("*")
        .withColumn("max(Date)", lit(maxDate))
        .withColumn("Month", month(dateDF("Date")))
        .withColumn("Year", year(dateDF("Date")))
        .withColumn("MaxMonth", month(col("max(Date)")))
        .withColumn("MaxYear", year(col("max(Date)")))
        .withColumn("TimePeriod", (((col("MaxMonth") - col("Month")) + (col("MaxYear") - col("Year")) * 12) / numMonths).cast(IntegerType) + 1)
        .sort("TimePeriod")
        .drop(col("Month"))
        .drop(col("Year"))
        .drop(col("MaxMonth"))
        .drop(col("MaxYear"))
        .drop(col("max(Date)"))
      (result)
    }
  }

  def custAgg(tpDF: DataFrame): Try[DataFrame] = Try {
    val aggWindow = Window
      .partitionBy("TimePeriod")
      .orderBy("CUST_ID")

    val tpWindow = Window
      .partitionBy("TimePeriod")
    val selectDF = tpDF.select("CUST_ID", "Date", "TXN_HEADER_ID", "ITEM_AMT", "ITEM_QTY", "TimePeriod")

    val aggDF = selectDF
      .groupBy("TimePeriod", "CUST_ID")
      .agg(
        countDistinct("TXN_HEADER_ID").alias("TXN_COUNT"),
        sum("ITEM_AMT").alias("TXN_AMT"),
        sum("ITEM_QTY").alias("ITEM_QTY"),
        max("Date").alias("maxCustDate")
      )

    val recentDF = selectDF
      .select("TimePeriod", "Date")
        .withColumn("maxTPDate", max("Date").over(tpWindow))
      .drop("Date")
      .distinct()

    val joined = aggDF.join(recentDF, Seq("TimePeriod"))

    val result = joined
      .withColumn("daysSince", datediff(col("maxTPDate"), col("maxCustDate")) + 1)
      .drop("maxCustDate")
      .drop("maxTPDate")

    result
  }

  def calcRFM(aggDF: DataFrame): Try[DataFrame] = Try {
    val rWindow = Window
      .partitionBy("TimePeriod")
      .orderBy(col("daysSince").desc)

    val fWindow = Window
      .partitionBy("TimePeriod")
      .orderBy(col("TXN_COUNT").asc)

    val mWindow = Window
      .partitionBy("TimePeriod")
      .orderBy(col("TXN_AMT").asc)

    val rankDF = aggDF
      .withColumn("rRank", percent_rank().over(rWindow))
      .withColumn("fRank", percent_rank().over(fWindow))
      .withColumn("mRank", percent_rank().over(mWindow))
      .withColumn("R", LifecycleUDFs.calcQuint(col("rRank")))
      .withColumn("F", LifecycleUDFs.calcQuint(col("fRank")))
      .withColumn("M", LifecycleUDFs.calcQuint(col("mRank")))
      .drop(col("rRank"))
      .drop(col("fRank"))
      .drop(col("mRank"))
      .withColumn("RFM", LifecycleUDFs.calcRFM(col("R"), col("F"), col("M")))


    rankDF
  }

  def groupRFM(rfmDF: DataFrame): Try[DataFrame] = Try{

    rfmDF
      .withColumn("Segment", LifecycleUDFs.labelRFM(rfmDF("RFM")))
  }

  def calcGlobalTotals(segDF: DataFrame): Try[DataFrame] = ???

  def segmentAgg(segDF: DataFrame): Try[DataFrame] = ???



  def calcPercentages(segAggDF: DataFrame): Try[DataFrame] = ???

  def calcAverages(perDF: DataFrame): Try[DataFrame] = ???


  def tpAggProd(tpDF: DataFrame): Try[DataFrame] = ???

  def segAggProd(tpDF: DataFrame, segDF: DataFrame): Try[DataFrame] = ???

  def indexProd(tpAggDF: DataFrame, segAggDF: DataFrame): Try[DataFrame] = ???



  def main(args: Array[String]): Unit = {
    val jobName = "Lifecycle"
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
      .keyBy(_ (0))
      .mapValues(_ (1))

    if (args.length != 4) {
      //sendError
      System.exit(-1)
    }

    val fileLocation = args(0)
    val delimiter = args(1)
    val jobKey = args(2)
    val timePeriod = args(3).toInt

    val validTP = List(0, 3, 6, 12)

    if (!validTP.contains(timePeriod)) {
      //sendError
      System.exit(-1)
    }
    /*
        for {
          orgFile <- loadFile(sqlCtx, delimiter, fileLocation)

          dateDF <- DateUtils.determineFormat(orgFile)

          tpDF <- calcTimePeriod(dateDF, timePeriod)

          aggDF <- custAgg(tpDF)

          rfmDF <- calcRFM(aggDF)

          labelRfmDF <- groupRFM(rfmDF)

        } yield orgFile
    */
  }

}
