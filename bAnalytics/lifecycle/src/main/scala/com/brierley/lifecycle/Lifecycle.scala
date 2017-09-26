package com.brierley.lifecycle

import java.time.LocalDateTime
import java.util

import com.brierley.avro.schemas._
import com.brierley.general.utils.DateUtils
import com.brierley.kafka.producer.KafkaProducer
import kafka.Kafka
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.{Failure, Success, Try}

/**
  * Created by amerrill on 7/24/17.
  */
object Lifecycle {
  def loadFile(sqlCtx: HiveContext, delimiter: String, fileLocation: String, columnName: String): Try[DataFrame] = Try {
    val orgFile = sqlCtx
      .read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("delimiter", delimiter)
      .load(fileLocation)

    val cols = orgFile.columns

    if (cols.contains("ENROLL_DATE")) {
      orgFile.select("CUST_ID", "ENROLL_DATE", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", columnName)
    }
    else
      orgFile.select("CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", columnName)
      .withColumn("ITEM_QTY", orgFile("ITEM_QTY").cast(IntegerType))


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
        .withColumn("TP", LifecycleUDFs.periodCalc(col("max(Date)"), trimDF("Date"), col("period")))
        .sort(col("TP").desc)
        .withColumn("TimePeriod", dense_rank().over(Window.orderBy(col("TP").desc)))
        .drop(col("period"))
        .drop(col("max(Date)"))
        .drop("TP")

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
        .drop(col("Month"))
        .drop(col("Year"))
        .drop(col("MaxMonth"))
        .drop(col("MaxYear"))
        .drop(col("max(Date)"))
        //.withColumn("TimePeriod", dense_rank().over(Window.orderBy(col("TP").desc)))
        //.drop("TP")
      (result)
    }
  }

  def custAgg(tpDF: DataFrame): Try[DataFrame] = Try {
    val aggWindow = Window
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
      .withColumn("maxTPDate", max("Date").over(aggWindow))
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
      .withColumn("Recency", LifecycleUDFs.calcQuint(col("rRank")))
      .withColumn("F", LifecycleUDFs.calcQuint(col("fRank")))
      .withColumn("M", LifecycleUDFs.calcQuint(col("mRank")))
      .drop(col("rRank"))
      .drop(col("fRank"))
      .drop(col("mRank"))
      .withColumn("RFM", LifecycleUDFs.calcRFM(col("Recency"), col("F"), col("M")))


    rankDF
  }

  def groupRFM(rfmDF: DataFrame): Try[DataFrame] = Try {

    rfmDF
      .withColumn("Segment", LifecycleUDFs.labelRFM(rfmDF("RFM")))
  }

  def calcGlobalTotals(segDF: DataFrame): Try[DataFrame] = Try {
    segDF
      .groupBy("TimePeriod")
      .agg(
        countDistinct("CUST_ID").alias("TotalCusts"),
        sum("TXN_COUNT").alias("TotalTxns"),
        sum("TXN_AMT").alias("TotalSpend"),
        sum("ITEM_QTY").alias("TotalItems")
      )
  }

