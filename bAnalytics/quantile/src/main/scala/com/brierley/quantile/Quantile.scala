package com.brierley.quantile

import java.time.LocalDateTime
import java.util

import com.brierley.avro.schemas._
import com.brierley.general.utils.DateUtils
import com.brierley.kafka.producer.KafkaProducer
import com.brierley.quantile.QuantileUDFs._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions.{count, _}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.storage.StorageLevel

import scala.util.{Failure, Success, Try}

/**
  * Created by amerrill on 6/8/17.
  */
object Quantile {

  def loadFile(sqlCtx: HiveContext, args: Array[String]): Try[DataFrame] = Try {

    val orgFile = sqlCtx
      .read.format("com.databricks.spark.csv")
      .option("header", "true")
      .option("delimiter", args(1))
      .load(args(0))

    val cols = orgFile.columns

    if (cols.contains("DISC_AMT")) {
      args.length match {
        case 8 => orgFile.select("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", args(7))
          .withColumn("ITEM_QTY", orgFile("ITEM_QTY").cast(IntegerType))
          .withColumnRenamed(args(7), "Level1")
          .select("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level1")
        case 9 => orgFile.select("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", args(7), args(8))
          .withColumn("ITEM_QTY", orgFile("ITEM_QTY").cast(IntegerType))
          .withColumnRenamed(args(7), "Level1")
          .withColumnRenamed(args(8), "Level2")
          .select("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level1", "Level2")
        case 10 => orgFile.select("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", args(7), args(8), args(9))
          .withColumn("ITEM_QTY", orgFile("ITEM_QTY").cast(IntegerType))
          .withColumnRenamed(args(7), "Level1")
          .withColumnRenamed(args(8), "Level2")
          .withColumnRenamed(args(9), "Level3")
          .select("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level1", "Level2", "Level3")
      }
    } else {
      args.length match {
        case 8 => orgFile.select("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", args(7))
          .withColumn("ITEM_QTY", orgFile("ITEM_QTY").cast(IntegerType))
          .withColumn("DISC_AMT", lit(0.toDouble))
          .withColumnRenamed(args(7), "Level1")
          .select("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level1")
        case 9 => orgFile.select("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", args(7), args(8))
          .withColumn("ITEM_QTY", orgFile("ITEM_QTY").cast(IntegerType))
          .withColumn("DISC_AMT", lit(0.toDouble))
          .withColumnRenamed(args(7), "Level1")
          .withColumnRenamed(args(8), "Level2")
          .select("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level1", "Level2")
        case 10 => orgFile.select("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", args(7), args(8), args(9))
          .withColumn("ITEM_QTY", orgFile("ITEM_QTY").cast(IntegerType))
          .withColumn("DISC_AMT", lit(0.toDouble))
          .withColumnRenamed(args(7), "Level1")
          .withColumnRenamed(args(8), "Level2")
          .withColumnRenamed(args(9), "Level3")
          .select("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level1", "Level2", "Level3")
      }
    }

  }

