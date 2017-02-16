package com.brierley.balor

import org.apache.spark.sql.DataFrame

/**
  * Created by amerrill on 1/30/17.
  */
object CadenceCalcs {

  def dateFormat(initialDF: DataFrame): DataFrame = ???

  def daysSinceLastVisit(dateDF: DataFrame): DataFrame = ???

  def calculateCadenceValue(daysDF: DataFrame): Double = ???

  def normalizeCadenceValue(cadence: Double): Int = ???

  def calcNumTimePeriods(cadenceValue: Int, cadenceDF: DataFrame): Int = ???

  def createFreqTable(daysDF: DataFrame): DataFrame = ???

}


