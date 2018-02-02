package com.brierley.quantile

import java.sql.Date
import java.time.temporal.ChronoUnit
import org.apache.spark.sql.functions.udf

/**
  * Created by amerrill on 6/26/17.
  */
object QuantileUDFs {

  def periodCalcFunc(maxDate: Date, currentDate: Date, period: Int): Int = {
    val maxJava = maxDate.toLocalDate
    val currJava = currentDate.toLocalDate

    (currJava.until(maxJava, ChronoUnit.MONTHS) / period + 1).toInt
  }

  def calcQuantFunc(perRank: Double, quant: Double): Int = {
    val bucket = (perRank/quant).ceil

    if(bucket == 0)
      bucket.toInt + 1
    else
      bucket.toInt
  }

  val periodCalc = udf(periodCalcFunc(_: Date, _: Date, _: Int))
  val calcQuant = udf(calcQuantFunc(_: Double, _: Double))
}
