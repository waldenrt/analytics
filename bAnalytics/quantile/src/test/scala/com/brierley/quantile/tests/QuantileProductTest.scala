package com.brierley.quantile.tests

import com.brierley.quantile.Quantile
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{to_date, unix_timestamp}
import org.apache.spark.sql.types._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by amerrill on 6/15/17.
  */
@RunWith(classOf[JUnitRunner])
class QuantileProductTest extends FunSuite with DataFrameSuiteBase {

  trait CreateProdData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val prodStoreColumns = List("TimePeriod", "STORE_ID", "ITEM_QTY", "ITEM_AMT", "Level1", "Level2", "Level3", "Quantile")
    val prodCustColumns = List("TimePeriod", "CUST_ID", "ITEM_QTY", "ITEM_AMT", "Level1", "Level2", "Level3", "Quantile")

    val storesTP = sc.parallelize(List(
      ("StoreA", "CustA", "12/31/2016", "HeaderId1", "DetailID1", 3, 5.00, 0.00, "class1", "style1", "dept1", 1),
      ("StoreD", "CustB", "11/30/2016", "HeaderID2", "DetailID2", 4, 10.00, 2.50, "class2", "style1", "dept1", 1),
      ("StoreE", "CustB", "11/30/2016", "HeaderID5", "DetailID5", 5, 15.00, 2.50, "class2", "style1", "dept1", 1),
      ("StoreB", "CustB", "11/30/2016", "HeaderID5", "DetailID6", 5, 15.00, 2.50, "class3", "style2", "dept1", 1),
      ("StoreA", "CustA", "10/31/2016", "HeaderID3", "DetailID3", 5, 15.00, 5.00, "class3", "style2", "dept1", 1),
      ("StoreD", "CustC", "10/01/2016", "HeaderID4", "DetailID4", 6, 12.00, 0.00, "class4", "style3", "dept2", 1),
      ("StoreC", "CustD", "10/24/2016", "HeaderID6", "DetailID7", 2, 5.00, 2.50, "class2", "style1", "dept1", 1),
      ("StoreE", "CustA", "10/30/2016", "HeaderID7", "DetailID8", 1, 11.00, 0.50, "class5", "style5", "dept5", 1)
    )).toDF("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level2", "Level3", "Level1", "TimePeriod")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val fiveStores1TP = sc.parallelize(List(
      (1, "StoreA", 1, 1, 1, 1.00, 0.00, 2),
      (1, "StoreB", 1, 1, 1, 2.00, 0.00, 2),
      (1, "StoreC", 2, 2, 2, 3.00, 0.00, 2),
      (1, "StoreD", 1, 1, 1, 4.00, 0.00, 1),
      (1, "StoreE", 1, 3, 7, 5.00, 0.00, 1)
    )).toDF("TimePeriod", "STORE_ID", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Quantile")

    val custTP = sc.parallelize(List(
      ("StoreA", "CustA", "12/31/2016", "HeaderId1", "DetailID1", 3, 5.00, 0.00, "class1", "style1", "dept1", 1),
      ("StoreA", "CustB", "11/30/2016", "HeaderID2", "DetailID2", 4, 10.00, 2.50, "class2", "style1", "dept1", 1),
      ("StoreB", "CustD", "11/30/2016", "HeaderID5", "DetailID5", 5, 15.00, 2.50, "class2", "style1", "dept1", 1),
      ("StoreB", "CustB", "11/30/2016", "HeaderID5", "DetailID6", 5, 15.00, 2.50, "class3", "style2", "dept1", 1),
      ("StoreA", "CustE", "10/31/2016", "HeaderID3", "DetailID3", 5, 15.00, 5.00, "class3", "style2", "dept1", 1),
      ("StoreA", "CustC", "10/01/2016", "HeaderID4", "DetailID4", 6, 12.00, 0.00, "class4", "style3", "dept2", 1),
      ("StoreB", "CustF", "10/24/2016", "HeaderID6", "DetailID7", 2, 5.00, 2.50, "class2", "style1", "dept1", 1),
      ("StoreB", "CustG", "10/30/2016", "HeaderID7", "DetailID8", 1, 11.00, 0.50, "class5", "style5", "dept5", 1),
      ("StoreA", "CustA", "12/31/2016", "HeaderId1", "DetailID1", 3, 5.00, 0.00, "class1", "style1", "dept1", 2),
      ("StoreA", "CustB", "11/30/2016", "HeaderID2", "DetailID2", 4, 10.00, 2.50, "class2", "style1", "dept1", 2),
      ("StoreB", "CustD", "11/30/2016", "HeaderID5", "DetailID5", 5, 15.00, 2.50, "class2", "style1", "dept1", 2),
      ("StoreB", "CustB", "11/30/2016", "HeaderID5", "DetailID6", 5, 15.00, 2.50, "class3", "style2", "dept1", 2),
      ("StoreA", "CustE", "10/31/2016", "HeaderID3", "DetailID3", 5, 15.00, 5.00, "class3", "style2", "dept1", 2),
      ("StoreA", "CustC", "10/01/2016", "HeaderID4", "DetailID4", 6, 12.00, 0.00, "class4", "style3", "dept2", 2),
      ("StoreB", "CustF", "10/24/2016", "HeaderID6", "DetailID7", 2, 5.00, 2.50, "class2", "style1", "dept1", 2)
    )).toDF("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level2", "Level3", "Level1", "TimePeriod")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val nineCusts2TP = sc.parallelize(List(
      (1, "CustA", 1, 1, 1.00, 0.00, 4),
      (1, "CustB", 1, 2, 2.00, 0.00, 4),
      (1, "CustC", 1, 1, 3.00, 0.00, 3),
      (1, "CustD", 1, 4, 4.00, 0.00, 3),
      (1, "CustE", 1, 1, 5.00, 0.00, 2),
      (1, "CustF", 1, 2, 7.00, 0.00, 1),
      (1, "CustG", 1, 1, 7.00, 0.00, 1),
      (2, "CustA", 1, 1, 1.00, 0.00, 4),
      (2, "CustB", 1, 1, 2.00, 0.00, 4),
      (2, "CustC", 1, 3, 3.00, 0.00, 3),
      (2, "CustD", 1, 1, 4.00, 0.00, 2),
      (2, "CustE", 1, 1, 5.00, 0.00, 2),
      (2, "CustF", 1, 1, 7.00, 0.00, 1)
    )).toDF("TimePeriod", "CUST_ID", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Quantile")


    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }

  trait AggProdData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val aggColumns = List("TimePeriod", "Quantile", "Descr", "Qty", "Amt", "Type")

    val storesSingle = sc.parallelize(List(
      (1, "StoreA", 3, 5.00, "class1", 4),
      (1, "StoreA", 4, 10.00, "class2", 4),
      (1, "StoreB", 5, 15.00, "class2", 4),
      (1, "StoreB", 5, 15.00, "class3", 4),
      (1, "StoreA", 5, 15.00, "class3", 4),
      (1, "StoreA", 6, 12.00, "class4", 4),
      (1, "StoreB", 2, 5.00, "class2", 4),
      (1, "StoreB", 1, 11.00, "class5", 3),
      (1, "StoreB", 1, 11.00, "class5", 3),
      (1, "StoreB", 1, 11.00, "class5", 3),
      (1, "StoreA", 6, 12.00, "class4", 3),
      (1, "StoreB", 2, 5.00, "class2", 3),
      (1, "StoreA", 5, 15.00, "class3", 2),
      (1, "StoreA", 6, 12.00, "class4", 2),
      (1, "StoreB", 2, 5.00, "class2", 2),
      (1, "StoreB", 1, 11.00, "class5", 2),
      (1, "StoreB", 1, 11.00, "class5", 2),
      (1, "StoreA", 6, 12.00, "class4", 1),
      (1, "StoreB", 2, 5.00, "class2", 1),
      (1, "StoreB", 1, 11.00, "class5", 1),
      (1, "StoreB", 1, 11.00, "class5", 1),
      (1, "StoreB", 1, 11.00, "class5", 1),
      (1, "StoreA", 6, 12.00, "class4", 1),
      (1, "StoreB", 2, 5.00, "class2", 1),
      (1, "StoreA", 5, 15.00, "class3", 1),
      (1, "StoreA", 6, 12.00, "class4", 1)
    )).toDF("TimePeriod", "STORE_ID", "ITEM_QTY", "ITEM_AMT", "Level1", "Quantile")

    val custDouble = sc.parallelize(List(
      (1, "CustA", 3, 5.00, "class1", "style1", 4),
      (1, "CustB", 4, 10.00, "class2", "style1", 4),
      (1, "CustD", 5, 15.00, "class2", "style1", 4),
      (1, "CustB", 5, 15.00, "class3", "style2", 4),
      (1, "CustE", 5, 15.00, "class3", "style2", 4),
      (1, "CustC", 6, 12.00, "class4", "style3", 4),
      (1, "CustF", 2, 5.00, "class2", "style1", 4),
      (1, "CustG", 1, 11.00, "class5", "style5", 3),
      (1, "CustG", 1, 11.00, "class5", "style5", 3),
      (1, "CustG", 1, 11.00, "class5", "style5", 3),
      (1, "CustC", 6, 12.00, "class4", "style3", 3),
      (1, "CustF", 2, 5.00, "class2", "style1", 3),
      (1, "CustE", 5, 15.00, "class3", "style2", 2),
      (1, "CustC", 6, 12.00, "class4", "style3", 2),
      (1, "CustF", 2, 5.00, "class2", "style1", 2),
      (1, "CustG", 1, 11.00, "class5", "style5", 2),
      (1, "CustG", 1, 11.00, "class5", "style5", 2),
      (1, "CustC", 6, 12.00, "class4", "style3", 1),
      (1, "CustF", 2, 5.00, "class2", "style1", 1),
      (1, "CustG", 1, 11.00, "class5", "style5", 1),
      (1, "CustG", 1, 11.00, "class5", "style5", 1),
      (1, "CustG", 1, 11.00, "class5", "style5", 1),
      (1, "CustC", 6, 12.00, "class4", "style3", 1),
      (1, "CustF", 2, 5.00, "class2", "style1", 1),
      (1, "CustE", 5, 15.00, "class3", "style2", 1),
      (1, "CustC", 6, 12.00, "class4", "style3", 1)
    )).toDF("TimePeriod", "CUST_ID", "ITEM_QTY", "ITEM_AMT", "Level1", "Level2", "Quantile")

    val custThree = sc.parallelize(List(
      (1, "CustA", 3, 5.00, "class1", "style1", "dept1", 4),
      (1, "CustB", 4, 10.00, "class2", "style1", "dept1", 4),
      (1, "CustD", 5, 15.00, "class2", "style1", "dept1", 4),
      (1, "CustB", 5, 15.00, "class3", "style2", "dept1", 4),
      (1, "CustE", 5, 15.00, "class3", "style2", "dept1", 4),
      (1, "CustC", 6, 12.00, "class4", "style3", "dept2", 4),
      (1, "CustF", 2, 5.00, "class2", "style1", "dept1", 4),
      (1, "CustG", 1, 11.00, "class5", "style5", "dept5", 3),
      (1, "CustG", 1, 11.00, "class5", "style5", "dept5", 3),
      (1, "CustG", 1, 11.00, "class5", "style5", "dept5", 3),
      (1, "CustC", 6, 12.00, "class4", "style3", "dept2", 3),
      (1, "CustF", 2, 5.00, "class2", "style1", "dept1", 3),
      (1, "CustE", 5, 15.00, "class3", "style2", "dept1", 2),
      (1, "CustC", 6, 12.00, "class4", "style3", "dept2", 2),
      (1, "CustF", 2, 5.00, "class2", "style1", "dept1", 2),
      (1, "CustG", 1, 11.00, "class5", "style5", "dept5", 2),
      (1, "CustG", 1, 11.00, "class5", "style5", "dept5", 2),
      (1, "CustC", 6, 12.00, "class4", "style3", "dept2", 1),
      (1, "CustF", 2, 5.00, "class2", "style1", "dept1", 1),
      (1, "CustG", 1, 11.00, "class5", "style5", "dept5", 1),
      (1, "CustG", 1, 11.00, "class5", "style5", "dept5", 1),
      (1, "CustG", 1, 11.00, "class5", "style5", "dept5", 1),
      (1, "CustC", 6, 12.00, "class4", "style3", "dept2", 1),
      (1, "CustF", 2, 5.00, "class2", "style1", "dept1", 1),
      (1, "CustE", 5, 15.00, "class3", "style2", "dept1", 1),
      (1, "CustC", 6, 12.00, "class4", "style3", "dept2", 1)
    )).toDF("TimePeriod", "CUST_ID", "ITEM_QTY", "ITEM_AMT", "Level2", "Level3", "Level1", "Quantile")


    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }

