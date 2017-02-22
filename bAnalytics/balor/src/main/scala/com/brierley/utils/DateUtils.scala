package com.brierley.utils

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
//{unix_timestamp, to_date}

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

  def determineFormat(initialDF: DataFrame): DataFrame = {
    val MMddyyyySlashRegEx1 = """([0-1][0-9]|[0-9])/([0-3][0-9]|[0-9])/[0-9]{4}"""
    val MMddyyyyDashRegEx2 = """([0-1][0-9]|[0-9])-([0-3][0-9]|[0-9])-[0-9]{4}"""
    val ddMMyyyySlashRegEx3 = """([0-3][0-9]|[0-9])/([0-1][0-9]|[0-9])/[0-9]{4}"""
    val ddMMyyyyDashRegEx4 = """([0-3][0-9]|[0-9])-([0-1][0-9]|[0-9])-[0-9]{4}"""
    val yyyyMMddSlashRegEx5 = """[0-9]{4}/([0-1][0-9]|[0-9])/([0-3][0-9]|[0-9])"""
    val yyyyMMddDashRegEx6 = """[0-9]{4}-([0-1][0-9]|[0-9])-([0-3][0-9]|[0-9])"""

    val dateOnly = initialDF.select("TXN_DATE").distinct()

    val totalDates = dateOnly.count()

    val Count1 = dateOnly.filter(dateOnly("TXN_DATE").rlike(MMddyyyySlashRegEx1)).count()
    val Count2 = dateOnly.filter(dateOnly("TXN_DATE").rlike(MMddyyyyDashRegEx2)).count()
    val Count3 = dateOnly.filter(dateOnly("TXN_DATE").rlike(ddMMyyyySlashRegEx3)).count()
    val Count4 = dateOnly.filter(dateOnly("TXN_DATE").rlike(ddMMyyyyDashRegEx4)).count()
    val Count5 = dateOnly.filter(dateOnly("TXN_DATE").rlike(yyyyMMddSlashRegEx5)).count()
    val Count6 = dateOnly.filter(dateOnly("TXN_DATE").rlike(yyyyMMddDashRegEx6)).count()

    //if there are no 0 before single digits then the month count will incorrectly match the day count, check day counts first
    totalDates match{
      case Count3 => convertDateDaySlash(initialDF)
      case Count4 => convertDateDayDash(initialDF)
      case Count1 => convertDateMonthSlash(initialDF)
      case Count2 => convertDateMonthDash(initialDF)
      case Count5 => convertDateYearSlash(initialDF)
      case Count6 => convertDateYearDash(initialDF)
      case _ => initialDF //TODO return error about incorrect dates
    }

  }
  
}
