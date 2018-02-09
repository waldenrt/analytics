package com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.mappings

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.models._
import spray.json._

trait MigrationResultsJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val segmentTotalsFormat = jsonFormat10(SegmentTotals)
  implicit val migrationDataFormat = jsonFormat3(MigrationData)
  //implicit val migrationResultsTimePeriodFormat = jsonFormat3(MigrationResultsTimePeriod)

  implicit object migrationResultsTimePeriodFormat extends RootJsonFormat[MigrationResultsTimePeriod] {
    def read(json: JsValue) : MigrationResultsTimePeriod = {
      val jsonObject = json.asJsObject()

      val seqFields = jsonObject.getFields("timePeriod", "migrationData", "segmentTotals")

      MigrationResultsTimePeriod(
        seqFields.head.convertTo[Int],
        seqFields(1).convertTo[List[MigrationData]],
        seqFields(2).convertTo[List[SegmentTotals]]
      )
    }
    def write(c:MigrationResultsTimePeriod) : JsObject = JsObject(Map(
      "timePeriod"->c.timePeriod.toJson,
      "migrationData"->c.migrationData.toJson,
      "segmentTotal"->c.segmentTotals.toJson
    ))
  }

  implicit val migrationResultsFormat = jsonFormat5(MigrationResults)
  implicit val migrationResultsReturnFormat = jsonFormat8(MigrationResultsReturn)
}
