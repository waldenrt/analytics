package com.brierley.bRelevant

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


  def createTransFile(transFileTemp: DataFrame): DataFrame = {
    transFileTemp.repartition(transFileTemp("CAPTURED_LOYALTY_ID"))
    //should we cache this one?
  }

  def createProductList(transFile: DataFrame): DataFrame = {
    transFile
      .select("PRODUCT_CATEGORY_DESCR")
      .distinct()
      .withColumn("RowNumber",
        functions.row_number()
          .over(Window.partitionBy("PRODUCT_CATEGORY_DESCR")
            .orderBy("PRODUCT_CATEGORY_DESCR")))
  }

  def createBasket(transFile: DataFrame, cutoffDate: String): DataFrame = {
    transFile
      .groupBy("CAPTURED_LOYALTY_ID", "PRODUCT_CATEGORY_DESCR")
      .agg(
        countDistinct("TXN_HEADER_ID").as("times_purchased"),
        sum("UNITS").as("CustProdPurchased").as("cust_prod_qty"),
        sum("LINE_AMT_AFTER_DISC").as("CustProdSales").as("cust_prod_sales"),
        productRecency(lit(cutoffDate), max("TXN_BUSINESS_DATE")).as("cust_prod_min_rec")
      )
  }

  def createCustomer(transFile: DataFrame): DataFrame = {
    transFile
      .groupBy("CAPTURED_LOYALTY_ID")
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

    //want this as an optional arg
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

    val transFile = createTransFile(transFileTemp)
    val productList = createProductList(transFile) // needed for ALS
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
