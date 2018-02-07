package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.services

import scala.concurrent.{ExecutionContext, Future}
import org.apache.hadoop.hbase.util.Bytes
import spray.json._
import collection.JavaConversions._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.utils.DatabaseService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.models._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.mappings._

class QuantileService(val databaseService: DatabaseService
                     )(implicit executionContext: ExecutionContext) extends QuantileSummaryResultJsonSupport
                      with QuantileProductResultJsonSupport with QuantileMigrationResultJsonSupport
                      with QuantileDimensionResultJsonSupport{

  import databaseService._

  def getQuantileSummary(jobId:String): Future[QuantileSummaryReturn] = {
    val results = getData("Quantile", jobId)

    val quantileDimensionResults = Bytes.toBytes("QuantileDimensions")
    val quantileSummaryResults = Bytes.toBytes("QuantileResults")
    val quantileSummaryResultsMap = results.getFamilyMap(quantileSummaryResults)

    val quantileSummaryResultsVector = for (c:Array[Byte] <- quantileSummaryResultsMap.keySet)
      yield Bytes.toString(results.getValue(quantileSummaryResults,c)).parseJson.convertTo[QuantileResults]

    val quantileSummaryViewResult = QuantileSummaryView(
      QuantileDimension(
        Bytes.toLong(results.getValue(quantileDimensionResults, Bytes.toBytes("numRecords"))),
        Bytes.toString(results.getValue(quantileDimensionResults, Bytes.toBytes("minDate"))),
        Bytes.toString(results.getValue(quantileDimensionResults,Bytes.toBytes("maxDate"))),
        Bytes.toString(results.getValue(quantileDimensionResults,Bytes.toBytes("dimension")))
      ),
      quantileSummaryResultsVector.toList.sortWith(_.timePeriod < _.timePeriod)
    )

    Future.successful[QuantileSummaryReturn](QuantileSummaryReturn("false",quantileSummaryViewResult,"","Successful","",0,200,""))
  }

  def getQuantileMigrations(jobId:String): Future[QuantileMigrationReturn] = {
    val results = getData("Quantile", jobId)

    val quantileDimensionResults = Bytes.toBytes("QuantileDimensions")
    val quantileMigrationResults = Bytes.toBytes("MigrationResults")
    val quantileMigrationResultsMap = results.getFamilyMap(quantileMigrationResults)

    val quantileMigrationResultsVector = for (c:Array[Byte] <- quantileMigrationResultsMap.keySet)
      yield Bytes.toString(results.getValue(quantileMigrationResults,c)).parseJson.convertTo[QuantileMigrationResults]

    val quantileMigrationViewResult = QuantileMigrationView(
      QuantileDimension(
        Bytes.toLong(results.getValue(quantileDimensionResults, Bytes.toBytes("numRecords"))),
        Bytes.toString(results.getValue(quantileDimensionResults, Bytes.toBytes("minDate"))),
        Bytes.toString(results.getValue(quantileDimensionResults,Bytes.toBytes("maxDate"))),
        Bytes.toString(results.getValue(quantileDimensionResults,Bytes.toBytes("dimension")))
      ),
      quantileMigrationResultsVector.toList.sortWith(_.timePeriod < _.timePeriod)
    )

    Future.successful[QuantileMigrationReturn](QuantileMigrationReturn("",quantileMigrationViewResult,"","Successful", "", 0, 200,""))
  }

  def getQuantileProducts(jobId:String): Future[QuantileProductsReturn] = {
    val results = getData("Quantile", jobId)

    val quantileDimensionResults = Bytes.toBytes("QuantileDimensions")
    val quantileProductResults = Bytes.toBytes("ProductResults")
    val quantileProductResultsMap = results.getFamilyMap(quantileProductResults)

    val quantileProductResultsMapVector = for (c:Array[Byte] <- quantileProductResultsMap.keySet)
      yield Bytes.toString(results.getValue(quantileProductResults,c)).parseJson.convertTo[QuantileProducts]

    val quantileProductViewReturn = QuantileProductView(
      QuantileDimension(
        Bytes.toLong(results.getValue(quantileDimensionResults, Bytes.toBytes("numRecords"))),
        Bytes.toString(results.getValue(quantileDimensionResults, Bytes.toBytes("minDate"))),
        Bytes.toString(results.getValue(quantileDimensionResults,Bytes.toBytes("maxDate"))),
        Bytes.toString(results.getValue(quantileDimensionResults,Bytes.toBytes("dimension")))
      ),
      quantileProductResultsMapVector.toList.sortWith(_.timePeriod < _.timePeriod)
    )

    Future.successful[QuantileProductsReturn](QuantileProductsReturn("",quantileProductViewReturn,"","Successful", "", 0, 200,""))

  }
}
