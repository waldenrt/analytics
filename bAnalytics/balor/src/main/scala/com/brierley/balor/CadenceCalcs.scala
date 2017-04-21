package com.brierley.balor

import java.time.LocalDateTime
import java.util

import com.brierley.avro.schemas.{Cadence, Error, FreqRow, exception}
import com.brierley.utils._
import org.apache.spark.sql._
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.{Failure, Success, Try}

/**
  * Created by amerrill on 1/30/17.
  */
object CadenceCalcs {

  def loadFile(sqlCtx: HiveContext, delimiter: String, fileLocation: String): Try[DataFrame] = Try{

    val orgFile = sqlCtx
      .read
      .format("com.databricks.spark.csv")
      .option("header", "true")
      .option("delimiter", delimiter)
      .load(fileLocation)

    orgFile
      .select("CUST_ID", "TXN_ID", "TXN_DATE")
      .na.drop(Seq("CUST_ID", "TXN_ID", "TXN_DATE"))

  }

  def basicCounts(orgDF: DataFrame): Try[Long] = Try {

    val singleVisitCount = orgDF
      .groupBy("CUST_ID")
      .agg(count("TXN_ID"))
      .filter(col("count(TXN_ID)") === 1)
      .count()

    singleVisitCount
  }

  def dateInfo(dateDF: DataFrame): Try[DataFrame] = Try {
    val begEndDates = dateDF
      .select(min("Date"), max("Date"))

    begEndDates
  }

