package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.mappings

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.models._
import spray.json._

trait BalorSupport extends SprayJsonSupport with DefaultJsonProtocol with NullOptions {
  implicit val retentionTimePeriodFormat = jsonFormat15(RetentionTimePeriodFull)

  //Balor Summary JSON formats
  implicit val timePeriodFormat = jsonFormat18(TimePeriod)
  implicit val balorSummaryFormat = jsonFormat7(BalorSummary)
  implicit val balorSummaryReturnFormat = jsonFormat8(BalorSummaryReturn)

  //Balor Segment JSON formats
  implicit val newCustTimePeriodFormat = jsonFormat12(NewCustTimePeriodFull)
  implicit val reactCustTimePeriodFormat = jsonFormat12(ReactCustTimePeriodFull)
  implicit val returnCustTimePeriodFormat = jsonFormat12(ReturnCustTimePeriodFull)
  implicit val lapsedCustTimePeriodFormat = jsonFormat12(LapsedCustTimePeriodFull)

  implicit val timePeriodFullFormat = jsonFormat10(TimePeriodFull)
  implicit val balorSegmentFormat = jsonFormat7(BalorSegment)
  implicit val balorSegmentReturnFormat = jsonFormat8(BalorSegmentReturn)

  //Balor Cadence JSON formats
  implicit val balorCadenceFreqTableFormat = jsonFormat3(FreqTable)
  implicit val balorCadenceFormat = jsonFormat9(BalorCadence)
  implicit val balorCadenceReturnFormat = jsonFormat8(BalorCadenceReturn)

  implicit object TimePeriodFormat extends RootJsonFormat[TimePeriod] {
    def write(t: TimePeriod): JsObject = JsObject(
      "timePeriod" -> t.timePeriod.toJson,
      "anchorDate" -> t.anchorDate.toJson,
      "newCustCount" -> t.newCustCount.toJson,
      "newTxnCount" -> t.newTxnCount.toJson,
      "newTxnAmt" -> t.newTxnAmt.toJson,
      "reactCustCount" -> t.reactCustCount.toJson,
      "reactTxnCount" -> t.reactTxnCount.toJson,
      "reactTxnAmt" -> t.reactTxnAmt.toJson,
      "returnCustCount" -> t.returnCustCount.toJson,
      "returnTxnCount" -> t.returnTxnCount.toJson,
      "returnTxnAmt" -> t.returnTxnAmt.toJson,
      "lapsedCustCount" -> t.lapsedCustCount.toJson,
      "lapsedTxnCount" -> t.lapsedTxnCount.toJson,
      "lapsedTxnAmt" -> t.lapsedTxnAmt.toJson,
      "custBalor" -> t.custBalor.toJson,
      "txnBalor" -> t.txnBalor.toJson,
      "spendBalor" -> t.spendBalor.toJson,
      "retention" -> t.retention.toJson
    )

    def read(json: JsValue) = json match {

      case JsObject(fields) =>
        TimePeriod(
          fields.get("timePeriod").get.convertTo[Int],
          fields.get("anchorDate").map(_.toString()),
          fields.get("newCustCount").get.convertTo[Long],
          fields.get("newTxnCount").get.convertTo[Long],
          fields.get("newTxnAmt").get.convertTo[Double],
          fields.get("reactCustCount").get.convertTo[Long],
          fields.get("reactTxnCount").get.convertTo[Long],
          fields.get("reactTxnAmt").get.convertTo[Double],
          fields.get("returnCustCount").get.convertTo[Long],
          fields.get("returnTxnCount").get.convertTo[Long],
          fields.get("returnTxnAmt").get.convertTo[Double],
          fields.get("lapsedCustCount").get.convertTo[Long],
          fields.get("lapsedTxnCount").get.convertTo[Long],
          fields.get("lapsedTxnAmt").get.convertTo[Double],
          fields.get("custBalor").get.convertTo[Double],
          fields.get("txnBalor").get.convertTo[Double],
          fields.get("spendBalor").get.convertTo[Double],
          RetentionTimePeriodFull(
            fields.get("retention").get.convertTo[Double],
            fields.get("retentionGrowth").map(_.toString().toDouble),
            fields.get("returnNewSales").map(_.toString().toDouble),
            fields.get("returnNewTxn").map(_.toString().toDouble),
            fields.get("returnNewCust").map(_.toString().toDouble),
            fields.get("returnReactSales").map(_.toString().toDouble),
            fields.get("returnReactTxn").map(_.toString().toDouble),
            fields.get("returnReactCust").map(_.toString().toDouble),
            fields.get("returnReturnSales").map(_.toString().toDouble),
            fields.get("returnReturnTxn").map(_.toString().toDouble),
            fields.get("returnReturnCust").map(_.toString().toDouble),
            fields.get("ttlSalesLift").map(_.toString().toDouble),
            fields.get("avgSalesLift").map(_.toString().toDouble),
            fields.get("ttlTxnLift").map(_.toString().toDouble),
            fields.get("avgTxnLift").map(_.toString().toDouble)
          )
        )
    }
  }

