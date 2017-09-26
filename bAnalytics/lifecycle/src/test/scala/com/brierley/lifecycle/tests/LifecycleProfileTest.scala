package com.brierley.lifecycle.tests

import java.text.SimpleDateFormat

import com.brierley.lifecycle.Lifecycle
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.Row
import org.apache.spark.sql.functions._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import scala.util.{Success, Failure}

/**
  * Created by amerrill on 7/24/17.
  */
@RunWith(classOf[JUnitRunner])
class LifecycleProfileTest extends FunSuite with DataFrameSuiteBase {

  trait fileLocations {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val commaFile = "src/test/resources/lifecycleComma.csv"
    val barFile = "src/test/resources/lifecycleBar.csv"
    val tabFile = "src/test/resources/lifecycleTab.csv"
    val semiFile = "src/test/resources/lifecycleSemi.csv"
    val noHeader = "src/test/resources/noHeaderCommawhitewhiteho.csv"

    val comma = ","
    val bar = "|"
    val tab = "\t"
    val semi = ";"

    val colNames = List("CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "PROD_CAT")

    val rowCount = 5

    val columnName = "PROD_CAT"

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }

  //DECILE TRIM METHOD, data only trimmed from oldest set
  trait TimePeriodData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    //trim Feb - 3 month - 2 periods
    val extraAtEnd = sc.parallelize(List(
      ("CustA", "1/1/2015", "02/01/2016", "txn-1", "detail-1", 15.00, 4, "prodA"),
      ("CustB", "1/1/2015", "03/01/2016", "txn-2", "detail-1", 13.00, 3, "prodB"),
      ("CustC", "1/1/2015", "03/15/2016", "txn-3", "detail-1", 25.00, 2, "prodA"),
      ("CustA", "1/1/2015", "04/01/2016", "txn-4", "detail-1", 22.00, 3, "prodC"),
      ("CustB", "1/1/2015", "04/11/2016", "txn-5", "detail-1", 19.00, 1, "prodD"),
      ("CustC", "1/1/2015", "04/25/2016", "txn-6", "detail-1", 13.00, 5, "prodA"),
      ("CustA", "1/1/2015", "05/15/2016", "txn-7", "detail-1", 15.00, 3, "prodB"),
      ("CustB", "1/1/2015", "05/31/2016", "txn-8", "detail-1", 18.00, 2, "prodE"),
      ("CustC", "1/1/2015", "06/15/2016", "txn-9", "detail-1", 20.00, 4, "prodD"),
      ("CustA", "1/1/2015", "06/30/2016", "txn-10", "detail-1", 15.00, 4, "prodF"),
      ("CustB", "1/1/2015", "07/02/2016", "txn-11", "detail-1", 13.00, 3, "prodB"),
      ("CustC", "1/1/2015", "07/15/2016", "txn-12", "detail-1", 25.00, 2, "prodC"),
      ("CustA", "1/1/2015", "08/01/2016", "txn-13", "detail-1", 22.00, 3, "prodG"),
      ("CustB", "1/1/2015", "08/11/2016", "txn-14", "detail-1", 19.00, 1, "prodFsept")
    )).toDF("CUST_ID", "ENROLL_DATE", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_AMT", "ITEM_QTY", "PROD_CAT")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    //trim March - 3 month
    val extraAtBeg = sc.parallelize(List(
      ("CustC", "1/1/2015", "03/15/2016", "txn-1", "detail-1", 10.00, 3, "prodA"),
      ("CustA", "1/1/2015", "04/01/2016", "txn-2", "detail-1", 10.00, 3, "prodA"),
      ("CustB", "1/1/2015", "04/11/2016", "txn-3", "detail-1", 10.00, 3, "prodA"),
      ("CustC", "1/1/2015", "04/25/2016", "txn-4", "detail-1", 10.00, 3, "prodA"),
      ("CustA", "1/1/2015", "05/15/2016", "txn-5", "detail-1", 10.00, 3, "prodA"),
      ("CustB", "1/1/2015", "05/31/2016", "txn-6", "detail-1", 10.00, 3, "prodA"),
      ("CustC", "1/1/2015", "06/15/2016", "txn-7", "detail-1", 10.00, 3, "prodA"),
      ("CustA", "1/1/2015", "06/30/2016", "txn-8", "detail-1", 10.00, 3, "prodA")
    )).toDF("CUST_ID", "ENROLL_DATE", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_QTY", "ITEM_AMT", "PROD_CAT")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    //no trim - all data
    val noTrim = sc.parallelize(List(
      ("CustA", "02/01/2016", "txn-10", "detail-1", 15.00, 4, "prodA"),
      ("CustB", "02/15/2016", "txn-1", "detail-1", 13.00, 3, "prodA"),
      ("CustC", "03/15/2016", "txn-2", "detail-1", 25.00, 2, "prodA"),
      ("CustA", "04/01/2016", "txn-3", "detail-1", 22.00, 3, "prodA"),
      ("CustB", "04/11/2016", "txn-4", "detail-1", 19.00, 1, "prodA"),
      ("CustC", "04/25/2016", "txn-5", "detail-1", 13.00, 5, "prodA"),
      ("CustA", "05/15/2016", "txn-6", "detail-1", 15.00, 3, "prodA"),
      ("CustB", "05/31/2016", "txn-7", "detail-1", 18.00, 2, "prodA"),
      ("CustC", "06/15/2016", "txn-8", "detail-1", 20.00, 4, "prodA")
    )).toDF("CUST_ID", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_AMT", "ITEM_QTY", "PROD_CAT")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))


    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")

    val colNames = List("CUST_ID", "ENROLL_DATE", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_AMT", "ITEM_QTY", "PROD_CAT", "Date", "TimePeriod")
  }

  trait CustAggData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val noTrim = sc.parallelize(List(
      ("CustA", "1/1/2015", "02/01/2016", "txn-1", "detail-1", 15.00, 4, "prodA", 1),
      ("CustB", "1/1/2015", "03/01/2016", "txn-2", "detail-1", 13.00, 3, "prodA", 1),
      ("CustC", "1/1/2015", "03/15/2016", "txn-3", "detail-1", 25.00, 2, "prodA", 1),
      ("CustA", "1/1/2015", "04/01/2016", "txn-4", "detail-1", 22.00, 3, "prodA", 1),
      ("CustB", "1/1/2015", "04/11/2016", "txn-5", "detail-1", 19.00, 1, "prodA", 1),
      ("CustC", "1/1/2015", "04/25/2016", "txn-6", "detail-1", 13.00, 5, "prodA", 1),
      ("CustA", "1/1/2015", "05/15/2016", "txn-7", "detail-1", 15.00, 3, "prodA", 2),
      ("CustB", "1/1/2015", "05/31/2016", "txn-8", "detail-1", 18.00, 2, "prodA", 2),
      ("CustC", "1/1/2015", "06/15/2016", "txn-9", "detail-1", 20.00, 4, "prodA", 2),
      ("CustA", "1/1/2015", "06/30/2016", "txn-10", "detail-1", 15.00, 4, "prodA", 2),
      ("CustB", "1/1/2015", "07/02/2016", "txn-11", "detail-1", 13.00, 3, "prodA", 2),
      ("CustC", "1/1/2015", "07/15/2016", "txn-12", "detail-1", 25.00, 2, "prodA", 2)
    )).toDF("CUST_ID", "ENROLL_DATE", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_AMT", "ITEM_QTY", "PROD_CAT", "TimePeriod")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")

    val colNames = List("TimePeriod", "CUST_ID", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "daysSince")

    val sdf = new SimpleDateFormat("yyyy-MM-dd")

  }

  trait RFMCalcData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val fiveCustNoTie = sc.parallelize(List(
      ("CustA", 5, 2.toLong, 15.0, 4.toLong, 1),
      ("CustB", 4, 4.toLong, 23.0, 5.toLong, 1),
      ("CustC", 3, 5.toLong, 31.0, 6.toLong, 1),
      ("CustD", 2, 6.toLong, 37.0, 7.toLong, 1),
      ("CustE", 1, 9.toLong, 43.0, 8.toLong, 1)
    )).toDF("CUST_ID", "daysSince", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "TimePeriod")

    val fiveCustRSwitched = sc.parallelize(List(
      ("CustA", 1, 2.toLong, 15.0, 4.toLong, 1),
      ("CustB", 2, 4.toLong, 23.0, 5.toLong, 1),
      ("CustC", 3, 5.toLong, 31.0, 6.toLong, 1),
      ("CustD", 4, 6.toLong, 37.0, 7.toLong, 1),
      ("CustE", 5, 9.toLong, 43.0, 8.toLong, 1)
    )).toDF("CUST_ID", "daysSince", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "TimePeriod")

    val fiveWithMonetaryTie = sc.parallelize(List(
      ("CustA", 5, 2.toLong, 15.0, 4.toLong, 1),
      ("CustB", 4, 4.toLong, 23.0, 5.toLong, 1),
      ("CustC", 3, 5.toLong, 31.0, 6.toLong, 1),
      ("CustD", 2, 6.toLong, 31.0, 7.toLong, 1),
      ("CustE", 1, 9.toLong, 43.0, 8.toLong, 1)
    )).toDF("CUST_ID", "daysSince", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "TimePeriod")

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")

    val colNames = List("CUST_ID", "daysSince", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "TimePeriod",
      "Recency", "F", "M", "RFM")


  }

  trait RFMLabelData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val allMiddleOfRoad = sc.parallelize(List(
      ("CustA", 1, 2.toLong, 15.0, 4.toLong, 1, 3, 4, 4, 344),
      ("CustB", 10, 4.toLong, 23.0, 5.toLong, 1, 4, 5, 2, 452),
      ("CustC", 20, 5.toLong, 31.0, 6.toLong, 1, 2, 3, 3, 234),
      ("CustD", 30, 6.toLong, 31.0, 7.toLong, 1, 4, 3, 2, 432),
      ("CustE", 40, 9.toLong, 43.0, 8.toLong, 1, 3, 5, 3, 353)
    )).toDF("CUST_ID", "daysSince", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "TimePeriod",
      "Recency", "Frequency", "Monetary", "RFM")

    val oneOfEach = sc.parallelize(List(
      ("CustA", 1, 2.toLong, 15.0, 4.toLong, 1, 5, 1, 5, 515),
      ("CustB", 10, 4.toLong, 23.0, 5.toLong, 1, 3, 3, 5, 335),
      ("CustC", 20, 5.toLong, 31.0, 6.toLong, 1, 2, 3, 4, 234),
      ("CustD", 30, 6.toLong, 31.0, 7.toLong, 1, 3, 3, 2, 332),
      ("CustE", 40, 9.toLong, 43.0, 8.toLong, 1, 2, 2, 2, 222)
    )).toDF("CUST_ID", "daysSince", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "TimePeriod",
      "Recency", "Frequency", "Monetary", "RFM")

    val skipMiddle = sc.parallelize(List(
      ("CustA", 1, 2.toLong, 15.0, 4.toLong, 1, 4, 5, 5, 455),
      ("CustB", 30, 4.toLong, 23.0, 5.toLong, 1, 5, 2, 2, 522),
      ("CustC", 60, 5.toLong, 31.0, 6.toLong, 1, 4, 5, 3, 453),
      ("CustD", 70, 6.toLong, 31.0, 7.toLong, 1, 2, 5, 1, 251),
      ("CustE", 80, 9.toLong, 43.0, 8.toLong, 1, 1, 5, 2, 152)
    )).toDF("CUST_ID", "daysSince", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "TimePeriod",
      "Recency", "Frequency", "Monetary", "RFM")

    //SegmentLabels
    val best = "Best in Class"
    val rising = "Rising Stars"
    val middle = "Middle of the Road"
    val lapsing = "Lapsing"
    val deeply = "Deeply Lapsed"


    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")

    val colNames = List("CUST_ID", "daysSince", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "TimePeriod",
      "Recency", "Frequency", "Monetary", "RFM", "Segment")
  }