  def segmentAgg(segDF: DataFrame): Try[DataFrame] = Try {
    val pivotDF = segDF
      .groupBy("TimePeriod")
      .pivot("Segment", Seq("Best in Class", "Rising Stars", "Middle of the Road", "Lapsing", "Deeply Lapsed"))
      .agg(
        countDistinct("CUST_ID"),
        sum("TXN_COUNT"),
        sum("TXN_AMT"),
        sum("ITEM_QTY"),
        sum("daysSince")
      )

    val renamedDF = pivotDF
      .withColumnRenamed("Best in Class_count(CUST_ID)", "BestCustCount")
      .withColumnRenamed("Best in Class_sum(TXN_COUNT)", "BestTxnCount")
      .withColumnRenamed("Best in Class_sum(TXN_AMT)", "BestTxnAmt")
      .withColumnRenamed("Best in Class_sum(ITEM_QTY)", "BestItemQty")
      .withColumnRenamed("Best in Class_sum(daysSince)", "BestRecency")
      .withColumnRenamed("Rising Stars_count(CUST_ID)", "RisingCustCount")
      .withColumnRenamed("Rising Stars_sum(TXN_COUNT)", "RisingTxnCount")
      .withColumnRenamed("Rising Stars_sum(TXN_AMT)", "RisingTxnAmt")
      .withColumnRenamed("Rising Stars_sum(ITEM_QTY)", "RisingItemQty")
      .withColumnRenamed("Rising Stars_sum(daysSince)", "RisingRecency")
      .withColumnRenamed("Middle of the Road_count(CUST_ID)", "MiddleCustCount")
      .withColumnRenamed("Middle of the Road_sum(TXN_COUNT)", "MiddleTxnCount")
      .withColumnRenamed("Middle of the Road_sum(TXN_AMT)", "MiddleTxnAmt")
      .withColumnRenamed("Middle of the Road_sum(ITEM_QTY)", "MiddleItemQty")
      .withColumnRenamed("Middle of the Road_sum(daysSince)", "MiddleRecency")
      .withColumnRenamed("Lapsing_count(CUST_ID)", "LapsingCustCount")
      .withColumnRenamed("Lapsing_sum(TXN_COUNT)", "LapsingTxnCount")
      .withColumnRenamed("Lapsing_sum(TXN_AMT)", "LapsingTxnAmt")
      .withColumnRenamed("Lapsing_sum(ITEM_QTY)", "LapsingItemQty")
      .withColumnRenamed("Lapsing_sum(daysSince)", "LapsingRecency")
      .withColumnRenamed("Deeply Lapsed_count(CUST_ID)", "DeeplyCustCount")
      .withColumnRenamed("Deeply Lapsed_sum(TXN_COUNT)", "DeeplyTxnCount")
      .withColumnRenamed("Deeply Lapsed_sum(TXN_AMT)", "DeeplyTxnAmt")
      .withColumnRenamed("Deeply Lapsed_sum(ITEM_QTY)", "DeeplyItemQty")
      .withColumnRenamed("Deeply Lapsed_sum(daysSince)", "DeeplyRecency")
      .na.fill(0)

    renamedDF
  }

