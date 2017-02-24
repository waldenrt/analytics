package com.brierley.balor

import com.brierley.utils.DateUtils
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{DataFrame, SQLContext, functions}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.hive.HiveContext

/**
  * Created by amerrill on 1/30/17.
  */
object CadenceCalcs {

  def daysSinceLastVisit(dateDF: DataFrame): DataFrame = {

    val custWindow = Window
      .partitionBy("CUST_ID")
      .orderBy("Date")

    val prevDate = lag(dateDF("Date"), 1).over(custWindow)

    val cadenceDF = dateDF
      .select("CUST_ID", "TXN_ID", "TXN_DATE", "Date")
      .withColumn("Cadence", datediff(dateDF("Date"), prevDate))

    cadenceDF.na.fill(0)
  }

  def calculateCadenceValue(daysDF: DataFrame, percentile: Double, sqlCtx: HiveContext): (Double, DataFrame) = {
    val custWindow = Window
      .partitionBy("CUST_ID")
      .orderBy("Date")
    val minDate = daysDF.select((min("Date").over(custWindow)).as("MinDate"), daysDF("Date"), daysDF("Cadence"))

    val query = s"select percentile(Cadence, $percentile) as 80th from validTxns"

    val validTxns = minDate
      .filter(minDate("Date") > minDate("MinDate"))
      .select("Cadence")
      .sort("Cadence")
    validTxns.registerTempTable("validTxns")
    val percentiles = sqlCtx.sql(query)
    (percentiles.select("80th").head().getDouble(0), validTxns)
  }

  def normalizeCadenceValue(cadence: Double): Int = {

    cadence match {
      case a if a > 183 => 365
      case b if b > 92 => 183
      case c if c > 60 => 92
      case d if d > 30 => 60
      case e if e > 14 => 30
      case f if f > 7 => 14
      case g if g <= 7 => 7
    }
  }

  def calcNumTimePeriods(cadenceValue: Int, dateDF: DataFrame): Int = {

    val minMaxDate = dateDF.select(min("Date"), max("Date"))
    val difference = minMaxDate
      .select(datediff(minMaxDate("max(Date)"), minMaxDate("min(Date)")))
      .head().getInt(0)

    (difference / cadenceValue)

  }

  def createFreqTable(cadenceDF: DataFrame): DataFrame = {
    cadenceDF.show()
    val freqDF = cadenceDF
      .select("Cadence")
        .groupBy("Cadence")
      .agg(count(cadenceDF("Cadence")).as("Frequency"))

    val cadenceWindow = Window.orderBy("Cadence")

    val runningTotal = sum("Frequency").over(cadenceWindow).as("CumFrequency")
    val cumFreqDF = freqDF
      .select(freqDF("*"), runningTotal).orderBy("Cadence")

    cumFreqDF.show()
    cumFreqDF
  }

  def main(args: Array[String]): (Int, Int, DataFrame) = {

    if (args.length < 3) {
      //TODO return exception
    }

    val fileLocation = args(0)
    val delimiter = args(1)

    val percentile = args(2).toDouble
    if (percentile < .75 || percentile > .95) {
      //TODO return exception about given percentile
    }

    val jobName = "CadenceCalcs"
    val conf = new SparkConf().setAppName(jobName)
      .set("spark.driver.maxResultSize", "3g")
      .set("spark.sql.autoBroadcastJoinThreshold", "500000000")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.speclation", "true")

    val sc = new SparkContext(conf)
    val sqlCtx = new HiveContext(sc)

    val orgFile = sqlCtx
      .read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("delimiter", delimiter)
      .load(fileLocation)

    val dateDF = DateUtils.determineFormat(orgFile)

    val daysDF = daysSinceLastVisit(dateDF)

    val (rawCadence, cadenceDF) = calculateCadenceValue(daysDF, percentile, sqlCtx)

    val cadence = normalizeCadenceValue(rawCadence)

    val timePeriods = calcNumTimePeriods(cadence, dateDF)

    val freqTable = createFreqTable(cadenceDF)


    sc.stop()

    (cadence, timePeriods, freqTable)

  }

}