  trait GlobalCountsData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._


    val singleTP = sc.parallelize(List(
      ("CustA", 1, 2.toLong, 15.0, 4.toLong, 1, 4, 5, 5, 455, "Best in Class"),
      ("CustB", 2, 4.toLong, 23.0, 5.toLong, 1, 5, 2, 2, 522, "Rising Stars"),
      ("CustC", 5, 5.toLong, 31.0, 6.toLong, 1, 4, 5, 3, 453, "Middle of the Road"),
      ("CustD", 12, 6.toLong, 31.0, 7.toLong, 1, 2, 5, 1, 251, "Lapsing"),
      ("CustE", 23, 9.toLong, 43.0, 8.toLong, 1, 1, 5, 2, 152, "Deeply Lapsed")
    )).toDF("CUST_ID", "daysSince", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "TimePeriod",
      "Recency", "Frequency", "Monetary", "RFM", "Segment")

    val twoTPs = sc.parallelize(List(
      ("CustA", 1, 2.toLong, 15.0, 4.toLong, 1, 4, 5, 5, 455, "Best in Class"),
      ("CustB", 2, 4.toLong, 23.0, 5.toLong, 1, 5, 2, 2, 522, "Rising Stars"),
      ("CustC", 3, 5.toLong, 31.0, 6.toLong, 1, 4, 5, 3, 453, "Middle of the Road"),
      ("CustD", 5, 6.toLong, 31.0, 7.toLong, 1, 2, 5, 1, 251, "Lapsing"),
      ("CustE", 12, 9.toLong, 43.0, 8.toLong, 1, 1, 5, 2, 152, "Deeply Lapsed"),
      ("CustA", 1, 2.toLong, 15.0, 4.toLong, 2, 5, 1, 5, 515, "Best in Class"),
      ("CustB", 4, 4.toLong, 23.0, 5.toLong, 2, 3, 3, 5, 335, "Rising Stars"),
      ("CustC", 6, 5.toLong, 31.0, 6.toLong, 2, 2, 3, 4, 234, "Middle of the Road"),
      ("CustD", 12, 7.toLong, 33.0, 7.toLong, 2, 3, 3, 2, 332, "Lapsing"),
      ("CustE", 23, 9.toLong, 43.0, 8.toLong, 2, 2, 2, 2, 222, "Deeply Lapsed")
    )).toDF("CUST_ID", "daysSince", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "TimePeriod",
      "Recency", "Frequency", "Monetary", "RFM", "Segment")