  def calcPercentages(segAggDF: DataFrame, globalDF: DataFrame): Try[DataFrame] = Try {
    val joinedDF = segAggDF.join(globalDF, Seq("TimePeriod"))

    joinedDF
      .withColumn("BestPercentCustBase", LifecycleUDFs.longAvgCalc(col("BestCustCount"), col("TotalCusts")))
      .withColumn("BestPercentTxnBase", LifecycleUDFs.longAvgCalc(col("BestTxnCount"), col("TotalTxns")))
      .withColumn("BestPercentSalesBase", col("BestTxnAmt") / col("TotalSpend"))
      .withColumn("BestAvgFreq", LifecycleUDFs.longAvgCalc(col("BestTxnCount"), col("BestCustCount")))
      .withColumn("BestAvgRecency", LifecycleUDFs.longAvgCalc(col("BestRecency"), col("BestCustCount")))
      .withColumn("BestAvgSales", LifecycleUDFs.doubleAvgCalc(col("BestTxnAmt"), col("BestCustCount")))
      .withColumn("BestAvgItems", LifecycleUDFs.longAvgCalc(col("BestItemQty"), col("BestCustCount")))
      .withColumn("BestVisitSpend", LifecycleUDFs.doubleAvgCalc(col("BestTxnAmt"), col("BestTxnCount")))

      .withColumn("RisingPercentCustBase", LifecycleUDFs.longAvgCalc(col("RisingCustCount"), col("TotalCusts")))
      .withColumn("RisingPercentTxnBase", LifecycleUDFs.longAvgCalc(col("RisingTxnCount"), col("TotalTxns")))
      .withColumn("RisingPercentSalesBase", col("RisingTxnAmt") / col("TotalSpend"))
      .withColumn("RisingAvgFreq", LifecycleUDFs.longAvgCalc(col("RisingTxnCount"), col("RisingCustCount")))
      .withColumn("RisingAvgRecency", LifecycleUDFs.longAvgCalc(col("RisingRecency"), col("RisingCustCount")))
      .withColumn("RisingAvgSales", LifecycleUDFs.doubleAvgCalc(col("RisingTxnAmt"), col("RisingCustCount")))
      .withColumn("RisingAvgItems", LifecycleUDFs.longAvgCalc(col("RisingItemQty"), col("RisingCustCount")))
      .withColumn("RisingVisitSpend", LifecycleUDFs.doubleAvgCalc(col("RisingTxnAmt"), col("RisingTxnCount")))

      .withColumn("MiddlePercentCustBase", LifecycleUDFs.longAvgCalc(col("MiddleCustCount"), col("TotalCusts")))
      .withColumn("MiddlePercentTxnBase", LifecycleUDFs.longAvgCalc(col("MiddleTxnCount"), col("TotalTxns")))
      .withColumn("MiddlePercentSalesBase", col("MiddleTxnAmt") / col("TotalSpend"))
      .withColumn("MiddleAvgFreq", LifecycleUDFs.longAvgCalc(col("MiddleTxnCount"), col("MiddleCustCount")))
      .withColumn("MiddleAvgRecency", LifecycleUDFs.longAvgCalc(col("MiddleRecency"), col("MiddleCustCount")))
      .withColumn("MiddleAvgSales", LifecycleUDFs.doubleAvgCalc(col("MiddleTxnAmt"), col("MiddleCustCount")))
      .withColumn("MiddleAvgItems", LifecycleUDFs.longAvgCalc(col("MiddleItemQty"), col("MiddleCustCount")))
      .withColumn("MiddleVisitSpend", LifecycleUDFs.doubleAvgCalc(col("MiddleTxnAmt"), col("MiddleTxnCount")))

      .withColumn("LapsingPercentCustBase", LifecycleUDFs.longAvgCalc(col("LapsingCustCount"), col("TotalCusts")))
      .withColumn("LapsingPercentTxnBase", LifecycleUDFs.longAvgCalc(col("LapsingTxnCount"), col("TotalTxns")))
      .withColumn("LapsingPercentSalesBase", col("LapsingTxnAmt") / col("TotalSpend"))
      .withColumn("LapsingAvgFreq", LifecycleUDFs.longAvgCalc(col("LapsingTxnCount"), col("LapsingCustCount")))
      .withColumn("LapsingAvgRecency", LifecycleUDFs.longAvgCalc(col("LapsingRecency"), col("LapsingCustCount")))
      .withColumn("LapsingAvgSales", LifecycleUDFs.doubleAvgCalc(col("LapsingTxnAmt"), col("LapsingCustCount")))
      .withColumn("LapsingAvgItems", LifecycleUDFs.longAvgCalc(col("LapsingItemQty"), col("LapsingCustCount")))
      .withColumn("LapsingVisitSpend", LifecycleUDFs.doubleAvgCalc(col("LapsingTxnAmt"), col("LapsingTxnCount")))

      .withColumn("DeeplyPercentCustBase", LifecycleUDFs.longAvgCalc(col("DeeplyCustCount"), col("TotalCusts")))
      .withColumn("DeeplyPercentTxnBase", LifecycleUDFs.longAvgCalc(col("DeeplyTxnCount"), col("TotalTxns")))
      .withColumn("DeeplyPercentSalesBase", col("DeeplyTxnAmt") / col("TotalSpend"))
      .withColumn("DeeplyAvgFreq", LifecycleUDFs.longAvgCalc(col("DeeplyTxnCount"), col("DeeplyCustCount")))
      .withColumn("DeeplyAvgRecency", LifecycleUDFs.longAvgCalc(col("DeeplyRecency"), col("DeeplyCustCount")))
      .withColumn("DeeplyAvgSales", LifecycleUDFs.doubleAvgCalc(col("DeeplyTxnAmt"), col("DeeplyCustCount")))
      .withColumn("DeeplyAvgItems", LifecycleUDFs.longAvgCalc(col("DeeplyItemQty"), col("DeeplyCustCount")))
      .withColumn("DeeplyVisitSpend", LifecycleUDFs.doubleAvgCalc(col("DeeplyTxnAmt"), col("DeeplyTxnCount")))

  }