  def daysSinceLastVisit(dateDF: DataFrame): Try[DataFrame] = Try {
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

  def normalizeCadenceValue(cadence: Double): Try[CadenceValues] = Try {

    cadence match {
      case a if a > 183 => OneYear
      case b if b > 92 => SixMonths
      case c if c > 61 => ThreeMonths
      case d if d > 30 => TwoMonths
      case e if e > 14 => OneMonth
      case f if f > 7 => TwoWeeks
      case g if g <= 7 => OneWeek
    }
  }

  def calcNumTimePeriods(cadenceValue: CadenceValues, dateDF: DataFrame): Try[Int] = Try {

    if (cadenceValue >= OneMonth) {
      val trimDF = DateUtils.trimToWholeMonth(dateDF)
      if (trimDF.count() == 0) return Try(0)

      val minMaxMonthsDF = trimDF
        .select(min("Date"), max("Date"))
        .withColumn("Months", months_between(col("max(Date)"), col("min(Date)")))
      val difference = minMaxMonthsDF
        .select("Months")
        .head().getDouble(0).toInt

      cadenceValue match {
        case OneMonth => return Try(difference + 1)
        case TwoMonths => return Try(difference / 2 + 1)
        case ThreeMonths => return Try(difference / 3 + 1)
        case SixMonths => return Try(difference / 6 + 1)
        case OneYear => return Try(difference / 12 + 1)
      }
    } else {
      val minMaxDate = dateDF.select(min("Date"), max("Date"))
      val difference = minMaxDate
        .select(datediff(minMaxDate("max(Date)"), minMaxDate("min(Date)")))
        .head().getInt(0)

      cadenceValue match {
        case OneWeek => return Try(difference / 7)
        case TwoWeeks => return Try(difference / 14)
      }
    }

  }

  def createFreqTable(cadenceDF: DataFrame, cadenceValue: CadenceValues): Try[DataFrame] = Try {

    if (cadenceValue < TwoMonths) {
      val freqDF = cadenceDF
        .select("Cadence")
        .groupBy("Cadence")
        .agg(count(cadenceDF("Cadence")).as("Frequency"))

      //no window partition will bring everything back to driver, at this point there will be a max of 31 rows so it should be fine
      val cadenceWindow = Window.orderBy("Cadence")

      val runningTotal = sum("Frequency").over(cadenceWindow).as("CumFrequency")
      val cumFreqDF = freqDF
        .select(freqDF("*"), runningTotal).orderBy("Cadence")
      (cumFreqDF)
    }

    else {
      val binDF = cadenceDF
        .select("*")
        .withColumn("Bin", ((cadenceDF("Cadence") + 7) / 7).cast(IntegerType))

      val binSumDF = binDF
        .select("Bin")
        .groupBy("Bin")
        .agg(count(binDF("Bin")).as("Frequency"))

      val binWindow = Window.orderBy("Bin")
      val binTotal = sum("Frequency").over(binWindow).as("CumFrequency")
      val cumBinDF = binSumDF
        .select(binSumDF("*"), binTotal).orderBy("Bin")

      (cumBinDF)
    }

  }

  def createCadenceAvro(jobKey: String, numRecords: Long, singleVisit: Long, rawCadence: Double, normalCadence: String,
                        numTimePeriods: Int, percentile: Double, minMaxDF: DataFrame, freqTable: DataFrame): Try[Cadence] = Try {

    val cadAvro = new Cadence()
    cadAvro.setJobKey(jobKey)
    cadAvro.setNumRecords(numRecords)
    cadAvro.setSingleVisit(singleVisit)
    cadAvro.setRawCadence(rawCadence)
    cadAvro.setNormalizedCadence(normalCadence)
    cadAvro.setNumTimePeriods(numTimePeriods)
    cadAvro.setPercentile(percentile)
    cadAvro.setCompletionTime(LocalDateTime.now().toString)

    val minDate = minMaxDF
      .select("min(Date)")
      .head()
      .getDate(0)
      .toString

    val maxDate = minMaxDF
      .select("max(Date)")
      .head()
      .getDate(0)
      .toString

    cadAvro.setMinDateCadence(minDate)
    cadAvro.setMaxDateCadence(maxDate)

    val tempList = new java.util.ArrayList[FreqRow]

    def mapFreqRow(freqRow: Row): Unit = {
      val freq = new FreqRow()
      freq.setCadence(freqRow.getInt(0))
      freq.setFrequency(freqRow.getLong(1))
      freq.setCumFreq(freqRow.getLong(2))

      tempList.add(freq)
    }

    freqTable.collect().foreach(f => mapFreqRow(f))
    cadAvro.setFreqTable(tempList)

    cadAvro

  }

  def sendCadError(jobKey: String, className: String, methodName:String, msg: String, exType: String): Unit = {
    val error = new Error()
    error.setJobKey(jobKey)
    error.setJobType("Cadence")

    val ex = new exception()
    ex.setClassName(className)
    ex.setMethodName(methodName)
    ex.setExceptionMsg(msg)
    ex.setExceptionType(exType)

    val tempList = new util.ArrayList[exception]
    tempList.add(ex)

    error.setErrorInfo(tempList)

    BalorProducer.sendError(error)

  }

  def main(args: Array[String]): Unit = {

    if (args.length < 4) {
      sendCadError("Unknown", "CadenceCalcs", "MainMethod", "Incorrect Usage, not enough args.", "User")
      System.exit(-1)
    }

    val fileLocation = args(0)
    val delimiter = args(1)
    val jobKey = args(2)

    val percentile = args(3).toDouble
    if (percentile < .75 || percentile > .95) {
      sendCadError(jobKey, "CadenceCalcs", "MainMethod", "Incorrect Usage, percentile is out of range .75 - .95", "User")
      System.exit(-1)
    }

    val jobName = "CadenceCalcs"
    val conf = new SparkConf().setAppName(jobName)
      .set("spark.driver.maxResultSize", "3g")
      .set("spark.sql.autoBroadcastJoinThreshold", "500000000")
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.speclation", "true")

    val sc = new SparkContext(conf)
    val sqlCtx = new HiveContext(sc)

    val results = for {

      orgFile <- loadFile(sqlCtx, delimiter, fileLocation)

      singleVisitCount <- basicCounts(orgFile)

      rowCount = orgFile.count()

      dateDF <- DateUtils.determineFormat(orgFile)

      minMaxDateDF <- dateInfo(dateDF)

      daysDF <- daysSinceLastVisit(dateDF)

      (rawCadence, cadenceDF) = calculateCadenceValue(daysDF, percentile, sqlCtx)

      cadence <- normalizeCadenceValue(rawCadence)

      timePeriods <- calcNumTimePeriods(cadence, dateDF)

      freqTable <- createFreqTable(cadenceDF, cadence)

      cadenceAvro <- createCadenceAvro(jobKey, rowCount, singleVisitCount, rawCadence, cadence.name, timePeriods, percentile, minMaxDateDF, freqTable)

    } yield cadenceAvro

    results match {
      case Success(avro) => {
        BalorProducer.sendBalor("CadenceCalcs", avro)
        println(avro)
        sc.stop()
      }
      case Failure(ex) => {
        ex match {
          case i: MatchError => sendCadError(jobKey, "Cadence", "calcNumTimePeriods","Invalid CadenceValue from enum", "System")
          case j: AnalysisException => sendCadError(jobKey, "Cadence","loadFile" ,"Incorrect File Format, check column names and delimiter", "User")
          case k: NumberFormatException => sendCadError(jobKey, "DateUtils", "determineFormat", k.getMessage, "User")
          case e => sendCadError(jobKey, "Cadence", "unknown", ex.toString, "System")
        }
        sc.stop()
        System.exit(-1)
      }
    }

  }

}