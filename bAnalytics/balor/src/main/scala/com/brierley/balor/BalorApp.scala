package com.brierley.balor

import org.apache.spark.sql.DataFrame

/**
  * Created by amerrill on 1/30/17.
  */
object BalorApp {

  def dateFormat(initialDF: DataFrame): DataFrame = ???

  def calcTimePeriod(dateDF: DataFrame, cadence: Int): DataFrame = ???

  def assignSegmentLabel(timePeriodDF: DataFrame): DataFrame = ???

  def counts(labelDF: DataFrame): DataFrame = ???

  def calcBalorRatios(countsDF: DataFrame): DataFrame = ???


}