  def createProfileAvro(pivotDF: DataFrame): Try[java.util.ArrayList[lifecyleProfileResults]] = Try {
    val profileList = new util.ArrayList[lifecyleProfileResults]()

    def mapToAvro(pRow: Row): Unit = {
      val pd = new lifecyleProfileResults()
      pd.setTimePeriod(pRow.getInt(0))
      pd.setTotalCustCount(pRow.getLong(1))
      pd.setTotalTxnCount(pRow.getLong(2))
      pd.setTotalSales(pRow.getDouble(3))
      pd.setTotalItems(pRow.getLong(4))

      pd.setBestCustTotal(pRow.getLong(5))
      pd.setBestTxnTotal(pRow.getLong(6))
      pd.setBestSalesTotal(pRow.getDouble(7))
      pd.setBestRecencyTotal(pRow.getLong(8))
      pd.setBestItemTotal(pRow.getLong(9))
      pd.setBestPercentCustBase(pRow.getDouble(10))
      pd.setBestPercentTxnBase(pRow.getDouble(11))
      pd.setBestPercentSalesBase(pRow.getDouble(12))
      pd.setBestAvgFreq(pRow.getDouble(13))
      pd.setBestAvgRecency(pRow.getDouble(14))
      pd.setBestAvgSales(pRow.getDouble(15))
      pd.setBestAvgItems(pRow.getDouble(16))
      pd.setBestVisitSpend(pRow.getDouble(17))

      pd.setRisingCustTotal(pRow.getLong(18))
      pd.setRisingTxnTotal(pRow.getLong(19))
      pd.setRisingSalesTotal(pRow.getDouble(20))
      pd.setRisingRecencyTotal(pRow.getLong(21))
      pd.setRisingItemTotal(pRow.getLong(22))
      pd.setRisingPercentCustBase(pRow.getDouble(23))
      pd.setRisingPercentTxnBase(pRow.getDouble(24))
      pd.setRisingPercentSalesBase(pRow.getDouble(25))
      pd.setRisingAvgFreq(pRow.getDouble(26))
      pd.setRisingAvgRecency(pRow.getDouble(27))
      pd.setRisingAvgSales(pRow.getDouble(28))
      pd.setRisingAvgItems(pRow.getDouble(29))
      pd.setRisingVisitSpend(pRow.getDouble(30))

      pd.setMiddleCustTotal(pRow.getLong(31))
      pd.setMiddleTxnTotal(pRow.getLong(32))
      pd.setMiddleSalesTotal(pRow.getDouble(33))
      pd.setMiddleRecencyTotal(pRow.getLong(34))
      pd.setMiddleItemTotal(pRow.getLong(35))
      pd.setMiddlePercentCustBase(pRow.getDouble(36))
      pd.setMiddlePercentTxnBase(pRow.getDouble(37))
      pd.setMiddlePercentSalesBase(pRow.getDouble(38))
      pd.setMiddleAvgFreq(pRow.getDouble(39))
      pd.setMiddleAvgRecency(pRow.getDouble(40))
      pd.setMiddleAvgSales(pRow.getDouble(41))
      pd.setMiddleAvgItems(pRow.getDouble(42))
      pd.setMiddleVisitSpend(pRow.getDouble(43))

      pd.setLapsingCustTotal(pRow.getLong(44))
      pd.setLapsingTxnTotal(pRow.getLong(45))
      pd.setLapsingSalesTotal(pRow.getDouble(46))
      pd.setLapsingRecencyTotal(pRow.getLong(47))
      pd.setLapsingItemTotal(pRow.getLong(48))
      pd.setLapsingPercentCustBase(pRow.getDouble(49))
      pd.setLapsingPercentTxnBase(pRow.getDouble(50))
      pd.setLapsingPercentSalesBase(pRow.getDouble(51))
      pd.setLapsingAvgFreq(pRow.getDouble(52))
      pd.setLapsingAvgRecency(pRow.getDouble(53))
      pd.setLapsingAvgSales(pRow.getDouble(54))
      pd.setLapsingAvgItems(pRow.getDouble(55))
      pd.setLapsingVisitSpend(pRow.getDouble(56))

      pd.setDeeplyCustTotal(pRow.getLong(57))
      pd.setDeeplyTxnTotal(pRow.getLong(58))
      pd.setDeeplySalesTotal(pRow.getDouble(59))
      pd.setDeeplyRecencyTotal(pRow.getLong(60))
      pd.setDeeplyItemTotal(pRow.getLong(61))
      pd.setDeeplyPercentCustBase(pRow.getDouble(62))
      pd.setDeeplyPercentTxnBase(pRow.getDouble(63))
      pd.setDeeplyPercentSalesBase(pRow.getDouble(64))
      pd.setDeeplyAvgFreq(pRow.getDouble(65))
      pd.setDeeplyAvgRecency(pRow.getDouble(66))
      pd.setDeeplyAvgSales(pRow.getDouble(67))
      pd.setDeeplyAvgItems(pRow.getDouble(68))
      pd.setDeeplyVisitSpend(pRow.getDouble(69))

      profileList.add(pd)

    }

    val orderCols = pivotDF
      .select("TimePeriod", "TotalCusts", "TotalTxns", "TotalSpend", "TotalItems",
        "BestCustCount", "BestTxnCount", "BestTxnAmt",  "BestRecency","BestItemQty",
        "BestPercentCustBase", "BestPercentTxnBase", "BestPercentSalesBase", "BestAvgFreq", "BestAvgRecency", "BestAvgSales", "BestAvgItems", "BestVisitSpend",
        "RisingCustCount", "RisingTxnCount", "RisingTxnAmt", "RisingRecency","RisingItemQty",
        "RisingPercentCustBase", "RisingPercentTxnBase", "RisingPercentSalesBase", "RisingAvgFreq", "RisingAvgRecency", "RisingAvgSales", "RisingAvgItems", "RisingVisitSpend",
        "MiddleCustCount", "MiddleTxnCount", "MiddleTxnAmt", "MiddleRecency","MiddleItemQty",
        "MiddlePercentCustBase", "MiddlePercentTxnBase", "MiddlePercentSalesBase", "MiddleAvgFreq", "MiddleAvgRecency", "MiddleAvgSales", "MiddleAvgItems", "MiddleVisitSpend",
        "LapsingCustCount", "LapsingTxnCount", "LapsingTxnAmt",  "LapsingRecency","LapsingItemQty",
        "LapsingPercentCustBase", "LapsingPercentTxnBase", "LapsingPercentSalesBase", "LapsingAvgFreq", "LapsingAvgRecency", "LapsingAvgSales", "LapsingAvgItems", "LapsingVisitSpend",
        "DeeplyCustCount", "DeeplyTxnCount", "DeeplyTxnAmt", "DeeplyRecency","DeeplyItemQty",
        "DeeplyPercentCustBase", "DeeplyPercentTxnBase", "DeeplyPercentSalesBase", "DeeplyAvgFreq", "DeeplyAvgRecency", "DeeplyAvgSales", "DeeplyAvgItems", "DeeplyVisitSpend")

    orderCols.collect().foreach(x => mapToAvro(x))

    println(s"profile avro output: $profileList")
    (profileList)
  }