  trait TopBottomData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val spendCols = List("TimePeriod", "Quantile", "Type", "Descr", "Amt", "Rank", "RowNum", "Position")
    val qtyCols = List("TimePeriod", "Quantile", "Type", "Descr", "Qty", "Rank", "RowNum", "Position")

    val amtSchema = StructType(Array(
      StructField("TimePeriod", IntegerType, false),
      StructField("Quantile", IntegerType, false),
      StructField("Type", StringType, true),
      StructField("Descr", StringType, true),
      StructField("Amt", DoubleType, false),
      StructField("Rank", IntegerType, true),
      StructField("RowNum", IntegerType, true),
      StructField("Position", StringType, false)))

    val qtySchema = StructType(Array(
      StructField("TimePeriod", IntegerType, false),
      StructField("Quantile", IntegerType, false),
      StructField("Type", StringType, true),
      StructField("Descr", StringType, true),
      StructField("Qty", IntegerType, false),
      StructField("Rank", IntegerType, true),
      StructField("RowNum", IntegerType, true),
      StructField("Position", StringType, false)))


    val top3SingleNoTie = sc.parallelize(List(
      (1, 1, "1", "Mens", 5, 25.00),
      (1, 1, "1", "Girls", 3, 30.00),
      (1, 1, "1", "Boys", 8, 21.00),
      (1, 1, "1", "Womens", 10, 54.00),
      (1, 1, "1", "Baby", 2, 5.00),
      (1, 1, "1", "Pet", 15, 28.00),
      (1, 1, "1", "Grocery", 54, 200.00),
      (1, 2, "1", "Girls", 5, 25.00),
      (1, 2, "1", "Boys", 3, 30.00),
      (1, 2, "1", "Mens", 8, 21.00),
      (1, 2, "1", "Pet", 2, 5.00),
      (1, 2, "1", "Womens", 7, 23.00),
      (1, 2, "1", "Grocery", 51, 175.00),
      (1, 3, "1", "Womens", 2, 20.00),
      (1, 3, "1", "Pet", 8, 9.00),
      (1, 3, "1", "Mens", 5, 12.00),
      (1, 3, "1", "Boys", 25, 5.00),
      (1, 3, "1", "Baby", 12, 36.00)
    )).toDF("TimePeriod", "Quantile", "Type", "Descr", "Qty", "Amt")

