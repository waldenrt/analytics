package com.brierley.quantile.tests

import com.brierley.quantile.Quantile
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions.{to_date, unix_timestamp, _}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner

/**
  * Created by amerrill on 6/8/17.
  */
@RunWith(classOf[JUnitRunner])
class QuantileProfileTest extends FunSuite with DataFrameSuiteBase {

  trait fileLocations {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val commaFile = "src/test/resources/QuantileComma.csv"
    val barFile = "src/test/resources/QuantileBar.csv"
    val tabFile = "src/test/resources/QuantileTab.csv"
    val semiFile = "src/test/resources/QuantileSemi.csv"
    val commaFileNoHeader = "src/test/resources/QuantileNoHeader.csv"

    val comma = ","
    val bar = "|"
    val tab = "\t"
    val semi = ";"

    val commaArgs3 = Array(commaFile, comma, "TestID", "TestTP", "TestQuant", "10", "customer", "dept_descr", "class_descr", "style_descr")
    val barArgs2 = Array(barFile, bar, "TestID", "TestTP", "TestQuant", "10", "customer", "class_descr", "style_descr")
    val tabArgs1 = Array(tabFile, tab, "TestID", "TestTP", "TestQuant", "10", "customer", "class_descr")
    val semiArgs1 = Array(semiFile, semi, "TestID", "TestTP", "TestQuant", "10", "customer", "class_descr")
    val badArgs1 = Array(commaFileNoHeader, comma, "TestID", "TestTP", "TestQuant", "10", "customer", "class_descr")

    val rowCount = 10
    //the columns that should be left after loading
    val colNames3 = List("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level1", "Level2", "Level3")
    val colNames2 = List("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level1", "Level2")
    val colNames1 = List("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level1")

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")

  }

