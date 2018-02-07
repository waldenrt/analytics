package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.mappings

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.models._

trait QuantileProductResultJsonSupport extends SprayJsonSupport with DefaultJsonProtocol
        with QuantileDimensionResultJsonSupport
        with NullOptions{

  implicit val productSpendFormat = jsonFormat3(QuantileProductSpend)
  implicit val productCountFormat = jsonFormat3(QuantileProductCount)
  implicit val quantileProductsFormat = jsonFormat6(QuantileProducts)
  implicit val quantileProductViewFormat = jsonFormat2(QuantileProductView)
  implicit val quantileProductsReturnFormat = jsonFormat8(QuantileProductsReturn)

}
