package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.mappings

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.models._

trait QuantileMigrationResultJsonSupport extends SprayJsonSupport with DefaultJsonProtocol
        with QuantileDimensionResultJsonSupport
        with NullOptions
{

  implicit val quantileMigrationDataFormat = jsonFormat3(QuantileMigrationData)
  implicit val quantileTotalsFormat = jsonFormat2(QuantileTotals)
  implicit val quantileMigrationResultsFormat = jsonFormat3(QuantileMigrationResults)
  implicit val quantileMigrationViewFormat = jsonFormat2(QuantileMigrationView)
  implicit val quantileMigrationReturnFormat = jsonFormat8(QuantileMigrationReturn)

}