  trait TimePeriodData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    //no trimming
    val monthEnd3months = sc.parallelize(List(
      ("StoreA", "CustA", "12/31/2016", "HeaderId1", "DetailID1", 3, 5.00, 0.00, "class1", "style1", "dept1"), //1
      ("StoreA", "CustB", "11/30/2016", "HeaderID2", "DetailID2", 4, 10.00, 2.50, "class2", "style1", "dept1"), //2
      ("StoreA", "CustA", "10/31/2016", "HeaderID3", "DetailID3", 5, 15.00, 5.00, "class3", "style2", "dept1"), //3
      ("StoreA", "CustC", "10/01/2016", "HeaderID4", "DetailID4", 6, 12.00, 0.00, "class4", "style3", "dept2") //3
    )).toDF("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level3", "Level2", "Level1")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    //Trim off Sept TXN
    val monthEnd3monthsTrim = sc.parallelize(List(
      ("StoreA", "CustA", "12/31/2016", "HeaderId1", "DetailID1", 3, 5.00, 0.00, "class1", "style1", "dept1"), //1
      ("StoreA", "CustB", "11/30/2016", "HeaderID2", "DetailID2", 4, 10.00, 2.50, "class2", "style1", "dept1"), //2
      ("StoreA", "CustA", "10/31/2016", "HeaderID3", "DetailID3", 5, 15.00, 5.00, "class3", "style2", "dept1"), //3
      ("StoreA", "CustC", "10/01/2016", "HeaderID4", "DetailID4", 6, 12.00, 0.00, "class4", "style3", "dept2"), //3
      ("StoreA", "CustD", "09/15/2016", "HeaderID5", "DetailID5", 7, 14.00, 2.00, "class5", "style3", "dept2")
    )).toDF("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level3", "Level2", "Level1")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    //no trimming
    val midMonth4months = sc.parallelize(List(
      ("StoreA", "CustA", "12/15/2016", "HeaderId1", "DetailID1", 3, 5.00, 0.00, "class1", "style1", "dept1"), //1
      ("StoreA", "CustB", "11/15/2016", "HeaderID2", "DetailID2", 4, 10.00, 2.50, "class2", "style1", "dept1"), //2
      ("StoreA", "CustA", "10/15/2016", "HeaderID3", "DetailID3", 5, 15.00, 5.00, "class3", "style2", "dept1"), //3
      ("StoreA", "CustC", "10/14/2016", "HeaderID4", "DetailID4", 6, 12.00, 0.00, "class4", "style3", "dept2"), //4
      ("StoreA", "CustD", "09/16/2016", "HeaderID5", "DeatilID5", 7, 21.00, 3.00, "class1", "style1", "dept1") //4
    )).toDF("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level3", "Level2", "Level1")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    //trim off June txn
    val midMonth6monthsEmptyMonth = sc.parallelize(List(
      ("StoreA", "CustA", "12/15/2016", "HeaderId1", "DetailID1", 3, 5.00, 0.00, "class1", "style1", "dept1"), //1
      ("StoreA", "CustB", "11/15/2016", "HeaderID2", "DetailID2", 4, 10.00, 2.50, "class2", "style1", "dept1"), //2
      ("StoreA", "CustA", "10/15/2016", "HeaderID3", "DetailID3", 5, 15.00, 5.00, "class3", "style2", "dept1"), //3
      ("StoreA", "CustC", "10/14/2016", "HeaderID4", "DetailID4", 6, 12.00, 0.00, "class4", "style3", "dept2"), //4
      ("StoreA", "CustD", "09/16/2016", "HeaderID5", "DetailID5", 7, 21.00, 3.00, "class1", "style1", "dept1"), //4
      ("StoreA", "CustA", "07/24/2016", "HeaderID6", "DetailID6", 8, 24.00, 0.00, "class5", "styel1", "dept1"), //6
      ("StoreA", "CustB", "06/25/2016", "HeaderID7", "DetailID7", 9, 27.00, 3.00, "class3", "style2", "dept1") //dropped
    )).toDF("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level3", "Level2", "Level1")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    //trim off earliest June txn
    val midMonth7monthsTrim = sc.parallelize(List(
      ("StoreA", "CustA", "12/15/2016", "HeaderId1", "DetailID1", 3, 5.00, 0.00, "class1", "style1", "dept1"), //1
      ("StoreA", "CustB", "11/15/2016", "HeaderID2", "DetailID2", 4, 10.00, 2.50, "class2", "style1", "dept1"), //2
      ("StoreA", "CustA", "10/15/2016", "HeaderID3", "DetailID3", 5, 15.00, 5.00, "class3", "style2", "dept1"), //3
      ("StoreA", "CustC", "10/14/2016", "HeaderID4", "DetailID4", 6, 12.00, 0.00, "class4", "style3", "dept2"), //4
      ("StoreA", "CustD", "09/16/2016", "HeaderID5", "DetailID5", 7, 21.00, 3.00, "class1", "style1", "dept1"), //4
      ("StoreA", "CustA", "07/24/2016", "HeaderID6", "DetailID6", 8, 24.00, 0.00, "class5", "styel1", "dept1"), //6
      ("StoreA", "CustB", "06/25/2016", "HeaderID7", "DetailID7", 9, 27.00, 3.00, "class3", "style2", "dept1"), //7
      ("StoreA", "CustD", "06/09/2016", "HeaderID8", "DetailID8", 10, 5.00, 0.00, "class7", "style4", "dept3") //dropped
    )).toDF("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level3", "Level2", "Level1")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val midMonthLeap = sc.parallelize(List(
      ("StoreA", "CustA", "4/15/2016", "HeaderId1", "DetailID1", 3, 5.00, 0.00, "class1", "style1", "dept1"), //1
      ("StoreA", "CustB", "3/15/2016", "HeaderID2", "DetailID2", 4, 10.00, 2.50, "class2", "style1", "dept1"), //2
      ("StoreA", "CustA", "2/15/2016", "HeaderID3", "DetailID3", 5, 15.00, 5.00, "class3", "style2", "dept1"), //3
      ("StoreA", "CustC", "1/16/2016", "HeaderID4", "DetailID4", 6, 12.00, 0.00, "class4", "style3", "dept2"), //3
      ("StoreA", "CustC", "1/14/2016", "HeaderID5", "DetailID5", 6, 12.00, 0.00, "class4", "style3", "dept2")
    )).toDF("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level3", "Level2", "Level1")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val endMonthLeap = sc.parallelize(List(
      ("StoreA", "CustA", "4/30/2016", "HeaderId1", "DetailID1", 3, 5.00, 0.00, "class1", "style1", "dept1"), //1
      ("StoreA", "CustB", "3/31/2016", "HeaderID2", "DetailID2", 4, 10.00, 2.50, "class2", "style1", "dept1"), //2
      ("StoreA", "CustB", "3/30/2016", "HeaderID5", "DetailID5", 4, 10.00, 2.50, "class2", "style1", "dept1"), //2
      ("StoreA", "CustB", "3/1/2016", "HeaderID8", "DetailID8", 4, 10.00, 2.50, "class2", "style1", "dept1"), //2
      ("StoreA", "CustA", "2/29/2016", "HeaderID3", "DetailID3", 5, 15.00, 5.00, "class3", "style2", "dept1"), //3
      ("StoreA", "CustC", "1/31/2016", "HeaderID7", "DetailID7", 6, 12.00, 0.00, "class4", "style3", "dept2"), //4
      ("StoreA", "CustC", "1/30/2016", "HeaderID6", "DetailID6", 6, 12.00, 0.00, "class4", "style3", "dept2"), //4
      ("StoreA", "CustC", "1/1/2016", "HeaderID4", "DetailID4", 6, 12.00, 0.00, "class4", "style3", "dept2") //4
    )).toDF("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Level3", "Level2", "Level1")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))


    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")

  }

  trait AggDimensionData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val storeColumns = List("TimePeriod", "AnchorDate", "STORE_ID", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")
    val custColumns = List("TimePeriod", "AnchorDate", "CUST_ID", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")

    val twoStores = sc.parallelize(List(
      ("StoreA", "CustA", "12/31/2016", "HeaderId1", "DetailID1", 3, 5.00, 0.00, "class1", "style1", "dept1", 1),
      ("StoreA", "CustB", "11/30/2016", "HeaderID2", "DetailID2", 4, 10.00, 2.50, "class2", "style1", "dept1", 1),
      ("StoreB", "CustB", "11/30/2016", "HeaderID5", "DetailID5", 5, 15.00, 2.50, "class2", "style1", "dept1", 1),
      ("StoreB", "CustB", "11/30/2016", "HeaderID5", "DetailID6", 5, 15.00, 2.50, "class3", "style2", "dept1", 1),
      ("StoreA", "CustA", "10/31/2016", "HeaderID3", "DetailID3", 5, 15.00, 5.00, "class3", "style2", "dept1", 2),
      ("StoreA", "CustC", "10/01/2016", "HeaderID4", "DetailID4", 6, 12.00, 0.00, "class4", "style3", "dept2", 2),
      ("StoreB", "CustD", "10/24/2016", "HeaderID6", "DetailID7", 2, 5.00, 2.50, "class2", "style1", "dept1", 2),
      ("StoreB", "CustA", "10/30/2016", "HeaderID7", "DetailID8", 1, 11.00, 0.50, "class5", "style5", "dept5", 2)
    )).toDF("STORE_ID", "CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "class_descr", "style_descr", "dept_descr", "TimePeriod")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))
      .withColumn("AnchorDate", lit("01/01/2015"))

    val minMaxDF = sc.parallelize(List(
      ("2016-10-01", "2016-12-31", 8)
    )).toDF("minDate", "maxDate", "count")
      .withColumn("min(Date)", to_date(unix_timestamp($"minDate", "yyyy-MM-dd").cast("timestamp")))
      .withColumn("max(Date)", to_date(unix_timestamp($"maxDate", "yyyy-MM-dd").cast("timestamp")))
      .drop("minDate")
      .drop("maxDate")
      .select("min(Date)", "max(Date)", "count")

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }

  trait QuantileSpendData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val storeColumns = List("TimePeriod", "AnchorDate", "STORE_ID", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Quantile")
    val custColumns = List("TimePeriod", "AnchorDate", "CUST_ID", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Quantile")

    val twentyStores = sc.parallelize(List(
      (1, "01/01/2015", "Store-1", 1, 1, 1, 1.00, 0.00),
      (1, "01/01/2015", "Store-2", 1, 1, 1, 2.00, 0.00),
      (1, "01/01/2015", "Store-3", 1, 1, 1, 3.00, 0.00),
      (1, "01/01/2015", "Store-4", 1, 1, 1, 4.00, 0.00),
      (1, "01/01/2015", "Store-5", 1, 1, 1, 5.00, 0.00),
      (1, "01/01/2015", "Store-6", 1, 1, 1, 6.00, 0.00),
      (1, "01/01/2015", "Store-7", 1, 1, 1, 7.00, 0.00),
      (1, "01/01/2015", "Store-8", 1, 1, 1, 8.00, 0.00),
      (1, "01/01/2015", "Store-9", 1, 1, 1, 9.00, 0.00),
      (1, "01/01/2015", "Store-10", 1, 1, 1, 10.00, 0.00),
      (1, "01/01/2015", "Store-11", 1, 1, 1, 11.00, 0.00),
      (1, "01/01/2015", "Store-12", 1, 1, 1, 12.00, 0.00),
      (1, "01/01/2015", "Store-13", 1, 1, 1, 13.00, 0.00),
      (1, "01/01/2015", "Store-14", 1, 1, 1, 14.00, 0.00),
      (1, "01/01/2015", "Store-15", 1, 1, 1, 15.00, 0.00),
      (1, "01/01/2015", "Store-16", 1, 1, 1, 16.00, 0.00),
      (1, "01/01/2015", "Store-17", 1, 1, 1, 17.00, 0.00),
      (1, "01/01/2015", "Store-18", 1, 1, 1, 18.00, 0.00),
      (1, "01/01/2015", "Store-19", 1, 1, 1, 19.00, 0.00),
      (1, "01/01/2015", "Store-20", 1, 1, 1, 20.00, 0.00)
    )).toDF("TimePeriod", "AnchorDate", "STORE_ID", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")

    val twentyCusts = sc.parallelize(List(
      (1, "01/01/2015", "Cust-1", 1, 1, 1.00, 0.00),
      (1, "01/01/2015", "Cust-2", 1, 1, 2.00, 0.00),
      (1, "01/01/2015", "Cust-3", 1, 1, 3.00, 0.00),
      (1, "01/01/2015", "Cust-4", 1, 1, 4.00, 0.00),
      (1, "01/01/2015", "Cust-5", 1, 1, 5.00, 0.00),
      (1, "01/01/2015", "Cust-6", 1, 1, 6.00, 0.00),
      (1, "01/01/2015", "Cust-7", 1, 1, 7.00, 0.00),
      (1, "01/01/2015", "Cust-8", 1, 1, 8.00, 0.00),
      (1, "01/01/2015", "Cust-9", 1, 1, 9.00, 0.00),
      (1, "01/01/2015", "Cust-10", 1, 1, 10.00, 0.00),
      (1, "01/01/2015", "Cust-11", 1, 1, 11.00, 0.00),
      (1, "01/01/2015", "Cust-12", 1, 1, 12.00, 0.00),
      (1, "01/01/2015", "Cust-13", 1, 1, 13.00, 0.00),
      (1, "01/01/2015", "Cust-14", 1, 1, 14.00, 0.00),
      (1, "01/01/2015", "Cust-15", 1, 1, 15.00, 0.00),
      (1, "01/01/2015", "Cust-16", 1, 1, 16.00, 0.00),
      (1, "01/01/2015", "Cust-17", 1, 1, 17.00, 0.00),
      (1, "01/01/2015", "Cust-18", 1, 1, 18.00, 0.00),
      (1, "01/01/2015", "Cust-19", 1, 1, 19.00, 0.00),
      (1, "01/01/2015", "Cust-20", 1, 1, 20.00, 0.00)
    )).toDF("TimePeriod", "AnchorDate", "CUST_ID", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")

    val tenStoresNoTie = sc.parallelize(List(
      (1, "01/01/2015", "Store-1", 1, 1, 1, 1.00, 0.00),
      (1, "01/01/2015", "Store-2", 1, 1, 1, 2.00, 0.00),
      (1, "01/01/2015", "Store-3", 1, 1, 1, 3.00, 0.00),
      (1, "01/01/2015", "Store-4", 1, 1, 1, 4.00, 0.00),
      (1, "01/01/2015", "Store-5", 1, 1, 1, 5.00, 0.00),
      (1, "01/01/2015", "Store-6", 1, 1, 1, 6.00, 0.00),
      (1, "01/01/2015", "Store-7", 1, 1, 1, 7.00, 0.00),
      (1, "01/01/2015", "Store-8", 1, 1, 1, 8.00, 0.00),
      (1, "01/01/2015", "Store-9", 1, 1, 1, 9.00, 0.00),
      (1, "01/01/2015", "Store-10", 1, 1, 1, 10.00, 0.00)
    )).toDF("TimePeriod", "AnchorDate", "STORE_ID", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")

    val tenCustsNoTie = sc.parallelize(List(
      (1, "01/01/2015", "Cust-1", 1, 1, 1.00, 0.00),
      (1, "01/01/2015", "Cust-2", 1, 1, 2.00, 0.00),
      (1, "01/01/2015", "Cust-3", 1, 1, 3.00, 0.00),
      (1, "01/01/2015", "Cust-4", 1, 1, 4.00, 0.00),
      (1, "01/01/2015", "Cust-5", 1, 1, 5.00, 0.00),
      (1, "01/01/2015", "Cust-6", 1, 1, 6.00, 0.00),
      (1, "01/01/2015", "Cust-7", 1, 1, 7.00, 0.00),
      (1,"01/01/2015",  "Cust-8", 1, 1, 8.00, 0.00),
      (1, "01/01/2015", "Cust-9", 1, 1, 9.00, 0.00),
      (1, "01/01/2015", "Cust-10", 1, 1, 10.00, 0.00)
    )).toDF("TimePeriod", "AnchorDate", "CUST_ID", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")

    val tenCustsTie = sc.parallelize(List(
      (1, "01/01/2015", "Cust-1", 1, 1, 1.00, 0.00),
      (1, "01/01/2015", "Cust-2", 1, 1, 2.00, 0.00),
      (1, "01/01/2015", "Cust-3", 1, 1, 3.00, 0.00),
      (1, "01/01/2015", "Cust-4", 1, 1, 3.00, 0.00),
      (1, "01/01/2015", "Cust-5", 1, 1, 5.00, 0.00),
      (1, "01/01/2015", "Cust-6", 1, 1, 6.00, 0.00),
      (1, "01/01/2015", "Cust-7", 1, 1, 7.00, 0.00),
      (1, "01/01/2015", "Cust-8", 1, 1, 7.00, 0.00),
      (1, "01/01/2015", "Cust-9", 1, 1, 9.00, 0.00),
      (1, "01/01/2015", "Cust-10", 1, 1, 10.00, 0.00)
    )).toDF("TimePeriod", "AnchorDate", "CUST_ID", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")

    val nineCustsTie = sc.parallelize(List(
      (1, "01/01/2015", "Cust-1", 1, 1, 1.00, 0.00),
      (1, "01/01/2015", "Cust-2", 1, 1, 2.00, 0.00),
      (1, "01/01/2015", "Cust-3", 1, 1, 3.00, 0.00),
      (1, "01/01/2015", "Cust-4", 1, 1, 4.00, 0.00),
      (1, "01/01/2015", "Cust-5", 1, 1, 5.00, 0.00),
      (1, "01/01/2015", "Cust-6", 1, 1, 7.00, 0.00),
      (1, "01/01/2015", "Cust-7", 1, 1, 7.00, 0.00),
      (1, "01/01/2015", "Cust-8", 1, 1, 7.00, 0.00),
      (1,"01/01/2015",  "Cust-9", 1, 1, 7.00, 0.00)
    )).toDF("TimePeriod", "AnchorDate", "CUST_ID", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")

    val eightCustsNoTie = sc.parallelize(List(
      (1, "01/01/2015", "Cust-1", 1, 1, 1.00, 0.00),
      (1, "01/01/2015", "Cust-2", 1, 1, 2.00, 0.00),
      (1, "01/01/2015", "Cust-3", 1, 1, 3.00, 0.00),
      (1, "01/01/2015", "Cust-4", 1, 1, 4.00, 0.00),
      (1, "01/01/2015", "Cust-5", 1, 1, 5.00, 0.00),
      (1, "01/01/2015", "Cust-6", 1, 1, 6.00, 0.00),
      (1, "01/01/2015", "Cust-7", 1, 1, 7.00, 0.00),
      (1, "01/01/2015", "Cust-8", 1, 1, 8.00, 0.00)
    )).toDF("TimePeriod", "AnchorDate", "CUST_ID", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")

    val eightCustsTie = sc.parallelize(List(
      (1, "01/01/2015", "Cust-1", 1, 1, 1.00, 0.00),
      (1, "01/01/2015", "Cust-2", 1, 1, 2.00, 0.00),
      (1, "01/01/2015", "Cust-3", 1, 1, 3.00, 0.00),
      (1, "01/01/2015", "Cust-4", 1, 1, 4.00, 0.00),
      (1, "01/01/2015", "Cust-5", 1, 1, 5.00, 0.00),
      (1, "01/01/2015", "Cust-6", 1, 1, 7.00, 0.00),
      (1, "01/01/2015", "Cust-7", 1, 1, 7.00, 0.00),
      (1, "01/01/2015", "Cust-8", 1, 1, 7.00, 0.00)
    )).toDF("TimePeriod", "AnchorDate", "CUST_ID", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")

    val eightCustsMidTie = sc.parallelize(List(
      (1, "01/01/2015", "Cust-1", 1, 1, 1.00, 0.00),
      (1, "01/01/2015", "Cust-2", 1, 1, 2.00, 0.00),
      (1, "01/01/2015", "Cust-3", 1, 1, 3.00, 0.00),
      (1, "01/01/2015", "Cust-4", 1, 1, 4.00, 0.00),
      (1, "01/01/2015", "Cust-5", 1, 1, 5.00, 0.00),
      (1, "01/01/2015", "Cust-6", 1, 1, 7.00, 0.00),
      (1, "01/01/2015", "Cust-7", 1, 1, 7.00, 0.00),
      (1, "01/01/2015", "Cust-8", 1, 1, 8.00, 0.00)
    )).toDF("TimePeriod", "AnchorDate", "CUST_ID", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")

    val real12Stores = sc.parallelize(List(
      (1, "01/01/2015", "1", 324524.62, 4304, 5455, 16666, 0.0),
      (1, "01/01/2015", "2", 298825.58, 3670, 4482, 12064, 0.0),
      (1, "01/01/2015", "3", 293152.99, 3461, 4344, 11719, 0.0),
      (1, "01/01/2015", "4", 278943.2, 3388, 4852, 12133, 0.0),
      (1, "01/01/2015", "5", 257001.65, 3520, 4264, 11409, 0.0),
      (1, "01/01/2015", "6", 250647.35, 3308, 4000, 10001, 0.0),
      (1, "01/01/2015", "7", 249612.04, 3023, 4051, 9583, 0.0),
      (1, "01/01/2015", "8", 186295.87, 2538, 3022, 7646, 0.0),
      (1, "01/01/2015", "9", 172619.8, 2280, 2856, 8110, 0.0),
      (1, "01/01/2015", "10", 167207.53, 2270, 2826, 6928, 0.0),
      (1, "01/01/2015", "11", 109326.78, 1723, 1987, 5075, 0.0),
      (1, "01/01/2015", "12", 91797.77, 1720, 2018, 5181, 0.0)
    )).toDF("TimePeriod", "AnchorDate", "STORE_ID", "ITEM_AMT", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "DISC_AMT")

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }

  trait AggQuantileData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val storeColumns = List("TimePeriod", "AnchorDate", "Quantile", "STORE_COUNT", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")
    val custColumns = List("TimePeriod", "AnchorDate", "Quantile", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")

    val tenStores1TP = sc.parallelize(List(
      (1, "01/01/2015", "Store-1", 1, 1, 1, 1.00, 0.00, 4),
      (1,"01/01/2015",  "Store-2", 1, 1, 1, 2.00, 0.00, 4),
      (1, "01/01/2015", "Store-3", 2, 2, 2, 3.00, 0.00, 4),
      (1, "01/01/2015", "Store-4", 1, 1, 1, 4.00, 0.00, 3),
      (1, "01/01/2015", "Store-5", 1, 3, 7, 5.00, 0.00, 3),
      (1, "01/01/2015", "Store-6", 1, 1, 1, 6.00, 0.00, 2),
      (1, "01/01/2015", "Store-7", 1, 2, 4, 7.00, 0.00, 2),
      (1, "01/01/2015", "Store-8", 1, 1, 1, 8.00, 0.00, 1),
      (1, "01/01/2015", "Store-9", 3, 4, 8, 9.00, 0.00, 1),
      (1, "01/01/2015", "Store-10", 5, 9, 9, 10.00, 0.00, 1)
    )).toDF("TimePeriod", "AnchorDate", "STORE_ID", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Quantile")

    val tenStores2TP = sc.parallelize(List(
      (1, "01/01/2015", "Store-1", 1, 1, 1, 1.00, 0.00, 4),
      (1, "01/01/2015", "Store-2", 1, 1, 1, 2.00, 0.00, 4),
      (1, "01/01/2015", "Store-3", 2, 2, 2, 3.00, 0.00, 4),
      (1, "01/01/2015", "Store-4", 1, 1, 1, 4.00, 0.00, 3),
      (1, "01/01/2015", "Store-5", 1, 3, 7, 5.00, 0.00, 3),
      (1, "01/01/2015", "Store-6", 1, 1, 1, 6.00, 0.00, 2),
      (1, "01/01/2015", "Store-7", 1, 2, 4, 7.00, 0.00, 2),
      (1, "01/01/2015", "Store-8", 1, 1, 1, 8.00, 0.00, 1),
      (1, "01/01/2015", "Store-9", 3, 4, 8, 9.00, 0.00, 1),
      (1,"01/01/2015", "Store-10", 5, 9, 9, 10.00, 0.00, 1),
      (2, "01/01/2015", "Store-1", 1, 1, 1, 1.00, 0.00, 4),
      (2, "01/01/2015", "Store-2", 1, 1, 1, 2.00, 0.00, 4),
      (2, "01/01/2015", "Store-3", 2, 2, 2, 3.00, 0.00, 3),
      (2, "01/01/2015", "Store-4", 1, 1, 1, 4.00, 0.00, 3),
      (2, "01/01/2015", "Store-5", 1, 3, 7, 5.00, 0.00, 3),
      (2, "01/01/2015", "Store-6", 1, 1, 1, 6.00, 0.00, 2),
      (2, "01/01/2015", "Store-7", 1, 2, 4, 7.00, 0.00, 2),
      (2, "01/01/2015", "Store-8", 1, 1, 1, 8.00, 0.00, 2),
      (2, "01/01/2015", "Store-9", 3, 4, 8, 9.00, 0.00, 1),
      (2, "01/01/2015", "Store-10", 5, 9, 9, 10.00, 0.00, 1)
    )).toDF("TimePeriod", "AnchorDate", "STORE_ID", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Quantile")

    val nineCusts1TP = sc.parallelize(List(
      (1, "01/01/2015", "Cust-1", 1, 1, 1.00, 0.00, 4),
      (1, "01/01/2015", "Cust-2", 1, 2, 2.00, 0.00, 4),
      (1, "01/01/2015", "Cust-3", 1, 1, 3.00, 0.00, 3),
      (1, "01/01/2015", "Cust-4", 3, 4, 4.00, 0.00, 3),
      (1, "01/01/2015", "Cust-5", 1, 1, 5.00, 0.00, 2),
      (1, "01/01/2015", "Cust-6", 1, 2, 7.00, 0.00, 1),
      (1, "01/01/2015", "Cust-7", 1, 1, 7.00, 0.00, 1),
      (1, "01/01/2015", "Cust-8", 1, 3, 7.00, 0.00, 1),
      (1, "01/01/2015", "Cust-9", 1, 1, 7.00, 0.00, 1)
    )).toDF("TimePeriod", "AnchorDate", "CUST_ID", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Quantile")

    val nineCusts2TP = sc.parallelize(List(
      (1,"01/01/2015",  "Cust-1", 1, 1, 1.00, 0.00, 4),
      (1, "01/01/2015", "Cust-2", 1, 2, 2.00, 0.00, 4),
      (1, "01/01/2015", "Cust-3", 1, 1, 3.00, 0.00, 3),
      (1, "01/01/2015", "Cust-4", 1, 4, 4.00, 0.00, 3),
      (1, "01/01/2015", "Cust-5", 1, 1, 5.00, 0.00, 2),
      (1, "01/01/2015", "Cust-6", 1, 2, 7.00, 0.00, 1),
      (1, "01/01/2015", "Cust-7", 1, 1, 7.00, 0.00, 1),
      (1, "01/01/2015", "Cust-8", 1, 3, 7.00, 0.00, 1),
      (1, "01/01/2015", "Cust-9", 1, 1, 7.00, 0.00, 1),
      (2, "01/01/2015", "Cust-1", 1, 1, 1.00, 0.00, 4),
      (2, "01/01/2015", "Cust-2", 1, 1, 2.00, 0.00, 4),
      (2, "01/01/2015", "Cust-3", 1, 3, 3.00, 0.00, 3),
      (2, "01/01/2015", "Cust-4", 1, 1, 4.00, 0.00, 2),
      (2, "01/01/2015", "Cust-5", 1, 1, 5.00, 0.00, 2),
      (2, "01/01/2015", "Cust-6", 1, 1, 7.00, 0.00, 1),
      (2, "01/01/2015", "Cust-7", 1, 4, 7.00, 0.00, 1),
      (2, "01/01/2015", "Cust-8", 1, 2, 7.00, 0.00, 1),
      (2, "01/01/2015", "Cust-9", 1, 1, 7.00, 0.00, 1)
    )).toDF("TimePeriod", "AnchorDate", "CUST_ID", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT", "Quantile")

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")

  }

  trait AvgQuantileData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val storeColumns = List("TimePeriod", "Quantile", "STORE_COUNT", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT",
      "AvgDisc", "AvgSpend", "AvgItems", "AvgVisits", "AvgCust", "AvgTripSpend", "AvgTripItems", "AvgTripDisc", "AvgCustSpend", "AvgCustVisits", "AvgCustItems")
    val custColumns = List("TimePeriod", "Quantile", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT",
      "AvgDisc", "AvgSpend", "AvgItems", "AvgVisits", "AvgTripSpend", "AvgTripItems", "AvgTripDisc", "AvgItemSpend", "AvgItemDisc")

    val store4bins = sc.parallelize(List(
      (1, 1, 10, 20, 40, 80, 120.00, 10.00),
      (1, 2, 5, 10, 20, 40, 80.00, 5.00),
      (1, 3, 3, 6, 12, 15, 30.00, 0.00),
      (1, 4, 2, 2, 4, 8, 16.00, 4.00)
    )).toDF("TimePeriod", "Quantile", "STORE_COUNT", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")

    val cust4bins = sc.parallelize(List(
      (1, 1, 10, 40, 80, 120.00, 10.00),
      (1, 2, 5, 20, 40, 80.00, 5.00),
      (1, 3, 3, 12, 15, 30.00, 0.00),
      (1, 4, 2, 4, 8, 16.00, 4.00)
    )).toDF("TimePeriod", "Quantile", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT")

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }

  trait AvroData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val store4bins = sc.parallelize(List(
      (1, 1, 10.toLong, 20.toLong, 40.toLong, 80.toLong, 120.00, 10.00, 1.0, 12.0, 8.0, 4.0, 2.0, 3.0, 2.0, .25, 6.0, 2.0, 4.0, "01/01/2015"),
      (1, 2, 5.toLong, 10.toLong, 20.toLong, 40.toLong, 80.00, 5.00, 1.0, 16.0, 8.0, 4.0, 2.0, 4.0, 2.0, .25, 8.0, 2.0, 4.0, "01/01/2015"),
      (1, 3, 3.toLong, 6.toLong, 12.toLong, 15.toLong, 30.00, 0.00, 0.0, 10.0, 5.0, 4.0, 2.0, .25, 1.25, 0.0, 5.0, 2.0, 2.5, "01/01/2015"),
      (1, 4, 2.toLong, 2.toLong, 4.toLong, 8.toLong, 16.00, 4.00, 2.0, 8.0, 4.0, 2.0, 1.0, 4.0, 2.0, 1.0, 8.0, 2.0, 4.0, "01/01/2015")
    )).toDF("TimePeriod", "Quantile", "STORE_COUNT", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT",
      "AvgDisc", "AvgSpend", "AvgItems", "AvgVisits", "AvgCust", "AvgTripSpend", "AvgTripItems", "AvgTripDisc", "AvgCustSpend", "AvgCustVisits", "AvgCustItems", "TXN_DATE")
      .withColumn("AnchorDate", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val cust4bins = sc.parallelize(List(
      (1, 1, 10.toLong, 40.toLong, 80.toLong, 120.00, 10.00, 1.0, 12.0, 8.0, 4.0, 3.0, 2.0, .25, 1.5, .125, "01/01/2015"),
      (1, 2, 5.toLong, 20.toLong, 40.toLong, 80.00, 5.00, 1.0, 16.0, 8.0, 4.0, 4.0, 2.0, .25, 2.0, .125, "01/01/2015"),
      (1, 3, 3.toLong, 12.toLong, 15.toLong, 30.00, 0.00, 0.0, 10.0, 5.0, 4.0, 2.5, 1.25, 0.0, 2.0, 0.0, "01/01/2015"),
      (1, 4, 2.toLong, 4.toLong, 8.toLong, 16.00, 4.00, 2.0, 8.0, 4.0, 2.0, 4.0, 2.0, 1.0, 2.0, .5, "01/01/2015")
    )).toDF("TimePeriod", "Quantile", "CUST_COUNT", "TXN_COUNT", "ITEM_QTY", "ITEM_AMT", "DISC_AMT",
      "AvgDisc", "AvgSpend", "AvgItems", "AvgVisits", "AvgTripSpend", "AvgTripItems", "AvgTripDisc", "AvgItemSpend", "AvgItemDisc", "TXN_DATE")
      .withColumn("AnchorDate", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }


  //FILE LOAD TESTS WITH ALL DELIMITERS
  test("Option for , delimited") {
    new fileLocations {
      val orgFile = Quantile.loadFile(sqlCtx, commaArgs3) getOrElse orElseDF

      assert(orgFile.count() === rowCount)
      assert(orgFile.columns === colNames3)
    }
  }

  test("Option for \t delimited") {
    new fileLocations {
      val orgFile = Quantile.loadFile(sqlCtx, tabArgs1) getOrElse orElseDF

      assert(orgFile.count() === rowCount)
      assert(orgFile.columns === colNames1)
    }
  }

  test("Option for | delimited") {
    new fileLocations {
      val orgFile = Quantile.loadFile(sqlCtx, barArgs2) getOrElse orElseDF

      assert(orgFile.count() === rowCount)
      assert(orgFile.columns === colNames2)
    }
  }

  test("Option for ; delimited") {
    new fileLocations {
      val orgFile = Quantile.loadFile(sqlCtx, semiArgs1) getOrElse orElseDF

      assert(orgFile.count() === rowCount)
      assert(orgFile.columns === colNames1)
    }
  }

  test("No Header Row in File, should error out") {
    new fileLocations {
      val orgFile = Quantile.loadFile(sqlCtx, badArgs1) getOrElse orElseDF

      assert(orgFile === orElseDF)
    }
  }

  //TIME PERIOD TESTS
  test("3 1-month Time Periods") {
    new TimePeriodData {
      val periodDF = Quantile.calcTimePeriod(monthEnd3months, 1) getOrElse orElseDF
      val tp = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(tp === List(1, 2, 3, 3))

      val decTP = periodDF
        .where("TXN_HEADER_ID = 'HeaderId1'")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(decTP === 1)

      val novTP = periodDF
        .where("TXN_HEADER_ID = 'HeaderID2'")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(novTP === 2)

      val octTP1 = periodDF
        .where("TXN_HEADER_ID = 'HeaderID3'")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(octTP1 === 3)

      val octTP2 = periodDF
        .where("TXN_HEADER_ID = 'HeaderID4'")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(octTP2 === 3)
    }
  }

  test("3 1-month Time Periods, drop last txn") {
    new TimePeriodData {
      val periodDF = Quantile.calcTimePeriod(monthEnd3monthsTrim, 1) getOrElse orElseDF
      val tp = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(tp === List(1, 2, 3, 3))

      val decTP = periodDF
        .where("TXN_HEADER_ID = 'HeaderId1'")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(decTP === 1)

      val novTP = periodDF
        .where("TXN_HEADER_ID = 'HeaderID2'")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(novTP === 2)

      val octTP1 = periodDF
        .where("TXN_HEADER_ID = 'HeaderID3'")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(octTP1 === 3)

      val octTP2 = periodDF
        .where("TXN_HEADER_ID = 'HeaderID4'")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(octTP2 === 3)
    }
  }

  test("4 1-month Time Periods, mid-month end date, no trim") {
    new TimePeriodData {
      val periodDF = Quantile.calcTimePeriod(midMonth4months, 1) getOrElse orElseDF
      val tp = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(tp === List(1, 2, 3, 3, 3))
    }
  }

  test("2, 2-month TimePeriods, mid-month, no trim") {
    new TimePeriodData {
      val periodDF = Quantile.calcTimePeriod(midMonth4months, 2) getOrElse orElseDF
      val tp = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(tp === List(1, 1, 2, 2, 2))

    }
  }

  test("6 1-month TimePeriods, midmonth, skip TP 5, drop last") {
    new TimePeriodData {
      val periodDF = Quantile.calcTimePeriod(midMonth6monthsEmptyMonth, 1) getOrElse orElseDF
      val tp = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(tp === List(1, 2, 3, 3, 3, 5))
    }
  }

  test("2 3-month TimePeriods, midmonth, drop last") {
    new TimePeriodData {
      val periodDF = Quantile.calcTimePeriod(midMonth6monthsEmptyMonth, 3) getOrElse orElseDF
      val tp = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(tp === List(1, 1, 1, 1, 1, 2))
    }
  }

  test("7 1-month TimePeriods, midmonth, skip TP 5, drop last") {
    new TimePeriodData {
      val periodDF = Quantile.calcTimePeriod(midMonth7monthsTrim, 1) getOrElse orElseDF
      val tp = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(tp === List(1, 2, 3, 3, 3, 5, 6))
    }
  }

  test("1 6-month TimePeriods, midmonth, drop last 1") {
    new TimePeriodData {
      val periodDF = Quantile.calcTimePeriod(midMonth7monthsTrim, 6) getOrElse orElseDF
      val tp = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(tp === List(1, 1, 1, 1, 1, 1, 1))
    }
  }

  test("3 1-month TimePeriods, midmonth, leap year") {
    new TimePeriodData {
      val periodDF = Quantile.calcTimePeriod(midMonthLeap, 1) getOrElse orElseDF
      val tp = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(tp === List(1, 2, 3, 3))
    }
  }

  test("4 1-month TimePeriods, end month, leap year") {
    new TimePeriodData {
      val periodDF = Quantile.calcTimePeriod(endMonthLeap, 1) getOrElse orElseDF
      val tp = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(tp === List(1, 2, 2, 2, 3, 4, 4, 4))
    }
  }

  //BASIC STATS TESTS
  test("min/max date and txn count") {
    new AggDimensionData {
      val statDF = Quantile.dfStats(twoStores) getOrElse orElseDF
      val row = statDF.head()
      assert(row === minMaxDF.head())
      statDF.show()
    }
  }

  //DIMENSION SELECTION TESTS
  test("Store dimension") {
    new AggDimensionData {
      val aggDF = Quantile.baseAgg(twoStores, "store") getOrElse orElseDF
      assert(aggDF.columns === storeColumns)
    }
  }

  test("Customer dimension") {
    new AggDimensionData {
      val aggDF = Quantile.baseAgg(twoStores, "customer") getOrElse orElseDF
      assert(aggDF.columns === custColumns)
    }
  }

  //STORE AGG TESTS
  test("2 stores in both TPs") {
    new AggDimensionData {
      val aggDF = Quantile.baseAgg(twoStores, "store") getOrElse orElseDF
      assert(aggDF.count === 4)

      val storeA1 = aggDF.where("TimePeriod = 1 AND STORE_ID ='StoreA'").head()
      val storeB1 = aggDF.where("TimePeriod = 1 AND STORE_ID ='StoreB'").head()
      val storeA2 = aggDF.where("TimePeriod = 2 AND STORE_ID ='StoreA'").head()
      val storeB2 = aggDF.where("TimePeriod = 2 AND STORE_ID ='StoreB'").head()

      aggDF.show()

      assert(storeA1 === Row(1, "01/01/2015", "StoreA", 2, 2, 7, 15.00, 2.50))
      assert(storeB1 === Row(1, "01/01/2015", "StoreB", 1, 1, 10, 30.00, 5.00))
      assert(storeA2 === Row(2, "01/01/2015", "StoreA", 2, 2, 11, 27.00, 5.00))
      assert(storeB2 === Row(2, "01/01/2015", "StoreB", 2, 2, 3, 16.00, 3.00))
    }
  }

  //CUSTOMER AGG TESTS
  test("same cust multiple stores, same cust multiple TPs, multiple items in single transaction") {
    new AggDimensionData {
      val aggDF = Quantile.baseAgg(twoStores, "customer") getOrElse orElseDF
      assert(aggDF.count === 5)

      val custA1 = aggDF.where("TimePeriod = 1 AND CUST_ID ='CustA'").head()
      val custB = aggDF.where("TimePeriod = 1 AND CUST_ID ='CustB'").head()
      val custA2 = aggDF.where("TimePeriod = 2 AND CUST_ID ='CustA'").head()
      val custC = aggDF.where("TimePeriod = 2 AND CUST_ID ='CustC'").head()
      val custD = aggDF.where("TimePeriod = 2 AND CUST_ID ='CustD'").head()

      assert(custA1 === Row(1, "01/01/2015", "CustA", 1, 3, 5.00, 0.00))
      assert(custB === Row(1, "01/01/2015", "CustB", 2, 14, 40.00, 7.50))
      assert(custA2 === Row(2, "01/01/2015", "CustA", 2, 6, 26.00, 5.50))
      assert(custC === Row(2, "01/01/2015", "CustC", 1, 6, 12.00, 0.00))
      assert(custD === Row(2, "01/01/2015", "CustD", 1, 2, 5.00, 2.50))
    }
  }

  //QUANTILE SELECTION TESTS
  test("Store quantile = .05") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(twentyStores, .05) getOrElse orElseDF
      assert(quantDF.columns === storeColumns)

      val quants = quantDF.select("Quantile")
        .distinct()
        .count()

      assert(quants === 20)

      val topQuant = quantDF.where("Quantile = 1").select("ITEM_AMT").head().getDouble(0)

      assert(topQuant === 20.00 +- .001)
    }
  }

  test("Store quantile = .10") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(twentyStores, .10) getOrElse orElseDF
      val quants = quantDF.select("Quantile")
        .distinct()
        .count()

      assert(quants === 10)
    }
  }

  test("Store quantile = .20") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(twentyStores, .20) getOrElse orElseDF
      val quants = quantDF.select("Quantile")
        .distinct()
        .count()

      assert(quants === 5)
    }
  }

  test("Store quantile = .25") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(twentyStores, .25) getOrElse orElseDF
      val quants = quantDF.select("Quantile")
        .distinct()
        .count()

      assert(quants === 4)
    }
  }

  test("Store quantile = .50") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(twentyStores, .50) getOrElse orElseDF
      val quants = quantDF.select("Quantile")
        .distinct()
        .count()

      assert(quants === 2)
    }
  }

  test("Customer quantile = .05") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(twentyStores, .05) getOrElse orElseDF
      assert(quantDF.columns === storeColumns)

      val quants = quantDF.select("Quantile")
        .distinct()
        .count()

      assert(quants === 20)

      val topQuant = quantDF.where("Quantile = 1").select("ITEM_AMT").head().getDouble(0)

      assert(topQuant === 20.00 +- .001)
    }
  }

  test("Customer quantile = .10") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(twentyStores, .10) getOrElse orElseDF

      val quants = quantDF.select("Quantile")
        .distinct()
        .count()

      assert(quants === 10)
    }
  }

  test("Customer quantile = .20") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(twentyStores, .20) getOrElse orElseDF

      val quants = quantDF.select("Quantile")
        .distinct()
        .count()

      assert(quants === 5)
    }
  }

  test("Customer quantile = .25") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(twentyStores, .25) getOrElse orElseDF

      val quants = quantDF.select("Quantile")
        .distinct()
        .count()

      assert(quants === 4)
    }
  }

  test("Customer quantile = .50") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(twentyStores, .50) getOrElse orElseDF

      val quants = quantDF.select("Quantile")
        .distinct()
        .count()

      assert(quants === 2)
    }
  }

  //QUANTILING CUSTOMER TESTS
  test("8 customers, 4 bins, no ties") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(eightCustsNoTie, .25) getOrElse orElseDF
      val quants = quantDF.select("Quantile").map(_ (0)).collect()
      assert(quants === Array(1, 1, 2, 2, 3, 3, 4, 4))

      val topQuants = quantDF.where("Quantile = 1").select("ITEM_AMT").map(_ (0)).collect()
      assert(topQuants === Array(8.0, 7.0))
    }
  }

  test("8 customers, 4 bins, top 3 tied") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(eightCustsTie, .25) getOrElse orElseDF
      val quants = quantDF.select("Quantile").map(_ (0)).collect()

      assert(quants === Array(1, 1, 1, 2, 3, 3, 4, 4))

      val topQuants = quantDF.where("Quantile = 1").select("ITEM_AMT").map(_ (0)).collect()
      assert(topQuants === Array(7.0, 7.0, 7.0))
    }
  }

  test("8 customers, 4 bins, mid tie") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(eightCustsMidTie, .25) getOrElse orElseDF
      val quants = quantDF.select("Quantile").map(_ (0)).collect()

      assert(quants === Array(1, 1, 1, 2, 3, 3, 4, 4))

      val topQuants = quantDF.where("Quantile = 1").select("ITEM_AMT").map(_ (0)).collect()
      assert(topQuants === Array(8.0, 7.0, 7.0))
    }
  }

  test("9 customers, 4 bins, 4 way tie") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(nineCustsTie, .25) getOrElse orElseDF
      val quants = quantDF.select("Quantile").map(_ (0)).collect()

      assert(quants === Array(1, 1, 1, 1, 2, 3, 3, 4, 4))

      val topQuants = quantDF.where("Quantile = 1").select("ITEM_AMT").map(_ (0)).collect()
      assert(topQuants === Array(7.0, 7.0, 7.0, 7.0))
    }
  }

  test("10 customers, 4 bins, no tie") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(tenCustsNoTie, .25) getOrElse orElseDF
      val quants = quantDF.select("Quantile").map(_ (0)).collect()

      assert(quants === Array(1, 1, 1, 2, 2, 3, 3, 4, 4, 4))

      val topQuants = quantDF.where("Quantile = 1").select("ITEM_AMT").map(_ (0)).collect()
      assert(topQuants === Array(10.0, 9.0, 8.0))
    }
  }

  test("10 customers, 4 bins, mid tie") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(tenCustsTie, .25) getOrElse orElseDF
      val quants = quantDF.select("Quantile").map(_ (0)).collect()

      assert(quants === Array(1, 1, 1, 1, 2, 3, 3, 3, 4, 4))

      val topQuants = quantDF.where("Quantile = 1").select("ITEM_AMT").map(_ (0)).collect()
      assert(topQuants === Array(10.0, 9.0, 7.0, 7.0))
    }
  }

  //QUANTILING STORE TESTS - only need one since it uses the same function as customers
  test("10 stores, 4 bins, no tie") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(tenStoresNoTie, .25) getOrElse orElseDF
      val quants = quantDF.select("Quantile").map(_ (0)).collect()

      assert(quants === Array(1, 1, 1, 2, 2, 3, 3, 4, 4, 4))

      val topQuants = quantDF.where("Quantile = 1").select("ITEM_AMT").map(_ (0)).collect()
      assert(topQuants === Array(10.0, 9.0, 8.0))
    }
  }

  test("12 real store data, 10 bins, 1 TP") {
    new QuantileSpendData {
      val quantDF = Quantile.quantileSpend(real12Stores, .10) getOrElse orElseDF
      val quants = quantDF.select("Quantile").map(_ (0)).collect()

      assert(quants === Array(1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10))
    }
  }

  //AGG QUANTILES
  test("10 stores, 4 bins, 1 TP") {
    new AggQuantileData {
      val aggDF = Quantile.aggQuantile(tenStores1TP, "store") getOrElse orElseDF
      assert(aggDF.count() === 4)
      assert(aggDF.columns === storeColumns)

      val quant1 = aggDF.where("Quantile = 1").head()
      val quant2 = aggDF.where("Quantile = 2").head()
      val quant3 = aggDF.where("Quantile = 3").head()
      val quant4 = aggDF.where("Quantile = 4").head()

      assert(quant1 === Row(1, "01/01/2015", 1, 3, 9, 14, 18, 27.00, 0.00))
      assert(quant2 === Row(1, "01/01/2015", 2, 2, 2, 3, 5, 13.00, 0.00))
      assert(quant3 === Row(1, "01/01/2015", 3, 2, 2, 4, 8, 9.00, 0.00))
      assert(quant4 === Row(1, "01/01/2015", 4, 3, 4, 4, 4, 6.00, 0.00))
    }
  }

  test("10 stores, 4 bins, 2 TPs") {
    new AggQuantileData {
      val aggDF = Quantile.aggQuantile(tenStores2TP, "store") getOrElse orElseDF
      assert(aggDF.count() === 8)

      val quant11 = aggDF.filter(aggDF("TimePeriod") === 1).where("Quantile = 1").head()
      val quant21 = aggDF.filter(aggDF("TimePeriod") === 1).where("Quantile = 2").head()
      val quant31 = aggDF.filter(aggDF("TimePeriod") === 1).where("Quantile = 3").head()
      val quant41 = aggDF.filter(aggDF("TimePeriod") === 1).where("Quantile = 4").head()

      val quant12 = aggDF.filter(aggDF("TimePeriod") === 2).where("Quantile = 1").head()
      val quant22 = aggDF.filter(aggDF("TimePeriod") === 2).where("Quantile = 2").head()
      val quant32 = aggDF.filter(aggDF("TimePeriod") === 2).where("Quantile = 3").head()
      val quant42 = aggDF.filter(aggDF("TimePeriod") === 2).where("Quantile = 4").head()

      aggDF.show()

      assert(quant11 === Row(1, "01/01/2015", 1, 3, 9, 14, 18, 27.00, 0.00))
      assert(quant21 === Row(1, "01/01/2015", 2, 2, 2, 3, 5, 13.00, 0.00))
      assert(quant31 === Row(1, "01/01/2015", 3, 2, 2, 4, 8, 9.00, 0.00))
      assert(quant41 === Row(1, "01/01/2015", 4, 3, 4, 4, 4, 6.00, 0.00))

      assert(quant12 === Row(2, "01/01/2015", 1, 2, 8, 13, 17, 19.00, 0.00))
      assert(quant22 === Row(2, "01/01/2015", 2, 3, 3, 4, 6, 21.00, 0.00))
      assert(quant32 === Row(2, "01/01/2015", 3, 3, 4, 6, 10, 12.00, 0.00))
      assert(quant42 === Row(2, "01/01/2015", 4, 2, 2, 2, 2, 3.00, 0.00))
    }
  }

  test("9 customers, 4 bins, 1 TP") {
    new AggQuantileData {
      val aggDF = Quantile.aggQuantile(nineCusts1TP, "customer") getOrElse orElseDF
      assert(aggDF.count() === 4)
      assert(aggDF.columns === custColumns)

      val quant1 = aggDF.where("Quantile = 1").head()
      val quant2 = aggDF.where("Quantile = 2").head()
      val quant3 = aggDF.where("Quantile = 3").head()
      val quant4 = aggDF.where("Quantile = 4").head()

      aggDF.show()

      assert(quant1 === Row(1, "01/01/2015", 1, 4, 4, 7, 28.00, 0.00))
      assert(quant2 === Row(1, "01/01/2015", 2, 1, 1, 1, 5.00, 0.00))
      assert(quant3 === Row(1, "01/01/2015", 3, 2, 4, 5, 7.00, 0.00))
      assert(quant4 === Row(1, "01/01/2015", 4, 2, 2, 3, 3.00, 0.00))
    }
  }

  test("9 customers, 4 bins, 2 TPs") {
    new AggQuantileData {
      val aggDF = Quantile.aggQuantile(nineCusts2TP, "customer") getOrElse orElseDF

      aggDF.show()
      assert(aggDF.count() === 8)

      val quant11 = aggDF.filter(aggDF("TimePeriod") === 1).where("Quantile = 1").head()
      val quant21 = aggDF.filter(aggDF("TimePeriod") === 1).where("Quantile = 2").head()
      val quant31 = aggDF.filter(aggDF("TimePeriod") === 1).where("Quantile = 3").head()
      val quant41 = aggDF.filter(aggDF("TimePeriod") === 1).where("Quantile = 4").head()

      val quant12 = aggDF.filter(aggDF("TimePeriod") === 2).where("Quantile = 1").head()
      val quant22 = aggDF.filter(aggDF("TimePeriod") === 2).where("Quantile = 2").head()
      val quant32 = aggDF.filter(aggDF("TimePeriod") === 2).where("Quantile = 3").head()
      val quant42 = aggDF.filter(aggDF("TimePeriod") === 2).where("Quantile = 4").head()


      assert(quant11 === Row(1, "01/01/2015", 1, 4, 4, 7, 28.00, 0.00))
      assert(quant21 === Row(1, "01/01/2015", 2, 1, 1, 1, 5.00, 0.00))
      assert(quant31 === Row(1, "01/01/2015", 3, 2, 2, 5, 7.00, 0.00))
      assert(quant41 === Row(1, "01/01/2015", 4, 2, 2, 3, 3.00, 0.00))

      assert(quant12 === Row(2, "01/01/2015", 1, 4, 4, 8, 28.00, 0.00))
      assert(quant22 === Row(2, "01/01/2015", 2, 2, 2, 2, 9.00, 0.00))
      assert(quant32 === Row(2, "01/01/2015", 3, 1, 1, 3, 3.00, 0.00))
      assert(quant42 === Row(2, "01/01/2015", 4, 2, 2, 2, 3.00, 0.00))
    }
  }

  //QUANTILE AVERAGE TESTS
  test("1 TP store averages") {
    new AvgQuantileData {
      val avgDF = Quantile.calcAvg(store4bins, "store") getOrElse orElseDF
      assert(avgDF.columns === storeColumns)

      val quant1 = avgDF.where("Quantile = 1")
        .select("AvgDisc", "AvgSpend", "AvgItems", "AvgVisits", "AvgCust", "AvgTripSpend", "AvgTripItems", "AvgTripDisc", "AvgCustSpend", "AvgCustVisits", "AvgCustItems")
        .head()
      val quant2 = avgDF.where("Quantile = 2")
        .select("AvgDisc", "AvgSpend", "AvgItems", "AvgVisits", "AvgCust", "AvgTripSpend", "AvgTripItems", "AvgTripDisc", "AvgCustSpend", "AvgCustVisits", "AvgCustItems")
        .head()
      val quant3 = avgDF.where("Quantile = 3")
        .select("AvgDisc", "AvgSpend", "AvgItems", "AvgVisits", "AvgCust", "AvgTripSpend", "AvgTripItems", "AvgTripDisc", "AvgCustSpend", "AvgCustVisits", "AvgCustItems")
        .head()
      val quant4 = avgDF.where("Quantile = 4")
        .select("AvgDisc", "AvgSpend", "AvgItems", "AvgVisits", "AvgCust", "AvgTripSpend", "AvgTripItems", "AvgTripDisc", "AvgCustSpend", "AvgCustVisits", "AvgCustItems")
        .head()

      assert(quant1 === Row(1, 12, 8, 4, 2, 3, 2, .25, 6, 2, 4))
      assert(quant2 === Row(1, 16, 8, 4, 2, 4, 2, .25, 8, 2, 4))
      assert(quant3 === Row(0, 10, 5, 4, 2, 2.5, 1.25, 0, 5, 2, 2.5))
      assert(quant4 === Row(2, 8, 4, 2, 1, 4, 2, 1, 8, 2, 4))

    }
  }

  test("1 TP customer averages") {
    new AvgQuantileData {
      val avgDF = Quantile.calcAvg(cust4bins, "customer") getOrElse orElseDF
      assert(avgDF.columns === custColumns)

      val quant1 = avgDF.where("Quantile = 1")
        .select("AvgDisc", "AvgSpend", "AvgItems", "AvgVisits", "AvgTripSpend", "AvgTripItems", "AvgTripDisc", "AvgItemSpend", "AvgItemDisc")
        .head()
      val quant2 = avgDF.where("Quantile = 2")
        .select("AvgDisc", "AvgSpend", "AvgItems", "AvgVisits", "AvgTripSpend", "AvgTripItems", "AvgTripDisc", "AvgItemSpend", "AvgItemDisc")
        .head()
      val quant3 = avgDF.where("Quantile = 3")
        .select("AvgDisc", "AvgSpend", "AvgItems", "AvgVisits", "AvgTripSpend", "AvgTripItems", "AvgTripDisc", "AvgItemSpend", "AvgItemDisc")
        .head()
      val quant4 = avgDF.where("Quantile = 4")
        .select("AvgDisc", "AvgSpend", "AvgItems", "AvgVisits", "AvgTripSpend", "AvgTripItems", "AvgTripDisc", "AvgItemSpend", "AvgItemDisc")
        .head()

      assert(quant1 === Row(1, 12, 8, 4, 3, 2, .25, 1.5, .125))
      assert(quant2 === Row(1, 16, 8, 4, 4, 2, .25, 2, .125))
      assert(quant3 === Row(0, 10, 5, 4, 2.5, 1.25, 0, 2, 0))
      assert(quant4 === Row(2, 8, 4, 2, 4, 2, 1, 2, .5))
    }
  }

  //AVRO FOR PERIOD RESULTS
  test("Store data into avro") {
    new AvroData {
      val avroTry = Quantile.createProfileAvro(store4bins, "store").get //getOrElse orElseDF

      assert(avroTry.size() === 4)
      val profile1 = avroTry.get(0)
      val profile2 = avroTry.get(1)
      val profile3 = avroTry.get(2)
      val profile4 = avroTry.get(3)

      //assert all values of 1st quantile, spot check the rest
      assert(profile1.getTimePeriod === 1)
      assert(profile1.getQuantile === 1)
      assert(profile1.getTotalCount === 10)
      assert(profile1.getTotalCust === 20)
      assert(profile1.getTotalVisits === 40)
      assert(profile1.getTotalUnits === 80)
      assert(profile1.getTotalSpend === 120.00)
      assert(profile1.getTotalDisc === 10.00)
      assert(profile1.getAvgDisc === 1.00)
      assert(profile1.getAvgSpend === 12.00)
      assert(profile1.getAvgUnits === 8)
      assert(profile1.getAvgVisits === 4)
      assert(profile1.getAvgStoreCust === 2)
      assert(profile1.getAvgVisitSpend === 3.00)
      assert(profile1.getAvgVisitUnits === 2)
      assert(profile1.getAvgVisitDisc === .25)
      assert(profile1.getAvgCustSales === 6.00)
      assert(profile1.getAvgCustVisits === 2)
      assert(profile1.getAvgCustUnits === 4)
    }
  }

  test("Cust data into avro") {
    new AvroData {
      val avro = Quantile.createProfileAvro(cust4bins, "customer").get //getOrElse orElseDF

      assert(avro.size() === 4)
      val profile1 = avro.get(0)
      val profile2 = avro.get(1)
      val profile3 = avro.get(2)
      val profile4 = avro.get(3)

      assert(profile1.getTimePeriod === 1)
      assert(profile1.getQuantile === 1)
      assert(profile1.getTotalCount === 10)
      assert(profile1.getTotalVisits === 40)
      assert(profile1.getTotalUnits === 80)
      assert(profile1.getTotalSpend === 120.00)
      assert(profile1.getTotalDisc === 10.00)
      assert(profile1.getAvgDisc === 1.00)
      assert(profile1.getAvgSpend === 12.00)
      assert(profile1.getAvgUnits === 8)
      assert(profile1.getAvgVisits === 4)
      assert(profile1.getAvgVisitSpend === 3.00)
      assert(profile1.getAvgVisitUnits === 2)
      assert(profile1.getAvgVisitDisc === .25)
      assert(profile1.getAvgItemSales === 1.50)
      assert(profile1.getAvgItemDisc === .125)
    }
  }

}
