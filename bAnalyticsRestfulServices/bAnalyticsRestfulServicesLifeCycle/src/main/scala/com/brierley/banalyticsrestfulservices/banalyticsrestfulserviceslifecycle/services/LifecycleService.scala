package com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.services

import scala.concurrent.{ExecutionContext, Future}
import com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.models._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.utils.DatabaseService
import org.apache.hadoop.hbase.util.Bytes
import com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.mappings._
import spray.json._
import collection.JavaConversions._

class LifecycleService(val databaseService: DatabaseService
                      )(implicit executionContext: ExecutionContext) extends LifeCycleResultJsonSupport
                      with LifeCycleProdResultJsonSupport
                      with MigrationResultsJsonSupport {

  import databaseService._

  def getLifeCycleResults(jobId:String): Future[LifeCycleResultsReturn] = {
    val result = getData("LifeCycle", jobId)

    val lifeCycleResults = Bytes.toBytes("LifeCycleResults")
    val lifeCycleResultsSummary = Bytes.toBytes("LifeCycleSummary")
    val lifeCycleResultsMap = result.getFamilyMap(lifeCycleResults)

    val lifeCycleResultsVector = for (c:Array[Byte] <- lifeCycleResultsMap.keySet)
      yield Bytes.toString(result.getValue(lifeCycleResults,c)).parseJson.convertTo[LifeCycleResultsTimePeriod]

    val lifeCycleResultsData = LifeCycleResults(
      Bytes.toString(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("minDate"))),
      Bytes.toString(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("maxDate"))),
      Bytes.toLong(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("numRecords"))),
      Bytes.toInt(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("timePeriodLength"))),
      Some(lifeCycleResultsVector.toList.sortWith(_.timePeriod < _.timePeriod))
    )

    Future.successful[LifeCycleResultsReturn](LifeCycleResultsReturn("false",lifeCycleResultsData, "","","",0,200,""))
  }

  def getLifeCycleResults(jobId:String, timePeriod:Int): Future[LifeCycleResultsTimePeriod] = {
    val result = getData("LifeCycle", jobId)

    val lifeCycleResults = Bytes.toBytes("LifeCycleResults")
    val lifeCycleResultsSummary = Bytes.toBytes("LifeCycleSummary")
    val lifeCycleResultsMap = result.getFamilyMap(lifeCycleResults)

    val lifeCycleResultsVector = for (c:Array[Byte] <- lifeCycleResultsMap.keySet)
      yield Bytes.toString(result.getValue(lifeCycleResults,c)).parseJson.convertTo[LifeCycleResultsTimePeriod]

    val lifeCycleResultsData = LifeCycleResults(
      Bytes.toString(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("minDate"))),
      Bytes.toString(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("maxDate"))),
      Bytes.toLong(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("numRecords"))),
      Bytes.toInt(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("timePeriodLength"))),
      Some(lifeCycleResultsVector.toList.sortWith(_.timePeriod < _.timePeriod))
    )
    val filteredLifeCycle = lifeCycleResultsVector.filter(_.timePeriod==timePeriod)

    Future.successful(filteredLifeCycle.head)
  }

  def getLifeCycleProdResults(jobId:String): Future[LifeCycleProdResultsReturn] = {
    val result = getData("LifeCycle", jobId)

    val lifeCycleProdResults = Bytes.toBytes("LifeCycleProdResults")
    val lifeCycleResultsSummary = Bytes.toBytes("LifeCycleSummary")
    val lifeCycleProdResultsMap = result.getFamilyMap(lifeCycleProdResults)

    val lifeCycleProdResultsVector = for (c:Array[Byte] <- lifeCycleProdResultsMap.keySet)
      yield Bytes.toString(result.getValue(lifeCycleProdResults,c)).parseJson.convertTo[LifeCycleProdResultsTimePeriod]

    val lifeCycleResultsData = LifeCycleProdResults(
      Bytes.toString(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("minDate"))),
      Bytes.toString(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("maxDate"))),
      Bytes.toLong(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("numRecords"))),
      Bytes.toInt(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("timePeriodLength"))),
      Some(lifeCycleProdResultsVector.toList.sortWith(_.timePeriod < _.timePeriod))
    )

    Future.successful(LifeCycleProdResultsReturn("",lifeCycleResultsData,"","Success","",0,200,""))
  }

  def getLifeCycleProdResults(jobId:String, timePeriod:Int): Future[LifeCycleProdResultsReturn] = {
    val result = getData("LifeCycle", jobId)

    val lifeCycleProdResults = Bytes.toBytes("LifeCycleProdResults")
    val lifeCycleResultsSummary = Bytes.toBytes("LifeCycleSummary")
    val lifeCycleProdResultsMap = result.getFamilyMap(lifeCycleProdResults)

    val lifeCycleProdResultsVector = for (c:Array[Byte] <- lifeCycleProdResultsMap.keySet)
      yield Bytes.toString(result.getValue(lifeCycleProdResults,c)).parseJson.convertTo[LifeCycleProdResultsTimePeriod]

    val filteredResult = lifeCycleProdResultsVector.filter(_.timePeriod==timePeriod)

    val lifeCycleResultsData = LifeCycleProdResults(
      Bytes.toString(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("minDate"))),
      Bytes.toString(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("maxDate"))),
      Bytes.toLong(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("numRecords"))),
      Bytes.toInt(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("timePeriodLength"))),
      Some(filteredResult.toList)
    )

    Future.successful(LifeCycleProdResultsReturn("",lifeCycleResultsData,"","Success","",0,200,""))
  }

  def getMigrationResults(jobId:String): Future[MigrationResultsReturn] = {
    val result = getData("LifeCycle", jobId)

    val migrationResults = Bytes.toBytes("MigrationResults")
    val lifeCycleResultsSummary = Bytes.toBytes("LifeCycleSummary")
    val migrationResultsMap = result.getFamilyMap(migrationResults)

    val migrationResultsVector = for (c:Array[Byte] <- migrationResultsMap.keySet)
      yield Bytes.toString(result.getValue(migrationResults,c)).parseJson.convertTo[MigrationResultsTimePeriod]

    val migrationResultsData = MigrationResults(
      Bytes.toString(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("minDate"))),
      Bytes.toString(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("maxDate"))),
      Bytes.toLong(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("numRecords"))),
      Bytes.toInt(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("timePeriodLength"))),
      Some(migrationResultsVector.toList.sortWith(_.timePeriod < _.timePeriod))
    )

    Future.successful(MigrationResultsReturn("",migrationResultsData,"","Success", "", 0, 200, ""))
  }

  def getMigrationResults(jobId:String, timePeriod:Int): Future[MigrationResultsReturn] = {
    val result = getData("LifeCycle", jobId)

    val migrationResults = Bytes.toBytes("MigrationResults")
    val lifeCycleResultsSummary = Bytes.toBytes("LifeCycleSummary")
    val migrationResultsMap = result.getFamilyMap(migrationResults)

    val migrationResultsVector = for (c:Array[Byte] <- migrationResultsMap.keySet)
      yield Bytes.toString(result.getValue(migrationResults,c)).parseJson.convertTo[MigrationResultsTimePeriod]

    val fiteredResult = migrationResultsVector.filter(_.timePeriod==timePeriod)

    val migrationResultsData = MigrationResults(
      Bytes.toString(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("minDate"))),
      Bytes.toString(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("maxDate"))),
      Bytes.toLong(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("numRecords"))),
      Bytes.toInt(result.getValue(lifeCycleResultsSummary,Bytes.toBytes("timePeriodLength"))),
      Some(fiteredResult.toList.sortWith(_.timePeriod < _.timePeriod))
    )

    Future.successful(MigrationResultsReturn("",migrationResultsData,"","Success", "", 0, 200, ""))
  }
}