  def calcTimePeriod(dateDF: DataFrame, tp: Int): Try[DataFrame] = Try {
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
        .withColumn("period", lit(tp))
        .withColumn("TimePeriod", periodCalc(col("max(Date)"), trimDF("Date"), col("period")))
        .sort("TimePeriod")
        .drop(col("period"))
        .drop(col("max(Date)"))
        .withColumn("AnchorDate", min("Date").over(Window.partitionBy("TimePeriod")))

      //result.persist(StorageLevel.MEMORY_AND_DISK)
      (result)
    } else {
      val result = trimDF
        .select("*")
        .withColumn("max(Date)", lit(maxDate))
        .withColumn("Month", month(dateDF("Date")))
        .withColumn("Year", year(dateDF("Date")))
        .withColumn("MaxMonth", month(col("max(Date)")))
        .withColumn("MaxYear", year(col("max(Date)")))
        .withColumn("TimePeriod", (((col("MaxMonth") - col("Month")) + (col("MaxYear") - col("Year")) * 12) / tp).cast(IntegerType) + 1)
        .sort("TimePeriod")
        .drop(col("Month"))
        .drop(col("Year"))
        .drop(col("MaxMonth"))
        .drop(col("MaxYear"))
        .drop(col("max(Date)"))
        .withColumn("AnchorDate", min("Date").over(Window.partitionBy("TimePeriod")))
      //result.persist(StorageLevel.MEMORY_AND_DISK)
      (result)
    }

  }

  def dfStats(tpDF: DataFrame): Try[DataFrame] = Try {
    tpDF
      .select(min("Date"), max("Date"))
      .withColumn("TXN_COUNT", lit(tpDF.count()))
  }

  def baseAgg(tpDF: DataFrame, dimension: String): Try[DataFrame] = Try {
    dimension match {
      case "store" => tpDF.groupBy("TimePeriod", "AnchorDate", "STORE_ID")
        .agg(countDistinct("CUST_ID").alias("CUST_COUNT"),
          countDistinct("TXN_HEADER_ID").alias("TXN_COUNT"),
          sum("ITEM_QTY").alias("ITEM_QTY"),
          sum("ITEM_AMT").alias("ITEM_AMT"),
          sum("DISC_AMT").alias("DISC_AMT"))
      case "customer" => tpDF.groupBy("TimePeriod", "AnchorDate", "CUST_ID")
        .agg(countDistinct("TXN_HEADER_ID").alias("TXN_COUNT"),
          sum("ITEM_QTY").alias("ITEM_QTY"),
          sum("ITEM_AMT").alias("ITEM_AMT"),
          sum("DISC_AMT").alias("DISC_AMT"))
    }

  }

  def quantileSpend(aggDF: DataFrame, quant: Double): Try[DataFrame] = Try {
    val rankWindow = Window
      .partitionBy("TimePeriod", "AnchorDate")
      .orderBy(col("ITEM_AMT").desc)

    val result = aggDF.select("*")
      .withColumn("PerRank", percent_rank().over(rankWindow))
      .withColumn("Quant", lit(quant))
      .withColumn("Quantile", calcQuant(col("PerRank"), col("Quant")))
      .drop("PerRank")
      .drop("Quant")

    //result.persist(StorageLevel.MEMORY_AND_DISK)
    (result)

  }

  def aggQuantile(quantDF: DataFrame, dimension: String): Try[DataFrame] = Try {
    dimension match {
      case "store" => quantDF.groupBy("TimePeriod", "AnchorDate", "Quantile")
        .agg(count("STORE_ID").alias("STORE_COUNT"),
          sum("CUST_COUNT").alias("CUST_COUNT"),
          sum("TXN_COUNT").alias("TXN_COUNT"),
          sum("ITEM_QTY").alias("ITEM_QTY"),
          sum("ITEM_AMT").alias("ITEM_AMT"),
          sum("DISC_AMT").alias("DISC_AMT"))
      case "customer" => quantDF.groupBy("TimePeriod", "AnchorDate", "Quantile")
        .agg(count("CUST_ID").alias("CUST_COUNT"),
          sum("TXN_COUNT").alias("TXN_COUNT"),
          sum("ITEM_QTY").alias("ITEM_QTY"),
          sum("ITEM_AMT").alias("ITEM_AMT"),
          sum("DISC_AMT").alias("DISC_AMT"))
    }
  }

  def calcAvg(aggQuantDF: DataFrame, dimension: String): Try[DataFrame] = Try {
    dimension match {
      case "store" => aggQuantDF
        .withColumn("AvgDisc", aggQuantDF("DISC_AMT") / aggQuantDF("STORE_COUNT"))
        .withColumn("AvgSpend", aggQuantDF("ITEM_AMT") / aggQuantDF("STORE_COUNT"))
        .withColumn("AvgItems", aggQuantDF("ITEM_QTY") / aggQuantDF("STORE_COUNT"))
        .withColumn("AvgVisits", aggQuantDF("TXN_COUNT") / aggQuantDF("STORE_COUNT"))
        .withColumn("AvgCust", aggQuantDF("CUST_COUNT") / aggQuantDF("STORE_COUNT"))
        .withColumn("AvgTripSpend", aggQuantDF("ITEM_AMT") / aggQuantDF("TXN_COUNT"))
        .withColumn("AvgTripItems", aggQuantDF("ITEM_QTY") / aggQuantDF("TXN_COUNT"))
        .withColumn("AvgTripDisc", aggQuantDF("DISC_AMT") / aggQuantDF("TXN_COUNT"))
        .withColumn("AvgCustSpend", aggQuantDF("ITEM_AMT") / aggQuantDF("CUST_COUNT"))
        .withColumn("AvgCustVisits", aggQuantDF("TXN_COUNT") / aggQuantDF("CUST_COUNT"))
        .withColumn("AvgCustItems", aggQuantDF("ITEM_QTY") / aggQuantDF("CUST_COUNT"))

      case "customer" => aggQuantDF
        .withColumn("AvgDisc", aggQuantDF("DISC_AMT") / aggQuantDF("CUST_COUNT"))
        .withColumn("AvgSpend", aggQuantDF("ITEM_AMT") / aggQuantDF("CUST_COUNT"))
        .withColumn("AvgItems", aggQuantDF("ITEM_QTY") / aggQuantDF("CUST_COUNT"))
        .withColumn("AvgVisits", aggQuantDF("TXN_COUNT") / aggQuantDF("CUST_COUNT"))
        .withColumn("AvgTripSpend", aggQuantDF("ITEM_AMT") / aggQuantDF("TXN_COUNT"))
        .withColumn("AvgTripItems", aggQuantDF("ITEM_QTY") / aggQuantDF("TXN_COUNT"))
        .withColumn("AvgTripDisc", aggQuantDF("DISC_AMT") / aggQuantDF("TXN_COUNT"))
        .withColumn("AvgItemSpend", aggQuantDF("ITEM_AMT") / aggQuantDF("ITEM_QTY"))
        .withColumn("AvgItemDisc", aggQuantDF("DISC_AMT") / aggQuantDF("ITEM_QTY"))
    }
  }

  def createProfileAvro(avgDF: DataFrame, dimension: String): Try[java.util.ArrayList[quantilePeriodResults]] = Try {

    val profileList = new util.ArrayList[quantilePeriodResults]()

    def mapStoreProfileData(pdRow: Row): Unit = {
      val pd = new quantilePeriodResults()
      pd.setTimePeriod(pdRow.getInt(0))
      pd.setQuantile(pdRow.getInt(1))
      pd.setTotalCount(pdRow.getLong(2))
      pd.setTotalCust(pdRow.getLong(3))
      pd.setTotalVisits(pdRow.getLong(4))
      pd.setTotalUnits(pdRow.getLong(5))
      pd.setTotalSpend(pdRow.getDouble(6))
      pd.setTotalDisc(pdRow.getDouble(7))

      pd.setAvgDisc(pdRow.getDouble(8))
      pd.setAvgSpend(pdRow.getDouble(9))
      pd.setAvgUnits(pdRow.getDouble(10))
      pd.setAvgVisits(pdRow.getDouble(11))
      pd.setAvgStoreCust(pdRow.getDouble(12))
      pd.setAvgVisitSpend(pdRow.getDouble(13))
      pd.setAvgVisitUnits(pdRow.getDouble(14))
      pd.setAvgVisitDisc(pdRow.getDouble(15))
      pd.setAvgCustSales(pdRow.getDouble(16))
      pd.setAvgCustVisits(pdRow.getDouble(17))
      pd.setAvgCustUnits(pdRow.getDouble(18))

      pd.setAnchorDate(pdRow.getString(19))

      profileList.add(pd)
    }

    def mapCustProfileData(pdRow: Row): Unit = {
      val pd = new quantilePeriodResults()
      pd.setTimePeriod(pdRow.getInt(0))
      pd.setQuantile(pdRow.getInt(1))
      pd.setTotalCount(pdRow.getLong(2))
      pd.setTotalVisits(pdRow.getLong(3))
      pd.setTotalUnits(pdRow.getLong(4))
      pd.setTotalSpend(pdRow.getDouble(5))
      pd.setTotalDisc(pdRow.getDouble(6))

      pd.setAvgDisc(pdRow.getDouble(7))
      pd.setAvgSpend(pdRow.getDouble(8))
      pd.setAvgUnits(pdRow.getDouble(9))
      pd.setAvgVisits(pdRow.getDouble(10))
      pd.setAvgVisitSpend(pdRow.getDouble(11))
      pd.setAvgVisitUnits(pdRow.getDouble(12))
      pd.setAvgVisitDisc(pdRow.getDouble(13))
      pd.setAvgItemSales(pdRow.getDouble(14))
      pd.setAvgItemDisc(pdRow.getDouble(15))

      pd.setAnchorDate(pdRow.getString(16))

      profileList.add(pd)
    }

    val invertedDF = avgDF
      .withColumn("TP", dense_rank().over(Window.orderBy(col("TimePeriod").desc)))
      .drop("TimePeriod")
      .withColumnRenamed("TP", "TimePeriod")
      .withColumn("StringAnchor", stringDate(avgDF("AnchorDate")))

    invertedDF.show()

    dimension match {
      case "store" => invertedDF
        .select("TimePeriod", "Quantile", "STORE_COUNT", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "AvgDisc", "AvgSpend",
          "AvgItems", "AvgVisits", "AvgCust", "AvgTripSpend", "AvgTripItems", "AvgTripDisc", "AvgCustSpend", "AvgCustVisits", "AvgCustItems", "StringAnchor")
        .collect().foreach(x => mapStoreProfileData(x))
      case "customer" => invertedDF
        .select("TimePeriod", "Quantile", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "AvgDisc", "AvgSpend",
          "AvgItems", "AvgVisits", "AvgTripSpend", "AvgTripItems", "AvgTripDisc", "AvgItemSpend", "AvgItemDisc", "StringAnchor")
        .collect().foreach(x => mapCustProfileData(x))
    }

    (profileList)
  }


  def createProductDF(tpDF: DataFrame, quantDF: DataFrame, dimension: String): Try[DataFrame] = Try {

    dimension match {
      case "store" => {
        val simpleQuant = quantDF.select("TimePeriod", "STORE_ID", "Quantile")
        val joinedDF = tpDF.join(simpleQuant, Seq("TimePeriod", "STORE_ID"))
        joinedDF.drop("CUST_ID")
          .drop("TXN_DATE")
          .drop("TXN_HEADER_ID")
          .drop("TXN_DETAIL_ID")
          .drop("DISC_AMT")
          .drop("Date")
      }
      case "customer" => {
        val simpleQuant = quantDF.select("TimePeriod", "CUST_ID", "Quantile")
        val joinedDF = tpDF.join(simpleQuant, Seq("TimePeriod", "CUST_ID"))
        joinedDF.drop("STORE_ID")
          .drop("TXN_DATE")
          .drop("TXN_HEADER_ID")
          .drop("TXN_DETAIL_ID")
          .drop("DISC_AMT")
          .drop("Date")
      }
    }
  }

  def aggProducts(prodDF: DataFrame): Try[DataFrame] = Try {
    val colNames = prodDF.columns
    if (colNames.contains("STORE_ID"))
      prodDF.drop("STORE_ID")
    if (colNames.contains("CUST_ID"))
      prodDF.drop("CUST_ID")

    if (colNames.contains("Level3")) {
      val level3Agg = prodDF.groupBy("TimePeriod", "AnchorDate", "Quantile", "Level3")
        .agg(sum("ITEM_QTY").alias("Qty"), sum("ITEM_AMT").alias("Amt"))
        .withColumn("Type", lit("Level3"))
      val level2Agg = prodDF.groupBy("TimePeriod", "AnchorDate", "Quantile", "Level2")
        .agg(sum("ITEM_QTY").alias("Qty"), sum("ITEM_AMT").alias("Amt"))
        .withColumn("Type", lit("Level2"))
      val level1Agg = prodDF.groupBy("TimePeriod", "AnchorDate", "Quantile", "Level1")
        .agg(sum("ITEM_QTY").alias("Qty"), sum("ITEM_AMT").alias("Amt"))
        .withColumn("Type", lit("Level1"))
        .withColumnRenamed("Level1", "Descr")
      val firstUnion = level1Agg.unionAll(level2Agg)
      val result = firstUnion.unionAll(level3Agg)
      result.persist(StorageLevel.MEMORY_AND_DISK)
      (result)
    }
    else if (colNames.contains("Level2")) {
      val level2Agg = prodDF.groupBy("TimePeriod", "AnchorDate", "Quantile", "Level2")
        .agg(sum("ITEM_QTY").alias("Qty"), sum("ITEM_AMT").alias("Amt"))
        .withColumn("Type", lit("Level2"))

      val level1Agg = prodDF.groupBy("TimePeriod", "AnchorDate", "Quantile", "Level1")
        .agg(sum("ITEM_QTY").alias("Qty"), sum("ITEM_AMT").alias("Amt"))
        .withColumn("Type", lit("Level1"))
        .withColumnRenamed("Level1", "Descr")

      val result = level1Agg.unionAll(level2Agg)
      result.persist(StorageLevel.MEMORY_AND_DISK)
      (result)
    }
    else {
      val result = prodDF.groupBy("TimePeriod", "AnchorDate", "Quantile", "Level1")
        .agg(sum("ITEM_QTY").alias("Qty"), sum("ITEM_AMT").alias("Amt"))
        .withColumn("Type", lit("Level1"))
        .withColumnRenamed("Level1", "Descr")

      result.persist(StorageLevel.MEMORY_AND_DISK)
      (result)
    }

  }

  def spendProds(aggProdDF: DataFrame, num: Int): Try[DataFrame] = Try {
    val spendWindow = Window.partitionBy("TimePeriod", "AnchorDate", "Quantile", "Type")
      .orderBy(desc("Amt"))

    val rankDF = aggProdDF.select("*")
      .drop("Qty")
      .withColumn("Rank", rank.over(spendWindow))
      .withColumn("RowNum", row_number().over(spendWindow))

    val maxDF = rankDF
      .groupBy("TimePeriod", "AnchorDate", "Quantile", "Type")
      .max("RowNum")

    val joinedDF = rankDF.join(maxDF, Seq("TimePeriod", "AnchorDate", "Quantile", "Type"))

    val topDF = joinedDF
      .filter(col("RowNum") <= num)
      .withColumn("Position", lit("Top"))
    val botDF = joinedDF
      .filter(col("RowNum") > (col("max(RowNum)") - num))
      .withColumn("Position", lit("Bottom"))

    topDF.unionAll(botDF)
      .drop("max(RowNum)")
      .sort("TimePeriod", "Quantile", "Type", "Rank")

  }

  def qtyProds(aggProdDF: DataFrame, num: Int): Try[DataFrame] = Try {
    val countWindow = Window.partitionBy("TimePeriod", "AnchorDate", "Quantile", "Type")
      .orderBy(desc("Qty"))

    val rankDF = aggProdDF.select("*")
      .drop("Amt")
      .withColumn("Rank", rank.over(countWindow))
      .withColumn("RowNum", row_number().over(countWindow))

    val maxDF = rankDF
      .groupBy("TimePeriod","AnchorDate", "Quantile", "Type")
      .max("RowNum")

    val joinedDF = rankDF.join(maxDF, Seq("TimePeriod", "AnchorDate", "Quantile", "Type"))

    val topDF = joinedDF
      .filter(col("RowNum") <= num)
      .withColumn("Position", lit("Top"))

    val botDF = joinedDF
      .filter(col("RowNum") > (col("max(RowNum)") - num))
      .withColumn("Position", lit("Bottom"))

    topDF.unionAll(botDF)
      .drop("max(RowNum)")
      .sort("TimePeriod", "Quantile", "Type", "RowNum")
  }

  def createProdAvro(spendDF: DataFrame, qtyDF: DataFrame, num: Int): Try[java.util.ArrayList[quantileProductResults]] = Try {
    var spendArray = new java.util.ArrayList[productSpend]
    var countArray = new java.util.ArrayList[productCount]
    val productArray = new java.util.ArrayList[quantileProductResults]
    val tempProd = new util.ArrayList[quantileProductResults]


    val joinedDF = spendDF
      .withColumnRenamed("Descr", "SpendDescr")
      .withColumnRenamed("Rank", "SpendRank")
      .join(qtyDF, Seq("TimePeriod", "AnchorDate", "Quantile", "Type", "RowNum", "Position"))
      .sort("TimePeriod", "AnchorDate", "Quantile", "Type", "RowNum")
      .drop("RowNum")

    def setSpendAndCount(row: Row): Unit = {
      val spend = new productSpend()
      spend.setPosition(row.getString(3))
      spend.setProduct(row.getString(4))
      spend.setSpend(row.getDouble(5))
      spend.setRank(row.getInt(6))

      spendArray.add(spend)

      val countEl = new productCount()
      countEl.setPosition(row.getString(3))
      countEl.setProduct(row.getString(7))
      countEl.setCount(row.getLong(8))
      countEl.setRank(row.getInt(9))

      countArray.add(countEl)
    }

    def mapArrays(row: Row): Unit = {
      if (tempProd.isEmpty) {
        val qpr = new quantileProductResults()
        qpr.setTimePeriod(row.getInt(0))
        qpr.setQuantile(row.getInt(1))
        qpr.setColumnName(row.getString(2))
        qpr.setAnchorDate(row.getString(10))
        tempProd.add(qpr)

        setSpendAndCount(row)

      } else if (row.getInt(0) == tempProd.get(tempProd.size() - 1).getTimePeriod &&
        row.getInt(1) == tempProd.get(tempProd.size() - 1).getQuantile &&
        row.getString(2) == tempProd.get(tempProd.size() - 1).getColumnName) {

        setSpendAndCount(row)

      } else {
        val prodEl = tempProd.get(tempProd.size() - 1)
        prodEl.setProductCount(countArray)
        prodEl.setProductSpend(spendArray)
        productArray.add(prodEl)

        val qpr = new quantileProductResults()
        qpr.setTimePeriod(row.getInt(0))
        qpr.setQuantile(row.getInt(1))
        qpr.setColumnName(row.getString(2))
        qpr.setAnchorDate(row.getString(10))
        tempProd.add(qpr)

        spendArray = new util.ArrayList[productSpend]
        countArray = new util.ArrayList[productCount]

        setSpendAndCount(row)
      }

    }

    val invertedDF = joinedDF
      .withColumn("TP", dense_rank().over(Window.orderBy(col("TimePeriod").desc)))
      .drop("TimePeriod")
      .withColumnRenamed("TP", "TimePeriod")
      .withColumn("StringAnchor", stringDate(joinedDF("AnchorDate")))
      .select("TimePeriod", "Quantile", "Type", "Position", "SpendDescr", "Amt", "SpendRank", "Descr", "Qty", "Rank", "StringAnchor")

    invertedDF.show()

    invertedDF.collect().foreach(f => mapArrays(f))

    val lastSet = tempProd.get(tempProd.size() - 1)
    lastSet.setProductCount(countArray)
    lastSet.setProductSpend(spendArray)
    productArray.add(lastSet)
    productArray
  }


  def createMigrationDF(quantDF: DataFrame, dimension: String): Try[DataFrame] = Try {

    def getIdCol(dimension: String): String = {
      dimension match {
        case "store" => "STORE_ID"
        case "customer" => "CUST_ID"
      }
    }

    val idCol = getIdCol(dimension)

    val baseDF = quantDF.select("TimePeriod", "AnchorDate", idCol, "Quantile")
      .withColumnRenamed("Quantile", "CurrQuant")
      .withColumnRenamed(idCol, "ID")

    val TPWindow = Window.partitionBy("ID").orderBy("TimePeriod", "AnchorDate")

    val missingTPDF = baseDF
      .drop("CurrQuant")
      .withColumn("TPDiff", baseDF("TimePeriod") - lag(("TimePeriod"), 1).over(TPWindow))
      .filter((col("TPDiff") > 1) || (col("TPDiff").isNull) && baseDF("TimePeriod") > 1)
      .withColumn("MissingTP", baseDF("TimePeriod") - 1)
      .drop("TimePeriod")
      .withColumnRenamed("MissingTP", "TimePeriod")
      .drop(col("TPDiff"))
      .withColumn("CurrQuant", lit(null))
      .select("TimePeriod", "AnchorDate", "ID", "CurrQuant")

    val unionDF = baseDF.unionAll(missingTPDF)

    unionDF
      .withColumn("PrevQuant", lead(("CurrQuant"), 1).over(TPWindow))
      .filter(unionDF("CurrQuant") isNotNull)
  }

  def countTotals(migDF: DataFrame): Try[DataFrame] = Try {
    val maxTP = migDF.select(max("TimePeriod")).head().getInt(0)
    val countDF = migDF.filter(migDF("PrevQuant") isNull)
      .filter(migDF("TimePeriod") < maxTP)
      .groupBy("TimePeriod", "CurrQuant")
      .agg(count("ID"))
      .drop("PrevQuant")

    countDF.show()
    countDF
  }

  def sumMigrations(migrationDF: DataFrame, quant: Double, sqlCtx: HiveContext, sc: SparkContext): Try[DataFrame] = Try {
    import sqlCtx.implicits._

    val buckets = (1 / quant).toInt

    val quantArray = 1 to buckets toList

    val quantDF = sc.parallelize(quantArray).toDF("CurrQuant")
    val prevDF = sc.parallelize(quantArray).toDF("PrevQuant")
    val TPs = migrationDF.select("TimePeriod", "AnchorDate").distinct()

    val matrixDF = TPs.join(quantDF).join(prevDF)

    val sumDF = migrationDF
      .na.drop()
      .groupBy("TimePeriod", "CurrQuant", "PrevQuant")
      .agg(count("ID").alias("Count"))

    val endDF = matrixDF.join(sumDF, Seq("TimePeriod", "CurrQuant", "PrevQuant"), "left_outer")
      .na.fill(0)

    endDF.show()

    endDF
  }

  def createMigrationAvro(sumDF: DataFrame, countDF: DataFrame): Try[java.util.ArrayList[quantileMigrationResults]] = Try {

    val migArray = new java.util.ArrayList[quantileMigrationResults]
    var innerMigArray = new java.util.ArrayList[migrationArray]
    var tempTotal = new java.util.ArrayList[newTotal]
    var timePeriod = 1


    def setInnerMigArray(row: Row): Unit = {
      val ma = new migrationArray()
      ma.setCurrentQuantile(row.getInt(1))
      ma.setFromQuantile(row.getInt(2))
      ma.setMigrationCount(row.getLong(3))
      innerMigArray.add(ma)
    }

    def mapMigArrays(row: Row): Unit = {
      if (migArray.isEmpty) {
        val qmr = new quantileMigrationResults()
        qmr.setTimePeriod(row.getInt(0))
        qmr.setAnchorDate(row.getString(4))

        migArray.add(qmr)

        setInnerMigArray(row)
      } else if (row.getInt(0) == migArray.get(migArray.size() - 1).getTimePeriod) {
        setInnerMigArray(row)
      } else {
        migArray.get(migArray.size() - 1).setMigrationData(innerMigArray)

        innerMigArray = new util.ArrayList[migrationArray]
        val qmr = new quantileMigrationResults()
        qmr.setTimePeriod(row.getInt(0))
        migArray.add(qmr)

        setInnerMigArray(row)
      }
    }

    def mapCountArrays(row: Row): Unit = {
      if (tempTotal.isEmpty || row.getInt(0) == timePeriod) {
        val total = new newTotal()
        total.setQuantile(row.getInt(1))
        total.setNewCount(row.getLong(2))
        tempTotal.add(total)
      }
      else {
        migArray.get(timePeriod - 1).setQuantileTotals(tempTotal)
        tempTotal = new util.ArrayList[newTotal]
        timePeriod = timePeriod + 1

        val total = new newTotal()
        total.setQuantile(row.getInt(1))
        total.setNewCount(row.getLong(2))
        tempTotal.add(total)
      }
    }

    val invertSumDF = sumDF
      .withColumnRenamed("TimePeriod", "TP")
      .withColumn("TimePeriod", dense_rank().over(Window.orderBy(col("TP").desc)))
        .withColumn("StringDate", stringDate(col("AnchorDate")))
      .select("TimePeriod", "CurrQuant", "PrevQuant", "Count", "StringDate")

    val invertCountDF = countDF
      .withColumnRenamed("TimePeriod", "TP")
      .withColumn("TimePeriod", dense_rank().over(Window.orderBy(col("TP").desc)))
      .select("TimePeriod", "CurrQuant", "count(ID)")


    invertSumDF.sort("TimePeriod").collect().foreach(f => mapMigArrays(f))
    migArray.get(migArray.size() - 1).setMigrationData(innerMigArray)
    invertCountDF.sort("TimePeriod").collect().foreach(g => mapCountArrays(g))
    migArray.get(migArray.size() - 1).setQuantileTotals(tempTotal)

    migArray
  }


  def createQuantileAvro(profileAvro: java.util.ArrayList[quantilePeriodResults], prodAvro: java.util.ArrayList[quantileProductResults],
                         migrationAvro: java.util.ArrayList[quantileMigrationResults], statDF: DataFrame, dimension: String, jobKey: String): Try[QuantileResults] = Try {

    val quantAvro = new QuantileResults()
    quantAvro.setJobKey(jobKey)
    quantAvro.setDimension(dimension)
    quantAvro.setMinDate(statDF.head().getDate(0).toString)
    quantAvro.setMaxDate(statDF.head().getDate(1).toString)
    quantAvro.setNumRecords(statDF.head().getLong(2))

    quantAvro.setQuantileResults(profileAvro)
    quantAvro.setProductResults(prodAvro)
    quantAvro.setMigrationResults(migrationAvro)

    quantAvro.setCompletionTime(LocalDateTime.now().toString)

    (quantAvro)
  }

  def sendQuantileError(jobKey: String, className: String, methodName: String, msg: String, extype: String, propsList: RDD[(String, String)]): Unit = {
    val error = new Error()
    error.setJobKey(jobKey)
    error.setJobType("Quantile")

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

    val jobName = "Quantile"
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

    if (args.length < 7) {
      sendQuantileError("Unknown", "Quantile", "MainMethod", s"Incorrect Usage, not enough args, ${args.length}, instead of 7.", "User", propsOnly)
      System.exit(-1)
      System.exit(-1)
    }

    val jobKey = args(2)
    val period = args(3).toInt
    val quantValue = args(4).toDouble
    val numProds = args(5).toInt
    val dimension = args(6)

    val validQuants = List(.01, .05, .1, .2, .25, .5)
    val validPeriods = List(1, 3, 6, 12)

    if (!validQuants.contains(quantValue)) {
      sendQuantileError(jobKey, "Quantile", "MainMethod", s"Invalid quantile selected: $quantValue.", "User", propsOnly)
      System.exit(-1)
    }

    if (dimension != "customer" && dimension != "store") {
      sendQuantileError(jobKey, "Quantile", "MainMethod", s"Invalid dimension selected: $dimension.", "User", propsOnly)
      System.exit(-1)
    }

    if (!validPeriods.contains(period)) {
      sendQuantileError(jobKey, "Quantile", "MainMethod", s"Invalid time period selected: $period.", "User", propsOnly)
      System.exit(-1)
    }

    val avro = for {
      orgFile <- loadFile(sqlCtx, args)

      dateDF <- DateUtils.determineFormat(orgFile)

      tpDF <- calcTimePeriod(dateDF, period)

      aggDF <- baseAgg(tpDF, dimension)

      statsDF <- dfStats(tpDF)

      quantDF <- quantileSpend(aggDF, quantValue)

      aggQuantDF <- aggQuantile(quantDF, dimension)

      avgDF <- calcAvg(aggQuantDF, dimension)

      profileAvro <- createProfileAvro(avgDF, dimension)

      productDF <- createProductDF(tpDF, quantDF, dimension)

      aggProdDf <- aggProducts(productDF)

      spendDF <- spendProds(aggProdDf, numProds)

      qtyDF <- qtyProds(aggProdDf, numProds)

      prodAvro <- createProdAvro(spendDF, qtyDF, numProds)

      migDF <- createMigrationDF(quantDF, dimension)

      totalDF <- countTotals(migDF)

      sumMigDF <- sumMigrations(migDF, quantValue, sqlCtx, sc)

      migAvro <- createMigrationAvro(sumMigDF, totalDF)

      finalAvro <- createQuantileAvro(profileAvro, prodAvro, migAvro, statsDF, dimension, jobKey)

    } yield finalAvro

    avro match {
      case Success(avro) => {
        println(s"was a success: $avro")
        //KafkaProducer.sendSucess("quantile", propsOnly, avro)
        sc.stop()
      }
      case Failure(ex) => {
        //sendQuantileError(jobKey, "BalorApp", "unknown", ex.toString, "System", propsOnly)
        println(s"there was an error: $ex")
        sc.stop()
        System.exit(-1)
      }
    }

  }
}
