package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.mappings

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.models._

trait QuantileSummaryResultJsonSupport extends SprayJsonSupport with DefaultJsonProtocol
  with NullOptions
  with QuantileDimensionResultJsonSupport{

  implicit val quantileSummaryTotalsFormat = jsonFormat6(QuantileSummaryTotals)
  implicit val quantileSummaryAveragesFormat = jsonFormat14(QuantileSummaryAverages)
  implicit val quantileResultsFormat = jsonFormat5(QuantileResults)
  implicit val quantileQuantileViewFormat = jsonFormat2(QuantileSummaryView)
  implicit val quantileSummaryReturnFormat = jsonFormat8(QuantileSummaryReturn)

  implicit object QuantileResultsFormat extends RootJsonFormat[QuantileResults] {

    def write(t: QuantileResults): JsObject = JsObject(
      "timePeriod" -> t.timePeriod.toJson,
      "anchorDate" -> t.anchorDate.toJson,
      "quantile" -> t.quantile.toJson,
      "summaryTotals" -> t.summaryTotals.toJson,
      "summaryAverages" -> t.summaryAverages.toJson
    )

    def read(json: JsValue) = json match {

      case JsObject(fields) => QuantileResults(
        fields.get("timePeriod").get.convertTo[Int],
        fields.get("anchorDate").get.toString,
        fields.get("quantile").get.convertTo[Int],
        QuantileSummaryTotals(
          fields.get("totalCount").get.convertTo[Long],
          fields.get("totalVisits").get.convertTo[Long],
          fields.get("totalSpend").get.convertTo[Double],
          fields.get("totalUnits").get.convertTo[Long],
          fields.get("totalDisc").get.convertTo[Double],
          if(fields.get("totalCust").get.toString() == "null") Some(0) else fields.get("totalCust").map(_.toString().toLong)
        ),
        QuantileSummaryAverages(
          fields.get("avgVisits").get.convertTo[Double],
          fields.get("avgSpend").get.convertTo[Double],
          fields.get("avgUnits").get.convertTo[Double],
          fields.get("avgDisc").get.convertTo[Double],
          fields.get("avgVisitSpend").get.convertTo[Double],
          fields.get("avgVisitUnits").get.convertTo[Double],
          fields.get("avgVisitDisc").get.convertTo[Double],
          if(fields.get("avgRecency").get.toString() == "null") Some(0.0) else fields.get("avgRecency").map(_.toString().toDouble),
          fields.get("avgItemSales").get.convertTo[Double],
          fields.get("avgItemDisc").get.convertTo[Double],
          if(fields.get("avgStoreCust").get.toString() == "null") Some(0.0) else fields.get("avgStoreCust").map(_.toString().toDouble),
          if(fields.get("avgCustSales").get.toString() == "null") Some(0.0) else fields.get("avgCustSales").map(_.toString().toDouble),
          if(fields.get("avgCustVisits").get.toString() == "null") Some(0.0) else fields.get("avgCustVisits").map(_.toString().toDouble),
          if(fields.get("avgCustUnits").get.toString() == "null") Some(0.0) else fields.get("avgCustUnits").map(_.toString().toDouble)
        )
      )
    }
  }

}
