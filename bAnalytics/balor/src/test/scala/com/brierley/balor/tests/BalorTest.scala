package com.brierley.balor.tests

import com.brierley.balor.BalorApp
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner


/**
  * Created by amerrill on 1/31/17.
  */

@RunWith(classOf[JUnitRunner])
class BalorTest extends FunSuite with DataFrameSuiteBase {

  trait TimePeriodData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val singleUserJanFebMar2015 = sc.parallelize(List(
      ("customerA", "txn1-custA-Jan1", "01/03/2015", 1, 5.00, 5.00, 2, 5),
      ("customerA", "txn2-custA-Feb1", "02/04/2015", 1, 7.50, 7.50, 1, 5),
      ("customerA", "txn3-custA-Mar1", "03/04/2015", 4, 22.12, 22.12, 5, 2)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "EXTRA1", "EXTRA2")

    val singleUserJanFebMar2016 = sc.parallelize(List(
      ("customerA", "txn1-custA-Jan1", "01/03/2016", 1, 5.00, 5.00, 2, 5),
      ("customerA", "txn2-custA-Feb1", "02/04/2016", 1, 7.50, 7.50, 1, 5),
      ("customerA", "txn3-custA-Feb2", "02/29/2016", 3, 14.14, 1.25, 5, 2),
      ("customerA", "txn4-custA-Mar1", "03/01/2016", 4, 22.12, 22.12, 5, 2),
      ("customerA", "txn5-custA-Mar2", "03/14/2016", 8, 325.13, 34.00, 1, 5)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "EXTRA1", "EXTRA2")

    val singleUser2weeksFeb2016 = sc.parallelize(List(
      ("customerA", "txn1-custA-Jan1", "01/31/2016", 1, 5.00, 5.00, 2, 5),
      ("customerA", "txn2-custA-Feb1", "02/04/2016", 1, 7.50, 7.50, 1, 5), //3
      ("customerA", "txn3-custA-Feb2", "02/15/2016", 1, 5.00, 5.00, 2, 5), //2
      ("customerA", "txn4-custA-Feb3", "02/28/2016", 1, 5.00, 5.00, 2, 5), //2
      ("customerA", "txn5-custA-Mar1", "03/04/2016", 4, 22.12, 22.12, 5, 2), //1
      ("customerA", "txn6-custA-Mar2", "03/13/2016", 4, 22.12, 22.12, 5, 2) //1
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "EXTRA1", "EXTRA2")

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
      ("custF", "txn3-custF-Jan1", "01/30/2016", 12.53, 2.14, 2),
      ("custC", "txn5-custC-Feb1", "02/10/2016", 160.23, 2.14, 2),
      ("custF", "txn4-custF-Feb1", "02/24/2016", 234.12, 34.13, 6),
      ("custB", "txn6-custB-Feb1", "02/29/2016", 243.14, 42.14, 8),
      ("custA", "txn4-custA-Mar1", "03/03/2016", 12.42, 1.24, 2),
      ("custC", "txn6-custC-Mar1", "03/15/2016", 23.14, 3.21, 5)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "DISC_AMT", "ITEM_QTY")

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

  }

  trait ClassLabelData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    //week cadence for density and ease -Jul 31, Aug 7, 14, 21, 28 2016 (sun - sat week)

    val oneNewLapsedDeeplyLapsedReturningReact = sc.parallelize(List(
      ("lapsed3", "lapsed_3_1", "07/31/2016", 3, 7.50, 1.25, 3), //wont be added to current balor
      ("return1", "return_1_1", "08/03/2016", 5, 12.50, 2.50, 3),
      ("react1", "react_1_1", "08/06/2016", 4, 10.00, 2.50, 3),
      ("lapsed1", "lapsed_1_2", "08/07/2016", 5, 12.50, 2.50, 2),
      ("return1", "return_1_2", "08/12/2016", 3, 7.50, 1.25, 2),
      ("new1", "new_1_1", "08/14/2016", 4, 10.00, 0.00, 1),
      ("react1", "react_1_2", "08/16/2016", 6, 15.00, 5.00, 1),
      ("return1", "return_1_3", "08/20/2016", 6, 15.00, 2.50, 1)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod")

    val threeOverTwo = sc.parallelize(List(
      ("lapsed1", "lapsed_1_1", "07/31/2016", 4, 10.00, 2.50, 3),
      ("lapsed3", "lapsed_3_1", "08/02/2016", 3, 7.50, 1.25, 3), //wont be added to current balor
      ("return1", "return_1_1", "08/03/2016", 5, 12.50, 2.50, 3),
      ("react1", "react_1_1", "08/06/2016", 4, 10.00, 2.50, 3),
      ("lapsed1", "lapsed_1_2", "08/09/2016", 5, 12.50, 2.50, 2),
      ("lapsed2", "lapsed_2_1", "08/10/2016", 4, 10.00, 0.00, 2),
      ("return1", "return_1_2", "08/12/2016", 3, 7.50, 1.25, 2),
      ("new1", "new_1_1", "08/14/2016", 4, 10.00, 0.00, 1),
      ("new2", "new_2_1", "08/15/2016", 5, 12.50, 2.50, 1),
      ("react1", "react_1_2", "08/16/2016", 6, 15.00, 5.00, 1),
      ("return1", "return_1_3", "08/20/2016", 6, 15.00, 2.50, 1)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod")

    val multiTransInSinglePeriod = sc.parallelize(List(
      ("lapsed3", "lapsed_3_1", "07/31/2016", 3, 7.50, 1.25, 3), //wont be added to current balor
      ("return1", "return_1_1", "08/03/2016", 5, 12.50, 2.50, 3),
      ("react1", "react_1_1", "08/06/2016", 4, 10.00, 2.50, 3),
      ("lapsed1", "lapsed_1_2", "08/07/2016", 5, 12.50, 2.50, 2),
      ("lapsed1", "lapsed_1_3", "08/10/2016", 4, 10.00, 1.25, 2),
      ("return1", "return_1_2", "08/12/2016", 3, 7.50, 1.25, 2),
      ("new1", "new_1_1", "08/14/2016", 4, 10.00, 0.00, 1),
      ("react1", "react_1_2", "08/16/2016", 6, 15.00, 5.00, 1),
      ("new1", "new_1_2", "08/17/2016", 6, 15.00, 5.00, 1),
      ("return1", "return_1_3", "08/17/2016", 5, 12.50, 2.25, 1),
      ("return1", "return_1_4", "08/20/2016", 6, 15.00, 2.50, 1)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod")

    val multiTransInSinglePeriodNewOutside = sc.parallelize(List(
      ("new1", "new_1_old", "07/17/2016", 2, 5.00, 1.00, 4), //shouldn't be in current balor
      ("lapsed3", "lapsed_3_1", "07/31/2016", 3, 7.50, 1.25, 3), //wont be added to current balor
      ("return1", "return_1_1", "08/03/2016", 5, 12.50, 2.50, 3),
      ("react1", "react_1_1", "08/06/2016", 4, 10.00, 2.50, 3),
      ("lapsed1", "lapsed_1_2", "08/07/2016", 5, 12.50, 2.50, 2),
      ("lapsed1", "lapsed_1_3", "08/10/2016", 4, 10.00, 1.25, 2),
      ("return1", "return_1_2", "08/12/2016", 3, 7.50, 1.25, 2),
      ("new1", "new_1_1", "08/14/2016", 4, 10.00, 0.00, 1),
      ("react1", "react_1_2", "08/16/2016", 6, 15.00, 5.00, 1),
      ("new1", "new_1_2", "08/17/2016", 6, 15.00, 5.00, 1),
      ("return1", "return_1_3", "08/17/2016", 5, 12.50, 2.25, 1),
      ("return1", "return_1_4", "08/20/2016", 6, 15.00, 2.50, 1)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod")


  }

  trait CountData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val singleSimpleBalorSet = sc.parallelize(List(
      ("lapsed3", "lapsed_3_1", "07/31/2016", 3, 7.50, 1.25, 3), //wont be added to current balor
      ("return1", "return_1_1", "08/03/2016", 5, 12.50, 2.50, 3),
      ("react1", "react_1_1", "08/06/2016", 4, 10.00, 2.50, 3),
      ("lapsed1", "lapsed_1_2", "08/07/2016", 5, 12.50, 2.50, 2, "Lapsed"),
      ("return1", "return_1_2", "08/12/2016", 3, 7.50, 1.25, 2, "Returning"),
      ("new1", "new_1_1", "08/14/2016", 4, 10.00, 0.00, 1, "New"),
      ("react1", "react_1_2", "08/16/2016", 6, 15.00, 5.00, 1, "Reactivated"),
      ("return1", "return_1_3", "08/20/2016", 6, 15.00, 2.50, 1, "Returning")
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod", "Segment")

    val missingDisc = sc.parallelize(List(
      ("lapsed3", "lapsed_3_1", "07/31/2016", 3, 7.50, 1.25, 3), //wont be added to current balor
      ("return1", "return_1_1", "08/03/2016", 5, 12.50, 2.50, 3),
      ("react1", "react_1_1", "08/06/2016", 4, 10.00, 2.50, 3),
      ("lapsed1", "lapsed_1_2", "08/07/2016", 5, 12.50, 2.50, 2, "Lapsed"),
      ("return1", "return_1_2", "08/12/2016", 3, 7.50, 1.25, 2, "Returning"),
      ("new1", "new_1_1", "08/14/2016", 4, 10.00, null, 1, "New"),
      ("react1", "react_1_2", "08/16/2016", 6, 15.00, 5.00, 1, "Reactivated"),
      ("return1", "return_1_3", "08/20/2016", 6, 15.00, null, 1, "Returning")
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod", "Segment")

    val missingItems = sc.parallelize(List(
      ("lapsed3", "lapsed_3_1", "07/31/2016", 3, 7.50, 1.25, 3), //wont be added to current balor
      ("return1", "return_1_1", "08/03/2016", 5, 12.50, 2.50, 3),
      ("react1", "react_1_1", "08/06/2016", 4, 10.00, 2.50, 3),
      ("lapsed1", "lapsed_1_2", "08/07/2016", 5, 12.50, 2.50, 2, "Lapsed"),
      ("return1", "return_1_2", "08/12/2016", null, 7.50, 1.25, 2, "Returning"),
      ("new1", "new_1_1", "08/14/2016", 4, 10.00, 0.00, 1, "New"),
      ("react1", "react_1_2", "08/16/2016", null, 15.00, 5.00, 1, "Reactivated"),
      ("return1", "return_1_3", "08/20/2016", 6, 15.00, 2.50, 1, "Returning")
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod", "Segment")

    val nullItemsAndDisc = sc.parallelize(List(
      ("lapsed3", "lapsed_3_1", "07/31/2016", null, 7.50, null, 3), //wont be added to current balor
      ("return1", "return_1_1", "08/03/2016", null, 12.50, null, 3),
      ("react1", "react_1_1", "08/06/2016", null, 10.00, null, 3),
      ("lapsed1", "lapsed_1_2", "08/07/2016", null, 12.50, null, 2, "Lapsed"),
      ("return1", "return_1_2", "08/12/2016", null, 7.50, null, 2, "Returning"),
      ("new1", "new_1_1", "08/14/2016", null, 10.00, null, 1, "New"),
      ("react1", "react_1_2", "08/16/2016", null, 15.00, null, 1, "Reactivated"),
      ("return1", "return_1_3", "08/20/2016", null, 15.00, null, 1, "Returning")
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "ITEM_QTY", "TXN_AMT", "DISC_AMT", "TimePeriod", "Segment")

  }

  trait BalorData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val twoOverOne = sc.parallelize(List(
      (1, "Lapsed", 1, 1, 12.50, 2.50, 5),
      (1, "New", 1, 1, 10.00, 0.00, 4),
      (1, "Reactivated", 1, 1, 15.00, 5.00, 6),
      (1, "Returning", 1, 1, 15.00, 2.50, 6)
    )).toDF("BalorSet", "Segment", "CustCount", "TxnCount", "TtlSpend", "TtlDisc", "ItemCount")

    val threeOverTwo = sc.parallelize(List(
      (1, "Lapsed", 2, 2, 25.00, 2.50, 10),
      (1, "New", 2, 3, 30.00, 0.00, 12),
      (1, "Reactivated", 1, 1, 15.00, 5.00, 6),
      (1, "Returning", 1, 1, 15.00, 2.50, 6)
    )).toDF("BalorSet", "Segment", "CustCount", "TxnCount", "TtlSpend", "TtlDisc", "ItemCount")

    val twoBalorSets = sc.parallelize(List(
      (1, "Lapsed", 2, 2, 25.00, 2.50, 10),
      (1, "New", 2, 3, 30.00, 0.00, 12),
      (1, "Reactivated", 1, 1, 15.00, 5.00, 6),
      (1, "Returning", 1, 1, 15.00, 2.50, 6),
      (2, "Lapsed", 1, 1, 12.50, 2.50, 5),
      (2, "New", 1, 1, 10.00, 0.00, 4),
      (2, "Reactivated", 1, 1, 15.00, 5.00, 6),
      (2, "Returning", 1, 1, 15.00, 2.50, 6)
    )).toDF("BalorSet", "Segment", "CustCount", "TxnCount", "TtlSpend", "TtlDisc", "ItemCount")

    val threeBalorSetsOneOverZero = sc.parallelize(List(
      (1, "Lapsed", 2, 2, 25.00, 2.50, 10),
      (1, "New", 2, 3, 30.00, 0.00, 12),
      (1, "Reactivated", 1, 1, 15.00, 5.00, 6),
      (1, "Returning", 1, 1, 15.00, 2.50, 6),
      (2, "Lapsed", 1, 1, 12.50, 2.50, 5),
      (2, "New", 1, 1, 10.00, 0.00, 4),
      (2, "Reactivated", 1, 1, 15.00, 5.00, 6),
      (2, "Returning", 1, 1, 15.00, 2.50, 6),
      (3, "Lapsed", 0, 1, 12.50, 2.50, 5),
      (3, "New", 1, 1, 10.00, 0.00, 4),
      (3, "Reactivated", 1, 1, 15.00, 5.00, 6),
      (3, "Returning", 1, 1, 15.00, 2.50, 6)
    )).toDF("BalorSet", "Segment", "CustCount", "TxnCount", "TtlSpend", "TtlDisc", "ItemCount")

  }

  //TODO Still need to figure out enum/case class for the cadence value into Balor

  //TIME PERIOD TESTS
  test("TimePeriod column added, extra columns dropped") {
    new TimePeriodData {
      val periodDF = BalorApp.calcTimePeriod(singleUserJanFebMar2015, 2)
      val periodColumns = periodDF.columns
      val columns = Array("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "TimePeriod")

      assert(periodColumns === columns)
    }

  }

  test("Time Period (month) around daylight savings start") {
    new TimePeriodData {
      val periodDF = BalorApp.calcTimePeriod(singleUserJanFebMar2015, 2)
      val timePeriods = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(timePeriods === List(1, 2, 3))

      val janPeriod = periodDF
        .where("TXN_ID = 'txn1-custA-Jan1'")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(janPeriod === 1)

      val febPeriod = periodDF
        .where("TXN_ID = 'txn2-custA-Feb1'")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(febPeriod === 2)

      val marPeriod = periodDF
        .where("TXN_ID = 'txn3-custA-Mar1'")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(marPeriod === 3)
    }
  }

  test("Time Period (month) breaks around Leap Day") {
    new TimePeriodData {
      val periodDF = BalorApp.calcTimePeriod(singleUserJanFebMar2016, 2)
      val timePeriods = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(timePeriods === List(1, 1, 2, 2, 3))

      val janPeriod = periodDF
        .where("TXN_ID = 'txn1-custA-Jan1'")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(janPeriod === 3)

      val febPeriod = periodDF
        .where("TXN_ID = 'txn3-custA-Feb2'")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(febPeriod === 2)

      val marPeriod = periodDF
        .where("TXN_ID = 'txn4-custA-Mar1'")
        .select("TimePeriod")
        .first()
        .getInt(0)

      assert(marPeriod === 1)
    }
  }

  test("Time Period (2 weeks) breaks around Leap Day") {
    new TimePeriodData {
      val periodDF = BalorApp.calcTimePeriod(singleUser2weeksFeb2016, 1)
      val timePeriods = periodDF.select("TimePeriod").map(_ (0)).collect()

      //jan 31 entry should be dropped
      assert(periodDF.count() === 5)
      assert(timePeriods === List(1, 1, 2, 2, 3))

    }
  }

  test("Time Period = month, 4 months of data, with newest being extra") {
    new TimePeriodData {
      val periodDF = BalorApp.calcTimePeriod(fourMonthsPlusNewest, 2)
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
      val periodDF = BalorApp.calcTimePeriod(fourMonthsPlusOldest, 2)
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
      val periodDF = BalorApp.calcTimePeriod(twoMonthsPlus, 2)
      val timePeriods = periodDF.select("TimePeriod").map(_ (0)).collect()

      assert(periodDF.count() === 10)
      assert(timePeriods === List(1, 1, 1, 1, 2, 2, 2, 2, 2, 2))
    }

  }

  //CLASSIFICATION TESTS
  test("Segment column added") {
    new ClassLabelData {
      val labelDF = BalorApp.assignSegmentLabel(threeOverTwo)
      val labelCols = labelDF.columns
      val columns = Array("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "TimePeriod", "Segment")

      assert(labelCols === columns)
    }
  }

  test("One of each label, single transaction per period") {
    new ClassLabelData {
      val labelDF = BalorApp.assignSegmentLabel(oneNewLapsedDeeplyLapsedReturningReact)
      val labelCol = labelDF.select("Segment").map(_ (0)).collect()

      val labels = Array("Returning", "Reactivated", "New", "Returning", "Lapsed")

      assert(labelCol === labels)

      assert(labelDF.count === 8)
    }

  }

  test("Multiple of each label") {
    new ClassLabelData {
      val labelDF = BalorApp.assignSegmentLabel(oneNewLapsedDeeplyLapsedReturningReact)
      val labelCol = labelDF.select("Segment").map(_ (0)).collect()

      val labels = Array("Returning", "Reactivated", "New", "New", "Returning", "Lapsed", "Lapsed")

      assert(labelCol === labels)

      assert(labelDF.count === 11)
    }
  }

  test("New customer, multiple txn in latest period") {
    new ClassLabelData {
      val labelDF = BalorApp.assignSegmentLabel(multiTransInSinglePeriod)
      val labelCol = labelDF.select("Segment").map(_ (0)).collect()

      val labels = Array("Returning", "Returning", "New", "Reactivated", "New", "Returning", "Lapsed", "Lapsed")

      assert(labelDF.count === 11)
    }
  }

  test("New customer, really old txn (outside of balor range), and single new") {
    new ClassLabelData {
      val labelDF = BalorApp.assignSegmentLabel(multiTransInSinglePeriodNewOutside)
      val labelCol = labelDF.select("Segment").map(_ (0)).collect()

      val labels = Array("Returning", "Reactivated", "New", "New", "Returning", "Lapsed", "Lapsed")

      assert(labelCol === labels)

      assert(labelDF.count === 12)
    }
  }

  //COUNTS
  test("Count columns (customer, transaction, total spend, discount amount, items) added to DF") {
    new CountData {
      val countDF = BalorApp.counts(singleSimpleBalorSet)
      val countCols = countDF.columns

      val columns = Array("BalorSet", "Segment", "CustCount", "TxnCount", "TtlSpend", "TtlDisc", "ItemCount")
    }
  }

  test("counts correctly given 1 complete balor time period") {
    new CountData {

      import sqlCtx.implicits._

      val countDF = BalorApp.counts(singleSimpleBalorSet)
      val counts = countDF
        .where("BalorSet = 1")
        .select("Segment", "CustCount", "TxnCount", "TtlSpend", "TtlDisc", "ItemCount")

      val realCounts = sc.parallelize(List(
        ("Lapsed", 1, 1, 12.50, 2.50, 5),
        ("New", 1, 1, 10.00, 0.00, 4),
        ("Reactivated", 1, 1, 15.00, 5.00, 6),
        ("Returning", 1, 1, 15.00, 2.50, 6)
      )).toDF("Segment", "CustCount", "TxnCount", "TtlSpend", "TtlDisc", "ItemCount")

      assertDataFrameEquals(realCounts, countDF)
    }
  }

  test("counts correctly given 4 time periods, results in 2 Balor result sets") {

    //don't know if this will be tested here or somewhere else, it will need to be done iteratively with the label application
  }

  test("counts correctly with null values in discount amount - random rows") {
    new CountData {

      import sqlCtx.implicits._

      val countDF = BalorApp.counts(missingDisc)
      val counts = countDF
        .where("BalorSet = 1")
        .select("Segment", "CustCount", "TxnCount", "TtlSpend", "TtlDisc", "ItemCount")

      val realCounts = sc.parallelize(List(
        ("Lapsed", 1, 1, 12.50, 2.50, 5),
        ("New", 1, 1, 10.00, 0.00, 4),
        ("Reactivated", 1, 1, 15.00, 0, 6),
        ("Returning", 1, 1, 15.00, 2.50, 6)
      )).toDF("Segment", "CustCount", "TxnCount", "TtlSpend", "TtlDisc", "ItemCount")

      assertDataFrameEquals(realCounts, countDF)
    }
  }

  test("counts correctly with null values in item amount - random rows") {
    new CountData {

      import sqlCtx.implicits._

      val countDF = BalorApp.counts(missingItems)
      val counts = countDF
        .where("BalorSet = 1")
        .select("Segment", "CustCount", "TxnCount", "TtlSpend", "TtlDisc", "ItemCount")

      val realCounts = sc.parallelize(List(
        ("Lapsed", 1, 1, 12.50, 2.50, 5),
        ("New", 1, 1, 10.00, 0.00, 4),
        ("Reactivated", 1, 1, 15.00, 5.00, 0),
        ("Returning", 1, 1, 15.00, 2.50, 6)
      )).toDF("Segment", "CustCount", "TxnCount", "TtlSpend", "TtlDisc", "ItemCount")

      assertDataFrameEquals(realCounts, countDF)
    }
  }

  test("counts correctly with null values in all rows for both columns") {
    new CountData {

      import sqlCtx.implicits._

      val countDF = BalorApp.counts(nullItemsAndDisc)
      val counts = countDF
        .where("BalorSet = 1")
        .select("Segment", "CustCount", "TxnCount", "TtlSpend", "TtlDisc", "ItemCount")

      val realCounts = sc.parallelize(List(
        ("Lapsed", 1, 1, 12.50, 0, 0),
        ("New", 1, 1, 10.00, 0, 0),
        ("Reactivated", 1, 1, 15.00, 0, 0),
        ("Returning", 1, 1, 15.00, 0, 0)
      )).toDF("Segment", "CustCount", "TxnCount", "TtlSpend", "TtlDisc", "ItemCount")

      assertDataFrameEquals(countDF, realCounts)
    }
  }


  //test current balor -1 (ie.  current: oct, nov, dec need to test: sept, oct, nov)

  test("return new DF (time period and 3 ratios) with calcs for single period") {
    new BalorData {

      import sqlCtx.implicits._

      val balorDF = BalorApp.calcBalorRatios(threeOverTwo)
      val balors = balorDF.select("CustBalor", "TxnBalor", "SpendBalor")

      val realBalor = sc.parallelize(List((1.5, 2, 1.8))).toDF("CustBalor", "TxnBalor", "SpendBalor")

      assertDataFrameEquals(realBalor, balors)
    }
  }

  test("Data for 2 balor calculations (aka 4 periods worth of data") {
    new BalorData {

      import sqlCtx.implicits._

      val balorDF = BalorApp.calcBalorRatios(twoBalorSets)
      val balors = balorDF.select("CustBalor", "TxnBalor", "SpendBalor")

      val realBalor = sc.parallelize(List(
        (1.5, 2, 1.8),
        (2, 2, 2)
      )).toDF("CustBalor", "TxnBalor", "SpendBalor")

      assertDataFrameEquals(realBalor, balors)
    }
  }

  test("Data for 3 balor calculations (aka 5 periods)") {
    new BalorData {

      import sqlCtx.implicits._

      val balorDF = BalorApp.calcBalorRatios(threeBalorSetsOneOverZero)
      val balors = balorDF.select("CustBalor", "TxnBalor", "SpendBalor")

      val realBalor = sc.parallelize(List(
        (1.5, 2, 1.8),
        (2, 2, 2),
        (2, 2, 25)
      )).toDF("CustBalor", "TxnBalor", "SpendBalor")

      assertDataFrameEquals(realBalor, balors)
    }
  }

}