    val twoTPRandomSegments = sc.parallelize(List(
      ("CustA", 1, 2.toLong, 15.0, 4.toLong, 1, 4, 5, 5, 455, "Best in Class"),
      ("CustB", 3, 4.toLong, 23.0, 5.toLong, 1, 5, 2, 2, 522, "Middle of the Road"),
      ("CustC", 5, 5.toLong, 31.0, 6.toLong, 1, 4, 5, 3, 453, "Middle of the Road"),
      ("CustD", 7, 6.toLong, 31.0, 7.toLong, 1, 2, 5, 1, 251, "Middle of the Road"),
      ("CustE", 9, 9.toLong, 43.0, 8.toLong, 1, 1, 5, 2, 152, "Deeply Lapsed"),
      ("CustA", 1, 2.toLong, 15.0, 4.toLong, 2, 5, 1, 5, 515, "Best in Class"),
      ("CustB", 2, 4.toLong, 23.0, 5.toLong, 2, 3, 3, 5, 335, "Lapsing"),
      ("CustC", 4, 5.toLong, 31.0, 6.toLong, 2, 2, 3, 4, 234, "Lapsing"),
      ("CustD", 6, 7.toLong, 33.0, 7.toLong, 2, 3, 3, 2, 332, "Lapsing"),
      ("CustE", 8, 9.toLong, 43.0, 8.toLong, 2, 2, 2, 2, 222, "Deeply Lapsed")
    )).toDF("CUST_ID", "daysSince", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "TimePeriod",
      "Recency", "Frequency", "Monetary", "RFM", "Segment")

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")