  def tpAggProd(tpDF: DataFrame, columnName: String): Try[DataFrame] = Try {
    val totalDF = tpDF
      .groupBy("TimePeriod")
      .agg(
        sum("ITEM_AMT").alias("tpTotalSales")
      )

    val aggProdDF = tpDF
      .groupBy("TimePeriod", columnName)
      .agg(
        sum("ITEM_AMT").alias("tpItemAmt")
      )

    aggProdDF.join(totalDF, Seq("TimePeriod"))

  }

  def segAggProd(tpDF: DataFrame, segDF: DataFrame, columnName: String): Try[DataFrame] = Try {
    val selectedDF = segDF.select("TimePeriod", "CUST_ID", "Segment")

    val joinedDF = tpDF.join(selectedDF, Seq("TimePeriod", "CUST_ID"))

    val totalSales = joinedDF
      .groupBy("TimePeriod")
      .pivot("Segment", Seq("Best in Class", "Rising Stars", "Middle of the Road", "Lapsing", "Deeply Lapsed"))
      .agg(
        sum("ITEM_AMT")
      )
      .withColumnRenamed("Best in Class", "BestTotalSales")
      .withColumnRenamed("Rising Stars", "RisingTotalSales")
      .withColumnRenamed("Middle of the Road", "MiddleTotalSales")
      .withColumnRenamed("Lapsing", "LapsingTotalSales")
      .withColumnRenamed("Deeply Lapsed", "DeeplyTotalSales")

    val pivotDF = joinedDF
      .groupBy("TimePeriod", columnName)
      .pivot("Segment", Seq("Best in Class", "Rising Stars", "Middle of the Road", "Lapsing", "Deeply Lapsed"))
      .agg(
        sum("ITEM_AMT")
      )
      .withColumnRenamed("Best in Class", "BestItemAmt")
      .withColumnRenamed("Rising Stars", "RisingItemAmt")
      .withColumnRenamed("Middle of the Road", "MiddleItemAmt")
      .withColumnRenamed("Lapsing", "LapsingItemAmt")
      .withColumnRenamed("Deeply Lapsed", "DeeplyItemAmt")

    val rejoined = pivotDF.join(totalSales, Seq("TimePeriod"))
      .na.fill(0)

    rejoined

  }

