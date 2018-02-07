package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.mappings

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.models._
import spray.json._

trait JobJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val jobParameterFormat = jsonFormat2(JobParameter)
  implicit val JobFormat = jsonFormat9(Job)
  implicit val JobHistoryFormat = jsonFormat14(JobHistory)
  implicit val JobHistoryReturnFormat = jsonFormat8(JobHistoryReturn)
  implicit val JobIdFormat = jsonFormat1(JobId)
  implicit val JobReturnFormat = jsonFormat8(JobReturn)
  implicit val JobSubmitFormat = jsonFormat7(JobSubmit)
  implicit val livyBatchInfoFormat = jsonFormat3(livyBatchInfo)


}