    val top3AllLevels = sc.parallelize(List(
      (1, 1, "1", "Mens", 5, 25.00),
      (1, 1, "1", "Girls", 3, 30.00),
      (1, 1, "1", "Boys", 8, 21.00),
      (1, 1, "1", "Womens", 10, 54.00),
      (1, 1, "1", "Baby", 2, 5.00),
      (1, 1, "1", "Pet", 15, 28.00),
      (1, 1, "1", "Grocery", 54, 200.00),
      (1, 1, "2", "Mens Shorts", 2, 10.00),
      (1, 1, "2", "Mens Shirts", 3, 15.00),
      (1, 1, "2", "Dress", 4, 20.00),
      (1, 1, "2", "Pants", 6, 45.00),
      (1, 1, "2", "Baby Tanks", 3, 11.00),
      (1, 1, "2", "Boys shorts", 2, 19.00),
      (1, 1, "2", "Paci", 4, 8.00),
      (1, 1, "2", "Diapers", 1, 22.00),
      (1, 1, "3", "size 2 diapers", 1, 22.00),
      (1, 1, "3", "boy paci", 4, 4.00),
      (1, 1, "3", "girls paci", 3, 4.00),
      (1, 1, "3", "Boy dino tank", 3, 8.00),
      (1, 1, "3", "Mens board shorts", 1, 20.00),
      (1, 1, "3", "Womens maxi dress", 2, 18.00),
      (1, 1, "3", "Green chew toy", 3, 6.00)
    )).toDF("TimePeriod", "Quantile", "Type", "Descr", "Qty", "Amt")