    val colNames = List("TimePeriod", "TotalCusts", "TotalTxns", "TotalSpend", "TotalItems")

    val segColNames = List("TimePeriod", "BestCustCount", "BestTxnCount", "BestTxnAmt", "BestItemQty", "BestRecency",
      "RisingCustCount", "RisingTxnCount", "RisingTxnAmt", "RisingItemQty", "RisingRecency",
      "MiddleCustCount", "MiddleTxnCount", "MiddleTxnAmt", "MiddleItemQty", "MiddleRecency",
      "LapsingCustCount", "LapsingTxnCount", "LapsingTxnAmt", "LapsingItemQty", "LapsingRecency",
      "DeeplyCustCount", "DeeplyTxnCount", "DeeplyTxnAmt", "DeeplyItemQty", "DeeplyRecency")
  }

  trait PercentageData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val twoTPs = sc.parallelize(List(
      (1, 1, 2, 15.0, 4, 4, 3, 15, 85.0, 18, 11, 0, 0, 0.0, 0, 0, 1, 9, 43.0, 8, 1),
      (2, 1, 2, 15.0, 4, 5, 0, 0, 0.0, 0, 0, 3, 16, 87.0, 18, 8, 1, 9, 43.0, 8, 2)
    )).toDF("TimePeriod", "BestCustCount", "BestTxnCount", "BestTxnAmt", "BestItemQty", "BestRecency",
      "MiddleCustCount", "MiddleTxnCount", "MiddleTxnAmt", "MiddleItemQty", "MiddleRecency",
      "LapsingCustCount", "LapsingTxnCount", "LapsingTxnAmt", "LapsingItemQty", "LapsingRecency",
      "DeeplyCustCount", "DeeplyTxnCount", "DeeplyTxnAmt", "DeeplyItemQty", "DeeplyRecency")
      .withColumn("RisingCustCount", lit(0))
      .withColumn("RisingTxnCount", lit(0))
      .withColumn("RisingTxnAmt", lit(0))
      .withColumn("RisingItemQty", lit(0))
      .withColumn("RisingRecency", lit(0))


