package com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.mappings

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.models._
import spray.json._

trait LifeCycleResultJsonSupport extends SprayJsonSupport with DefaultJsonProtocol{


  implicit val LifeCycleResultsBestFormat = jsonFormat13(LifeCycleResultsBest)
  implicit val LifeCycleResultsRisingFormat = jsonFormat13(LifeCycleResultsRising)
  implicit val LifeCycleResultsMiddleFormat = jsonFormat13(LifeCycleResultsMiddle)
  implicit val LifeCycleResultsLapsingFormat = jsonFormat13(LifeCycleResultsLapsing)
  implicit val LifeCycleResultsDeeplyFormat = jsonFormat13(LifeCycleResultsDeeply)
  implicit val LifeçycleResultsTimePeriodFormat = jsonFormat10(LifeCycleResultsTimePeriod)
  implicit val LifeCycleResultsFormat = jsonFormat5(LifeCycleResults)
  implicit val LifeCycleResultsReturnFormat = jsonFormat8(LifeCycleResultsReturn)

  implicit object LifeCycleResultsTimePeriodFormat extends RootJsonFormat[LifeCycleResultsTimePeriod] {
    def write(c: LifeCycleResultsTimePeriod) : JsObject = JsObject(Map(
      "timePeriod" -> c.timePeriod.toJson,
      "totalCustCount" -> c.totalCustCount.toJson,
      "totalTxnCount" -> c.totalTxnCount.toJson,
      "totalSales" -> c.totalSales.toJson,
      "totalItems" -> c.totalItems.toJson,
      "bestCustTotal" -> c.lifeCycleResultsBest.bestCustTotal.toJson,
      "bestTxnTotal"-> c.lifeCycleResultsBest.bestTxnTotal.toJson,
      "bestSalesTotal"-> c.lifeCycleResultsBest.bestSalesTotal.toJson,
      "bestRecencyTotal"-> c.lifeCycleResultsBest.bestRecencyTotal.toJson,
      "bestItemTotal"-> c.lifeCycleResultsBest.bestItemTotal.toJson,
      "bestPercentCustBase"-> c.lifeCycleResultsBest.bestPercentCustBase.toJson,
      "bestPercentTxnBase"-> c.lifeCycleResultsBest.bestPercentTxnBase.toJson,
      "bestPercentSalesBase"-> c.lifeCycleResultsBest.bestPercentSalesBase.toJson,
      "bestAvgFreq"-> c.lifeCycleResultsBest.bestAvgFreq.toJson,
      "bestAvgRecency"-> c.lifeCycleResultsBest.bestAvgRecency.toJson,
      "bestAvgSales"-> c.lifeCycleResultsBest.bestAvgSales.toJson,
      "bestAvgItems"-> c.lifeCycleResultsBest.bestAvgItems.toJson,
      "bestVisitSpend"-> c.lifeCycleResultsBest.bestVisitSpend.toJson,
      "risingCustTotal" -> c.lifeCycleResultsRising.risingCustTotal.toJson,
      "risingTxnTotal" -> c.lifeCycleResultsRising.risingTxnTotal.toJson,
      "risingSalesTotal" -> c.lifeCycleResultsRising.risingSalesTotal.toJson,
      "risingRecencyTotal" -> c.lifeCycleResultsRising.risingRecencyTotal.toJson,
      "risingItemTotal" -> c.lifeCycleResultsRising.risingItemTotal.toJson,
      "risingPercentCustBase" -> c.lifeCycleResultsRising.risingPercentCustBase.toJson,
      "risingPercentTxnBase" -> c.lifeCycleResultsRising.risingPercentTxnBase.toJson,
      "risingPercentSalesBase" -> c.lifeCycleResultsRising.risingPercentSalesBase.toJson,
      "risingAvgFreq" -> c.lifeCycleResultsRising.risingAvgFreq.toJson,
      "risingAvgRecency" -> c.lifeCycleResultsRising.risingAvgRecency.toJson,
      "risingAvgSales" -> c.lifeCycleResultsRising.risingAvgSales.toJson,
      "risingAvgItems" -> c.lifeCycleResultsRising.risingAvgItems.toJson,
      "risingVisitSpend" -> c.lifeCycleResultsRising.risingVisitSpend.toJson,
      "middleCustTotal" -> c.lifeCycleResultsMiddle.middleCustTotal.toJson,
      "middleTxnTotal" -> c.lifeCycleResultsMiddle.middleTxnTotal.toJson,
      "middleSalesTotal" -> c.lifeCycleResultsMiddle.middleSalesTotal.toJson,
      "middleRecencyTotal" -> c.lifeCycleResultsMiddle.middleRecencyTotal.toJson,
      "middleItemTotal" -> c.lifeCycleResultsMiddle.middleItemTotal.toJson,
      "middlePercentCustBase" -> c.lifeCycleResultsMiddle.middlePercentCustBase.toJson,
      "middlePercentTxnBase" -> c.lifeCycleResultsMiddle.middlePercentTxnBase.toJson,
      "middlePercentSalesBase" -> c.lifeCycleResultsMiddle.middlePercentSalesBase.toJson,
      "middleAvgFreq" -> c.lifeCycleResultsMiddle.middleAvgFreq.toJson,
      "middleAvgRecency" -> c.lifeCycleResultsMiddle.middleAvgRecency.toJson,
      "middleAvgSales" -> c.lifeCycleResultsMiddle.middleAvgSales.toJson,
      "middleAvgItems" -> c.lifeCycleResultsMiddle.middleAvgItems.toJson,
      "middleVisitSpend" -> c.lifeCycleResultsMiddle.middleVisitSpend.toJson,
      "lapsingCustTotal" -> c.lifeCycleResultsLapsing.lapsingCustTotal.toJson,
      "lapsingTxnTotal" -> c.lifeCycleResultsLapsing.lapsingTxnTotal.toJson,
      "lapsingSalesTotal" -> c.lifeCycleResultsLapsing.lapsingSalesTotal.toJson,
      "lapsingRecencyTotal" -> c.lifeCycleResultsLapsing.lapsingRecencyTotal.toJson,
      "lapsingItemTotal" -> c.lifeCycleResultsLapsing.lapsingItemTotal.toJson,
      "lapsingPercentCustBase" -> c.lifeCycleResultsLapsing.lapsingPercentCustBase.toJson,
      "lapsingPercentTxnBase" -> c.lifeCycleResultsLapsing.lapsingPercentTxnBase.toJson,
      "lapsingPercentSalesBase" -> c.lifeCycleResultsLapsing.lapsingPercentSalesBase.toJson,
      "lapsingAvgFreq" -> c.lifeCycleResultsLapsing.lapsingAvgFreq.toJson,
      "lapsingAvgRecency" -> c.lifeCycleResultsLapsing.lapsingAvgRecency.toJson,
      "lapsingAvgSales" -> c.lifeCycleResultsLapsing.lapsingAvgSales.toJson,
      "lapsingAvgItems" -> c.lifeCycleResultsLapsing.lapsingAvgItems.toJson,
      "lapsingVisitSpend" -> c.lifeCycleResultsLapsing.lapsingVisitSpend.toJson,
      "deeplyCustTotal" -> c.lifeCycleResultsDeeply.deeplyCustTotal.toJson,
      "deeplyTxnTotal" -> c.lifeCycleResultsDeeply.deeplyTxnTotal.toJson,
      "deeplySalesTotal" -> c.lifeCycleResultsDeeply.deeplySalesTotal.toJson,
      "deeplyRecencyTotal" -> c.lifeCycleResultsDeeply.deeplyRecencyTotal.toJson,
      "deeplyItemTotal" -> c.lifeCycleResultsDeeply.deeplyItemTotal.toJson,
      "deeplyPercentCustBase" -> c.lifeCycleResultsDeeply.deeplyPercentCustBase.toJson,
      "deeplyPercentTxnBase" -> c.lifeCycleResultsDeeply.deeplyPercentTxnBase.toJson,
      "deeplyPercentSalesBase" -> c.lifeCycleResultsDeeply.deeplyPercentSalesBase.toJson,
      "deeplyAvgFreq" -> c.lifeCycleResultsDeeply.deeplyAvgFreq.toJson,
      "deeplyAvgRecency" -> c.lifeCycleResultsDeeply.deeplyAvgRecency.toJson,
      "deeplyAvgSales" -> c.lifeCycleResultsDeeply.deeplyAvgSales.toJson,
      "deeplyAvgItems" -> c.lifeCycleResultsDeeply.deeplyAvgItems.toJson,
      "deeplyVisitSpend"  -> c.lifeCycleResultsDeeply.deeplyVisitSpend.toJson
    ))

    def read(json: JsValue): LifeCycleResultsTimePeriod = {
      val jsonObject = json.asJsObject()

      val seqFields = jsonObject.getFields("timePeriod", "totalCustCount", "totalTxnCount", "totalSales", "totalItems",
        "bestCustTotal", "bestTxnTotal", "bestSalesTotal", "bestRecencyTotal", "bestItemTotal", "bestPercentCustBase",
        "bestPercentTxnBase", "bestPercentSalesBase", "bestAvgFreq", "bestAvgRecency", "bestAvgSales", "bestAvgItems",
        "bestVisitSpend", "risingCustTotal", "risingTxnTotal", "risingSalesTotal", "risingRecencyTotal",
        "risingItemTotal", "risingPercentCustBase", "risingPercentTxnBase", "risingPercentSalesBase", "risingAvgFreq",
        "risingAvgRecency", "risingAvgSales", "risingAvgItems", "risingVisitSpend", "middleCustTotal", "middleTxnTotal",
        "middleSalesTotal", "middleRecencyTotal", "middleItemTotal", "middlePercentCustBase", "middlePercentTxnBase",
        "middlePercentSalesBase", "middleAvgFreq", "middleAvgRecency", "middleAvgSales", "middleAvgItems",
        "middleVisitSpend", "lapsingCustTotal", "lapsingTxnTotal", "lapsingSalesTotal", "lapsingRecencyTotal",
        "lapsingItemTotal", "lapsingPercentCustBase", "lapsingPercentTxnBase", "lapsingPercentSalesBase",
        "lapsingAvgFreq", "lapsingAvgRecency", "lapsingAvgSales", "lapsingAvgItems", "lapsingVisitSpend",
        "deeplyCustTotal", "deeplyTxnTotal", "deeplySalesTotal", "deeplyRecencyTotal", "deeplyItemTotal",
        "deeplyPercentCustBase", "deeplyPercentTxnBase", "deeplyPercentSalesBase", "deeplyAvgFreq", "deeplyAvgRecency",
        "deeplyAvgSales", "deeplyAvgItems", "deeplyVisitSpend")

      LifeCycleResultsTimePeriod(
        seqFields.head.convertTo[Int],
        seqFields(1).convertTo[Long],
        seqFields(2).convertTo[Long],
        seqFields(3).convertTo[Double],
        seqFields(4).convertTo[Long],
        LifeCycleResultsBest(
          seqFields(5).convertTo[Long],
          seqFields(6).convertTo[Long],
          seqFields(7).convertTo[Double],
          seqFields(8).convertTo[Long],
          seqFields(9).convertTo[Long],
          seqFields(10).convertTo[Double],
          seqFields(11).convertTo[Double],
          seqFields(12).convertTo[Double],
          seqFields(13).convertTo[Double],
          seqFields(14).convertTo[Double],
          seqFields(15).convertTo[Double],
          seqFields(16).convertTo[Double],
          seqFields(17).convertTo[Double]
        ),
        LifeCycleResultsRising(
          seqFields(18).convertTo[Long],
          seqFields(19).convertTo[Long],
          seqFields(20).convertTo[Double],
          seqFields(21).convertTo[Long],
          seqFields(22).convertTo[Long],
          seqFields(23).convertTo[Double],
          seqFields(24).convertTo[Double],
          seqFields(25).convertTo[Double],
          seqFields(26).convertTo[Double],
          seqFields(27).convertTo[Double],
          seqFields(28).convertTo[Double],
          seqFields(29).convertTo[Double],
          seqFields(30).convertTo[Double]
        ),
        LifeCycleResultsMiddle(
          seqFields(31).convertTo[Long],
          seqFields(32).convertTo[Long],
          seqFields(33).convertTo[Double],
          seqFields(34).convertTo[Long],
          seqFields(35).convertTo[Long],
          seqFields(36).convertTo[Double],
          seqFields(37).convertTo[Double],
          seqFields(38).convertTo[Double],
          seqFields(39).convertTo[Double],
          seqFields(40).convertTo[Double],
          seqFields(41).convertTo[Double],
          seqFields(42).convertTo[Double],
          seqFields(43).convertTo[Double]
        ),
        LifeCycleResultsLapsing(
          seqFields(44).convertTo[Long],
          seqFields(45).convertTo[Long],
          seqFields(46).convertTo[Double],
          seqFields(47).convertTo[Long],
          seqFields(48).convertTo[Long],
          seqFields(49).convertTo[Double],
          seqFields(50).convertTo[Double],
          seqFields(51).convertTo[Double],
          seqFields(52).convertTo[Double],
          seqFields(53).convertTo[Double],
          seqFields(54).convertTo[Double],
          seqFields(55).convertTo[Double],
          seqFields(56).convertTo[Double]
        ),
        LifeCycleResultsDeeply(
          seqFields(57).convertTo[Long],
          seqFields(58).convertTo[Long],
          seqFields(59).convertTo[Double],
          seqFields(60).convertTo[Long],
          seqFields(61).convertTo[Long],
          seqFields(62).convertTo[Double],
          seqFields(63).convertTo[Double],
          seqFields(64).convertTo[Double],
          seqFields(65).convertTo[Double],
          seqFields(66).convertTo[Double],
          seqFields(67).convertTo[Double],
          seqFields(68).convertTo[Double],
          seqFields(69).convertTo[Double]
        )
      )


    }
  }

}
