package com.brierley.utils

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{unix_timestamp, to_date}

/**
  * Created by amerrill on 2/14/17.
  */
object DateUtils {

  def convertDateDaySlash(initialDF: DataFrame): DataFrame = {
    initialDF.withColumn("Date", to_date(unix_timestamp(initialDF("TXN_DATE"), "dd/MM/yyyy").cast("timestamp")))
  }

  def convertDateDayDash(initialDF: DataFrame): DataFrame = {
    initialDF.withColumn("Date", to_date(unix_timestamp(initialDF("TXN_DATE"), "dd-MM-yyyy").cast("timestamp")))
  }

  def convertDateMonthSlash(initialDF: DataFrame): DataFrame = {
    initialDF.withColumn("Date", to_date(unix_timestamp(initialDF("TXN_DATE"), "MM/dd/yyyy").cast("timestamp")))
  }

  def convertDateMonthDash(initialDF: DataFrame): DataFrame = {
    initialDF.withColumn("Date", to_date(unix_timestamp(initialDF("TXN_DATE"), "MM-dd-yyyy").cast("timestamp")))
  }

  def convertDateYearSlash(initialDF: DataFrame): DataFrame = {
    initialDF.withColumn("Date", to_date(unix_timestamp(initialDF("TXN_DATE"), "yyyy/MM/dd").cast("timestamp")))
  }

  def convertDateYearDash(initialDF: DataFrame): DataFrame = {
    initialDF.withColumn("Date", to_date(unix_timestamp(initialDF("TXN_DATE"), "yyyy-MM-dd").cast("timestamp")))
  }

}
