package com.brierley.bRelevant

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.brierley.utils.DataframeUtils._
import org.apache.log4j.Logger
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{DataFrame, functions}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.SparkContext

/**
  * Created by rwalden on 12/22/16.
  */
object bRelevant {

  def createCustList(transFileTemp: DataFrame): DataFrame = {
    transFileTemp
      .select("CAPTURED_LOYALTY_ID")
      .distinct()
      .withColumn("CustNum", functions.row_number()
        .over(Window.orderBy("CAPTURED_LOYALTY_ID")))
  }

  def createProdList(transFileTemp: DataFrame): DataFrame = {
    transFileTemp
      .select("PRODUCT_CATEGORY_DESCR")
      .distinct()
      .withColumn("PROD_ID", functions.row_number()
        .over(Window.orderBy("PRODUCT_CATEGORY_DESCR")))
  }

  def createTransFile(transFileTemp: DataFrame, custList: DataFrame, prodList: DataFrame): DataFrame = {
    transFileTemp
      .join(custList, "CAPTURED_LOYALTY_ID")
      .join(prodList, "PRODUCT_CATEGORY_DESCR")
      .repartition(transFileTemp("CAPTURED_LOYALTY_ID"))
    //should we cache this one?
  }

  def createBasket(transFile: DataFrame, cutoffDate: String): DataFrame = {
    transFile
      .withColumn("FormatDate", to_date(unix_timestamp(transFile("TXN_BUSINESS_DATE"), "dd-MMM-yy").cast("timestamp")))
      .drop("TXN_BUSINESS_DATE")
      .groupBy("CAPTURED_LOYALTY_ID", "PRODUCT_CATEGORY_DESCR", "ProdNum")
      .agg(
        countDistinct("TXN_HEADER_ID").as("times_purchased"),
        sum("UNITS").as("cust_prod_qty"),
        sum("LINE_AMT_AFTER_DISC").as("cust_prod_sales"),
        productRecency(lit(cutoffDate), max("FormatDate")).as("cust_prod_min_rec")
      )
  }

  def createCustomer(transFile: DataFrame): DataFrame = {
    transFile
      .groupBy("CAPTURED_LOYALTY_ID", "CustNum")
      .agg(
        countDistinct("TXN_HEADER_ID").as("cust_ttl_num_bask"),
        countDistinct("PRODUCT_CATEGORY_DESCR").as("number_products_purchased")
      )
      .withColumnRenamed("CAPTURED_LOYALTY_ID", "CUST_ID")
  }

  def createCustProdMetric(basket: DataFrame, customer: DataFrame): DataFrame = {
    basket
      .select("*")
      .join(customer, basket("CAPTURED_LOYALTY_ID") === customer("CUST_ID"))
      .withColumn("metric", metric(basket("times_purchased"), basket("cust_prod_min_rec"), basket("cust_prod_sales")))
      .drop("CAPTURED_LOYALTY_ID")
  }

  def createProdRank(custProdMetric: DataFrame): DataFrame = {
    val rankingMetric = Window
      .partitionBy("CUST_ID")
      .orderBy(custProdMetric("metric"))
    val custProdMinRec = Window
      .partitionBy("CUST_ID")
      .orderBy(custProdMetric("cust_prod_min_rec").desc)
    val custProdQty = Window
      .partitionBy("CUST_ID")
      .orderBy(custProdMetric("cust_prod_qty") / custProdMetric("cust_ttl_num_bask"))
    val custProdSales = Window
      .partitionBy("CUST_ID")
      .orderBy(custProdMetric("cust_prod_sales"))

    custProdMetric
      .select("*")
      .withColumn(
        "Ranking_Metric",
        ntile(5).over(rankingMetric)
      )
      .withColumn(
        "RFM_Term",
        concat(
          ntile(9).over(custProdMinRec),
          when(
            custProdMetric("cust_prod_qty") <= 1, 0)
            .otherwise(ntile(9).over(custProdQty)),
          ntile(9).over(custProdSales)
        )
      )
      .orderBy("PRODUCT_CATEGORY_DESCR")
  }

  def run(args: Array[String], sc: SparkContext, sqlContext: HiveContext): (DataFrame, DataFrame) = {

    val logger = Logger.getLogger(this.getClass)

    val cutoffDate = args(3)
    val fullPathandName = args(0)

    logger.info("Loading the file")
    val transFileTemp = sqlContext
      .read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .option("delimiter", "|")
      .load(fullPathandName)

    val custList = createCustList(transFileTemp)
    val prodList = createProdList(transFileTemp)

    val transFile = createTransFile(transFileTemp, custList, prodList)
    val basket = createBasket(transFile, cutoffDate)
    val customer = createCustomer(transFile)

    logger.info("Creating the custProdMetric file")

    //needs to be returned for tryscores
    val custProdMetric = createCustProdMetric(basket, customer)

    //both need
    val custProdRank = createProdRank(custProdMetric)

    (custProdMetric, custProdRank)

  }

}