  def indexProd(tpAggDF: DataFrame, segAggDF: DataFrame, columnName: String): Try[DataFrame] = Try {
    val joinedDF = segAggDF.join(tpAggDF, Seq("TimePeriod", columnName))

    val percentDF = joinedDF
      .withColumn("BestPercentSales", col("BestItemAmt") / col("BestTotalSales") * 100)
      .withColumn("RisingPercentSales", col("RisingItemAmt") / col("RisingTotalSales") * 100)
      .withColumn("MiddlePercentSales", col("MiddleItemAmt") / col("MiddleTotalSales") * 100)
      .withColumn("LapsingPercentSales", col("LapsingItemAmt") / col("LapsingTotalSales") * 100)
      .withColumn("DeeplyPercentSales", col("DeeplyItemAmt") / col("DeeplyTotalSales") * 100)
      .withColumn("ProdPercentSales", col("tpItemAmt") / col("tpTotalSales") * 100)
      .withColumn("BestIndex", col("BestPercentSales") - col("ProdPercentSales"))
      .withColumn("RisingIndex", col("RisingPercentSales") - col("ProdPercentSales"))
      .withColumn("MiddleIndex", col("MiddlePercentSales") - col("ProdPercentSales"))
      .withColumn("LapsingIndex", col("LapsingPercentSales") - col("ProdPercentSales"))
      .withColumn("DeeplyIndex", col("DeeplyPercentSales") - col("ProdPercentSales"))

    percentDF
  }

  def createProdAvro(calcDF: DataFrame, columnName: String): Try[java.util.ArrayList[lifecycleProdMetrics]] = Try {

    val prodList = new util.ArrayList[lifecycleProdMetrics]()

    def mapAvro(pRow: Row): Unit = {
      val p = new lifecycleProdMetrics()
      p.setTimePeriod(pRow.getInt(0))
      p.setProductCat(pRow.getString(1))
      p.setProdTotalSales(pRow.getDouble(2))
      p.setProdPercentSales(pRow.getDouble(3))
      p.setTotalSales(pRow.getDouble(4))
      p.setBestProdSales(pRow.getDouble(5))
      p.setBestTotalSales(pRow.getDouble(6))
      p.setBestPercentSales(pRow.getDouble(7))
      p.setBestIndex(pRow.getDouble(8))
      p.setRisingProdSales(pRow.getDouble(9))
      p.setRisingTotalSales(pRow.getDouble(10))
      p.setRisingPercentSales(pRow.getDouble(11))
      p.setRisingIndex(pRow.getDouble(12))
      p.setMiddleProdSales(pRow.getDouble(13))
      p.setMiddleTotalSales(pRow.getDouble(14))
      p.setMiddlePercentSales(pRow.getDouble(15))
      p.setMiddleIndex(pRow.getDouble(16))
      p.setLapsingProdSales(pRow.getDouble(17))
      p.setLapsingTotalSales(pRow.getDouble(18))
      p.setLapsingPercentSales(pRow.getDouble(19))
      p.setLapsingIndex(pRow.getDouble(20))
      p.setDeeplyProdSales(pRow.getDouble(21))
      p.setDeeplyTotalSales(pRow.getDouble(22))
      p.setDeeplyPercentSales(pRow.getDouble(23))
      p.setDeeplyIndex(pRow.getDouble(24))

      prodList.add(p)

    }

    val orderCols = calcDF
      .select("TimePeriod", columnName, "tpItemAmt", "ProdPercentSales", "tpTotalSales",
        "BestItemAmt", "BestTotalSales", "BestPercentSales", "BestIndex",
        "RisingItemAmt", "RisingTotalSales", "RisingPercentSales", "RisingIndex",
        "MiddleItemAmt", "MiddleTotalSales", "MiddlePercentSales", "MiddleIndex",
        "LapsingItemAmt", "LapsingTotalSales", "LapsingPercentSales", "LapsingIndex",
        "DeeplyItemAmt", "DeeplyTotalSales", "DeeplyPercentSales", "DeeplyIndex")

    orderCols.collect().foreach(x => mapAvro(x))

    println(s"Product avro output: $prodList")


    (prodList)
  }


  def createMigrationDF(segDF: DataFrame): Try[DataFrame] = Try {

    val baseDF = segDF.select("TimePeriod", "CUST_ID", "Segment")
      .withColumnRenamed("Segment", "CurrSeg")

    val TPWindow = Window.partitionBy("CUST_ID").orderBy(col("TimePeriod").desc)

    val missingTPDF = baseDF
      .drop("CurrSeg")
      .withColumn("TPDiff", baseDF("TimePeriod") - lag("TimePeriod", 1).over(TPWindow))
      .filter((col("TPDiff") > 1) || col("TPDiff").isNull && baseDF("TimePeriod") > 1)
      .withColumn("MissingTP", baseDF("TimePeriod") - 1)
      .drop("TimePeriod")
      .withColumnRenamed("MissingTP", "TimePeriod")
      .withColumn("CurrSeg", lit(null))
      .select("TimePeriod", "CUST_ID", "CurrSeg")

    val unionDF = baseDF.unionAll(missingTPDF)

    unionDF.withColumn("PrevSeg", lead("CurrSeg", 1).over(TPWindow))
      .filter(unionDF("CurrSeg") isNotNull)

  }

