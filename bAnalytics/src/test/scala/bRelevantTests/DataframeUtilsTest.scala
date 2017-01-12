package com.brierley.utils

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.functions.{to_date, unix_timestamp}
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}

/**
  * Created by amerrill on 1/11/17.
  */

@RunWith(classOf[JUnitRunner])
class bRelevantTransformationTest extends FunSuite with DataFrameSuiteBase {


  val formatDate = new SimpleDateFormat("dd-MMM-yy")

    val begMonth2: Date = new Date(formatDate.parse("03-FEB-15").getTime)
    val midMonth2: Date = new Date(formatDate.parse("15-FEB-15").getTime)
    val endMonth2: Date = new Date(formatDate.parse("25-FEB-15").getTime)

    val begMonth3_4: Date =new Date(formatDate.parse("04-MAR-15").getTime)
    val begMonth3_6: Date =new Date(formatDate.parse("06-MAR-15").getTime)
    val midMonth3: Date = new Date(formatDate.parse("14-MAR-15").getTime)


  val cutoffDateBeg2: String = "02/05/2015"
  val cutoffDateEnd2: String = "02/28/2015"
  val cutoffDateMid3: String = "03/15/2015"
  val cutoffDateEnd10: String = "10/31/2015"

    val begMonth10: Date = new Date(formatDate.parse("02-OCT-15").getTime)
    val midMonth10: Date = new Date(formatDate.parse("17-OCT-15").getTime)
    val endMonth10: Date = new Date(formatDate.parse("30-OCT-15").getTime)
    val lastMonth10: Date = new Date(formatDate.parse("31-OCT-15").getTime)

  test("productRecencyFunc with begMonth2 and cutoffDateBeg2") {
    val recency = DataframeUtils.productRecencyFunc(cutoffDateBeg2, begMonth2)

    assert(recency === 2)
  }

  test("productRecencyFunc with all Month2 and cutoffDateEnd2") {
    val recency1 = DataframeUtils.productRecencyFunc(cutoffDateEnd2, begMonth2)
    val recency2 = DataframeUtils.productRecencyFunc(cutoffDateEnd2, midMonth2)
    val recency3 = DataframeUtils.productRecencyFunc(cutoffDateEnd2, endMonth2)

    assert(recency1 === 25)
    assert(recency2 === 13)
    assert(recency3 === 3)
  }

  test("productRecencyFunc with begMonth3 and endMonth2") {
    println("String cutoffdate going into func: " + cutoffDateMid3)
    println("endMonth2 Date going into func: " + endMonth2.toString)
    println("begMonth3_4 Date going into func: " + begMonth3_4.toString)
    println("midMonth3 Date going into func: " + midMonth3.toString)
    println("begMonth3_6 Date going into func: " + begMonth3_6.toString)
    val recency = DataframeUtils.productRecencyFunc(cutoffDateMid3, endMonth2)
    val recency1 = DataframeUtils.productRecencyFunc(cutoffDateMid3, begMonth3_4)
    val recency2 = DataframeUtils.productRecencyFunc(cutoffDateMid3, midMonth3)
    val recency3 = DataframeUtils.productRecencyFunc(cutoffDateMid3, begMonth3_6)

    //assert(recency === 18)
    assert(recency2 === 1)
    assert(recency3 === 9) //-- says 8
    assert(recency1 === 11) //-- says it should be 10??
  }

  test("productRecencyFunc with allMonth10 and endMonth10") {
    val recency1 = DataframeUtils.productRecencyFunc(cutoffDateEnd10, begMonth10)
    val recency2 = DataframeUtils.productRecencyFunc(cutoffDateEnd10, midMonth10)
    val recency3 = DataframeUtils.productRecencyFunc(cutoffDateEnd10, endMonth10)
    val recency4 = DataframeUtils.productRecencyFunc(cutoffDateEnd10, lastMonth10)

    assert(recency1 === 29)
    assert(recency2 === 14)
    assert(recency3 === 1)
    assert(recency4 === 0)

  }


}