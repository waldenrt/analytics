package com.brierley.utils

import org.apache.spark.sql.functions.udf

/**
  * Created by amerrill on 3/6/17.
  */
object BalorUDFs {

  def nonLapsedLabelFunc(tp: Long, sum: Long): String = {
    if(sum == tp) "New"
    else if(sum == (tp * 2 + 2)) "Reactivated"
    else if(sum == (tp * 2 + 1) || sum == (tp * 3 + 3)) "Returning"
    else "None"
  }

  def balorMoneyFunc(newVal: Double, react: Double, lapsed: Double): Double = {
    if (lapsed == 0) (newVal + react)/ 1
    else (newVal + react) / lapsed
  }

  def balorCountFunc(newVal: Long, react: Long, lapsed: Long): Double = {
    if (lapsed == 0) (newVal + react)/ 1
    else (newVal + react) / lapsed.toDouble
  }

  val nonLapsedLabel = udf(nonLapsedLabelFunc(_: Long, _: Long))
  val balorMoney = udf(balorMoneyFunc(_: Double, _: Double, _: Double))
  val balorCount = udf(balorCountFunc(_: Long, _: Long, _: Long))

}