  def countMigTotals(migDF: DataFrame): Try[DataFrame] = Try {

    val maxTP = migDF.select(max("TimePeriod")).head().getInt(0)

    //counts all new customers not seen in previous TP, excludes TP 1 since it is the first TP
    val newCountDF = migDF
      .filter(migDF("PrevSeg") isNull)
      .filter(migDF("TimePeriod") > 1)
      .drop("PrevSeg")
      .groupBy("TimePeriod", "CurrSeg")
      .agg(count("CUST_ID").alias("NewCount"))

    //counts all customers in all TimePeriods
    val allCountDF = migDF
      .drop("PrevSeg")
      .groupBy("TimePeriod", "CurrSeg")
      .agg(count("CUST_ID").alias("AllCount"))

    val joinedDF = allCountDF.join(newCountDF, Seq("TimePeriod", "CurrSeg"), "leftouter")
      .na.fill(0)

    joinedDF
      .groupBy("TimePeriod")
      .pivot("CurrSeg", Seq("Best in Class", "Rising Stars", "Middle of the Road", "Lapsing", "Deeply Lapsed"))
      .agg(
        sum("NewCount"),
        sum("AllCount")
      )
      .na.fill(0)

  }

  def sumMigrations(migDF: DataFrame, sqlCtx: HiveContext, sc: SparkContext): Try[DataFrame] = Try {
    import sqlCtx.implicits._

    val segList = List("Best in Class", "Rising Stars", "Middle of the Road", "Lapsing", "Deeply Lapsed")

    val currSegDF = sc.parallelize(segList).toDF("CurrSeg")
    val prevSegDF = sc.parallelize(segList).toDF("PrevSeg")
    val TPs = migDF.select("TimePeriod").distinct()

    val matrixDF = TPs.join(currSegDF).join(prevSegDF)

    val sumDF = migDF
      .na.drop()
      .groupBy("TimePeriod", "CurrSeg", "PrevSeg")
      .agg(
        count("CUST_ID").alias("Count")
      )

    val endDF = matrixDF.join(sumDF, Seq("TimePeriod", "CurrSeg", "PrevSeg"), "Left_outer")
      .na.fill(0)

    endDF
  }

  def createMigrationAvro(sumDF: DataFrame, countDF: DataFrame): Try[java.util.ArrayList[lifecycleMigrationResults]] = Try {
    val migList = new util.ArrayList[lifecycleMigrationResults]()
    var innerMigArray = new util.ArrayList[migrationArray]()
    var timePeriod = 1

    def mapCounts(cRow: Row): Unit = {
      val ttlList = new util.ArrayList[segmentCounts]()
      val ttls = new segmentCounts()
      ttls.setBestNewCount(cRow.getLong(1))
      ttls.setBestTotalCount(cRow.getLong(2))
      ttls.setRisingNewCount(cRow.getLong(3))
      ttls.setRisingTotalCount(cRow.getLong(4))
      ttls.setMiddleNewCount(cRow.getLong(5))
      ttls.setMiddleTotalCount(cRow.getLong(6))
      ttls.setLapsingNewCount(cRow.getLong(7))
      ttls.setLapsingTotalCount(cRow.getLong(8))
      ttls.setDeeplyNewCount(cRow.getLong(9))
      ttls.setDeeplyTotalCount(cRow.getLong(10))

      ttlList.add(ttls)
      migList.get(cRow.getInt(0) - 1).setSegmentTotals(ttlList)
    }

    def setInnerMigArray(row: Row): Unit = {
      val ma = new migrationArray()
      ma.setCurrentSegment(row.getString(1))
      ma.setFromSegment(row.getString(2))
      ma.setMigrationCount(row.getLong(3))
      innerMigArray.add(ma)
    }

    def mapMigrations(mRow: Row): Unit = {
      if (migList.isEmpty) {
        val lmr = new lifecycleMigrationResults()
        lmr.setTimePeriod(mRow.getInt(0))

        migList.add(lmr)

        setInnerMigArray(mRow)
      } else if (mRow.getInt(0) == migList.get(migList.size() - 1).getTimePeriod) {
        setInnerMigArray(mRow)
      } else {
        migList.get(migList.size() - 1).setMigrationData(innerMigArray)

        innerMigArray = new util.ArrayList[migrationArray]()
        val lmr = new lifecycleMigrationResults()
        lmr.setTimePeriod(mRow.getInt(0))
        migList.add(lmr)

        setInnerMigArray(mRow)
      }

    }

    sumDF.sort("TimePeriod").collect().foreach(f => mapMigrations(f))
    migList.get(migList.size() - 1).setMigrationData(innerMigArray)
    countDF.sort("TimePeriod").collect().foreach(g => mapCounts(g))

    migList
  }


