package com.brierley.lifecycle

import java.sql.Date
import java.time.temporal.ChronoUnit

import org.apache.spark.sql.functions.udf

/**
  * Created by amerrill on 7/25/17.
  */
object LifecycleUDFs {

  def calcQuintFunc(perRank: Double): Int = {
    val bucket = (perRank / .2).ceil

    if (bucket == 0)
      bucket.toInt + 1
    else
      bucket.toInt
  }

  def periodCalcFunc(maxDate: Date, currentDate: Date, period: Int): Int = {
    val maxJava = maxDate.toLocalDate
    val currJava = currentDate.toLocalDate

    (currJava.until(maxJava, ChronoUnit.MONTHS) / period + 1).toInt
  }

  def calcRFMFunc(r: Int, f: Int, m: Int): Int = {
    ((r * 100) + (f * 10) + m)
  }

  def labelRFMFunc(rfm: Int): String = {
    val best = List(455, 515, 525, 534, 535, 544, 545, 554, 555)
    val rising = List(335, 345, 354, 355, 413, 414, 415, 423, 424, 425, 433, 434, 435, 443, 444, 445, 453,
      454, 511, 512, 513, 514, 521, 522, 523, 524, 531, 532, 533, 541, 542, 543, 551, 552, 553)
    val middle = List(234, 235, 244, 245, 254, 255, 314, 315, 324, 325, 333, 334, 343, 344, 351, 352, 353,
      411, 412, 421, 422, 431, 432, 441, 442, 451, 452)
    val lapsing = List(113, 114, 115, 123, 124, 125, 133, 134, 135, 143, 144, 145, 153, 154, 155, 213, 214,
      215, 223, 224, 225, 231, 232, 233, 241, 242, 243, 251, 252, 253, 311, 312, 313,
      321, 322, 323, 331, 332, 341, 342)
    val deeply = List(111, 112, 121, 122, 131, 132, 141, 142, 151, 152, 211, 212, 221, 222)

    if (best.contains(rfm))
      "Best in Class"
    else if (rising.contains(rfm))
      "Rising Stars"
    else if (middle.contains(rfm))
      "Middle of the Road"
    else if (lapsing.contains(rfm))
      "Lapsing"
    else if (deeply.contains(rfm))
      "Deeply Lapsed"
    else
      "Unknown value"

  }

  def longAvgCalcFunc(num: Long, denom: Long): Double = {
    if (num == 0 || denom == 0) 0
    else num.toDouble / denom
  }

  def doubleAvgCalcFunc(num: Double, denom: Long): Double = {
    if (num == 0 || denom == 0) 0
    else num/denom
  }

  val calcQuint = udf(calcQuintFunc(_: Double))
  val periodCalc = udf(periodCalcFunc(_: Date, _: Date, _: Int))
  val calcRFM = udf(calcRFMFunc(_: Int, _: Int, _: Int))
  val labelRFM = udf(labelRFMFunc(_: Int))
  val longAvgCalc = udf(longAvgCalcFunc(_: Long, _: Long))
  val doubleAvgCalc = udf(doubleAvgCalcFunc(_: Double, _: Long))
}
