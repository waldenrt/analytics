package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.mappings

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.models._
import spray.json._

trait UserAuthJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val UserAuthFormat = jsonFormat2(UserAuth)
  implicit val UserSignUpFormat = jsonFormat6(UserSignUp)
  implicit val ClientInfoFormat = jsonFormat2(ClientInfo)
  implicit val UserFormat = jsonFormat5(User)
  implicit val UserAuthReturnFormat = jsonFormat8(UserAuthReturn)
}
