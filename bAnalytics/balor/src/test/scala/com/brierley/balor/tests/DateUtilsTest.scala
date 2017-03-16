package com.brierley.balor.tests

import com.brierley.utils.DateUtils
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by amerrill on 2/14/17.
  */

@RunWith(classOf[JUnitRunner])
class DateUtilsTest extends FunSuite with DataFrameSuiteBase {

  trait TransformDateData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    //dd-MM-yyyy
    val ddmmyyyyDash = sc.parallelize(List(
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
    val ddmmyyyySlash = sc.parallelize(List(
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
    val yyyymmddDash = sc.parallelize(List(
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
    val yyyymmddSlash = sc.parallelize(List(
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
    val mmddyyyyDash = sc.parallelize(List(
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
    val mmddyyyySlash = sc.parallelize(List(
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

  trait TrimToWholeMonthData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val trimBegOnly = sc.parallelize(List(
      ("customerA", "txn1-custA-Feb1", "2016-02-15", 12.34, 1, 2.35),
      ("customerA", "txn2-custA-Feb2", "2016-02-22", 22.10, 1, 2.35),
      ("customerA", "txn3-custA-Feb3", "2016-02-29", 10.50, 1, 2.35),
      ("customerA", "txn4-custA-Mar1", "2016-03-02", 25.45, 1, 2.35),
      ("customerA", "txn5-custA-Mar2", "2016-03-05", 1.50, 1, 2.35),
      ("customerA", "txn6-custA-Mar3", "2016-03-12", 3.25, 2, 2.13),
      ("customerA", "txn7-custA-Mar4", "2016-03-13", 4.36, 4, 1.24),
      ("customerA", "txn8-custA-Mar5", "2016-03-14", 3.42, 1, 1.21),
      ("customerA", "txn9-custA-Mar6", "2016-03-31", 4.52, 2, 2.12)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY", "DISC_AMT")
      .withColumn("Date", $"TXN_DATE".cast("date"))

    val trimEndOnly = sc.parallelize(List(
      ("customerA", "txn0-custA-Feb0", "2016-02-01", 34.52, 1, 0.00),
      ("customerA", "txn1-custA-Feb1", "2016-02-15", 12.34, 1, 2.35),
      ("customerA", "txn2-custA-Feb2", "2016-02-22", 22.10, 1, 2.35),
      ("customerA", "txn3-custA-Feb3", "2016-02-29", 10.50, 1, 2.35),
      ("customerA", "txn4-custA-Mar1", "2016-03-02", 25.45, 1, 2.35),
      ("customerA", "txn5-custA-Mar2", "2016-03-05", 1.50, 1, 2.35),
      ("customerA", "txn6-custA-Mar3", "2016-03-12", 3.25, 2, 2.13),
      ("customerA", "txn7-custA-Mar4", "2016-03-13", 4.36, 4, 1.24),
      ("customerA", "txn8-custA-Mar5", "2016-03-14", 3.42, 1, 1.21)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY", "DISC_AMT")
      .withColumn("Date", $"TXN_DATE".cast("date"))

    val trimBoth = sc.parallelize(List(
      ("customerA", "txn1-custA-Feb1", "2016-02-15", 12.34, 1, 2.35),
      ("customerA", "txn2-custA-Feb2", "2016-02-22", 22.10, 1, 2.35),
      ("customerA", "txn3-custA-Feb3", "2016-02-29", 10.50, 1, 2.35),
      ("customerA", "txn4-custA-Mar1", "2016-03-02", 25.45, 1, 2.35),
      ("customerA", "txn5-custA-Mar2", "2016-03-05", 1.50, 1, 2.35),
      ("customerA", "txn6-custA-Mar3", "2016-03-12", 3.25, 2, 2.13),
      ("customerA", "txn7-custA-Mar4", "2016-03-13", 4.36, 4, 1.24),
      ("customerA", "txn8-custA-Mar5", "2016-03-14", 3.42, 1, 1.21),
      ("customerA", "txn9-custA-Mar6", "2016-03-31", 4.52, 2, 2.12),
      ("customerA", "txn10-custA-Apr1", "2016-04-01", 5.24, 1, 1.24)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY", "DISC_AMT")
      .withColumn("Date", $"TXN_DATE".cast("date"))

    val noTrim = sc.parallelize(List(
      ("customerA", "txn0-custA-Feb0", "2016-02-01", 34.52, 1, 0.00),
      ("customerA", "txn1-custA-Feb1", "2016-02-15", 12.34, 1, 2.35),
      ("customerA", "txn2-custA-Feb2", "2016-02-22", 22.10, 1, 2.35),
      ("customerA", "txn3-custA-Feb3", "2016-02-29", 10.50, 1, 2.35),
      ("customerA", "txn4-custA-Mar1", "2016-03-02", 25.45, 1, 2.35),
      ("customerA", "txn5-custA-Mar2", "2016-03-05", 1.50, 1, 2.35),
      ("customerA", "txn6-custA-Mar3", "2016-03-12", 3.25, 2, 2.13),
      ("customerA", "txn7-custA-Mar4", "2016-03-13", 4.36, 4, 1.24),
      ("customerA", "txn8-custA-Mar5", "2016-03-14", 3.42, 1, 1.21),
      ("customerA", "txn9-custA-Mar6", "2016-03-31", 4.52, 2, 2.12)
    )).toDF("CUST_ID", "TXN_ID", "TXN_DATE", "TXN_AMT", "ITEM_QTY", "DISC_AMT")
      .withColumn("Date", $"TXN_DATE".cast("date"))

  }

  test("Date format of MM/dd/yyyy") {

    new TransformDateData {
      val trans = DateUtils.convertDateMonthSlash(mmddyyyySlash)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Date format of MM-dd-yyyy") {
    new TransformDateData {
      val trans = DateUtils.convertDateMonthDash(mmddyyyyDash)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Date format of dd-MM-yyyy") {
    new TransformDateData {
      val trans = DateUtils.convertDateDayDash(ddmmyyyyDash)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Date format of dd/MM/yyyy") {
    new TransformDateData {
      val trans = DateUtils.convertDateDaySlash(ddmmyyyySlash)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Date format of yyyy-MM-dd") {
    new TransformDateData {
      val trans = DateUtils.convertDateYearDash(yyyymmddDash)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Date format of yyyy/MM/dd") {
    new TransformDateData {
      val trans = DateUtils.convertDateYearSlash(yyyymmddSlash)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Determine format of MM-dd-yyyy, has enough data") {
    new TransformDateData {
      val trans = DateUtils.determineFormat(mmddyyyyDash)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Determine format of MM/dd/yyyy, has enough data") {
    new TransformDateData {
      val trans = DateUtils.determineFormat(mmddyyyySlash)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Determine format of dd-MM-yyyy, has enough data") {
    new TransformDateData {
      val trans = DateUtils.determineFormat(ddmmyyyyDash)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Determine format of dd/MM/yyyy, has enough data") {
    new TransformDateData {
      val trans = DateUtils.determineFormat(ddmmyyyySlash)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Determine format of yyyy/MM/dd, has enough data") {
    new TransformDateData {
      val trans = DateUtils.determineFormat(yyyymmddSlash)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }

  test("Determine format of yyyy-MM-dd, has enough data") {
    new TransformDateData {
      val trans = DateUtils.determineFormat(yyyymmddDash)
        .select("CUST_ID", "TXN_ID", "TXN_AMT", "ITEM_QTY", "DISC_AMT", "Date")

      assertDataFrameEquals(trans, dateResults)
    }
  }


  //TRIM TO WHOLE MONTH TESTS

  test("Trim extra from beginning") {
    new TrimToWholeMonthData {
      val trimDF = DateUtils.trimToWholeMonth(trimBegOnly)
      val count = trimDF.count()

      assert(count === 6)
    }
  }

  test("Trim extra from end") {
    new TrimToWholeMonthData {
      val trimDF = DateUtils.trimToWholeMonth(trimEndOnly)
      val count = trimDF.count()

      assert(count === 4)
    }
  }

  test("Trim extra from beginning and end") {
    new TrimToWholeMonthData {
      val trimDF = DateUtils.trimToWholeMonth(trimBoth)
      val count = trimDF.count()

      assert(count === 6)
    }
  }

  test("No trimming required, return same DF") {
    new TrimToWholeMonthData {
      val trimDF = DateUtils.trimToWholeMonth(noTrim)
      val count = trimDF.count()

      assert(count === 10)
    }
  }

}