    val top3SingleTie = sc.parallelize(List(
      (1, 1, "1", "Mens", 5, 25.00),
      (1, 1, "1", "Girls", 3, 30.00),
      (1, 1, "1", "Boys", 8, 21.00),
      (1, 1, "1", "Womens", 10, 54.00),
      (1, 1, "1", "Baby", 2, 5.00),
      (1, 1, "1", "Pet", 15, 28.00),
      (1, 1, "1", "Books", 8, 28.00),
      (1, 1, "1", "Grocery", 54, 200.00),
      (1, 2, "1", "Girls", 5, 25.00),
      (1, 2, "1", "Boys", 3, 30.00),
      (1, 2, "1", "Mens", 8, 21.00),
      (1, 2, "1", "Pet", 2, 5.00),
      (1, 2, "1", "Womens", 7, 23.00),
      (1, 2, "1", "Books", 5, 25.00),
      (1, 2, "1", "Grocery", 51, 175.00),
      (1, 3, "1", "Womens", 2, 20.00),
      (1, 3, "1", "Pet", 8, 9.00),
      (1, 3, "1", "Mens", 5, 12.00),
      (1, 3, "1", "Boys", 25, 5.00),
      (1, 3, "1", "Baby", 12, 36.00),
      (1, 3, "1", "Books", 8, 9.00)
    )).toDF("TimePeriod", "Quantile", "Type", "Descr", "Qty", "Amt")

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }

  trait AvroData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val spendDF = sc.parallelize(List(
      (1, 1, 1, "1", "Grocery", 200.00, 1, "Top"),
      (2, 1, 1, "1", "Womens", 54.00, 2, "Top"),
      (3, 1, 1, "1", "Girls", 30.00, 3, "Top"),
      (10, 1, 1, "1", "Baby", 5.00, 7, "Bottom"),
      (9, 1, 1, "1", "Boys", 21.00, 6, "Bottom"),
      (8, 1, 1, "1", "Mens", 25.00, 5, "Bottom"),
      (1, 1, 2, "1", "Grocery", 175.00, 1, "Top"),
      (2, 1, 2, "1", "Boys", 30.00, 2, "Top"),
      (3, 1, 2, "1", "Girls", 25.00, 3, "Top"),
      (8, 1, 2, "1", "Pet", 5.00, 6, "Bottom"),
      (7, 1, 2, "1", "Mens", 21.00, 5, "Bottom"),
      (6, 1, 2, "1", "Womens", 23.00, 4, "Bottom"),
      (1, 1, 3, "1", "Baby", 36.00, 1, "Top"),
      (2, 1, 3, "1", "Womens", 20.00, 2, "Top"),
      (3, 1, 3, "1", "Mens", 12.00, 3, "Top"),
      (6, 1, 3, "1", "Boys",  5.00, 5, "Bottom"),
      (5, 1, 3, "1", "Pet", 9.00, 4, "Bottom"),
      (4, 1, 3, "1", "Mens", 12.00, 3, "Bottom")
    )).toDF("RowNum", "TimePeriod", "Quantile", "Type", "Descr", "Amt", "Rank", "Position")

    val qtyDF = sc.parallelize(List(
      (1, 1, 1, "1", "Grocery", 54.toLong, 1, "Top"),
      (2, 1, 1, "1", "Pet", 15.toLong, 2, "Top"),
      (3, 1, 1, "1", "Womens", 10.toLong, 3, "Top"),
      (10, 1, 1, "1", "Baby", 2.toLong, 7, "Bottom"),
      (9, 1, 1, "1", "Girls", 3.toLong, 6, "Bottom"),
      (8, 1, 1, "1", "Mens", 5.toLong, 5, "Bottom"),
      (1, 1, 2, "1", "Grocery", 51.toLong, 1, "Top"),
      (2, 1, 2, "1", "Mens", 8.toLong, 2, "Top"),
      (3, 1, 2, "1", "Womens", 7.toLong, 3, "Top"),
      (8, 1, 2, "1", "Pet", 2.toLong, 6, "Bottom"),
      (7, 1, 2, "1", "Boys", 3.toLong, 5, "Bottom"),
      (6, 1, 2, "1", "Girls", 5.toLong, 4, "Bottom"),
      (1, 1, 3, "1", "Boys", 25.toLong, 1, "Top"),
      (2, 1, 3, "1", "Baby", 12.toLong, 2, "Top"),
      (3, 1, 3, "1", "Pet", 8.toLong, 3, "Top"),
      (6, 1, 3, "1", "Womens", 2.toLong, 5, "Bottom"),
      (5, 1, 3, "1", "Mens", 5.toLong, 4, "Bottom"),
      (4, 1, 3, "1", "Pet", 8.toLong, 3, "Bottom")
    )).toDF("RowNum", "TimePeriod", "Quantile", "Type", "Descr", "Qty", "Rank", "Position")

    val spendDF3 = sc.parallelize(List(
      (1, 1, 1, "1", "Grocery", 200.00, 1, "Top"),
      (2, 1, 1, "1", "Womens", 54.00, 2, "Top"),
      (3, 1, 1, "1", "Girls", 30.00, 3, "Top"),
      (3, 1, 1, "1", "Baby", 5.00, 7, "Bottom"),
      (2, 1, 1, "1", "Boys", 21.00, 6, "Bottom"),
      (1, 1, 1, "1", "Mens", 25.00, 5, "Bottom"),
      (1, 1, 1, "2", "Pants", 45.00, 1, "Top"),
      (2, 1, 1, "2", "Diapers", 22.00, 2, "Top"),
      (3, 1, 1, "2", "Dress", 20.00, 3, "Top"),
      (8, 1, 1, "2", "Paci", 8.00, 8, "Bottom"),
      (7, 1, 1, "2", "Mens Shorts", 10.00, 7, "Bottom"),
      (6, 1, 1, "2", "Baby Tanks", 11.00, 6, "Bottom"),
      (1, 1, 1, "3", "size 2 diapers", 22.00, 1, "Top"),
      (2, 1, 1, "3", "Mens board shorts", 20.00, 2, "Top"),
      (3, 1, 1, "3", "Womens maxi dress", 18.00, 3, "Top"),
      (6, 1, 1, "3", "boy paci", 4.00, 6, "Bottom"),
      (5, 1, 1, "3", "girls paci", 4.00, 6, "Bottom"),
      (4, 1, 1, "3", "Green chew toy", 6.00, 5, "Bottom")
    )).toDF("RowNum", "TimePeriod", "Quantile", "Type", "Descr", "Amt", "Rank", "Position")

    val qtyDF3 = sc.parallelize(List(
      (1, 1, 1, "1", "Grocery", 54.toLong, 1, "Top"),
      (2, 1, 1, "1", "Pet", 15.toLong, 2, "Top"),
      (3, 1, 1, "1", "Womens", 10.toLong, 3, "Top"),
      (3, 1, 1, "1", "Baby", 2.toLong, 7, "Bottom"),
      (2, 1, 1, "1", "Girls", 3.toLong, 6, "Bottom"),
      (1, 1, 1, "1", "Mens", 5.toLong, 5, "Bottom"),
      (1, 1, 1, "2", "Pants", 6.toLong, 1, "Top"),
      (2, 1, 1, "2", "Dress", 4.toLong, 2, "Top"),
      (3, 1, 1, "2", "Paci", 4.toLong, 2, "Top"),
      (8, 1, 1, "2", "Diapers", 1.toLong, 8, "Bottom"),
      (7, 1, 1, "2", "Mens Shorts", 2.toLong, 6, "Bottom"),
      (6, 1, 1, "2", "Boys shorts", 2.toLong, 6, "Bottom"),
      (1, 1, 1, "3", "boy paci", 3.toLong, 1, "Top"),
      (2 ,1, 1, "3", "girls paci", 2.toLong, 2, "Top"),
      (3, 1, 1, "3", "Boy dino tank", 2.toLong, 2, "Top"),
      (6, 1, 1, "3", "Mens board shorts", 1.toLong, 5, "Bottom"),
      (5, 1, 1, "3", "size 2 diapers", 1.toLong, 5, "Bottom"),
      (4, 1, 1, "3", "Womens maxi dress", 1.toLong, 5, "Bottom")
    )).toDF("RowNum", "TimePeriod", "Quantile", "Type", "Descr", "Qty", "Rank", "Position")
  }


  //CREATE PRODUCT DF TESTS
  test("Create Product DF for stores, single TP, 2 buckets") {
    new CreateProdData {
      val prodDF = Quantile.createProductDF(storesTP, fiveStores1TP, "store") getOrElse orElseDF
      assert(prodDF.columns forall (prodStoreColumns.contains))
      assert(prodDF.count() === 8)

      val storeC = prodDF.where("STORE_ID = 'StoreC'")
        .select("TimePeriod", "STORE_ID", "ITEM_QTY", "ITEM_AMT", "Level2", "Level3", "Level1", "Quantile")
        .head()
      assert(storeC === Row(1, "StoreC", 2, 5.00, "class2", "style1", "dept1", 2))

      val storeB = prodDF.where("STORE_ID = 'StoreB'")
        .select("TimePeriod", "STORE_ID", "ITEM_QTY", "ITEM_AMT", "Level2", "Level3", "Level1", "Quantile")
        .head()
      assert(storeB === Row(1, "StoreB", 5, 15.0, "class3", "style2", "dept1", 2))

      val storeA = prodDF.where("STORE_ID = 'StoreA'")
      assert(storeA.count() === 2)

    }
  }

  test("ProdDF for customers, 2 TPs, 4 buckets") {
    new CreateProdData {
      val prodDF = Quantile.createProductDF(custTP, nineCusts2TP, "customer") getOrElse orElseDF
      assert(prodDF.columns forall (prodCustColumns.contains))
      assert(prodDF.count() === 15)

      val custA1 = prodDF.where("CUST_ID = 'CustA' AND TimePeriod = 1")
        .select("TimePeriod", "CUST_ID", "ITEM_QTY", "ITEM_AMT", "Level2", "Level3", "Level1", "Quantile")
        .head()
      assert(custA1 === Row(1, "CustA", 3, 5.00, "class1", "style1", "dept1", 4))

      val custG = prodDF.where("CUST_ID = 'CustG'")
        .select("TimePeriod", "CUST_ID", "ITEM_QTY", "ITEM_AMT", "Level2", "Level3", "Level1", "Quantile")
      assert(custG.count() === 1)
      assert(custG.head() === Row(1, "CustG", 1, 11.00, "class5", "style5", "dept5", 1))

    }
  }

  //AGG PRODUCTS
  test("Cust, 4 bins, 1 TP, 5 items") {
    new AggProdData {
      val aggDF = Quantile.aggProducts(storesSingle) getOrElse orElseDF
      assert(aggDF.columns === aggColumns)
      assert(aggDF.count() === 15)

      val quant4 = aggDF.where("Quantile = 4")
        .select("Quantile", "Type", "Descr", "Qty", "Amt")
      assert(quant4.count() === 4)
      val class3 = quant4.where("Descr = 'class2'").head()
      assert(class3 === Row(4, "Level1", "class2", 11.toLong, 30.00))

      val quant3 = aggDF.where("Quantile = 3")
        .select("Quantile", "Type", "Descr", "Qty", "Amt")
      assert(quant3.count() === 3)
      val class5 = quant3.where("Descr = 'class5'").head()
      assert(class5 === Row(3, "Level1", "class5", 3.toLong, 33.00))

      val quant2 = aggDF.where("Quantile = 2")
        .select("Quantile", "Type", "Descr", "Qty", "Amt")
      assert(quant2.count() === 4)
      val class2 = quant2.where("Descr = 'class2'").head()
      assert(class2 === Row(2, "Level1", "class2", 2.toLong, 5.00))

      val quant1 = aggDF.where("Quantile = 1")
        .select("Quantile", "Type", "Descr", "Qty", "Amt")
      assert(quant1.count() === 4)
      val class4 = quant1.where("Descr = 'class4'").head()
      assert(class4 === Row(1, "Level1", "class4", 18.toLong, 36.00))
    }
  }

  test("4 bins, 1 TP, 5 items, 4 styles") {
    new AggProdData {
      val aggDF = Quantile.aggProducts(custDouble) getOrElse orElseDF
      assert(aggDF.count() === 29)

      val quant1 = aggDF.where("Quantile = 1")
        .select("Quantile", "Type", "Descr", "Qty", "Amt")
      assert(quant1.count() === 8)
      val style2 = quant1.where("Descr = 'style2'").head()
      assert(style2 === Row(1, "Level2", "style2", 5.toLong, 15.00))
      val class3 = quant1.where("Descr = 'class3'").head()
      assert(class3 === Row(1, "Level1", "class3", 5.toLong, 15.00))

      val quant4 = aggDF.where("Quantile = 4")
        .select("Quantile", "Type", "Descr", "Qty", "Amt")
      assert(quant4.count() === 7)
      val style1 = quant4.where("Descr = 'style1'").head()
      assert(style1 === Row(4, "Level2", "style1", 14.toLong, 35.00))
      val class1 = quant4.where("Descr = 'class1'").head()
      assert(class1 === Row(4, "Level1", "class1", 3.toLong, 5.00))
    }
  }

  test("4 bins, 1 TP, 5 items, 4 styles, 3 dept") {
    new AggProdData {
      val aggDF = Quantile.aggProducts(custThree) getOrElse orElseDF
      aggDF.show()
      assert(aggDF.count() === 40)

      val quant1 = aggDF.where("Quantile = 1")
        .select("Quantile", "Type", "Descr", "Qty", "Amt")
      assert(quant1.count() === 11)
      val style2 = quant1.where("Descr = 'style2'").head()
      assert(style2 === Row(1, "Level3", "style2", 5.toLong, 15.00))
      val class3 = quant1.where("Descr = 'class3'").head()
      assert(class3 === Row(1, "Level2", "class3", 5.toLong, 15.00))
      val dept1 = quant1.where("Descr = 'dept1'").head()
      assert(dept1 === Row(1, "Level1", "dept1", 9.toLong, 25.00))

      val quant4 = aggDF.where("Quantile = 4")
        .select("Quantile", "Type", "Descr", "Qty", "Amt")
      assert(quant4.count() === 9)
      val style1 = quant4.where("Descr = 'style1'").head()
      assert(style1 === Row(4, "Level3", "style1", 14.toLong, 35.00))
      val class1 = quant4.where("Descr = 'class1'").head()
      assert(class1 === Row(4, "Level2", "class1", 3.toLong, 5.00))
    }
  }

  //TOP/BOTTOM PRODUCTS BY SPEND
  test("SPEND 1 TP, 3 quartiles: 7 in 1, 6 in 2, 5 in 3, pick 3 for each") {
    new TopBottomData {

      import sqlCtx.implicits._

      val spendDF = Quantile.spendProds(top3SingleNoTie, 3) getOrElse orElseDF
      spendDF.show()
      assert(spendDF.count() === 18) //6 for each quantile
      assert(spendDF.columns === spendCols)

      val quant1DF = sqlCtx.createDataFrame(sc.parallelize(List(
        Row(1, 1, "1", "Grocery", 200.00, Some(1), Some(1),"Top"),
        Row(1, 1, "1", "Womens", 54.00, Some(2), Some(2),"Top"),
        Row(1, 1, "1", "Girls", 30.00, Some(3), Some(3), "Top"),
        Row(1, 1, "1", "Mens", 25.00, Some(5), Some(5),"Bottom"),
        Row(1, 1, "1", "Boys", 21.00, Some(6), Some(6),"Bottom"),
        Row(1, 1, "1", "Baby", 5.00, Some(7), Some(7),"Bottom")
      )), amtSchema)

      val quant2DF = sqlCtx.createDataFrame(sc.parallelize(List(
        Row(1, 2, "1", "Grocery", 175.00, Some(1),Some(1), "Top"),
        Row(1, 2, "1", "Boys", 30.00, Some(2),Some(2), "Top"),
        Row(1, 2, "1", "Girls", 25.00, Some(3), Some(3), "Top"),
        Row(1, 2, "1", "Womens", 23.00, Some(4),Some(4), "Bottom"),
        Row(1, 2, "1", "Mens", 21.00, Some(5),Some(5), "Bottom"),
        Row(1, 2, "1", "Pet", 5.00, Some(6),Some(6), "Bottom")
      )), amtSchema)

      val quant3DF = sqlCtx.createDataFrame(sc.parallelize(List(
        Row(1, 3, "1", "Baby", 36.00, Some(1),Some(1), "Top"),
        Row(1, 3, "1", "Womens", 20.00, Some(2),Some(2), "Top"),
        Row(1, 3, "1", "Mens", 12.00, Some(3),Some(3), "Top"),
        Row(1, 3, "1", "Mens", 12.00, Some(3),Some(3), "Bottom"),
        Row(1, 3, "1", "Pet", 9.00, Some(4),Some(4), "Bottom"),
        Row(1, 3, "1", "Boys", 5.00, Some(5),Some(5), "Bottom")
      )), amtSchema)

      val quant1 = spendDF.where("Quantile = 1")
      val quant2 = spendDF.where("Quantile = 2")
      val quant3 = spendDF.where("Quantile = 3")

      assertDataFrameEquals(quant1, quant1DF)
      assertDataFrameEquals(quant2, quant2DF)
      assertDataFrameEquals(quant3, quant3DF)
    }
  }

  //TODO fill in after running a few times to verify tie behavior
  test("SPEND 1 TP, 3 quartiles, ties") {
    new TopBottomData {
      val amtDF = Quantile.spendProds(top3SingleTie, 3) getOrElse orElseDF
      amtDF.show()
      assert(amtDF.count() === 18)


    }
  }

  test("SPEND 1 Bin, 1 TP, 3 product levels") {
    new TopBottomData {

      import sqlCtx.implicits._

      val amtDF = Quantile.spendProds(top3AllLevels, 3) getOrElse orElseDF
      amtDF.show()
      assert(amtDF.count() === 18)

      val lvl1DF = sqlCtx.createDataFrame(sc.parallelize(List(
        Row(1, 1, "1", "Grocery", 200.00, Some(1),1,  "Top"),
        Row(1, 1, "1", "Womens", 54.00, Some(2), 2,"Top"),
        Row(1, 1, "1", "Girls", 30.00, Some(3), 3,"Top"),
        Row(1, 1, "1", "Mens", 25.00, Some(5), 5,"Bottom"),
        Row(1, 1, "1", "Boys", 21.00, Some(6), 6,"Bottom"),
        Row(1, 1, "1", "Baby", 5.00, Some(7), 7,"Bottom")
      )), amtSchema)

      val lvl2DF = sqlCtx.createDataFrame(sc.parallelize(List(
        Row(1, 1, "2", "Pants", 45.00, Some(1), 1,"Top"),
        Row(1, 1, "2", "Diapers", 22.00, Some(2), 2,"Top"),
        Row(1, 1, "2", "Dress", 20.00, Some(3), 3,"Top"),
        Row(1, 1, "2", "Baby Tanks", 11.00, Some(6), 6,"Bottom"),
        Row(1, 1, "2", "Mens Shorts", 10.00, Some(7), 7,"Bottom"),
        Row(1, 1, "2", "Paci", 8.00, Some(8), 8,"Bottom")
      )), amtSchema)

      val lvl3DF = sqlCtx.createDataFrame(sc.parallelize(List(
        Row(1, 1, "3", "size 2 diapers", 22.00, Some(1), 1,"Top"),
        Row(1, 1, "3", "Mens board shorts", 20.00, Some(2), 2,"Top"),
        Row(1, 1, "3", "Womens maxi dress", 18.00, Some(3), 3,"Top"),
        Row(1, 1, "3", "Green chew toy", 6.00, Some(5), 5,"Bottom"),
        Row(1, 1, "3", "boy paci", 4.00, Some(6), 6,"Bottom"),
        Row(1, 1, "3", "girls paci", 4.00, Some(6), 7,"Bottom")
      )), amtSchema)

      val level1 = amtDF.filter(amtDF("Type") === 1)
      val level2 = amtDF.filter(amtDF("Type") === 2)
      val level3 = amtDF.filter(amtDF("Type") === 3)

      assertDataFrameEquals(level1, lvl1DF)
      assertDataFrameEquals(level2, lvl2DF)
      assertDataFrameEquals(level3, lvl3DF)
    }
  }

  //TOP/BOTTOM PRODUCTS BY QTY
  test("QTY 1 TP, 3 quartiles: 7 in 1, 6 in 2, 5 in 3, pick 3 for each") {
    new TopBottomData {

      import sqlCtx.implicits._

      val qtyDF = Quantile.qtyProds(top3SingleNoTie, 3) getOrElse orElseDF
      qtyDF.show()
      assert(qtyDF.count() === 18)
      assert(qtyDF.columns === qtyCols)

      val quant1DF = sqlCtx.createDataFrame(sc.parallelize(List(
        Row(1, 1, "1", "Grocery", 54, Some(1), 1, "Top"),
        Row(1, 1, "1", "Pet", 15, Some(2), 2, "Top"),
        Row(1, 1, "1", "Womens", 10, Some(3), 3, "Top"),
        Row(1, 1, "1", "Mens", 5, Some(5), 5,"Bottom"),
        Row(1, 1, "1", "Girls", 3, Some(6),6, "Bottom"),
        Row(1, 1, "1", "Baby", 2, Some(7), 7,"Bottom")
      )), qtySchema)

      val quant2DF = sqlCtx.createDataFrame(sc.parallelize(List(
        Row(1, 2, "1", "Grocery", 51, Some(1), 1,"Top"),
        Row(1, 2, "1", "Mens", 8, Some(2), 2,"Top"),
        Row(1, 2, "1", "Womens", 7, Some(3), 3,"Top"),
        Row(1, 2, "1", "Girls", 5, Some(4), 4, "Bottom"),
        Row(1, 2, "1", "Boys", 3, Some(5), 5,"Bottom"),
        Row(1, 2, "1", "Pet", 2, Some(6), 6, "Bottom")
      )), qtySchema)

      val quant3DF = sqlCtx.createDataFrame(sc.parallelize(List(
        Row(1, 3, "1", "Boys", 25, Some(1), 1,"Top"),
        Row(1, 3, "1", "Baby", 12, Some(2), 2,"Top"),
        Row(1, 3, "1", "Pet", 8, Some(3), 3,"Top"),
        Row(1, 3, "1", "Pet", 8, Some(3), 3, "Bottom"),
        Row(1, 3, "1", "Mens", 5, Some(4), 4,"Bottom"),
        Row(1, 3, "1", "Womens", 2, Some(5), 5,"Bottom")
      )), qtySchema)

      val quant1 = qtyDF.filter(qtyDF("Quantile") === 1)
      val quant2 = qtyDF.filter(qtyDF("Quantile") === 2)
      val quant3 = qtyDF.filter(qtyDF("Quantile") === 3)

      assertDataFrameEquals(quant1, quant1DF)
      assertDataFrameEquals(quant2, quant2DF)
      assertDataFrameEquals(quant3, quant3DF)

    }
  }

  //TODO fill in after verifying tie behavior
  test("QTY 1 TP, 3 quartiles, ties") {
    new TopBottomData {
      val qtyDF = Quantile.qtyProds(top3SingleTie, 3) getOrElse orElseDF
      assert(qtyDF.count() === 18)

      qtyDF.show()
    }
  }

  test("QTY  1 Bin, 1 TP, 3 product levels") {
    new TopBottomData {

      import sqlCtx.implicits._

      val qtyDF = Quantile.qtyProds(top3AllLevels, 3) getOrElse orElseDF
      qtyDF.show()
      assert(qtyDF.count() === 18)

      val lvl1DF = sqlCtx.createDataFrame(sc.parallelize(List(
        Row(1, 1, "1", "Grocery", 54, Some(1), 1, "Top"),
        Row(1, 1, "1", "Pet", 15, Some(2), 2,"Top"),
        Row(1, 1, "1", "Womens", 10, Some(3), 3,"Top"),
        Row(1, 1, "1", "Mens", 5, Some(5), 5,"Bottom"),
        Row(1, 1, "1", "Girls", 3, Some(6), 6,"Bottom"),
        Row(1, 1, "1", "Baby", 2, Some(7), 7,"Bottom")
      )), qtySchema)

      val lvl2DF = sqlCtx.createDataFrame(sc.parallelize(List(
        Row(1, 1, "2", "Pants", 6, Some(1), 1,"Top"),
        Row(1, 1, "2", "Dress", 4, Some(2), 2,"Top"),
        Row(1, 1, "2", "Paci", 4, Some(2), 3,"Top"),
        Row(1, 1, "2", "Mens Shorts", 2, Some(6), 6,"Bottom"),
        Row(1, 1, "2", "Boys shorts", 2, Some(6), 7,"Bottom"),
        Row(1, 1, "2", "Diapers", 1, Some(8), 8,"Bottom")
      )), qtySchema)

      val lvl3DF = sqlCtx.createDataFrame(sc.parallelize(List(
        Row(1, 1, "3", "boy paci", 4, Some(1), 1,"Top"),
        Row(1, 1, "3", "girls paci", 3, Some(2), 2,"Top"),
        Row(1, 1, "3", "Boy dino tank", 3, Some(2), 3,"Top"),
        Row(1, 1, "3", "Womens maxi dress", 2, Some(5), 5,"Bottom"),
        Row(1, 1, "3", "size 2 diapers", 1, Some(6), 6,"Bottom"),
        Row(1, 1, "3", "Mens board shorts", 1, Some(6), 7,"Bottom")
      )), qtySchema)

      val level1 = qtyDF.filter(qtyDF("Type") === 1)
      val level2 = qtyDF.filter(qtyDF("Type") === 2)
      val level3 = qtyDF.filter(qtyDF("Type") === 3)

      assertDataFrameEquals(level1, lvl1DF)
      assertDataFrameEquals(level2, lvl2DF)
      assertDataFrameEquals(level3, lvl3DF)
    }
  }

  //PRODUCT AVRO ARRAY
  test("Single level products, 3 quantiles") {
    new AvroData {
      val prodAvro = Quantile.createProdAvro(spendDF, qtyDF, 3).get
      println(s"prodAvro list: $prodAvro")
      assert(prodAvro.size() === 3)


      val quant1 = prodAvro.get(0)
      val quant2 = prodAvro.get(1)
      val quant3 = prodAvro.get(2)

      assert(quant1.getTimePeriod === 1)
      assert(quant1.getQuantile === 1)
      assert(quant1.getColumnName === "1")
      assert(quant1.getProductCount.size() === 6)
      assert(quant1.getProductSpend.size() === 6)

      val topCount1 = quant1.getProductCount.get(0)
      val topCount2 = quant1.getProductCount.get(1)
      val topCount3 = quant1.getProductCount.get(2)
      val botCount1 = quant1.getProductCount.get(3)
      val botCount2 = quant1.getProductCount.get(4)
      val botCount3 = quant1.getProductCount.get(5)

      val topSpend1 = quant1.getProductSpend.get(0)
      val topSpend2 = quant1.getProductSpend.get(1)
      val topSpend3 = quant1.getProductSpend.get(2)
      val botSpend1 = quant1.getProductSpend.get(3)
      val botSpend2 = quant1.getProductSpend.get(4)
      val botSpend3 = quant1.getProductSpend.get(5)

      assert(topCount1 === new com.brierley.avro.schemas.productCount("Grocery", 1, 54.toLong, "Top"))
      assert(topCount2 === new com.brierley.avro.schemas.productCount("Pet", 2, 15.toLong, "Top"))
      assert(topCount3 === new com.brierley.avro.schemas.productCount("Womens", 3, 10.toLong, "Top"))
      assert(botCount3 === new com.brierley.avro.schemas.productCount("Baby", 7, 2.toLong, "Bottom"))
      assert(botCount2 === new com.brierley.avro.schemas.productCount("Girls", 6, 3.toLong, "Bottom"))
      assert(botCount1 === new com.brierley.avro.schemas.productCount("Mens", 5, 5.toLong, "Bottom"))

      assert(topSpend1 === new com.brierley.avro.schemas.productSpend("Grocery", 1, 200.00, "Top"))
      assert(topSpend2 === new com.brierley.avro.schemas.productSpend("Womens", 2, 54.00, "Top"))
      assert(topSpend3 === new com.brierley.avro.schemas.productSpend("Girls", 3, 30.00, "Top"))
      assert(botSpend3 === new com.brierley.avro.schemas.productSpend("Baby", 7, 5.00, "Bottom"))
      assert(botSpend2 === new com.brierley.avro.schemas.productSpend("Boys", 6, 21.00, "Bottom"))
      assert(botSpend1 === new com.brierley.avro.schemas.productSpend("Mens", 5, 25.00, "Bottom"))

    }
  }

  test("Single Quantile, 3 levels of products") {
    new AvroData {
      val prodAvro = Quantile.createProdAvro(spendDF3, qtyDF3, 3).get
      println(s"prodAvro list: $prodAvro")
      assert(prodAvro.size() === 3)

      val prod1 = prodAvro.get(0)
      assert(prod1.getProductCount.size() === 6)
      assert(prod1.getProductSpend.size() === 6)
      val prod2 = prodAvro.get(1)
      assert(prod2.getProductCount.size() === 6)
      assert(prod2.getProductSpend.size() === 6)
      val prod3 = prodAvro.get(2)
      assert(prod3.getProductCount.size() === 6)
      assert(prod3.getProductSpend.size() === 6)

      assert(prod1.getTimePeriod === 1)
      assert(prod1.getQuantile === 1)
      assert(prod1.getColumnName === "1")
      assert(prod2.getColumnName === "2")
      assert(prod3.getColumnName === "3")
    }
  }

}
