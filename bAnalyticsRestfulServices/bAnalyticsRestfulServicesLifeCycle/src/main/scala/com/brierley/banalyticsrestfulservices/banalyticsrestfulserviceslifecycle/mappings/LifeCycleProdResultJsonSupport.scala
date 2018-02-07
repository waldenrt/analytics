package com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.mappings

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.models._
import spray.json._

trait LifeCycleProdResultJsonSupport  extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val lifeCycleProdMetricsFormat = jsonFormat20(LifeCycleProdMetrics)
  implicit val lifeCycleProdResultsTimePeriodFormat = jsonFormat6(LifeCycleProdResultsTimePeriod)
  implicit val lifeCycleProdResultsFormat = jsonFormat5(LifeCycleProdResults)
  implicit val lifeCycleProdResultsReturnFormat = jsonFormat8(LifeCycleProdResultsReturn)

  implicit object LifeCycleProdResultsTimePeriodFormat extends RootJsonFormat[LifeCycleProdResultsTimePeriod] {
    def write(c:LifeCycleProdResultsTimePeriod)  : JsObject = JsObject(Map(
      "timePeriod"->c.timePeriod.toJson,
      "productCat"->c.productCat.toJson,
      "prodTotalSales"->c.prodTotalSales.toJson,
      "prodPercentSales"->c.prodPercentSales.toJson,
      "totalSales"->c.totalSales.toJson,
      "bestProdSales"->c.lifeCycleProdMetrics.bestProdSales.toJson,
      "bestTotalSales"->c.lifeCycleProdMetrics.bestTotalSales.toJson,
      "bestPercentSales"->c.lifeCycleProdMetrics.bestPercentSales.toJson,
      "bestIndex"->c.lifeCycleProdMetrics.bestIndex.toJson,
      "risingProdSales"->c.lifeCycleProdMetrics.risingProdSales.toJson,
      "risingTotalSales"->c.lifeCycleProdMetrics.risingTotalSales.toJson,
      "risingPercentSales"->c.lifeCycleProdMetrics.risingPercentSales.toJson,
      "risingIndex"->c.lifeCycleProdMetrics.risingIndex.toJson,
      "middleProdSales"->c.lifeCycleProdMetrics.middleProdSales.toJson,
      "middleTotalSales"->c.lifeCycleProdMetrics.middleTotalSales.toJson,
      "middlePercentSales"->c.lifeCycleProdMetrics.middlePercentSales.toJson,
      "middleIndex"->c.lifeCycleProdMetrics.middleIndex.toJson,
      "lapsingProdSales"->c.lifeCycleProdMetrics.lapsingProdSales.toJson,
      "lapsingTotalSales"->c.lifeCycleProdMetrics.lapsingTotalSales.toJson,
      "lapsingPercentSales"->c.lifeCycleProdMetrics.lapsingPercentSales.toJson,
      "lapsingIndex"->c.lifeCycleProdMetrics.lapsingIndex.toJson.toJson,
      "deeplyProdSales"->c.lifeCycleProdMetrics.deeplyProdSales.toJson,
      "deeplyTotalSales"->c.lifeCycleProdMetrics.deeplyTotalSales.toJson,
      "deeplyPercentSales"->c.lifeCycleProdMetrics.deeplyPercentSales.toJson,
      "deeplyIndex"->c.lifeCycleProdMetrics.deeplyIndex .toJson
    ))

    def read(json:JsValue): LifeCycleProdResultsTimePeriod = {
      val jsObject = json.asJsObject()

      val seqFields = jsObject.getFields("timePeriod", "productCat", "prodTotalSales", "prodPercentSales",
        "totalSales", "bestProdSales", "bestTotalSales", "bestPercentSales", "bestIndex",
        "risingProdSales", "risingTotalSales","risingPercentSales", "risingIndex", "middleProdSales",
        "middleTotalSales", "middlePercentSales", "middleIndex", "lapsingProdSales", "lapsingTotalSales",
        "lapsingPercentSales","lapsingIndex", "deeplyProdSales", "deeplyTotalSales","deeplyPercentSales",
        "deeplyIndex")

      LifeCycleProdResultsTimePeriod(
        seqFields.head.convertTo[Int],
        seqFields(1).toString(),
        seqFields(2).convertTo[Double],
        seqFields(3).convertTo[Double],
        seqFields(4).convertTo[Double],
        LifeCycleProdMetrics(
          seqFields(5).convertTo[Double],
          seqFields(6).convertTo[Double],
          seqFields(7).convertTo[Double],
          seqFields(8).convertTo[Double],
          seqFields(9).convertTo[Double],
          seqFields(10).convertTo[Double],
          seqFields(11).convertTo[Double],
          seqFields(12).convertTo[Double],
          seqFields(13).convertTo[Double],
          seqFields(14).convertTo[Double],
          seqFields(15).convertTo[Double],
          seqFields(16).convertTo[Double],
          seqFields(17).convertTo[Double],
          seqFields(18).convertTo[Double],
          seqFields(19).convertTo[Double],
          seqFields(20).convertTo[Double],
          seqFields(21).convertTo[Double],
          seqFields(22).convertTo[Double],
          seqFields(23).convertTo[Double],
          seqFields(24).convertTo[Double]
        )
      )
    }
  }
}
