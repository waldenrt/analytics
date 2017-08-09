package com.brierley.utils

import org.apache.spark.sql.functions.udf

/**
  * Created by amerrill on 3/6/17.
  */
object BalorUDFs {

  def nonLapsedLabelFunc(tp: Long, sum: Long): String = {
    if (sum == tp) "New"
    else if (sum == (tp * 2 + 2)) "Reactivated"
    else if (sum == (tp * 2 + 1) || sum == (tp * 3 + 3)) "Returning"
    else "None"
  }

  def balorMoneyFunc(newVal: Double, react: Double, lapsed: Double): Double = {
    if (lapsed == 0) (newVal + react) / 1
    else (newVal + react) / lapsed
  }

  def balorCountFunc(newVal: Long, react: Long, lapsed: Long): Double = {
    if (lapsed == 0) (newVal + react) / 1
    else (newVal + react) / lapsed.toDouble
  }

  def retentionFunc(currRet: Long, lastReact: Long, lastNew: Long, lastRet: Long): Double = {
    if (currRet == 0) 0
    else currRet.toDouble / (lastReact + lastNew + lastRet)
  }

  def longAvgCalcFunc(num: Long, denom: Long): Double = {
    if (num == 0 || denom == 0) 0
    else num.toDouble / denom
  }

  def doubleAvgCalcFunc(num: Double, denom: Long): Double = {
    if(num == 0 || denom == 0) 0
    else num/denom
  }

  def stringDateFunc(date: java.sql.Date, cad: Int): String = {
    if(cad == 1 || cad == 2) {
      val format = new java.text.SimpleDateFormat("MM/dd/yyyy")
      format.format(date)
    }
    else {
      val format = new java.text.SimpleDateFormat("MMM yyyy")
      format.format(date)
    }
  }


  val nonLapsedLabel = udf(nonLapsedLabelFunc(_: Long, _: Long))
  val balorMoney = udf(balorMoneyFunc(_: Double, _: Double, _: Double))
  val balorCount = udf(balorCountFunc(_: Long, _: Long, _: Long))
  val retention = udf(retentionFunc(_: Long, _: Long, _: Long, _: Long))
  val longAvgCalc = udf(longAvgCalcFunc(_: Long, _: Long))
  val doubleAvgCalc = udf(doubleAvgCalcFunc(_: Double, _: Long))
  val stringDate = udf(stringDateFunc(_: java.sql.Date, _: Int))

}