    val globalDF = sc.parallelize(List(
      (1, 5.toLong, 26.toLong, 143.0),
      (2, 5.toLong, 27.toLong, 145.0)
    )).toDF("TimePeriod", "TotalCusts", "TotalTxns", "TotalSpend")

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }

  trait ProductTPData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val singleTP = sc.parallelize(List(
      ("CustA", "1/1/2015", "02/01/2016", "txn-1", "detail-1", 15.00, 4, "prodA", 1),
      ("CustB", "1/1/2015", "03/01/2016", "txn-2", "detail-1", 13.00, 3, "prodA", 1),
      ("CustC", "1/1/2015", "03/15/2016", "txn-3", "detail-1", 25.00, 2, "prodA", 1),
      ("CustD", "1/1/2015", "04/01/2016", "txn-4", "detail-1", 22.00, 3, "prodA", 1),
      ("CustE", "1/1/2015", "04/11/2016", "txn-5", "detail-1", 19.00, 1, "prodA", 1),
      ("CustC", "1/1/2015", "04/25/2016", "txn-6", "detail-1", 13.00, 5, "prodA", 1)
    )).toDF("CUST_ID", "ENROLL_DATE", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_AMT", "ITEM_QTY", "PROD_CAT", "TimePeriod")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val doubleTP = sc.parallelize(List(
      ("CustA", "1/1/2015", "02/01/2016", "txn-1", "detail-1", 15.00, 4, "prodA", 1),
      ("CustB", "1/1/2015", "03/01/2016", "txn-2", "detail-1", 13.00, 3, "prodA", 1),
      ("CustC", "1/1/2015", "03/15/2016", "txn-3", "detail-1", 25.00, 2, "prodA", 1),
      ("CustD", "1/1/2015", "04/01/2016", "txn-4", "detail-1", 22.00, 3, "prodA", 1),
      ("CustE", "1/1/2015", "04/11/2016", "txn-5", "detail-1", 19.00, 1, "prodA", 1),
      ("CustC", "1/1/2015", "04/25/2016", "txn-6", "detail-1", 13.00, 5, "prodA", 1),
      ("CustA", "1/1/2015", "05/15/2016", "txn-7", "detail-1", 15.00, 3, "prodA", 2),
      ("CustB", "1/1/2015", "05/31/2016", "txn-8", "detail-1", 18.00, 2, "prodA", 2),
      ("CustC", "1/1/2015", "06/15/2016", "txn-9", "detail-1", 20.00, 4, "prodA", 2),
      ("CustA", "1/1/2015", "06/30/2016", "txn-10", "detail-1", 15.00, 4, "prodA", 2),
      ("CustE", "1/1/2015", "07/02/2016", "txn-11", "detail-1", 13.00, 3, "prodA", 2),
      ("CustD", "1/1/2015", "07/15/2016", "txn-12", "detail-1", 25.00, 2, "prodA", 2)
    )).toDF("CUST_ID", "ENROLL_DATE", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_AMT", "ITEM_QTY", "PROD_CAT", "TimePeriod")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val columnName = "PROD_CAT"

    val tpColNames = List("TimePeriod", "PROD_CAT", "tpItemAmt", "tpTotalSales")

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }

  trait ProductSegData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val singleTP = sc.parallelize(List(
      ("CustA", "1/1/2015", "02/01/2016", "txn-1", "detail-1", 15.00, 4, "prodA", 1),
      ("CustB", "1/1/2015", "03/01/2016", "txn-2", "detail-1", 13.00, 3, "prodB", 1),
      ("CustC", "1/1/2015", "03/15/2016", "txn-3", "detail-1", 25.00, 2, "prodA", 1),
      ("CustD", "1/1/2015", "04/01/2016", "txn-4", "detail-1", 22.00, 3, "prodA", 1),
      ("CustD", "1/1/2015", "04/11/2016", "txn-5", "detail-1", 19.00, 1, "prodC", 1),
      ("CustC", "1/1/2015", "04/25/2016", "txn-6", "detail-1", 13.00, 5, "prodA", 1)
    )).toDF("CUST_ID", "ENROLL_DATE", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_AMT", "ITEM_QTY", "PROD_CAT", "TimePeriod")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val doubleTP = sc.parallelize(List(
      ("CustA", "1/1/2015", "02/01/2016", "txn-1", "detail-1", 15.00, 4, "prodA", 1),
      ("CustB", "1/1/2015", "03/01/2016", "txn-2", "detail-1", 13.00, 3, "prodA", 1),
      ("CustC", "1/1/2015", "03/15/2016", "txn-3", "detail-1", 25.00, 2, "prodA", 1),
      ("CustD", "1/1/2015", "04/01/2016", "txn-4", "detail-1", 22.00, 3, "prodA", 1),
      ("CustE", "1/1/2015", "04/11/2016", "txn-5", "detail-1", 19.00, 1, "prodB", 1),
      ("CustC", "1/1/2015", "04/25/2016", "txn-6", "detail-1", 13.00, 5, "prodA", 1),
      ("CustA", "1/1/2015", "05/15/2016", "txn-7", "detail-1", 15.00, 3, "prodA", 2),
      ("CustB", "1/1/2015", "05/31/2016", "txn-8", "detail-1", 18.00, 2, "prodA", 2),
      ("CustC", "1/1/2015", "06/15/2016", "txn-9", "detail-1", 20.00, 4, "prodA", 2),
      ("CustA", "1/1/2015", "06/30/2016", "txn-10", "detail-1", 15.00, 4, "prodC", 2),
      ("CustC", "1/1/2015", "07/02/2016", "txn-11", "detail-1", 13.00, 3, "prodA", 2),
      ("CustD", "1/1/2015", "07/15/2016", "txn-12", "detail-1", 25.00, 2, "prodA", 2)
    )).toDF("CUST_ID", "ENROLL_DATE", "TXN_DATE", "TXN_HEADER_ID", "TXN_DETAIL_ID", "ITEM_AMT", "ITEM_QTY", "PROD_CAT", "TimePeriod")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val segSingleTP = sc.parallelize(List(
      ("CustA", 1, 2.toLong, 15.0, 4.toLong, 1, 4, 5, 5, 455, "Best in Class"),
      ("CustB", 2, 4.toLong, 23.0, 5.toLong, 1, 5, 2, 2, 522, "Rising Stars"),
      ("CustC", 5, 5.toLong, 31.0, 6.toLong, 1, 4, 5, 3, 453, "Middle of the Road"),
      ("CustD", 12, 6.toLong, 31.0, 7.toLong, 1, 2, 5, 1, 251, "Lapsing"),
      ("CustE", 23, 9.toLong, 43.0, 8.toLong, 1, 1, 5, 2, 152, "Deeply Lapsed")
    )).toDF("CUST_ID", "daysSince", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "TimePeriod",
      "Recency", "Frequency", "Monetary", "RFM", "Segment")

    val twoTPRandomSegments = sc.parallelize(List(
      ("CustA", 1, 2.toLong, 15.0, 4.toLong, 1, 4, 5, 5, 455, "Best in Class"),
      ("CustB", 3, 4.toLong, 23.0, 5.toLong, 1, 5, 2, 2, 522, "Middle of the Road"),
      ("CustC", 5, 5.toLong, 31.0, 6.toLong, 1, 4, 5, 3, 453, "Middle of the Road"),
      ("CustD", 7, 6.toLong, 31.0, 7.toLong, 1, 2, 5, 1, 251, "Middle of the Road"),
      ("CustE", 9, 9.toLong, 43.0, 8.toLong, 1, 1, 5, 2, 152, "Deeply Lapsed"),
      ("CustA", 1, 2.toLong, 15.0, 4.toLong, 2, 5, 1, 5, 515, "Best in Class"),
      ("CustB", 2, 4.toLong, 23.0, 5.toLong, 2, 3, 3, 5, 335, "Lapsing"),
      ("CustC", 4, 5.toLong, 31.0, 6.toLong, 2, 2, 3, 4, 234, "Lapsing"),
      ("CustD", 6, 7.toLong, 33.0, 7.toLong, 2, 3, 3, 2, 332, "Lapsing"),
      ("CustE", 8, 9.toLong, 43.0, 8.toLong, 2, 2, 2, 2, 222, "Deeply Lapsed")
    )).toDF("CUST_ID", "daysSince", "TXN_COUNT", "TXN_AMT", "ITEM_QTY", "TimePeriod",
      "Recency", "Frequency", "Monetary", "RFM", "Segment")

    val columnName = "PROD_CAT"

    val segColNames = List("TimePeriod", "PROD_CAT", "BestItemAmt", "RisingItemAmt", "MiddleItemAmt", "LapsingItemAmt", "DeeplyItemAmt",
      "BestTotalSales", "RisingTotalSales", "MiddleTotalSales", "LapsingTotalSales", "DeeplyTotalSales")

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }

  trait ProductIndexData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }


  //INITIAL DATA PREPARTAION AND FILE LOADING TESTS
  test("comma delimited load") {
    new fileLocations {
      val orgFile = Lifecycle.loadFile(sqlCtx, comma, commaFile, columnName) getOrElse orElseDF
      assert(orgFile.columns === colNames)
      assert(orgFile.count() === rowCount)
    }
  }

  test("bar delimited load") {
    new fileLocations {
      val orgFile = Lifecycle.loadFile(sqlCtx, bar, barFile, columnName) getOrElse orElseDF
      assert(orgFile.columns === colNames)
      assert(orgFile.count() === rowCount)
    }
  }

  test("tab delimited load") {
    new fileLocations {
      val orgFile = Lifecycle.loadFile(sqlCtx, tab, tabFile, columnName) getOrElse orElseDF
      assert(orgFile.columns === colNames)
      assert(orgFile.count() === rowCount)
    }
  }

  test("semi delimited load") {
    new fileLocations {
      val orgFile = Lifecycle.loadFile(sqlCtx, semi, semiFile, columnName) getOrElse orElseDF
      assert(orgFile.columns === colNames)
      assert(orgFile.count() === rowCount)
    }
  }

  /*test("no header row, should return failure") {
    new fileLocations {
      val orgFile = Lifecycle.loadFile(sqlCtx, comma, noHeader)

      orgFile match {
        case Success(df) => fail("should not have loaded a file")
        case Failure(ex) => assert(true) //should return an error for incorrect file format
      }
    }
  }*/

  //TIME PERIOD CALCULATION TESTS
  test("trim last month, 3 months, 2 time periods") {
    new TimePeriodData {
      val tpDF = Lifecycle.calcTimePeriod(extraAtEnd, 3) getOrElse orElseDF
      assert(tpDF.count() === 13)
      assert(tpDF.select("TimePeriod").distinct().count() === 2)
      assert(tpDF.columns === colNames)
    }
  }

  test("trim first month, 3 months, 1 time period") {
    new TimePeriodData {
      val tpDF = Lifecycle.calcTimePeriod(extraAtBeg, 3) getOrElse orElseDF
      assert(tpDF.count() === 7)
      assert(tpDF.select("TimePeriod").distinct().count() === 1)
    }
  }

  test("no trim, return all") {
    new TimePeriodData {
      val tpDF = Lifecycle.calcTimePeriod(noTrim, 0) getOrElse orElseDF
      assert(tpDF.count() === 9)
      assert(tpDF.select("TimePeriod").distinct().count() === 1)
    }
  }

  //CUSTOMER AGG TEST
  test("3 customers, 2 time periods") {
    new CustAggData {
      val orgFile = Lifecycle.custAgg(noTrim) getOrElse orElseDF
      assert(orgFile.columns === colNames)

      val custA = orgFile.where("CUST_ID = 'CustA' AND TimePeriod = 1").head()
      assert(custA === Row(1, "CustA", 2.toLong, 37.0, 7, 25))


      val custC = orgFile.where("CUST_ID = 'CustC' AND TimePeriod = 2").head()
      assert(custC === Row(2, "CustC", 2.toLong, 45.00, 6, 1))
    }
  }

  //RFM CALCULATION TESTS
  test("5 customers, no ties, ranked the same (111, 222, etc)") {
    new RFMCalcData {
      val orgFile = Lifecycle.calcRFM(fiveCustNoTie) getOrElse orElseDF
      assert(orgFile.columns === colNames)

      val rfmVals = orgFile.select("RFM")
        .map(_ (0)).collect()

      assert(rfmVals === List(111, 222, 333, 444, 555))
    }
  }

  test("5 customers, no ties, recency opposite (511, 422, etc)") {
    new RFMCalcData {
      val orgFile = Lifecycle.calcRFM(fiveCustRSwitched) getOrElse orElseDF
      assert(orgFile.columns === colNames)

      val rfmVals = orgFile.select("RFM")
        .map(_ (0)).collect()

      assert(rfmVals === List(511, 422, 333, 244, 155))
    }
  }

  test("5 customers,monetary tie, should skip a middle bucket") {
    new RFMCalcData {
      val orgFile = Lifecycle.calcRFM(fiveWithMonetaryTie) getOrElse orElseDF
      assert(orgFile.columns === colNames)

      val rfmVals = orgFile.select("RFM")
        .map(_ (0)).collect()

      //two of these should have the same last number
      assert(rfmVals === List(111, 222, 333, 443, 555))
    }
  }

  //RFM SEGRAGATION TESTS
  test("All in the middle") {
    new RFMLabelData {
      val orgFile = Lifecycle.groupRFM(allMiddleOfRoad) getOrElse orElseDF
      assert(orgFile.columns === colNames)
      val labels = orgFile.select("Segment")
        .map(_ (0)).collect()

      assert(labels === List(middle, middle, middle, middle, middle))
    }
  }

  test("One of each segment") {
    new RFMLabelData {
      val orgFile = Lifecycle.groupRFM(oneOfEach) getOrElse orElseDF
      val labels = orgFile.select("Segment")
        .map(_ (0)).collect()
      assert(labels === List(best, rising, middle, lapsing, deeply))
    }
  }

  test("Skip middle of the road, different values for the rest") {
    new RFMLabelData {
      val orgFile = Lifecycle.groupRFM(skipMiddle) getOrElse orElseDF
      val labels = orgFile.select("Segment")
        .map(_ (0)).collect()
      assert(labels === List(best, rising, rising, lapsing, deeply))
    }
  }

  //GLOBAL COUNT TESTS
  test("single timePeriod") {
    new GlobalCountsData {
      val orgDF = Lifecycle.calcGlobalTotals(singleTP) getOrElse orElseDF
      assert(orgDF.columns === colNames)

      val results = orgDF.head()

      assert(results === Row(1, 5.toLong, 26.toLong, 143.0, 30))
    }
  }

  test("2 timePeriods") {
    new GlobalCountsData {
      val orgDF = Lifecycle.calcGlobalTotals(twoTPs) getOrElse orElseDF
      val tp1 = orgDF.where("TimePeriod = 1").head()
      assert(tp1 === Row(1, 5.toLong, 26.toLong, 143.0, 30))

      val tp2 = orgDF.where("TimePeriod = 2").head()
      assert(tp2 === Row(2, 5.toLong, 27.toLong, 145.0, 30))
    }
  }

  //SEGMENT AGG TESTS
  test("5 customers, 2 timeperiods, random segments") {
    new GlobalCountsData {
      val orgFile = Lifecycle.segmentAgg(twoTPRandomSegments) getOrElse orElseDF
      assert(orgFile.columns === segColNames)

      val middle1 = orgFile
        .select("TimePeriod", "MiddleCustCount", "MiddleTxnCount", "MiddleTxnAmt", "MiddleItemQty", "MiddleRecency")
        .where("TimePeriod = 1").head()
      assert(middle1 === Row(1, 3, 15, 85.0, 18, 15))


      val deeply2 = orgFile
        .select("TimePeriod", "DeeplyCustCount", "DeeplyTxnCount", "DeeplyTxnAmt", "DeeplyItemQty", "DeeplyRecency")
        .where("TimePeriod = 2").head()
      assert(deeply2 === Row(2, 1, 9.toLong, 43.0, 8.toLong, 8))

      println(orgFile.where("TimePeriod = 1").head())
      println(orgFile.where("TimePeriod = 2").head())

    }
  }

  //PERCENTAGES TESTS
  test("2 TPs various missing segments") {
    new PercentageData {
      val orgFile = Lifecycle.calcPercentages(twoTPs, globalDF) getOrElse orElseDF
      val tp1 = orgFile
          .select("TimePeriod", "BestCustCount", "BestTxnCount", "BestTxnAmt", "BestRecency",
            "LapsingCustCount", "LapsingTxnCount", "LapsingTxnAmt", "LapsingRecency")
          .where("TimePeriod = 1").head()
      assert(tp1 === Row(1,1,2,15.0,4,0,0,0.0,0))

      val tp2 = orgFile
          .select("TimePeriod", "MiddleCustCount", "MiddleTxnCount", "MiddleTxnAmt", "MiddleRecency",
          "DeeplyCustCount", "DeeplyTxnCount", "DeeplyTxnAmt", "DeeplyRecency")
        .where("TimePeriod = 2").head()
      assert(tp2 === Row(2,0,0,0.0,0,1,9,43.0,2))

      orgFile.show()

    }
  }


  //TIMEPERIOD AGG PRODUCTS TESTS
  test("single TimePeriod") {
    new ProductTPData {
      val prodDF = Lifecycle.tpAggProd(singleTP, columnName) getOrElse orElseDF
      assert(prodDF.columns === tpColNames)

      val totals = prodDF.head()
      assert(totals === Row(1, "prodA", 107.0, 107.0))
    }
  }

  test("2 TimePeriods") {
    new ProductTPData {
      val prodDF = Lifecycle.tpAggProd(doubleTP, columnName) getOrElse orElseDF
      assert(prodDF.columns === tpColNames)

      val totals1 = prodDF.where("TimePeriod = 1").head()
      assert(totals1 === Row(1, "prodA", 107.0, 107.0))

      val totals2 = prodDF.where("TimePeriod = 2").head()
      assert(totals2 === Row(2, "prodA", 106.0, 106.0))
    }
  }


  //SEGMENT AGG PRODUCTS TESTS
  test("singleTimePeriod various segments") {
    new ProductSegData {
      val prodDF = Lifecycle.segAggProd(singleTP, segSingleTP, columnName) getOrElse orElseDF
      assert(prodDF.columns === segColNames)

      val totals = prodDF.head()
      assert(totals === Row(1,"prodA",15.0,0.0,38.0,22.0,0.0,15.0,13.0,38.0,41.0,0.0))
    }
  }

  test("2 TimePeriods various segments") {
    new ProductSegData {
      val prodDF = Lifecycle.segAggProd(doubleTP, twoTPRandomSegments, columnName) getOrElse orElseDF
      assert(prodDF.columns === segColNames)

      val totals1 = prodDF.where("TimePeriod = 1").head()
      assert(totals1 === Row(1,"prodA",15.0,0.0,73.0,0.0,0.0,15.0,0.0,73.0,0.0,19.0))

      val totals2 = prodDF.where("TimePeriod = 2").head()
      assert(totals2 === Row(2,"prodA",15.0,0.0,0.0,76.0,0.0,30.0,0.0,0.0,76.0,0.0))
    }
  }

  //INDEX PRODUCTS TESTS
  test("index products where products exist in more than one segment") {

  }


}