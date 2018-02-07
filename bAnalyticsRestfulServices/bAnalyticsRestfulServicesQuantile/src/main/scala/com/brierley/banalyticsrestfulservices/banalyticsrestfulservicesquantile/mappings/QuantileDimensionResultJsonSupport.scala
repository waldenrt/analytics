package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.mappings

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.models._

trait QuantileDimensionResultJsonSupport extends SprayJsonSupport with DefaultJsonProtocol with NullOptions {

  implicit val quantileDimensionFormat = jsonFormat4(QuantileDimension)

}
