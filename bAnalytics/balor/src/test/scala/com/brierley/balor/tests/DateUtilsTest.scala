package com.brierley.balor.tests

import java.sql.Date

import com.brierley.utils.DateUtils
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by amerrill on 2/14/17.
  */

@RunWith(classOf[JUnitRunner])
class DateUtilsTest extends FunSuite with DataFrameSuiteBase{

  trait TransformDateData{
    val sqlCtx = sqlContext
    import sqlCtx.implicits._

    //dd-MM-yyyy
    val leapSavingsDate1 = sc.parallelize(List(
      ("customerA", "txn1-custA-Feb1", "15-02-2016", 12.34, 1, 2.35),
      ("customerA", "txn2-custA-Feb2", "22-2-2016", 22.10, 1, 2.35),
      ("customerA", "txn3-custA-Feb3", "29-02-2016", 10.50, 1, 2.35),
      ("customerA", "txn4-custA-Mar1", "2-3-2016", 25.45, 1, 2.35),
      ("customerA", "txn5-custA-Mar2", "05-03-2016", 1.50, 1, 2.35),
      ("customerA", "txn6-custA-Mar3", "12-3-2016", 3.25, 2, 2.13),
      ("customerA", "txn7-custA-Mar4", "13-03-2016", 4.36, 4, 1.24),
      ("customerA", "txn8-custA-Mar5", "14-03-2016", 3.42, 1, 1.21)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY", "DISC_AMT")

    //dd/MM/yyyy
    val leapSavingsDate2 = sc.parallelize(List(
      ("customerA", "txn1-custA-Feb1", "15/2/2016", 12.34, 1, 2.35),
      ("customerA", "txn2-custA-Feb2", "22/02/2016", 22.10, 1, 2.35),
      ("customerA", "txn3-custA-Feb3", "29/02/2016", 10.50, 1, 2.35),
      ("customerA", "txn4-custA-Mar1", "2/3/2016", 25.45, 1, 2.35),
      ("customerA", "txn5-custA-Mar2", "5/03/2016", 1.50, 1, 2.35),
      ("customerA", "txn6-custA-Mar3", "12/3/2016", 3.25, 2, 2.13),
      ("customerA", "txn7-custA-Mar4", "13/03/2016", 4.36, 4, 1.24),
      ("customerA", "txn8-custA-Mar5", "14/03/2016", 3.42, 1, 1.21)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY", "DISC_AMT")

    //yyyy-MM-dd
    val leapSavingsDate3 = sc.parallelize(List(
      ("customerA", "txn1-custA-Feb1", "2016-2-15", 12.34, 1, 2.35),
      ("customerA", "txn2-custA-Feb2", "2016-02-22", 22.10, 1, 2.35),
      ("customerA", "txn3-custA-Feb3", "2016-02-29", 10.50, 1, 2.35),
      ("customerA", "txn4-custA-Mar1", "2016-03-2", 25.45, 1, 2.35),
      ("customerA", "txn5-custA-Mar2", "2016-3-5", 1.50, 1, 2.35),
      ("customerA", "txn6-custA-Mar3", "2016-03-12", 3.25, 2, 2.13),
      ("customerA", "txn7-custA-Mar4", "2016-03-13", 4.36, 4, 1.24),
      ("customerA", "txn8-custA-Mar5", "2016-03-14", 3.42, 1, 1.21)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY", "DISC_AMT")

    //yyyy/MM/dd
    val leapSavingsDate4 = sc.parallelize(List(
      ("customerA", "txn1-custA-Feb1", "2016/2/15", 12.34, 1, 2.35),
      ("customerA", "txn2-custA-Feb2", "2016/02/22", 22.10, 1, 2.35),
      ("customerA", "txn3-custA-Feb3", "2016/02/29", 10.50, 1, 2.35),
      ("customerA", "txn4-custA-Mar1", "2016/03/2", 25.45, 1, 2.35),
      ("customerA", "txn5-custA-Mar2", "2016/3/5", 1.50, 1, 2.35),
      ("customerA", "txn6-custA-Mar3", "2016/03/12", 3.25, 2, 2.13),
      ("customerA", "txn7-custA-Mar4", "2016/03/13", 4.36, 4, 1.24),
      ("customerA", "txn8-custA-Mar5", "2016/03/14", 3.42, 1, 1.21)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY", "DISC_AMT")

    //MM-dd-yyyy
    val leapSavingsDate5 = sc.parallelize(List(
      ("customerA", "txn1-custA-Feb1", "2-15-2016", 12.34, 1, 2.35),
      ("customerA", "txn2-custA-Feb2", "02-22-2016", 22.10, 1, 2.35),
      ("customerA", "txn3-custA-Feb3", "02-29-2016", 10.50, 1, 2.35),
      ("customerA", "txn4-custA-Mar1", "03-2-2016", 25.45, 1, 2.35),
      ("customerA", "txn5-custA-Mar2", "3-5-2016", 1.50, 1, 2.35),
      ("customerA", "txn6-custA-Mar3", "03-12-2016", 3.25, 2, 2.13),
      ("customerA", "txn7-custA-Mar4", "03-13-2016", 4.36, 4, 1.24),
      ("customerA", "txn8-custA-Mar5", "03-14-2016", 3.42, 1, 1.21)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY", "DISC_AMT")

    //MM/dd/yyyy
    val leapSavingsDate6 = sc.parallelize(List(
      ("customerA", "txn1-custA-Feb1", "2/15/2016", 12.34, 1, 2.35),
      ("customerA", "txn2-custA-Feb2", "02/22/2016", 22.10, 1, 2.35),
      ("customerA", "txn3-custA-Feb3", "02/29/2016", 10.50, 1, 2.35),
      ("customerA", "txn4-custA-Mar1", "03/2/2016", 25.45, 1, 2.35),
      ("customerA", "txn5-custA-Mar2", "3/5/2016", 1.50, 1, 2.35),
      ("customerA", "txn6-custA-Mar3", "03/12/2016", 3.25, 2, 2.13),
      ("customerA", "txn7-custA-Mar4", "03/13/2016", 4.36, 4, 1.24),
      ("customerA", "txn8-custA-Mar5", "03/14/2016", 3.42, 1, 1.21)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY", "DISC_AMT")

    val dateinput = sc.parallelize(List(
      ("customerA", "txn1-custA-Feb1", "2016-02-15", 12.34, 1, 2.35),
      ("customerA", "txn2-custA-Feb2", "2016-02-22", 22.10, 1, 2.35),
      ("customerA", "txn3-custA-Feb3", "2016-02-29", 10.50, 1, 2.35),
      ("customerA", "txn4-custA-Mar1", "2016-03-02", 25.45, 1, 2.35),
      ("customerA", "txn5-custA-Mar2", "2016-03-05", 1.50, 1, 2.35),
      ("customerA", "txn6-custA-Mar3", "2016-03-12", 3.25, 2, 2.13),
      ("customerA", "txn7-custA-Mar4", "2016-03-13", 4.36, 4, 1.24),
      ("customerA", "txn8-custA-Mar5", "2016-03-14", 3.42, 1, 1.21)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY", "DISC_AMT")

    val dateResults = dateinput.withColumn("Date", dateinput("TXN_DATE").cast("date"))
      .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

  }

  test("Date format of MM/dd/yyyy"){

    new TransformDateData {
      val trans = DateUtils.convertDateMonthSlash(leapSavingsDate6)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Date format of MM-dd-yyyy"){
    new TransformDateData {
      val trans = DateUtils.convertDateMonthDash(leapSavingsDate5)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Date format of dd-MM-yyyy"){
    new TransformDateData {
      val trans = DateUtils.convertDateDayDash(leapSavingsDate1)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Date format of dd/MM/yyyy"){
    new TransformDateData {
      val trans = DateUtils.convertDateDaySlash(leapSavingsDate2)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Date format of yyyy-MM-dd"){
    new TransformDateData {
      val trans = DateUtils.convertDateYearDash(leapSavingsDate3)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Date format of yyyy/MM/dd"){
    new TransformDateData {
      val trans = DateUtils.convertDateYearSlash(leapSavingsDate4)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

}
