package com.brierley.lifecycle.tests

import com.brierley.lifecycle.Lifecycle
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.Row
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by amerrill on 8/15/17.
  */
@RunWith(classOf[JUnitRunner])
class LifecycleMigrationTest extends FunSuite with DataFrameSuiteBase {

  trait CreateMigrationData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

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
  }

  trait SumMigData {
    val sqlCtx = sqlContext

    import sqlCtx.implicits._

    val twoTPs = sc.parallelize(List(
      ("CustA", 2, "Best in Class", "Lapsing"),
      ("CustA", 1, "Lapsing", null),
      ("CustB", 2, "Deeply Lapsed", "Lapsing"),
      ("CustB", 1, "Lapsing", null),
      ("CustC", 2, "Middle of the Road", "Rising Stars"),
      ("CustC", 1, "Rising Stars", null),
      ("CustD", 2, "Best in Class", null)
    )).toDF("CUST_ID", "TimePeriod", "CurrSeg", "PrevSeg")

    val orElseDF = sc.parallelize(List(1, 2, 3, 4)).toDF("Dummy")
  }

  //CREATE MIGRATION DF TESTS
  test("create migDF from segDF") {
    new CreateMigrationData {

      import sqlCtx.implicits._

      val migDF = Lifecycle.createMigrationDF(twoTPRandomSegments) getOrElse orElseDF
      assert(migDF.columns === List("TimePeriod", "CUST_ID", "CurrSeg", "PrevSeg"))

      val custA = migDF.where("CUST_ID = 'CustA'")

      val custADF = sc.parallelize(List(
        (2, "CustA", "Best in Class", "Best in Class"),
        (1, "CustA", "Best in Class", null)
      )).toDF("TimePeriod", "CUST_ID", "CurrSeg", "PrevSeg")

      assertDataFrameEquals(custA, custADF)

      val custC = migDF.where("CUST_ID = 'CustC'")
      val custCDF = sc.parallelize(List(
        (2, "CustC","Lapsing", "Middle of the Road"),
        (1, "CustC", "Middle of the Road", null)
      )).toDF("TimePeriod", "CUST_ID", "CurrSeg", "PrevSeg")

      assertDataFrameEquals(custC, custCDF)

      migDF.show()

    }
  }

  //COUNT SEGMENT NEW AND ALL TOTALS
  test("Count migrations, 2 TimePeriods") {
    new SumMigData {
      val countDF = Lifecycle.countMigTotals(twoTPs) getOrElse orElseDF
      countDF.show()
      assert(countDF.head() === Row(1, 0, 0, 0, 1, 0, 0, 0,2, 0, 0))

    }
  }


  //SUM MIGRATION TESTS

  test("sumMigrations 2 TPs, 3 in both") {
    new SumMigData {
      val sumDF = Lifecycle.sumMigrations(twoTPs, sqlCtx, sc) getOrElse orElseDF
      val count1DF = sumDF.filter(sumDF("Count") === 1)
      assert(count1DF.count() === 3)


    }

  }


  //AVRO CREATION TESTS

}
