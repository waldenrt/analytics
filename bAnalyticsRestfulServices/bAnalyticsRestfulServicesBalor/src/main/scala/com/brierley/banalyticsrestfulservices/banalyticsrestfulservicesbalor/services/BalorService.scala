package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.services

import org.apache.hadoop.hbase.util.Bytes
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.utils.{DatabaseService, ccFromMap}
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.models._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.mappings._

import scala.concurrent.{ExecutionContext, Future}

import spray.json._
import collection.JavaConversions._

class BalorService(val databaseService: DatabaseService)
                  (implicit executionContext: ExecutionContext) extends BalorSupport {

  import databaseService._
  import ccFromMap._

  def getBalorSummary(jobId: String): Future[BalorSummaryReturn] = {


    val result = getData("Balor", jobId)

    val balorSummaryFamily = Bytes.toBytes("BalorSummary")
    val balorTimePeriodFamily = Bytes.toBytes("BalorResults")

    val balorSummaryMap = result.getFamilyMap(balorSummaryFamily)
    val balorTimePeriodMap = result.getFamilyMap(balorTimePeriodFamily)


    val balorTimePeriodVector = for (c: Array[Byte] <- balorTimePeriodMap.keySet)
      yield Bytes.toString(result.getValue(balorTimePeriodFamily, c)).parseJson.convertTo[TimePeriod]

    val m: PartialFunction[Array[Byte], (String, _)] = {

      case c if Bytes.toString(c) == "minDateBalor" => Bytes.toString(c) -> Bytes.toString(result.getValue(balorSummaryFamily, c))
      case c if Bytes.toString(c) == "maxDateBalor" => Bytes.toString(c) -> Bytes.toString(result.getValue(balorSummaryFamily, c))
      case c if Bytes.toString(c) == "numRecords" => Bytes.toString(c) -> Bytes.toLong(result.getValue(balorSummaryFamily, c))
      case c if Bytes.toString(c) == "singleVisit" => Bytes.toString(c) -> Bytes.toLong(result.getValue(balorSummaryFamily, c))
      case c if Bytes.toString(c) == "totalCusts" => Bytes.toString(c) -> Bytes.toLong(result.getValue(balorSummaryFamily, c))
      case c if Bytes.toString(c) == "normalizedCadence" => Bytes.toString(c) -> Bytes.toString(result.getValue(balorSummaryFamily, c))

    }

    val balorSummaryVector = for (c: Array[Byte] <- balorSummaryMap.keySet) yield c

    val balorSummaryVectorProcessed = balorSummaryVector.collect(m)

    val balorSummaryTemp = fromMap[BalorSummary](balorSummaryVectorProcessed.toMap)

    val balorSummary = BalorSummary(balorSummaryTemp.minDateBalor,
      balorSummaryTemp.maxDateBalor,
      balorSummaryTemp.numRecords,
      balorSummaryTemp.singleVisit,
      balorSummaryTemp.totalCusts,
      balorSummaryTemp.normalizedCadence,
      Some(balorTimePeriodVector.toList.sortWith(_.timePeriod < _.timePeriod)))

    Future.successful[BalorSummaryReturn](BalorSummaryReturn("false", balorSummary, "", "Success", "", 0, 200, ""))
  }

  def getBalorSegment(jobId: String): Future[BalorSegmentReturn] = {

    val result = getData("Balor", jobId)

    val balorSummaryFamily = Bytes.toBytes("BalorSummary")
    val balorTimePeriodFamily = Bytes.toBytes("BalorResults")

    val balorSummaryMap = result.getFamilyMap(balorSummaryFamily)
    val balorTimePeriodMap = result.getFamilyMap(balorTimePeriodFamily)


    val balorTimePeriodVector = for (c: Array[Byte] <- balorTimePeriodMap.keySet)
      yield Bytes.toString(result.getValue(balorTimePeriodFamily, c)).parseJson.convertTo[TimePeriodFull]

    val m: PartialFunction[Array[Byte], (String, _)] = {

      case c if Bytes.toString(c) == "minDateBalor" => Bytes.toString(c) -> Bytes.toString(result.getValue(balorSummaryFamily, c))
      case c if Bytes.toString(c) == "maxDateBalor" => Bytes.toString(c) -> Bytes.toString(result.getValue(balorSummaryFamily, c))
      case c if Bytes.toString(c) == "numRecords" => Bytes.toString(c) -> Bytes.toLong(result.getValue(balorSummaryFamily, c))
      case c if Bytes.toString(c) == "singleVisit" => Bytes.toString(c) -> Bytes.toLong(result.getValue(balorSummaryFamily, c))
      case c if Bytes.toString(c) == "totalCusts" => Bytes.toString(c) -> Bytes.toLong(result.getValue(balorSummaryFamily, c))
      case c if Bytes.toString(c) == "normalizedCadence" => Bytes.toString(c) -> Bytes.toString(result.getValue(balorSummaryFamily, c))

    }

    val balorSummaryVector = for (c: Array[Byte] <- balorSummaryMap.keySet) yield c

    val balorSummaryVectorProcessed = balorSummaryVector.collect(m)

    val balorSummaryTemp = fromMap[BalorSummary](balorSummaryVectorProcessed.toMap)

    val balorSegment = BalorSegment(balorSummaryTemp.minDateBalor,
      balorSummaryTemp.maxDateBalor,
      balorSummaryTemp.numRecords,
      balorSummaryTemp.singleVisit,
      balorSummaryTemp.totalCusts,
      balorSummaryTemp.normalizedCadence,
      Some(balorTimePeriodVector.toList.sortWith(_.timePeriod < _.timePeriod)))

    Future.successful[BalorSegmentReturn](BalorSegmentReturn("false", balorSegment, "", "Success", "", 0, 200, ""))
  }

  def getBalorCadence(jobId: String): Future[BalorCadenceReturn] = {

    val result = getData("Cadence", jobId)

    val cadenceSummaryFamily = Bytes.toBytes("CadenceSummary")
    val cadenceDetailsFamily = Bytes.toBytes("CadenceDetails")

    val cadenceSummaryMap = result.getFamilyMap(cadenceSummaryFamily)
    val cadenceDetailsMap = result.getFamilyMap(cadenceDetailsFamily)

    val cadenceDetailsVector = for (c: Array[Byte] <- cadenceDetailsMap.keySet)
      yield Bytes.toString(result.getValue(cadenceDetailsFamily, c)).parseJson.convertTo[FreqTable]

    val m: PartialFunction[Array[Byte], (String, _)] = {

      case c if Bytes.toString(c) == "minDateCadence" => Bytes.toString(c) -> Bytes.toString(result.getValue(cadenceSummaryFamily, c))
      case c if Bytes.toString(c) == "maxDateCadence" => Bytes.toString(c) -> Bytes.toString(result.getValue(cadenceSummaryFamily, c))
      case c if Bytes.toString(c) == "numRecords" => Bytes.toString(c) -> Bytes.toLong(result.getValue(cadenceSummaryFamily, c))
      case c if Bytes.toString(c) == "singleVisit" => Bytes.toString(c) -> Bytes.toLong(result.getValue(cadenceSummaryFamily, c))
      case c if Bytes.toString(c) == "normalizedCadence" => Bytes.toString(c) -> Bytes.toString(result.getValue(cadenceSummaryFamily, c))
      case c if Bytes.toString(c) == "rawCadence" => Bytes.toString(c) -> Bytes.toDouble(result.getValue(cadenceSummaryFamily, c))
      case c if Bytes.toString(c) == "numTimePeriods" => Bytes.toString(c) -> Bytes.toInt(result.getValue(cadenceSummaryFamily, c))
      case c if Bytes.toString(c) == "percentile" => Bytes.toString(c) -> Bytes.toDouble(result.getValue(cadenceSummaryFamily, c))

    }

    val cadenceSummaryVector = for (c: Array[Byte] <- cadenceSummaryMap.keySet) yield c
    val cadenceSummaryVectorProcessed = cadenceSummaryVector.collect(m)

    val cadenceSummaryTemp = fromMap[BalorCadence](cadenceSummaryVectorProcessed.toMap)

    val balorCadence = BalorCadence(cadenceSummaryTemp.numRecords,
      cadenceSummaryTemp.singleVisit,
      cadenceSummaryTemp.minDateCadence,
      cadenceSummaryTemp.maxDateCadence,
      cadenceSummaryTemp.rawCadence,
      cadenceSummaryTemp.normalizedCadence,
      cadenceSummaryTemp.numTimePeriods,
      cadenceSummaryTemp.percentile,
      Some(cadenceDetailsVector.toList.sortWith(_.cadence < _.cadence)))

    Future.successful[BalorCadenceReturn](BalorCadenceReturn("false", balorCadence, "", "Success", "", 0, 200, ""))
  }
}