  implicit object TimePeriodFullFormat extends RootJsonFormat[TimePeriodFull] {

    def write(t: TimePeriodFull): JsObject = JsObject(
      "timePeriod" -> t.timePeriod.toJson,
      "anchorDate" -> t.anchorDate.toJson,
      "custBalor" -> t.custBalor.toJson,
      "txnBalor" -> t.txnBalor.toJson,
      "spendBalor" -> t.spendBalor.toJson,
      "retention" -> t.retention.toJson,
      "newCustInfo" -> t.newCustInfo.toJson,
      "reactCustInfo" ->t.reactCustInfo.toJson,
      "returnCustInfo" ->t.returnCustInfo.toJson,
      "lapsedCustInfo" ->t.lapsedCustInfo.toJson
    )

    def read(json: JsValue) = json match {

      case JsObject(fields) =>

       TimePeriodFull(
        fields.get("timePeriod").get.convertTo[Int],
        fields.get("anchorDate").map(_.toString()),
        NewCustTimePeriodFull(
          fields.get("newCustCount").get.convertTo[Long],
          fields.get("newTxnCount").get.convertTo[Long],
          fields.get("newTxnAmt").get.convertTo[Double],
          fields.get("newDiscAmt").get.convertTo[Long],
          fields.get("newItemQty").get.convertTo[Double],
          fields.get("newCustSpendAvg").get.convertTo[Double],
          fields.get("newCustVisitAvg").get.convertTo[Double],
          fields.get("newCustItemAvg").get.convertTo[Double],
          fields.get("newCustDiscAvg").get.convertTo[Double],
          fields.get("newVisitSpendAvg").get.convertTo[Double],
          fields.get("newVisitDiscAvg").get.convertTo[Double],
          fields.get("newVisitItemAvg").get.convertTo[Double]),
        ReactCustTimePeriodFull(
          fields.get("reactCustCount").get.convertTo[Long],
          fields.get("reactTxnCount").get.convertTo[Long],
          fields.get("reactTxnAmt").get.convertTo[Double],
          fields.get("reactDiscAmt").get.convertTo[Long],
          fields.get("reactItemQty").get.convertTo[Double],
          fields.get("reactCustSpendAvg").get.convertTo[Double],
          fields.get("reactCustVisitAvg").get.convertTo[Double],
          fields.get("reactCustItemAvg").get.convertTo[Double],
          fields.get("reactCustDiscAvg").get.convertTo[Double],
          fields.get("reactVisitSpendAvg").get.convertTo[Double],
          fields.get("reactVisitDiscAvg").get.convertTo[Double],
          fields.get("reactVisitItemAvg").get.convertTo[Double]),
        ReturnCustTimePeriodFull(
          fields.get("returnCustCount").get.convertTo[Long],
          fields.get("returnTxnCount").get.convertTo[Long],
          fields.get("returnTxnAmt").get.convertTo[Double],
          fields.get("returnDiscAmt").get.convertTo[Long],
          fields.get("returnItemQty").get.convertTo[Double],
          fields.get("returnCustSpendAvg").get.convertTo[Double],
          fields.get("returnCustVisitAvg").get.convertTo[Double],
          fields.get("returnCustItemAvg").get.convertTo[Double],
          fields.get("returnCustDiscAvg").get.convertTo[Double],
          fields.get("returnVisitSpendAvg").get.convertTo[Double],
          fields.get("returnVisitDiscAvg").get.convertTo[Double],
          fields.get("returnVisitItemAvg").get.convertTo[Double]),
        LapsedCustTimePeriodFull(
          fields.get("lapsedCustCount").get.convertTo[Long],
          fields.get("lapsedTxnCount").get.convertTo[Long],
          fields.get("lapsedTxnAmt").get.convertTo[Double],
          fields.get("lapsedDiscAmt").get.convertTo[Long],
          fields.get("lapsedItemQty").get.convertTo[Double],
          fields.get("lapsedCustSpendAvg").get.convertTo[Double],
          fields.get("lapsedCustVisitAvg").get.convertTo[Double],
          fields.get("lapsedCustItemAvg").get.convertTo[Double],
          fields.get("lapsedCustDiscAvg").get.convertTo[Double],
          fields.get("lapsedVisitSpendAvg").get.convertTo[Double],
          fields.get("lapsedVisitDiscAvg").get.convertTo[Double],
          fields.get("lapsedVisitItemAvg").get.convertTo[Double]),
          fields.get("custBalor").get.convertTo[Double],
          fields.get("txnBalor").get.convertTo[Double],
          fields.get("spendBalor").get.convertTo[Double],
          fields.get("retention").get.convertTo[Double]
      )
    }
  }
}