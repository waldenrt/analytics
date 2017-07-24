package com.brierley.balor.tests

import java.util

import com.brierley.avro.schemas.TimePeriodData
import com.brierley.balor.BalorApp
import com.brierley.utils.{OneMonth, TwoWeeks}
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers._

/**
  * Created by amerrill on 1/31/17.
  */

@RunWith(classOf[JUnitRunner])
class BalorTest extends FunSuite with DataFrameSuiteBase {

  trait TimePeriodData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val singleUserJanFebMar2015 = sc.parallelize(List(
      ("customerA", "txn1-custA-Jan1", "01/01/2015", 1, 5.00, 5.00, 2, 5),
      ("customerA", "txn2-custA-Feb1", "02/04/2015", 1, 7.50, 7.50, 1, 5),
      ("customerA", "txn3-custA-Mar1", "03/031/2015", 4, 22.12, 22.12, 5, 2)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "EXTRA1", "EXTRA2")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val singleUserJanFebMar2016 = sc.parallelize(List(
      ("customerA", "txn1-custA-Jan1", "01/01/2016", 1, 5.00, 5.00, 2, 5),
      ("customerA", "txn2-custA-Feb1", "02/04/2016", 1, 7.50, 7.50, 1, 5),
      ("customerA", "txn3-custA-Feb2", "02/29/2016", 3, 14.14, 1.25, 5, 2),
      ("customerA", "txn4-custA-Mar1", "03/01/2016", 4, 22.12, 22.12, 5, 2),
      ("customerA", "txn5-custA-Mar2", "03/31/2016", 8, 325.13, 34.00, 1, 5)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "EXTRA1", "EXTRA2")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val singleUser2weeksFeb2016 = sc.parallelize(List(
      ("customerA", "txn1-custA-Jan1", "01/31/2016", 1, 5.00, 5.00, 2, 5),
      ("customerA", "txn2-custA-Feb1", "02/04/2016", 1, 7.50, 7.50, 1, 5), //3
      ("customerA", "txn3-custA-Feb2", "02/15/2016", 1, 5.00, 5.00, 2, 5), //2
      ("customerA", "txn4-custA-Feb3", "02/28/2016", 1, 5.00, 5.00, 2, 5), //2
      ("customerA", "txn5-custA-Mar1", "03/04/2016", 4, 22.12, 22.12, 5, 2), //1
      ("customerA", "txn6-custA-Mar2", "03/13/2016", 4, 22.12, 22.12, 5, 2) //1
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "EXTRA1", "EXTRA2")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val fourMonthsPlusNewest = sc.parallelize(List(
      ("custZ", "txn1-custZ-Nov1", "11/01/2015", 51.15, 2.14, 2),
      ("custA", "txn1-custA-Nov1", "11/18/2015", 2.18, 1.51, 1),
      ("custB", "txn1-custB-Nov1", "11/18/2015", 3.22, 1.24, 2),
      ("custB", "txn2-custB-Nov2", "11/27/2015", 42.14, 4.42, 5),
      ("custC", "txn1-custC-Nov1", "11/12/2015", 23.51, 4.42, 5),
      ("custD", "txn1-custD-Nov1", "11/12/2015", 235.15, 4.42, 5),
      ("custC", "txn2-custC-Dec1", "12/14/2015", 134.15, 4.42, 5),
      ("custA", "txn2-custA-Dec1", "12/14/2015", 12.41, 4.42, 5),
      ("custA", "txn3-custA-Dec2", "12/22/2015", 154.12, 4.42, 5),
      ("custB", "txn3-custB-Dec1", "12/23/2015", 36.13, 4.42, 5),
      ("custA", "txn4-custA-Jan1", "01/12/2016", 35.12, 4.42, 5),
      ("custE", "txn1-custE-Jan1", "01/13/2016", 643.19, 4.42, 5),
      ("custB", "txn4-custB-Jan1", "01/13/2016", 23.75, 4.42, 5),
      ("custF", "txn1-custF-Jan1", "01/30/2016", 53.98, 4.42, 5),
      ("custB", "txn5-custB-Jan2", "01/30/2016", 87.15, 4.42, 5),
      ("custC", "txn3-custC-Jan1", "01/30/2016", 32.57, 2.14, 2),
      ("custF", "txn2-custF-Jan1", "01/30/2016", 123.53, 2.14, 2),
      ("custC", "txn4-custC-Jan1", "01/30/2016", 43.12, 2.14, 2),
      ("custF", "txn3-custF-Jan1", "01/31/2016", 12.53, 2.14, 2),
      ("custC", "txn5-custC-Feb1", "02/10/2016", 160.23, 2.14, 2),
      ("custF", "txn4-custF-Feb1", "02/24/2016", 234.12, 34.13, 6),
      ("custB", "txn6-custB-Feb1", "02/29/2016", 243.14, 42.14, 8),
      ("custA", "txn4-custA-Mar1", "03/03/2016", 12.42, 1.24, 2),
      ("custC", "txn6-custC-Mar1", "03/15/2016", 23.14, 3.21, 5)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "DISC_AMT", "ITEM_QTY")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val fourMonthsPlusOldest = sc.parallelize(List(
      ("custX", "txn1-custX-Oct1", "10/07/2015", 31.53, 12.12, 6),
      ("custZ", "txn1-custZ-Oct1", "10/23/2015", 42.12, 1.21, 4),
      ("custZ", "txn2-custZ-Nov1", "11/01/2015", 51.15, 2.14, 2),
      ("custA", "txn1-custA-Nov1", "11/18/2015", 2.18, 1.51, 1),
      ("custB", "txn1-custB-Nov1", "11/18/2015", 3.22, 1.24, 2),
      ("custB", "txn2-custB-Nov2", "11/27/2015", 42.14, 4.42, 5),
      ("custC", "txn1-custC-Nov1", "11/12/2015", 23.51, 4.42, 5),
      ("custD", "txn1-custD-Nov1", "11/12/2015", 235.15, 4.42, 5),
      ("custC", "txn2-custC-Dec1", "12/14/2015", 134.15, 4.42, 5),
      ("custA", "txn2-custA-Dec1", "12/14/2015", 12.41, 4.42, 5),
      ("custA", "txn3-custA-Dec2", "12/22/2015", 154.12, 4.42, 5),
      ("custB", "txn3-custB-Dec1", "12/23/2015", 36.13, 4.42, 5),
      ("custA", "txn4-custA-Jan1", "01/12/2016", 35.12, 4.42, 5),
      ("custE", "txn1-custE-Jan1", "01/13/2016", 643.19, 4.42, 5),
      ("custB", "txn4-custB-Jan1", "01/13/2016", 23.75, 4.42, 5),
      ("custF", "txn1-custF-Jan1", "01/30/2016", 53.98, 4.42, 5),
      ("custB", "txn5-custB-Jan2", "01/30/2016", 87.15, 4.42, 5),
      ("custC", "txn3-custC-Jan1", "01/30/2016", 32.57, 2.14, 2),
      ("custF", "txn2-custF-Jan1", "01/30/2016", 123.53, 2.14, 2),
      ("custC", "txn4-custC-Jan1", "01/30/2016", 43.12, 2.14, 2),
      ("custF", "txn3-custF-Jan1", "01/30/2016", 12.53, 2.14, 2),
      ("custC", "txn5-custC-Feb1", "02/10/2016", 160.23, 2.14, 2),
      ("custF", "txn4-custF-Feb1", "02/24/2016", 234.12, 34.13, 6),
      ("custB", "txn6-custB-Feb1", "02/29/2016", 243.14, 42.14, 8)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "DISC_AMT", "ITEM_QTY")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

    val twoMonthsPlus = sc.parallelize(List(
      ("custZ", "txn1-custZ-Nov1", "11/01/2015", 51.15, 2.14, 2),
      ("custA", "txn1-custA-Nov1", "11/18/2015", 2.18, 1.51, 1),
      ("custB", "txn1-custB-Nov1", "11/18/2015", 3.22, 1.24, 2),
      ("custB", "txn2-custB-Nov2", "11/27/2015", 42.14, 4.42, 5),
      ("custC", "txn1-custC-Nov1", "11/12/2015", 23.51, 4.42, 5),
      ("custD", "txn1-custD-Nov1", "11/12/2015", 235.15, 4.42, 5),
      ("custC", "txn2-custC-Dec1", "12/14/2015", 134.15, 4.42, 5),
      ("custA", "txn2-custA-Dec1", "12/14/2015", 12.41, 4.42, 5),
      ("custA", "txn3-custA-Dec2", "12/22/2015", 154.12, 4.42, 5),
      ("custB", "txn3-custB-Dec1", "12/23/2015", 36.13, 4.42, 5),
      ("custA", "txn4-custA-Jan1", "01/12/2016", 35.12, 4.42, 5),
      ("custE", "txn1-custE-Jan1", "01/13/2016", 643.19, 4.42, 5),
      ("custB", "txn4-custB-Jan1", "01/13/2016", 23.75, 4.42, 5)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "DISC_AMT", "ITEM_QTY")
      .withColumn("Date", to_date(unix_timestamp($"TXN_DATE", "MM/dd/yyyy").cast("timestamp")))

  }

  trait ClassLabelData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    //week cadence for density and ease -Jul 31, Aug 7, 14, 21, 28 2016 (sun - sat week)

    val oneNewLapsedDeeplyLapsedReturningReact = sc.parallelize(List(
      ("lapsed3", "usedToBe2", 3, 7.50, 1.25, 3), //wont be added to current balor
      ("return1", "usedToBe1", 5, 12.50, 2.50, 3),
      ("react1", "usedToBe3", 4, 10.00, 2.50, 3),
      ("lapsed1", "usedToBe1", 5, 12.50, 2.50, 2),
      ("return1", "usedToBe3", 3, 7.50, 1.25, 2),
      ("new1", "usedToBe3", 4, 10.00, 0.00, 1),
      ("react1", "usedToBe1", 6, 15.00, 5.00, 1),
      ("return1", "usedToBe1", 6, 15.00, 2.50, 1)
    )).toDF("CUST_ID", "TXN_ID", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod")

    val threeOverTwo = sc.parallelize(List(
      ("lapsed1", "usedToBe2", 4, 10.00, 2.50, 3),
      ("lapsed3", "usedToBe1", 3, 7.50, 1.25, 3), //wont be added to current balor
      ("return1", "usedToBe1", 5, 12.50, 2.50, 3),
      ("react1", "usedToBe1", 4, 10.00, 2.50, 3),
      ("lapsed1", "usedToBe2", 5, 12.50, 2.50, 2),
      ("lapsed2", "usedToBe3", 4, 10.00, 0.00, 2),
      ("return1", "usedToBe2", 3, 7.50, 1.25, 2),
      ("new1", "usedToBe1", 4, 10.00, 0.00, 1),
      ("new2", "usedToBe2", 5, 12.50, 2.50, 1),
      ("react1", "usedToBe1", 6, 15.00, 5.00, 1),
      ("return1", "usedToBe2", 6, 15.00, 2.50, 1)
    )).toDF("CUST_ID", "TXN_ID", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod")

    val multiTransInSinglePeriod = sc.parallelize(List(
      ("lapsed3", "usedToBe1", 3, 7.50, 1.25, 3), //wont be added to current balor
      ("return1", "usedToBe2", 5, 12.50, 2.50, 3),
      ("react1", "usedToBe1", 4, 10.00, 2.50, 3),
      ("lapsed1", "usedToBe2", 5, 12.50, 2.50, 2),
      ("return1", "usedToBe1", 3, 7.50, 1.25, 2),
      ("new1", "usedToBe2", 4, 10.00, 0.00, 1),
      ("react1", "usedToBe3", 6, 15.00, 5.00, 1),
      ("return1", "usedToBe1", 6, 15.00, 2.50, 1)
    )).toDF("CUST_ID", "TXN_ID", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod")

    val multiTransInSinglePeriodNewOutside = sc.parallelize(List(
      ("new1", "usedToBe1", 2, 5.00, 1.00, 4), //shouldn't be in current balor
      ("lapsed3", "usedToBe2", 3, 7.50, 1.25, 3), //wont be added to current balor
      ("return1", "usedToBe2", 5, 12.50, 2.50, 3),
      ("react1", "usedToBe2", 4, 10.00, 2.50, 3),
      ("lapsed1", "usedToBe1", 4, 10.00, 1.25, 2),
      ("return1", "usedToBe1", 3, 7.50, 1.25, 2),
      ("new1", "usedToBe2", 4, 10.00, 0.00, 1),
      ("react1", "usedToBe3", 6, 15.00, 5.00, 1),
      ("return1", "usedToBe2", 6, 15.00, 2.50, 1)
    )).toDF("CUST_ID", "TXN_ID", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod")


  }

  trait CountData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val singleSimpleBalorSet = sc.parallelize(List(
      ("lapsed3", 1, 3, 7.50, 1.25, 3, "None"), //wont be added to current balor
      ("return1", 1, 5, 12.50, 2.50, 3, "None"),
      ("react1", 1, 4, 10.00, 2.50, 3, "None"),
      ("lapsed1", 1, 5, 12.50, 2.50, 2, "Lapsed"),
      ("return1", 1, 3, 7.50, 1.25, 2, "Returning"),
      ("new1", 1, 4, 10.00, 0.00, 1, "New"),
      ("react1", 1, 6, 15.00, 5.00, 1, "Reactivated"),
      ("return1", 1, 6, 15.00, 2.50, 1, "Returning")
    )).toDF("CUST_ID", "TXN_COUNT", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod", "Label")

    val missingDisc = sc.parallelize(List(
      ("lapsed3", 1, 3, 7.50, 1.25, 3, "None"), //wont be added to current balor
      ("return1", 1, 5, 12.50, 2.50, 3, "None"),
      ("react1", 1, 4, 10.00, 2.50, 3, "None"),
      ("lapsed1", 1, 5, 12.50, 2.50, 2, "Lapsed"),
      ("return1", 1, 3, 7.50, 1.25, 2, "Returning"),
      ("new1", 1, 4, 10.00, 0.00, 1, "New"),
      ("react1", 1, 6, 15.00, 5.00, 1, "Reactivated"),
      ("return1", 1, 6, 15.00, 0.00, 1, "Returning")
    )).toDF("CUST_ID", "TXN_COUNT", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod", "Label")

    val missingItems = sc.parallelize(List(
      ("lapsed3", 1, 3, 7.50, 1.25, 3, "None"), //wont be added to current balor
      ("return1", 1, 5, 12.50, 2.50, 3, "None"),
      ("react1", 1, 4, 10.00, 2.50, 3, "None"),
      ("lapsed1", 1, 5, 12.50, 2.50, 2, "Lapsed"),
      ("return1", 1, 0, 7.50, 1.25, 2, "Returning"),
      ("new1", 1, 4, 10.00, 0.00, 1, "New"),
      ("react1", 1, 0, 15.00, 5.00, 1, "Reactivated"),
      ("return1", 1, 6, 15.00, 2.50, 1, "Returning")
    )).toDF("CUST_ID", "TXN_COUNT", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod", "Label")

    val nullItemsAndDisc = sc.parallelize(List(
      ("lapsed3", 1, 0, 7.50, 0.00, 3, "None"), //wont be added to current balor
      ("return1", 1, 0, 12.50, 0.00, 3, "None"),
      ("react1", 1, 0, 10.00, 0.00, 3, "None"),
      ("lapsed1", 1, 0, 12.50, 0.00, 2, "Lapsed"),
      ("return1", 1, 0, 7.50, 0.00, 2, "Returning"),
      ("new1", 1, 0, 10.00, 0.00, 1, "New"),
      ("react1", 1, 0, 15.00, 0.00, 1, "Reactivated"),
      ("return1", 1, 0, 15.00, 0.00, 1, "Returning")
    )).toDF("CUST_ID", "TXN_COUNT", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod", "Label")

    val fourMonthsPlusOldest = sc.parallelize(List(
      ("custX", 1, 31.53, 12.12, 6, 5, "New"),
      ("custZ", 1, 42.12, 1.21, 4, 5, "New"),
      ("custZ", 1, 51.15, 2.14, 2, 4, "Returning"),
      ("custA", 1, 2.18, 1.51, 1, 4, "New"),
      ("custB", 1, 42.14, 4.42, 5, 4, "New"),
      ("custC", 1, 23.51, 4.42, 5, 4, "New"),
      ("custD", 1, 235.15, 4.42, 5, 4, "New"),
      ("custC", 1, 134.15, 4.42, 5, 3, "Returning"),
      ("custA", 1, 154.12, 4.42, 5, 3, "Returning"),
      ("custB", 1, 36.13, 4.42, 5, 3, "Returning"),
      ("custA", 1, 35.12, 4.42, 5, 2, "Returning"), //lapsed for next period
      ("custE", 1, 643.19, 4.42, 5, 2, "New"),
      ("custB", 1, 23.75, 4.42, 5, 2, "Returning"),
      ("custC", 1, 43.12, 2.14, 2, 2, "Returning"),
      ("custF", 1, 12.53, 2.14, 2, 2, "New"),
      ("custC", 1, 160.23, 2.14, 2, 1, "Returning"),
      ("custF", 1, 234.12, 34.13, 6, 1, "Returning"),
      ("custB", 1, 243.14, 42.14, 8, 1, "Returning")
    )).toDF("CUST_ID", "TXN_COUNT", "TXN_AMT", "DISC_AMT", "ITEM_QTY", "TimePeriod", "Label")

    val countSchema = StructType(Array(
      StructField("Label", StringType, true),
      StructField("CustCount", LongType, true),
      StructField("TxnCount", LongType, true),
      StructField("TtlSpend", DoubleType, true),
      StructField("TtlDisc", DoubleType, true),
      StructField("ItemCount", LongType, true)
    ))


  }

  trait SegAvg {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val oneTimePeriod = sc.parallelize(List(
      (1, 2, 3, 30.00, 0.00, 12, 1, 1, 15.00, 5.00, 6, 1, 1, 15.00, 2.50, 6, 2, 2, 25.00, 2.50, 10)
    )).toDF("TimePeriod", "newCustCount", "newTxnCount", "newTxnAmt", "newDiscAmt", "newItemCount",
      "reactCustCount", "reactTxnCount", "reactTxnAmt", "reactDiscAmt", "reactItemCount",
      "returnCustCount", "returnTxnCount", "returnTxnAmt", "returnDiscAmt", "returnItemCount",
      "lapsedCustCount", "lapsedTxnCount", "lapsedTxnAmt", "lapsedDiscAmt", "lapsedItemCount")

    val zeroReturning = sc.parallelize(List(
      (1, 2.toLong, 3.toLong, 30.00, 0.00, 12, 1.toLong, 1.toLong, 15.00, 5.00, 6, 0.toLong, 0.toLong, 0.00, 0.00, 0, 2.toLong, 2.toLong, 25.00, 2.50, 10),
      (2, 1.toLong, 1.toLong, 10.00, 0.00, 4, 1.toLong, 1.toLong, 15.00, 5.00, 6, 1.toLong, 1.toLong, 15.00, 2.50, 6, 1.toLong, 1.toLong, 12.50, 2.50, 5)
    )).toDF("TimePeriod", "newCustCount", "newTxnCount", "newTxnAmt", "newDiscAmt", "newItemCount",
      "reactCustCount", "reactTxnCount", "reactTxnAmt", "reactDiscAmt", "reactItemCount",
      "returnCustCount", "returnTxnCount", "returnTxnAmt", "returnDiscAmt", "returnItemCount",
      "lapsedCustCount", "lapsedTxnCount", "lapsedTxnAmt", "lapsedDiscAmt", "lapsedItemCount")

    val threeTimePeriods = sc.parallelize(List(
      (1, 2.toLong, 3.toLong, 30.00, 0.00, 12, 1.toLong, 1.toLong, 15.00, 5.00, 6, 0.toLong, 0.toLong, 0.00, 0.00, 0, 2.toLong, 2.toLong, 25.00, 2.50, 10),
      (2, 1.toLong, 1.toLong, 10.00, 0.00, 4, 1.toLong, 1.toLong, 15.00, 5.00, 6, 1.toLong, 1.toLong, 15.00, 2.50, 6, 1.toLong, 1.toLong, 12.50, 2.50, 5),
      (3, 3.toLong, 8.toLong, 45.00, 6.00, 0, 5.toLong, 15.toLong, 60.00, 0.00, 0, 4.toLong, 4.toLong, 12.00, 0.00, 0, 2.toLong, 9.toLong, 24.00, 4.00, 0)
    )).toDF("TimePeriod", "newCustCount", "newTxnCount", "newTxnAmt", "newDiscAmt", "newItemCount",
      "reactCustCount", "reactTxnCount", "reactTxnAmt", "reactDiscAmt", "reactItemCount",
      "returnCustCount", "returnTxnCount", "returnTxnAmt", "returnDiscAmt", "returnItemCount",
      "lapsedCustCount", "lapsedTxnCount", "lapsedTxnAmt", "lapsedDiscAmt", "lapsedItemCount")
  }

  trait BalorData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    //purposely leaving out the 28 avg columns for ease of testing this portion
    val twoOverOne = sc.parallelize(List(
      (1, 1, 1, 10.00, 0.00, 4, 1, 1, 15.00, 5.00, 6, 1, 1, 15.00, 2.50, 6, 1, 1, 12.50, 2.50, 5)
    )).toDF("TimePeriod", "newCustCount", "newTxnCount", "newTxnAmt", "newDiscAmt", "newItemCount",
      "reactCustCount", "reactTxnCount", "reactTxnAmt", "reactDiscAmt", "reactItemCount",
      "returnCustCount", "returnTxnCount", "returnTxnAmt", "returnDiscAmt", "returnItemCount",
      "lapsedCustCount", "lapsedTxnCount", "lapsedTxnAmt", "lapsedDiscAmt", "lapsedItemCount")

    val threeOverTwo = sc.parallelize(List(
      (1, 2, 3, 30.00, 0.00, 12, 1, 1, 15.00, 5.00, 6, 1, 1, 15.00, 2.50, 6, 2, 2, 25.00, 2.50, 10)
    )).toDF("TimePeriod", "newCustCount", "newTxnCount", "newTxnAmt", "newDiscAmt", "newItemCount",
      "reactCustCount", "reactTxnCount", "reactTxnAmt", "reactDiscAmt", "reactItemCount",
      "returnCustCount", "returnTxnCount", "returnTxnAmt", "returnDiscAmt", "returnItemCount",
      "lapsedCustCount", "lapsedTxnCount", "lapsedTxnAmt", "lapsedDiscAmt", "lapsedItemCount")

    val twoBalorSets = sc.parallelize(List(
      (1, 2, 3, 30.00, 0.00, 12, 1, 1, 15.00, 5.00, 6, 1, 1, 15.00, 2.50, 6, 2, 2, 25.00, 2.50, 10),
      (2, 1, 1, 10.00, 0.00, 4, 1, 1, 15.00, 5.00, 6, 1, 1, 15.00, 2.50, 6, 1, 1, 12.50, 2.50, 5)
    )).toDF("TimePeriod", "newCustCount", "newTxnCount", "newTxnAmt", "newDiscAmt", "newItemCount",
      "reactCustCount", "reactTxnCount", "reactTxnAmt", "reactDiscAmt", "reactItemCount",
      "returnCustCount", "returnTxnCount", "returnTxnAmt", "returnDiscAmt", "returnItemCount",
      "lapsedCustCount", "lapsedTxnCount", "lapsedTxnAmt", "lapsedDiscAmt", "lapsedItemCount")

    val threeBalorSetsOneOverZero = sc.parallelize(List(
      (1, 2.toLong, 3.toLong, 30.00, 0.00, 12, 1.toLong, 1.toLong, 15.00, 5.00, 6, 1.toLong, 1.toLong, 15.00, 2.50, 6, 2.toLong, 2.toLong, 25.00, 2.50, 10),
      (2, 1.toLong, 1.toLong, 10.00, 0.00, 4, 1.toLong, 1.toLong, 15.00, 5.00, 6, 1.toLong, 1.toLong, 15.00, 2.50, 6, 1.toLong, 1.toLong, 12.50, 2.50, 5),
      (3, 1.toLong, 1.toLong, 10.00, 0.00, 4, 1.toLong, 1.toLong, 15.00, 5.00, 6, 2.toLong, 2.toLong, 15.00, 2.50, 6, 0.toLong, 1.toLong, 12.50, 2.50, 5)
    )).toDF("TimePeriod", "newCustCount", "newTxnCount", "newTxnAmt", "newDiscAmt", "newItemCount",
      "reactCustCount", "reactTxnCount", "reactTxnAmt", "reactDiscAmt", "reactItemCount",
      "returnCustCount", "returnTxnCount", "returnTxnAmt", "returnDiscAmt", "returnItemCount",
      "lapsedCustCount", "lapsedTxnCount", "lapsedTxnAmt", "lapsedDiscAmt", "lapsedItemCount")

    val zeroReturning = sc.parallelize(List(
      (1, 2.toLong, 3.toLong, 30.00, 0.00, 12, 1.toLong, 1.toLong, 15.00, 5.00, 6, 0.toLong, 0.toLong, 0.00, 0.00, 0, 2.toLong, 2.toLong, 25.00, 2.50, 10),
      (2, 1.toLong, 1.toLong, 10.00, 0.00, 4, 1.toLong, 1.toLong, 15.00, 5.00, 6, 1.toLong, 1.toLong, 15.00, 2.50, 6, 1.toLong, 1.toLong, 12.50, 2.50, 5)
    )).toDF("TimePeriod", "newCustCount", "newTxnCount", "newTxnAmt", "newDiscAmt", "newItemCount",
      "reactCustCount", "reactTxnCount", "reactTxnAmt", "reactDiscAmt", "reactItemCount",
      "returnCustCount", "returnTxnCount", "returnTxnAmt", "returnDiscAmt", "returnItemCount",
      "lapsedCustCount", "lapsedTxnCount", "lapsedTxnAmt", "lapsedDiscAmt", "lapsedItemCount")


    val avroSchemaResults = sc.parallelize(List(
      (1, 2.toLong, 3.toLong, 40.00, 5.00, 6.toLong, 7.toLong, 8.toLong, 90.00, 10.00, 11.toLong, 12.toLong, 13.toLong, 140.00, 15.0, 16.toLong, 17.toLong, 18.toLong, 190.00, 20.00, 21.toLong),
      (2, 23.toLong, 24.toLong, 25.00, 0.00, 27.toLong, 28.toLong, 29.toLong, 30.00, 31.00, 32.toLong, 3.toLong, 4.toLong, 35.00, 36.00, 37.toLong, 38.toLong, 39.toLong, 40.00, 2.50, 42.toLong),
      (3, 44.toLong, 45.toLong, 46.00, 0.00, 48.toLong, 49.toLong, 50.toLong, 15.00, 52.00, 53.toLong, 54.toLong, 55.toLong, 56.00, 57.50, 58.toLong, 0.toLong, 60.toLong, 61.50, 62.50, 63.toLong)
    )).toDF("TimePeriod", "newCustCount", "newTxnCount", "newTxnAmt", "newDiscAmt", "newItemCount",
      "reactCustCount", "reactTxnCount", "reactTxnAmt", "reactDiscAmt", "reactItemCount",
      "returnCustCount", "returnTxnCount", "returnTxnAmt", "returnDiscAmt", "returnItemCount",
      "lapsedCustCount", "lapsedTxnCount", "lapsedTxnAmt", "lapsedDiscAmt", "lapsedItemCount")

    val minMaxDateDF = sc.parallelize(List(
      ("2015-03-03", "2015-06-06")
    )).toDF("min", "max")
      .withColumn("min(Date)", $"min".cast("date"))
      .withColumn("max(Date)", $"max".cast("date"))
      .select("min(Date)", "max(Date)")

  }

  //TIME PERIOD TESTS
  test("TimePeriod column added, extra columns dropped") {
    new TimePeriodData {
      val periodDF = BalorApp.calcTimePeriod(singleUserJanFebMar2015, TwoWeeks) getOrElse singleUserJanFebMar2015
      val periodColumns = periodDF.columns
      val columns = Array("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date", "TimePeriod")

      assert(periodColumns === columns)
    }

  }

  test("Time Period (month) around daylight savings start") {
    new TimePeriodData {
      val periodDF = BalorApp.calcTimePeriod(singleUserJanFebMar2015, OneMonth) getOrElse singleUserJanFebMar2015
      val timePeriods = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(timePeriods === List(1, 2, 3))

      val janPeriod = periodDF
        .where("TXN_AMT = 5.0")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(janPeriod === 3)

      val febPeriod = periodDF
        .where("TXN_AMT = 7.5")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(febPeriod === 2)

      val marPeriod = periodDF
        .where("TXN_AMT = 22.12")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(marPeriod === 1)
    }
  }

  test("Time Period (month) breaks around Leap Day") {
    new TimePeriodData {
      val periodDF = BalorApp.calcTimePeriod(singleUserJanFebMar2016, OneMonth) getOrElse singleUserJanFebMar2015
      val timePeriods = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(timePeriods === List(1, 1, 2, 2, 3))

      val janPeriod = periodDF
        .where("TXN_AMT = 5.0")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(janPeriod === 3)

      val febPeriod = periodDF
        .where("TXN_AMT = 14.14")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(febPeriod === 2)

      val febPeriod2 = periodDF
        .where("TXN_AMT = 7.5")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(febPeriod2 === 2)

      val marPeriod = periodDF
        .where("TXN_AMT = 22.12")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(marPeriod === 1)

      val marPeriod2 = periodDF
        .where("TXN_AMT = 325.13")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(marPeriod2 === 1)
    }
  }

  test("Time Period (2 weeks) breaks around Leap Day") {
    new TimePeriodData {
      val periodDF = BalorApp.calcTimePeriod(singleUser2weeksFeb2016, TwoWeeks) getOrElse singleUserJanFebMar2015
      val timePeriods = periodDF.select("TimePeriod").map(_ (0)).collect()

      //jan 31 entry should be dropped
      assert(periodDF.count() === 5)
      assert(timePeriods === List(1, 1, 2, 2, 3))

    }
  }

  test("Time Period = month, 4 months of data, with newest being extra") {
    new TimePeriodData {
      val periodDF = BalorApp.calcTimePeriod(fourMonthsPlusNewest, OneMonth) getOrElse singleUserJanFebMar2015
      val period4 = periodDF.where("TimePeriod = 4").select("TimePeriod").count()
      val period3 = periodDF.where("TimePeriod = 3").select("TimePeriod").count()
      val period2 = periodDF.where("TimePeriod = 2").select("TimePeriod").count()
      val period1 = periodDF.where("TimePeriod = 1").select("TimePeriod").count()

      assert(periodDF.count() === 22)
      assert(period1 === 3)
      assert(period2 === 9)
      assert(period3 === 4)
      assert(period4 === 6)
    }

  }

  test("Time Period = month, 4 months of data, with oldest being extra") {
    new TimePeriodData {
      val periodDF = BalorApp.calcTimePeriod(fourMonthsPlusOldest, OneMonth) getOrElse singleUserJanFebMar2015
      val period4 = periodDF.where("TimePeriod = 4").select("TimePeriod").count()
      val period3 = periodDF.where("TimePeriod = 3").select("TimePeriod").count()
      val period2 = periodDF.where("TimePeriod = 2").select("TimePeriod").count()
      val period1 = periodDF.where("TimePeriod = 1").select("TimePeriod").count()

      assert(periodDF.count() === 22)
      assert(period1 === 3)
      assert(period2 === 9)
      assert(period3 === 4)
      assert(period4 === 6)
    }
  }

  test("Time Period = month 2 1/2 months of data") {
    new TimePeriodData {
      val periodDF = BalorApp.calcTimePeriod(twoMonthsPlus, OneMonth) getOrElse singleUserJanFebMar2015
      val timePeriods = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(periodDF.count() === 10)
      assert(timePeriods === List(1, 1, 1, 1, 2, 2, 2, 2, 2, 2))
    }

  }

  //CLASSIFICATION TESTS
  test("Label column added") {
    new ClassLabelData {
      val labelDF = BalorApp.assignSegmentLabel(threeOverTwo) getOrElse threeOverTwo
      val labelCols = labelDF.columns
      val columns = Array("TimePeriod", "Label", "CUST_ID", "TXN_COUNT", "TXN_AMT", "DISC_AMT", "ITEM_QTY")

      assert(labelCols === columns)
    }
  }

  test("One of each label, single transaction per period") {
    new ClassLabelData {
      val labelDF = BalorApp.assignSegmentLabel(oneNewLapsedDeeplyLapsedReturningReact) getOrElse threeOverTwo
      val labelCol = labelDF.filter(labelDF("TimePeriod") === 1).select("Label").map(_(0)).collect()

      val labels = Array("New", "Reactivated", "Returning", "Lapsed")

      assert(labelCol === labels)

      assert(labelDF.count === 11)
    }
  }

  test("Multiple of each label") {
    new ClassLabelData {
      val labelDF = BalorApp.assignSegmentLabel(threeOverTwo) getOrElse threeOverTwo
      val labelCol = labelDF.select("Label").map(_ (0)).collect()

      val labels = Array("New", "New", "Reactivated", "Returning", "Lapsed", "Lapsed", "Returning", "New", "Returning",
        "Lapsed", "Lapsed", "New", "New", "New", "New")

      assert(labelCol === labels)

      assert(labelDF.count === 15)
    }
  }

  test("New customer, multiple txn in latest period") {
    new ClassLabelData {
      val labelDF = BalorApp.assignSegmentLabel(multiTransInSinglePeriod) getOrElse threeOverTwo
      val labelCol = labelDF.select("Label").map(_ (0)).collect()

      assert(labelDF.count === 11)
    }
  }

  test("New customer, really old txn (outside of balor range), and single new") {
    new ClassLabelData {
      val labelDF = BalorApp.assignSegmentLabel(multiTransInSinglePeriodNewOutside) getOrElse threeOverTwo
      val labelCol = labelDF.select("Label").map(_ (0)).collect()

      val labels = Array("New", "Reactivated", "Returning", "Lapsed", "New", "Returning", "Lapsed", "Lapsed", "New", "New",
        "New", "Lapsed", "New")

      assert(labelCol === labels)

      assert(labelDF.count === 13)
    }
  }

  //COUNTS
  test("Count pivot columns added to DF") {
    new CountData {
      val countDF = BalorApp.counts(singleSimpleBalorSet) getOrElse singleSimpleBalorSet
      val countCols = countDF.columns

      val columns = Array("TimePeriod", "newCustCount", "newTxnCount", "newTxnAmt", "newDiscAmt", "newItemCount",
        "reactCustCount", "reactTxnCount", "reactTxnAmt", "reactDiscAmt", "reactItemCount",
        "returnCustCount", "returnTxnCount", "returnTxnAmt", "returnDiscAmt", "returnItemCount",
        "lapsedCustCount", "lapsedTxnCount", "lapsedTxnAmt", "lapsedDiscAmt", "lapsedItemCount")

      assert(countCols === columns)
    }
  }

  test("counts correctly given 1 complete balor time period") {
    new CountData {

      val countDF = BalorApp.counts(singleSimpleBalorSet) getOrElse singleSimpleBalorSet
      val counts = countDF
        .select("*")
        .where(countDF("TimePeriod") === 1)
        .head()

      val realCounts = Row(1, 1, 1, 10.0, 0.0, 4, 1, 1, 15.0, 5.0, 6, 1, 1, 15.0, 2.5, 6, 0, null, null, null, null)

      assert(counts === realCounts)
    }
  }

  test("counts correctly given 4 time periods, results in 2 Balor result sets") {
    new CountData {

      //TODO fill out test.....
      assertDataFrameEquals(singleSimpleBalorSet, singleSimpleBalorSet)

      val countDF = BalorApp.counts(fourMonthsPlusOldest)
    }
    //don't know if this will be tested here or somewhere else, it will need to be done iteratively with the label application
  }

  //SEGMENT METRICS
  //need to have various ones divide by zero, disc and item is optional so that needs to be reflected (columns created with 0)
  test("segment metrics for a single time period") {
    new SegAvg {

      val avgDF = BalorApp.calcSegAvg(oneTimePeriod) getOrElse oneTimePeriod
      assert(avgDF != oneTimePeriod)
      val avgCols = avgDF.columns
      val columns = Array("TimePeriod", "newCustCount", "newTxnCount", "newTxnAmt", "newDiscAmt", "newItemCount",
        "reactCustCount", "reactTxnCount", "reactTxnAmt", "reactDiscAmt", "reactItemCount",
        "returnCustCount", "returnTxnCount", "returnTxnAmt", "returnDiscAmt", "returnItemCount",
        "lapsedCustCount", "lapsedTxnCount", "lapsedTxnAmt", "lapsedDiscAmt", "lapsedItemCount",
        "newCustSpendAvg", "newCustVisitAvg", "newCustItemAvg", "newCustDiscAvg", "newVisitSpendAvg", "newVisitItemAvg", "newVisitDiscAvg",
        "reactCustSpendAvg", "reactCustVisitAvg", "reactCustItemAvg", "reactCustDiscAvg", "reactVisitSpendAvg", "reactVisitItemAvg", "reactVisitDiscAvg",
        "returnCustSpendAvg", "returnCustVisitAvg", "returnCustItemAvg", "returnCustDiscAvg", "returnVisitSpendAvg", "returnVisitItemAvg", "returnVisitDiscAvg",
        "lapsedCustSpendAvg", "lapsedCustVisitAvg", "lapsedCustItemAvg", "lapsedCustDiscAvg", "lapsedVisitSpendAvg", "lapsedVisitItemAvg", "lapsedVisitDiscAvg")

      assert(avgCols === columns)

      val newAvg = avgDF.where(avgDF("TimePeriod") === 1)
        .select("newCustSpendAvg", "newCustVisitAvg", "newCustItemAvg", "newCustDiscAvg", "newVisitSpendAvg", "newVisitDiscAvg", "newVisitItemAvg")
        .head()
      val reactAvg = avgDF.where(avgDF("TimePeriod") === 1)
        .select("reactCustSpendAvg", "reactCustVisitAvg", "reactCustItemAvg", "reactCustDiscAvg", "reactVisitSpendAvg", "reactVisitDiscAvg", "reactVisitItemAvg")
        .head()
      val returnAvg = avgDF.where(avgDF("TimePeriod") === 1)
        .select("returnCustSpendAvg", "returnCustVisitAvg", "returnCustItemAvg", "returnCustDiscAvg", "returnVisitSpendAvg", "returnVisitDiscAvg", "returnVisitItemAvg")
        .head()
      val lapsedAvg = avgDF.where(avgDF("TimePeriod") === 1)
        .select("lapsedCustSpendAvg", "lapsedCustVisitAvg", "lapsedCustItemAvg", "lapsedCustDiscAvg", "lapsedVisitSpendAvg", "lapsedVisitDiscAvg", "lapsedVisitItemAvg")
        .head()

      val realNewAvg = Row(15, 1.5, 6, 0, 10, 0, 4)
      val realReactAvg = Row(15, 1, 6, 5, 15, 5, 6)
      val realReturnAvg = Row(15, 1, 6, 2.5, 15, 2.5, 6)
      val realLapsedAvg = Row(12.5, 1, 5, 1.25, 12.5, 1.25, 5)

      assert(newAvg === realNewAvg)
      assert(reactAvg === realReactAvg)
      assert(returnAvg === realReturnAvg)
      assert(lapsedAvg === realLapsedAvg)
    }
  }

  test("segment metrics for 2 time periods") {
    new SegAvg {
      val avgDF = BalorApp.calcSegAvg(zeroReturning) getOrElse zeroReturning
      assert(avgDF != zeroReturning)

      val newAvg1 = avgDF.where(avgDF("TimePeriod") === 1)
        .select("newCustSpendAvg", "newCustVisitAvg", "newCustItemAvg", "newCustDiscAvg", "newVisitSpendAvg", "newVisitDiscAvg", "newVisitItemAvg")
        .head()
      val reactAvg1 = avgDF.where(avgDF("TimePeriod") === 1)
        .select("reactCustSpendAvg", "reactCustVisitAvg", "reactCustItemAvg", "reactCustDiscAvg", "reactVisitSpendAvg", "reactVisitDiscAvg", "reactVisitItemAvg")
        .head()
      val returnAvg1 = avgDF.where(avgDF("TimePeriod") === 1)
        .select("returnCustSpendAvg", "returnCustVisitAvg", "returnCustItemAvg", "returnCustDiscAvg", "returnVisitSpendAvg", "returnVisitDiscAvg", "returnVisitItemAvg")
        .head()
      val lapsedAvg1 = avgDF.where(avgDF("TimePeriod") === 1)
        .select("lapsedCustSpendAvg", "lapsedCustVisitAvg", "lapsedCustItemAvg", "lapsedCustDiscAvg", "lapsedVisitSpendAvg", "lapsedVisitDiscAvg", "lapsedVisitItemAvg")
        .head()

      val newAvg2 = avgDF.where(avgDF("TimePeriod") === 2)
        .select("newCustSpendAvg", "newCustVisitAvg", "newCustItemAvg", "newCustDiscAvg", "newVisitSpendAvg", "newVisitDiscAvg", "newVisitItemAvg")
        .head()
      val reactAvg2 = avgDF.where(avgDF("TimePeriod") === 2)
        .select("reactCustSpendAvg", "reactCustVisitAvg", "reactCustItemAvg", "reactCustDiscAvg", "reactVisitSpendAvg", "reactVisitDiscAvg", "reactVisitItemAvg")
        .head()
      val returnAvg2 = avgDF.where(avgDF("TimePeriod") === 2)
        .select("returnCustSpendAvg", "returnCustVisitAvg", "returnCustItemAvg", "returnCustDiscAvg", "returnVisitSpendAvg", "returnVisitDiscAvg", "returnVisitItemAvg")
        .head()
      val lapsedAvg2 = avgDF.where(avgDF("TimePeriod") === 2)
        .select("lapsedCustSpendAvg", "lapsedCustVisitAvg", "lapsedCustItemAvg", "lapsedCustDiscAvg", "lapsedVisitSpendAvg", "lapsedVisitDiscAvg", "lapsedVisitItemAvg")
        .head()

      val realNewAvg1 = Row(15, 1.5, 6, 0, 10, 0, 4)
      val realReactAvg1 = Row(15, 1, 6, 5, 15, 5, 6)
      val realReturnAvg1 = Row(0, 0, 0, 0, 0, 0, 0)
      val realLapsedAvg1 = Row(12.5, 1, 5, 1.25, 12.5, 1.25, 5)

      assert(newAvg1 === realNewAvg1)
      assert(reactAvg1 === realReactAvg1)
      assert(returnAvg1 === realReturnAvg1)
      assert(lapsedAvg1 === realLapsedAvg1)

      val realNewAvg2 = Row(10, 1, 4, 0, 10, 0, 4)
      val realReactAvg2 = Row(15, 1, 6, 5, 15, 5, 6)
      val realReturnAvg2 = Row(15, 1, 6, 2.5, 15, 2.5, 6)
      val realLapsedAvg2 = Row(12.5, 1, 5, 2.5, 12.5, 2.5, 5)

      assert(newAvg2 === realNewAvg2)
      assert(reactAvg2 === realReactAvg2)
      assert(returnAvg2 === realReturnAvg2)
      assert(lapsedAvg2 === realLapsedAvg2)
    }
  }

  test("segment metrics for 3 time periods, 3rd has 0 for itemQty") {
    new SegAvg {
      val avgDF = BalorApp.calcSegAvg(threeTimePeriods) getOrElse zeroReturning
      assert(avgDF != zeroReturning)

      val newAvg1 = avgDF.where(avgDF("TimePeriod") === 1)
        .select("newCustSpendAvg", "newCustVisitAvg", "newCustItemAvg", "newCustDiscAvg", "newVisitSpendAvg", "newVisitDiscAvg", "newVisitItemAvg")
        .head()
      val reactAvg1 = avgDF.where(avgDF("TimePeriod") === 1)
        .select("reactCustSpendAvg", "reactCustVisitAvg", "reactCustItemAvg", "reactCustDiscAvg", "reactVisitSpendAvg", "reactVisitDiscAvg", "reactVisitItemAvg")
        .head()
      val returnAvg1 = avgDF.where(avgDF("TimePeriod") === 1)
        .select("returnCustSpendAvg", "returnCustVisitAvg", "returnCustItemAvg", "returnCustDiscAvg", "returnVisitSpendAvg", "returnVisitDiscAvg", "returnVisitItemAvg")
        .head()
      val lapsedAvg1 = avgDF.where(avgDF("TimePeriod") === 1)
        .select("lapsedCustSpendAvg", "lapsedCustVisitAvg", "lapsedCustItemAvg", "lapsedCustDiscAvg", "lapsedVisitSpendAvg", "lapsedVisitDiscAvg", "lapsedVisitItemAvg")
        .head()

      val newAvg2 = avgDF.where(avgDF("TimePeriod") === 2)
        .select("newCustSpendAvg", "newCustVisitAvg", "newCustItemAvg", "newCustDiscAvg", "newVisitSpendAvg", "newVisitDiscAvg", "newVisitItemAvg")
        .head()
      val reactAvg2 = avgDF.where(avgDF("TimePeriod") === 2)
        .select("reactCustSpendAvg", "reactCustVisitAvg", "reactCustItemAvg", "reactCustDiscAvg", "reactVisitSpendAvg", "reactVisitDiscAvg", "reactVisitItemAvg")
        .head()
      val returnAvg2 = avgDF.where(avgDF("TimePeriod") === 2)
        .select("returnCustSpendAvg", "returnCustVisitAvg", "returnCustItemAvg", "returnCustDiscAvg", "returnVisitSpendAvg", "returnVisitDiscAvg", "returnVisitItemAvg")
        .head()
      val lapsedAvg2 = avgDF.where(avgDF("TimePeriod") === 2)
        .select("lapsedCustSpendAvg", "lapsedCustVisitAvg", "lapsedCustItemAvg", "lapsedCustDiscAvg", "lapsedVisitSpendAvg", "lapsedVisitDiscAvg", "lapsedVisitItemAvg")
        .head()

      val newAvg3 = avgDF.where(avgDF("TimePeriod") === 3)
        .select("newCustSpendAvg", "newCustVisitAvg", "newCustItemAvg", "newCustDiscAvg", "newVisitSpendAvg", "newVisitDiscAvg", "newVisitItemAvg")
        .head()
      val reactAvg3 = avgDF.where(avgDF("TimePeriod") === 3)
        .select("reactCustSpendAvg", "reactCustVisitAvg", "reactCustItemAvg", "reactCustDiscAvg", "reactVisitSpendAvg", "reactVisitDiscAvg", "reactVisitItemAvg")
        .head()
      val returnAvg3 = avgDF.where(avgDF("TimePeriod") === 3)
        .select("returnCustSpendAvg", "returnCustVisitAvg", "returnCustItemAvg", "returnCustDiscAvg", "returnVisitSpendAvg", "returnVisitDiscAvg", "returnVisitItemAvg")
        .head()
      val lapsedAvg3 = avgDF.where(avgDF("TimePeriod") === 3)
        .select("lapsedCustSpendAvg", "lapsedCustVisitAvg", "lapsedCustItemAvg", "lapsedCustDiscAvg", "lapsedVisitSpendAvg", "lapsedVisitDiscAvg", "lapsedVisitItemAvg")
        .head()

      val realNewAvg1 = Row(15, 1.5, 6, 0, 10, 0, 4)
      val realReactAvg1 = Row(15, 1, 6, 5, 15, 5, 6)
      val realReturnAvg1 = Row(0, 0, 0, 0, 0, 0, 0)
      val realLapsedAvg1 = Row(12.5, 1, 5, 1.25, 12.5, 1.25, 5)

      assert(newAvg1 === realNewAvg1)
      assert(reactAvg1 === realReactAvg1)
      assert(returnAvg1 === realReturnAvg1)
      assert(lapsedAvg1 === realLapsedAvg1)

      val realNewAvg2 = Row(10, 1, 4, 0, 10, 0, 4)
      val realReactAvg2 = Row(15, 1, 6, 5, 15, 5, 6)
      val realReturnAvg2 = Row(15, 1, 6, 2.5, 15, 2.5, 6)
      val realLapsedAvg2 = Row(12.5, 1, 5, 2.5, 12.5, 2.5, 5)

      assert(newAvg2 === realNewAvg2)
      assert(reactAvg2 === realReactAvg2)
      assert(returnAvg2 === realReturnAvg2)
      assert(lapsedAvg2 === realLapsedAvg2)

      val realNewAvg3 = Row(15, 2.6666666666666667, 0, 2, 5.625, .75, 0)
      val realReactAvg3 = Row(12, 3, 0, 0, 4, 0, 0)
      val realReturnAvg3 = Row(3, 1, 0, 0, 3, 0, 0)
      val realLapsedAvg3 = Row(12, 4.5, 0, 2, 2.6666666666666665, .44444444444444444, 0)

      assert(newAvg3 === realNewAvg3)
      assert(reactAvg3 === realReactAvg3)
      assert(returnAvg3 === realReturnAvg3)
      assert(lapsedAvg3 === realLapsedAvg3)
    }
  }

  //BALOR RATIOS AND RETENTION
  test("return new DF (time period and 3 ratios) with calcs for single period") {
    new BalorData {

      val balorDF = BalorApp.calcBalorRatios(threeOverTwo) getOrElse threeOverTwo
      val balors = balorDF.select("custBalor", "txnBalor", "spendBalor", "retention").head()

      val realBalor = Row(1.5, 2, 1.8, 0)

      assert(realBalor === balors)
    }
  }

  test("Data for 2 balor calculations (aka 4 periods worth of data") {
    new BalorData {

      val balorDF = BalorApp.calcBalorRatios(twoBalorSets) getOrElse twoBalorSets
      val balor1 = balorDF.where(balorDF("TimePeriod") === 1).select("custBalor", "txnBalor", "spendBalor").head()
      val balor2 = balorDF.where(balorDF("TimePeriod") === 2).select("custBalor", "txnBalor", "spendBalor").head()

      val retention1 = balorDF.where(balorDF("TimePeriod") === 1).select("retention").first().getDouble(0)
      val retention2 = balorDF.where(balorDF("TimePeriod") === 2).select("retention").first().getDouble(0)

      val realBalor1 = Row(1.5, 2, 1.8)
      val realBalor2 = Row(2.0, 2.0, 2.0)

      assert(realBalor1 === balor1)
      assert(realBalor2 === balor2)

      assert(retention1 ===.33 +- .01)
      assert(retention2 === 0)
    }
  }

  test("0 Returning customers (4 periods worth of data") {
    new BalorData {

      val balorDF = BalorApp.calcBalorRatios(zeroReturning) getOrElse twoBalorSets
      val balor1 = balorDF.where(balorDF("TimePeriod") === 1).select("custBalor", "txnBalor", "spendBalor").head()
      val balor2 = balorDF.where(balorDF("TimePeriod") === 2).select("custBalor", "txnBalor", "spendBalor").head()

      val retention1 = balorDF.where(balorDF("TimePeriod") === 1).select("retention").first().getDouble(0)
      val retention2 = balorDF.where(balorDF("TimePeriod") === 2).select("retention").first().getDouble(0)

      val realBalor1 = Row(1.5, 2, 1.8)
      val realBalor2 = Row(2.0, 2.0, 2.0)

      assert(realBalor1 === balor1)
      assert(realBalor2 === balor2)

      assert(retention1 === 0)
      assert(retention2 === 0)
    }
  }

  test("Data for 3 balor calculations (aka 5 periods)") {
    new BalorData {

      val balorDF = BalorApp.calcBalorRatios(threeBalorSetsOneOverZero) getOrElse threeBalorSetsOneOverZero
      assert(balorDF != threeBalorSetsOneOverZero)
      val balor1 = balorDF.where(balorDF("TimePeriod") === 1).select("custBalor", "txnBalor", "spendBalor").head()
      val balor2 = balorDF.where(balorDF("TimePeriod") === 2).select("custBalor", "txnBalor", "spendBalor").head()
      val balor3 = balorDF.where(balorDF("TimePeriod") === 3).select("custBalor", "txnBalor", "spendBalor").head()

      val retention1 = balorDF.where(balorDF("TimePeriod") === 1).select("retention").first().getDouble(0)
      val retention2 = balorDF.where(balorDF("TimePeriod") === 2).select("retention").first().getDouble(0)
      val retention3 = balorDF.where(balorDF("TimePeriod") === 3).select("retention").first().getDouble(0)

      val realBalor1 = Row(1.5, 2.0, 1.8)
      val realBalor2 = Row(2.0, 2.0, 2)
      val realBalor3 = Row(2.0, 2.0, 2.0)

      assert(realBalor1 === balor1)
      assert(realBalor2 === balor2)
      assert(realBalor3 === balor3)

      assert(retention1 ===.33 +- .01)
      assert(retention2 ===.25 +- .01)
      assert(retention3 === 0)
    }
  }

  test("BalorRatios for avroschema test") {
    new BalorData {

      val balorDF = BalorApp.calcBalorRatios(avroSchemaResults) getOrElse threeBalorSetsOneOverZero
      assert(balorDF != threeBalorSetsOneOverZero)
      val balor1 = balorDF.where(balorDF("TimePeriod") === 1).select("custBalor", "txnBalor", "spendBalor").head()
      val balor2 = balorDF.where(balorDF("TimePeriod") === 2).select("custBalor", "txnBalor", "spendBalor").head()
      val balor3 = balorDF.where(balorDF("TimePeriod") === 3).select("custBalor", "txnBalor", "spendBalor").head()

      val retention1 = balorDF.where(balorDF("TimePeriod") === 1).select("retention").first().getDouble(0)
      val retention2 = balorDF.where(balorDF("TimePeriod") === 2).select("retention").first().getDouble(0)
      val retention3 = balorDF.where(balorDF("TimePeriod") === 3).select("retention").first().getDouble(0)

      val realBalor1 = Row(0.5294117647058824, 0.6111111111111112, 0.6842105263157895)
      val realBalor2 = Row(1.3421052631578947, 1.358974358974359, 1.375)
      val realBalor3 = Row(93.0, 1.5833333333333333, 0.991869918699187)

      assert(realBalor1 === balor1)
      assert(realBalor2 === balor2)
      assert(realBalor3 === balor3)

      assert(retention1 ===.22 +- .01)
      assert(retention2 ===.02 +- .01)
      assert(retention3 === 0)

    }
  }

  //AVRO SCHEMA CREATION
  //too  many rows to create complete dataframe, will submit DF to calcBalorRatios, calcSegmentAvg, and then to createBalorAvro
  //this test should always be run last, that way if it fails it will be because of the avro and not a previous method
  test("createBalorAvro with 3 time periods of data") {
    new BalorData {
      val avgDF = BalorApp.calcSegAvg(avroSchemaResults) getOrElse avroSchemaResults
      val balorDF = BalorApp.calcBalorRatios(avgDF) getOrElse avroSchemaResults
      val balorAvro = BalorApp.createBalorAvro("jobKey", 25, minMaxDateDF, balorDF).get

      val balorSets = balorAvro.getBalorSets

      val balor1 = balorSets.get(0)
      val balor2 = balorSets.get(1)
      val balor3 = balorSets.get(2)

      //check every field in first timeperiod
      assert(balor1.getTimePeriod === 1)
      assert(balor1.getNewCustCount === 2)
      assert(balor1.getNewTxnCount === 3)
      assert(balor1.getNewTxnAmt === 40.0)
      assert(balor1.getNewDiscAmt === 5.0)
      assert(balor1.getNewItemQty === 6)

      assert(balor1.getReactCustCount === 7)
      assert(balor1.getReactTxnCount === 8)
      assert(balor1.getReactTxnAmt === 90.0)
      assert(balor1.getReactDiscAmt === 10.0)
      assert(balor1.getReactItemQty === 11)

      assert(balor1.getReturnCustCount === 12)
      assert(balor1.getReturnTxnCount === 13)
      assert(balor1.getReturnTxnAmt === 140.0)
      assert(balor1.getReturnDiscAmt === 15.0)
      assert(balor1.getReturnItemQty === 16)

      assert(balor1.getLapsedCustCount === 17)
      assert(balor1.getLapsedTxnCount === 18)
      assert(balor1.getLapsedTxnAmt === 190.0)
      assert(balor1.getLapsedDiscAmt === 20.0)
      assert(balor1.getLapsedItemQty === 21)

      assert(balor1.getNewCustSpendAvg === 20.0)
      assert(balor1.getNewCustVisitAvg === 1.5)
      assert(balor1.getNewCustItemAvg === 3.0)
      assert(balor1.getNewCustDiscAvg === 2.5)
      assert(balor1.getNewVisitSpendAvg === 13.333333333333334)
      assert(balor1.getNewVisitItemAvg === 2.0)
      assert(balor1.getNewVisitDiscAvg === 1.6666666666666667)

      assert(balor1.getReactCustSpendAvg === 12.857142857142858)
      assert(balor1.getReactCustVisitAvg === 1.1428571428571428)
      assert(balor1.getReactCustItemAvg === 1.5714285714285714)
      assert(balor1.getReactCustDiscAvg === 1.4285714285714286)
      assert(balor1.getReactVisitSpendAvg === 11.25)
      assert(balor1.getReactVisitItemAvg === 1.375)
      assert(balor1.getReactVisitDiscAvg === 1.25)

      assert(balor1.getReturnCustSpendAvg === 11.666666666666666)
      assert(balor1.getReturnCustVisitAvg === 1.0833333333333333)
      assert(balor1.getReturnCustItemAvg === 1.3333333333333333)
      assert(balor1.getReturnCustDiscAvg === 1.25)
      assert(balor1.getReturnVisitSpendAvg === 10.76923076923077)
      assert(balor1.getReturnVisitItemAvg === 1.2307692307692308)
      assert(balor1.getReturnVisitDiscAvg === 1.1538461538461537)

      assert(balor1.getLapsedCustSpendAvg === 11.176470588235293)
      assert(balor1.getLapsedCustVisitAvg === 1.0588235294117647)
      assert(balor1.getLapsedCustItemAvg === 1.2352941176470589)
      assert(balor1.getLapsedCustDiscAvg === 1.1764705882352942)
      assert(balor1.getLapsedVisitSpendAvg === 10.555555555555555)
      assert(balor1.getLapsedVisitItemAvg === 1.1666666666666667)
      assert(balor1.getLapsedVisitDiscAvg == 1.1111111111111112)

      assert(balor1.getCustBalor === 0.5294117647058824)
      assert(balor1.getTxnBalor === .6111111111111112)
      assert(balor1.getSpendBalor === 0.6842105263157895)
      assert(balor1.getRetention === .2222222222222222)

      assert(balor2.getTimePeriod === 2)
      assert(balor2.getNewCustCount === 23)

      assert(balor3.getTimePeriod === 3)
      assert(balor3.getNewTxnAmt === 46.0)

      balorDF.show()
      println(s"This is the avro output: $balorAvro")

    }
  }
}