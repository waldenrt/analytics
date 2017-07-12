package com.brierley.utils

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._
import java.sql.Date
import java.time.temporal.TemporalAdjusters._

import org.apache.spark.storage.StorageLevel

import scala.util.{Failure, Try}

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

  def determineFormat(initialDF: DataFrame): Try[DataFrame] = Try {
    val MMddyyyySlashRegEx1 = """([0-1][0-9]|[0-9])/([0-3][0-9]|[0-9])/[0-9]{4}"""
    val MMddyyyyDashRegEx2 = """([0-1][0-9]|[0-9])-([0-3][0-9]|[0-9])-[0-9]{4}"""
    val ddMMyyyySlashRegEx3 = """([0-3][0-9]|[0-9])/([0-1][0-9]|[0-9])/[0-9]{4}"""
    val ddMMyyyyDashRegEx4 = """([0-3][0-9]|[0-9])-([0-1][0-9]|[0-9])-[0-9]{4}"""
    val yyyyMMddSlashRegEx5 = """[0-9]{4}/([0-1][0-9]|[0-9])/([0-3][0-9]|[0-9])"""
    val yyyyMMddDashRegEx6 = """[0-9]{4}-([0-1][0-9]|[0-9])-([0-3][0-9]|[0-9])"""

    val dateOnly = initialDF.select("TXN_DATE").distinct().limit(30)
    dateOnly.persist(StorageLevel.MEMORY_AND_DISK)

    val totalDates = dateOnly.count()

    if (totalDates == dateOnly.filter(dateOnly("TXN_DATE").rlike(ddMMyyyySlashRegEx3)).count()) {
      val dateDF = convertDateDaySlash(initialDF)
      dateDF.persist(StorageLevel.MEMORY_AND_DISK)
      return Try(dateDF)
    } else if (totalDates == dateOnly.filter(dateOnly("TXN_DATE").rlike(ddMMyyyyDashRegEx4)).count()) {
      val dateDF = convertDateDayDash(initialDF)
      dateDF.persist(StorageLevel.MEMORY_AND_DISK)
      return Try(dateDF)
    } else if (totalDates == dateOnly.filter(dateOnly("TXN_DATE").rlike(MMddyyyySlashRegEx1)).count()) {
      val dateDF = convertDateMonthSlash(initialDF)
      dateDF.persist(StorageLevel.MEMORY_AND_DISK)
      return Try(dateDF)
    } else if (totalDates == dateOnly.filter(dateOnly("TXN_DATE").rlike(MMddyyyyDashRegEx2)).count()) {
      val dateDF = convertDateMonthDash(initialDF)
      dateDF.persist(StorageLevel.MEMORY_AND_DISK)
      return Try(dateDF)
    } else if (totalDates == dateOnly.filter(dateOnly("TXN_DATE").rlike(yyyyMMddSlashRegEx5)).count()) {
      val dateDF = convertDateYearSlash(initialDF)
      dateDF.persist(StorageLevel.MEMORY_AND_DISK)
      return Try(dateDF)
    } else if (totalDates == dateOnly.filter(dateOnly("TXN_DATE").rlike(yyyyMMddDashRegEx6)).count()) {
      val dateDF = convertDateYearDash(initialDF)
      dateDF.persist(StorageLevel.MEMORY_AND_DISK)
      return Try(dateDF)
    } else {
      return new Failure(new NumberFormatException("Bad Date Format"))
    }


    //this requires to filter for every format and then count in order to use the match
    /*val Count1 = dateOnly.filter(dateOnly("TXN_DATE").rlike(MMddyyyySlashRegEx1)).count()
    val Count2 = dateOnly.filter(dateOnly("TXN_DATE").rlike(MMddyyyyDashRegEx2)).count()
    val Count3 = dateOnly.filter(dateOnly("TXN_DATE").rlike(ddMMyyyySlashRegEx3)).count()
    val Count4 = dateOnly.filter(dateOnly("TXN_DATE").rlike(ddMMyyyyDashRegEx4)).count()
    val Count5 = dateOnly.filter(dateOnly("TXN_DATE").rlike(yyyyMMddSlashRegEx5)).count()
    val Count6 = dateOnly.filter(dateOnly("TXN_DATE").rlike(yyyyMMddDashRegEx6)).count()

    //if there are no 0 before single digits then the month count will incorrectly match the day count, check day counts first
    val dateDF = totalDates match {
      case Count3 => convertDateDaySlash(initialDF)
      case Count4 => convertDateDayDash(initialDF)
      case Count1 => convertDateMonthSlash(initialDF)
      case Count2 => convertDateMonthDash(initialDF)
      case Count5 => convertDateYearSlash(initialDF)
      case Count6 => convertDateYearDash(initialDF)
      case _ => return new Failure(new Throwable("BadDateFormat"))
    }

    dateDF.persist(StorageLevel.MEMORY_AND_DISK)
    dateDF*/

  }

  def trimToWholeMonth(dateDF: DataFrame): DataFrame = {
    val dayOfMonthDF = dateDF.select("Date")
      .withColumn("day", dayofmonth(dateDF("Date")))
      .withColumn("lastDay", last_day(dateDF("Date")))

    val maxMin = dayOfMonthDF
      .select(max("lastDay"), max("Date"), min("Date"))
      .withColumn("minDay", dayofmonth(col("min(Date)")))
      .withColumn("minLastDay", last_day(col("min(Date)")))
      .withColumn("maxDiff", datediff(col("max(lastDay)"), col("max(Date)")))
      .head()

    val maxLastDate = maxMin.getAs[java.sql.Date](0).toLocalDate
    val minDay = maxMin.getInt(3)
    val minLastDay = maxMin.getAs[java.sql.Date](4)
    val maxDiff = maxMin.getInt(5)

    val prevMonthEnd = Date.valueOf(maxLastDate.`with`(firstDayOfMonth()))

    if (minDay > 1 && maxDiff > 0) {
      dateDF
        .select("*")
        .where(dateDF("Date") > minLastDay && dateDF("Date") < prevMonthEnd)
    }
    else if (minDay > 1) {
      dateDF
        .select("*")
        .where(dateDF("Date") > minLastDay)
    }
    else if (maxDiff > 0) {
      dateDF
        .select("*")
        .where(dateDF("Date") < prevMonthEnd)
    }
    else dateDF
  }

  def trimWeeks(dateDF: DataFrame, numDays: Int): DataFrame = {
    val maxMinDF = dateDF
      .select(max("Date"), min("Date"))

    dateDF
      .withColumn("max(Date)", lit(maxMinDF.select("max(Date)").first().getDate(0)))
      .withColumn("min(Date)", lit(maxMinDF.select("min(Date)").first().getDate(0)))
      .withColumn("End", datediff(col("max(Date)"), col("min(Date)")) / numDays)
      .filter(((datediff(col("max(Date)"), dateDF("Date")) + 1) / numDays) <= col("End"))
  }

  def trimMonths(dateDF: DataFrame): DataFrame = {
    val maxMinDF = dateDF.select(max("Date"), min("Date"))
      .withColumn("maxDay", dayofmonth(col("max(Date)")))
      .withColumn("lastDay", last_day(col("max(Date)")))
      .withColumn("minDay", dayofmonth(col("min(Date)")))
      .head()

    val maxDate = maxMinDF.getAs[java.sql.Date](0)
    val minDate = maxMinDF.getAs[java.sql.Date](1).toLocalDate
    val maxDay = maxMinDF.getInt(2)
    val lastDay = maxMinDF.getAs[java.sql.Date](3)
    val minDay = maxMinDF.getInt(4)
    val minEnd = Date.valueOf(minDate.`with`(lastDayOfMonth()))

    if ((maxDate == lastDay && minDay == 1) || (maxDay + 1 == minDay)) {
      dateDF
    }
    else if (maxDate == lastDay) {
      dateDF
        .select("*")
        .where(dateDF("Date") > minEnd)
    }
    else {
      if (minDay > maxDay + 1) {
        val minMaxDayMore = Date.valueOf(minDate.plusMonths(1).withDayOfMonth(maxDay))
        dateDF
          .select("*")
          .where(dateDF("Date") >= minMaxDayMore)
      }
      else {
        val minMaxDayLess = Date.valueOf(minDate.withDayOfMonth(maxDay))
        dateDF
          .select("*")
          .where(dateDF("Date") >= minMaxDayLess)
      }
    }
  }

}
