package com.brierley.utils

import java.time.LocalDate
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
import java.time.temporal.ChronoUnit

import org.apache.spark.sql.functions.udf
import java.sql.Date


object DataframeUtils {

  def productRecencyFunc(cutoffDate: String, purchaseDate: Date): Long = {
    val cutOff: LocalDate = LocalDate.parse(cutoffDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"))

    ChronoUnit.DAYS.between(purchaseDate.toLocalDate, cutOff)
  }

  def metricFunc(timePurchased: Double, prodRecency: Double, prodSales: Double): Double = {
    if (prodRecency > 0)
      (timePurchased / prodRecency) * prodSales
    else
      prodSales
  }

  def chiSquareFunc(exp_lhs_rhs: Double,
                    num_lhs_rhs: Double,
                    exp_lhs_no_rhs: Double,
                    num_lhs_no_rhs: Double,
                    exp_no_lhs_rhs: Double,
                    num_no_lhs_rhs: Double,
                    exp_no_lhs_no_rhs: Double,
                    num_no_lhs_no_rhs: Double): Double = {

    Math.pow(exp_lhs_rhs - num_lhs_rhs, 2) / exp_lhs_rhs +
      Math.pow(exp_lhs_no_rhs - num_lhs_no_rhs, 2) / exp_lhs_no_rhs +
      Math.pow(exp_no_lhs_rhs - num_no_lhs_rhs, 2) / exp_no_lhs_rhs +
      Math.pow(exp_no_lhs_no_rhs - num_no_lhs_no_rhs, 2) / exp_no_lhs_no_rhs
  }

  def laplaceFunc(num_lhs_rhs: Double,
                  num_lhs: Double): Double = {

    (num_lhs_rhs + 1) / (num_lhs + 2)
  }

  def convictionFunc(num_basket: Double,
                     num_lhs_no_rhs: Double,
                     num_lhs: Double,
                     num_no_rhs: Double): Double = {

    if (num_basket * num_lhs_no_rhs > 0) {
      (num_lhs * num_no_rhs) / (num_basket * num_lhs_no_rhs)
    } else
      -9999
  }

  def addedValueFunc(num_lhs_rhs: Double,
                     num_lhs: Double,
                     num_rhs: Double,
                     num_basket: Double): Double = {

    num_lhs_rhs / num_lhs - num_rhs / num_basket
  }

  def certaintyFactorFunc(num_lhs_rhs: Double,
                          num_lhs: Double,
                          num_rhs: Double,
                          num_basket: Double): Double = {

    (num_lhs_rhs / num_lhs - num_rhs / num_basket) / (1 - num_rhs / num_basket)
  }

  def jMeasureFunc
  (num_basket: Double,
   num_lhs_no_rhs: Double,
   num_lhs_rhs: Double,
   num_lhs: Double,
   num_rhs: Double,
   num_no_rhs: Double): Double = {

    if (num_basket * num_lhs_no_rhs > 0) {
      num_lhs_rhs / num_basket *
        Math.log((num_basket * num_lhs_rhs) / (num_lhs * num_rhs)) +
        (num_lhs_no_rhs / num_basket *
          Math.log((num_basket * num_lhs_no_rhs) / (num_lhs * num_no_rhs)))

    } else {
      -9999.0
    }

  }

  def giniIndexFunc(num_lhs: Double,
                    num_basket: Double,
                    num_lhs_rhs: Double,
                    num_lhs_no_rhs: Double,
                    num_rhs: Double,
                    num_no_lhs: Double,
                    num_no_lhs_no_rhs: Double,
                    num_no_rhs: Double,
                    num_no_lhs_rhs: Double): Double = {

    num_lhs / num_basket * (
      Math.pow(num_lhs_rhs, 2) +
        Math.pow(num_lhs_no_rhs, 2)) /
      Math.pow(num_lhs, 2) -
      Math.pow(num_rhs / num_basket, 2) +
      num_no_lhs / num_basket * (
        Math.pow(num_no_lhs_rhs, 2) +
          Math.pow(num_no_lhs_no_rhs, 2)) /
        Math.pow(num_no_lhs, 2) -
      Math.pow(num_no_rhs / num_basket, 2)

  }

  def jaccardFunc(num_lhs_rhs: Double,
                  num_lhs: Double,
                  num_rhs: Double): Double = {

    num_lhs_rhs / (num_lhs + num_rhs - num_lhs_rhs)
  }

  def shapiroFunc(num_lhs_rhs: Double,
                  num_basket: Double,
                  num_lhs: Double,
                  num_rhs: Double): Double = {

    if (num_basket == 0) {
      0.0
    } else {
      num_lhs_rhs / num_basket - num_rhs * num_lhs / Math.pow(num_basket, 2)
    }
  }

  def cosineFunc(num_lhs_rhs: Double,
                 num_lhs: Double,
                 num_rhs: Double): Double = {

    if (Math.sqrt(num_lhs) * Math.sqrt(num_rhs) > 0) {
      num_lhs_rhs / (Math.sqrt(num_lhs) * Math.sqrt(num_rhs))
    } else {
      0.0
    }
  }

  def correlationFunc(num_lhs_rhs: Double,
                      num_basket: Double,
                      num_lhs: Double,
                      num_rhs: Double,
                      num_no_lhs: Double,
                      num_no_rhs: Double): Double = {

    if (Math.sqrt(num_lhs * num_rhs * num_no_lhs * num_no_rhs) > 0)
      (num_lhs_rhs * num_basket - num_lhs * num_rhs) /
        Math.sqrt(num_lhs * num_rhs * num_no_lhs * num_no_rhs)
    else
      0.0
  }

  def oddsRationFunc(num_lhs_no_rhs: Double,
                     num_no_lhs_rhs: Double,
                     num_lhs_rhs: Double,
                     num_no_lhs_no_rhs: Double): Double = {

    if (num_lhs_no_rhs * num_no_lhs_rhs > 0) {
      num_lhs_rhs * num_no_lhs_no_rhs / (num_lhs_no_rhs * num_no_lhs_rhs)
    } else {
      -9999
    }
  }

  def simScoreFunc(lift: Double,
                   cosSim: Double,
                   support: Double,
                   num_rhs: Double): Double = {

    if (num_rhs > 0) {
      10000 * (lift * ((cosSim * support) / num_rhs))
    } else {
      0.0
    }
  }

  def tryScoreFunc(beta0: Double,
                   beta1: Double,
                   beta2: Double,
                   beta3: Double,
                   beta4: Double,
                   RFM_SIM_SCORE: Double,
                   LIFT_SIM_SCORE: Double,
                   LAPLACE_SIM_SCORE: Double,
                   CORRELATION_SIM_SCORE: Double): Double = {

    1 / (1 + scala.math.exp(-1 * (beta0
      + RFM_SIM_SCORE * beta1
      + LIFT_SIM_SCORE * beta2
      + LAPLACE_SIM_SCORE * beta3
      + CORRELATION_SIM_SCORE * beta4)))
  }

  def tryScore2(coefficients: Seq[Double],
                intercept: Double) = udf(
    (array: Seq[Double]) =>
      1 / (1 + scala.math.exp(-1 * intercept
        + (array.head * coefficients.head)
        + (array(1) * coefficients(1))
        + (array(2) * coefficients(2))
        + (array(3) * coefficients(3))
        + (array(4) * coefficients(4))
        + (array(5) * coefficients(5))
        + (array(6) * coefficients(6))
        + (array(7) * coefficients(7))
        + (array(8) * coefficients(8))
        + (array(9) * coefficients(9))
        + (array(10) * coefficients(10))
        + (array(11) * coefficients(11))
        + (array(12) * coefficients(12))
        + (array(13) * coefficients(13))
        + (array(14) * coefficients(14))
      )))


  val productRecency = udf(productRecencyFunc(_: String, _: Date))
  val metric = udf(metricFunc(_: Double, _: Double, _: Double))
  val chiSquare = udf(chiSquareFunc(_: Double, _: Double, _: Double, _: Double, _: Double, _: Double, _: Double, _: Double))
  val laplace = udf(laplaceFunc(_: Double, _: Double))
  val conviction = udf(convictionFunc(_: Double, _: Double, _: Double, _: Double))
  val addedValue = udf(addedValueFunc(_: Double, _: Double, _: Double, _: Double))
  val certaintyFactor = udf(certaintyFactorFunc(_: Double, _: Double, _: Double, _: Double))
  val jMeasure = udf(jMeasureFunc(_: Double, _: Double, _: Double, _: Double, _: Double, _: Double))
  val giniIndex = udf(giniIndexFunc(_: Double, _: Double, _: Double, _: Double, _: Double, _: Double, _: Double, _: Double, _: Double))
  val jaccard = udf(jaccardFunc(_: Double, _: Double, _: Double))
  val shapiro = udf(shapiroFunc(_: Double, _: Double, _: Double, _: Double))
  val cosine = udf(cosineFunc(_: Double, _: Double, _: Double))
  val correlation = udf(correlationFunc(_: Double, _: Double, _: Double, _: Double, _: Double, _: Double))
  val oddsRatio = udf(oddsRationFunc(_: Double, _: Double, _: Double, _: Double))
  val simScore = udf(simScoreFunc(_: Double, _: Double, _: Double, _: Double))
  val tryScore = udf(tryScoreFunc(_: Double, _: Double, _: Double, _: Double, _: Double, _: Double, _: Double, _: Double, _: Double))
  //val tryScore2 = udf(tryScore2Func(_:Seq[Double], _:Double, _:Seq[Double]))


}