  def dfStats(tpDF: DataFrame): Try[DataFrame] = Try {
    tpDF
      .select(min("Date"), max("Date"))
      .withColumn("TXN_COUNT", lit(tpDF.count))
  }

  def createLifecycleAvro(profAvro: util.ArrayList[lifecyleProfileResults], prodAvro: util.ArrayList[lifecycleProdMetrics],
                          migAvro: util.ArrayList[lifecycleMigrationResults], statDF: DataFrame, jobKey: String, tpLength: Int): Try[LifecycleResults] = Try {

    val stats = statDF.head()
    val lifeAvro = new LifecycleResults()

    lifeAvro.setJobKey(jobKey)
    lifeAvro.setMinDate(stats.getDate(0).toString)
    lifeAvro.setMaxDate(stats.getDate(1).toString)
    lifeAvro.setNumRecords(stats.getLong(2))
    lifeAvro.setTimePeriodLength(tpLength)
    lifeAvro.setMigrationResults(migAvro)
    lifeAvro.setProdMetrics(prodAvro)
    lifeAvro.setLifecycleResults(profAvro)


    lifeAvro.setCompletionTime(LocalDateTime.now().toString)

    lifeAvro
  }

  def sendLifecycleError(jobKey: String, className: String, methodName: String, msg: String, extype: String, propsList: RDD[(String, String)]): Unit = {
    val error = new Error()
    error.setJobKey(jobKey)
    error.setJobType("Lifecycle")

    val ex = new exception()
    ex.setClassName(className)
    ex.setMethodName(methodName)
    ex.setExceptionMsg(msg)
    ex.setExceptionType(extype)

    val tempList = new util.ArrayList[exception]
    tempList.add(ex)
    error.setErrorInfo(tempList)

    KafkaProducer.sendError(error, propsList)
  }


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

    if (args.length != 5) {
      //sendError
      System.exit(-1)
    }

    val fileLocation = args(0)
    val delimiter = args(1)
    val jobKey = args(2)
    val timePeriod = args(3).toInt
    val columnName = args(4)

    val validTP = List(0, 3, 6, 12)

    if (!validTP.contains(timePeriod)) {
      println(s"Invalid timeperiod length submitted, must be: 0, 3, 6, or 12.  you entered $timePeriod")
      //sendError
      System.exit(-1)
    }

    val avro = for {
      orgFile <- loadFile(sqlCtx, delimiter, fileLocation, columnName)

      dateDF <- DateUtils.determineFormat(orgFile)

      tpDF <- calcTimePeriod(dateDF, timePeriod)

      aggDF <- custAgg(tpDF)

      rfmDF <- calcRFM(aggDF)

      segDF <- groupRFM(rfmDF)

      globalDF <- calcGlobalTotals(segDF)

      segAggDF <- segmentAgg(segDF)

      percentDF <- calcPercentages(segAggDF, globalDF)

      profAvro <- createProfileAvro(percentDF)

      tpAggDF <- tpAggProd(tpDF, columnName)

      segProdAggDF <- segAggProd(tpDF, segDF, columnName)

      indexDF <- indexProd(tpAggDF, segProdAggDF, columnName)

      prodAvro <- createProdAvro(indexDF, columnName)

      migDF <- createMigrationDF(segDF)

      migCountDF <- countMigTotals(migDF)

      sumMigDF <- sumMigrations(migDF, sqlCtx, sc)

      migAvro <- createMigrationAvro(sumMigDF, migCountDF)

      statDF <- dfStats(tpDF)

      lifecycleAvro <- createLifecycleAvro(profAvro, prodAvro, migAvro, statDF, jobKey, timePeriod)

    } yield lifecycleAvro

    avro match {
      case Success(avro) => {
        println(s"Lifecycle calculations were successful: $avro")
        KafkaProducer.sendSucess("lifecycle", propsOnly, avro)
        sc.stop()
      }
      case Failure(ex) => {
        println(s"Lifecycle had an error: $ex")
        sendLifecycleError(jobKey, "Lifecycle", "unknown", ex.toString, "System", propsOnly)
        sc.stop()
        System.exit(-1)
      }
    }

  }

}
