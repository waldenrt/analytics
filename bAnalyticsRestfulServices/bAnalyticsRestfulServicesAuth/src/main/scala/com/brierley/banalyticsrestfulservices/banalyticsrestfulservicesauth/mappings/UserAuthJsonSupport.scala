package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.mappings

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.models._
import spray.json._

trait UserAuthJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val UserAuthFormat = jsonFormat2(UserAuth)
  implicit val ClientInfoFormat = jsonFormat2(ClientInfo)
  implicit val TokenFormat = jsonFormat2(Token)
  implicit val UserFormat = jsonFormat7(User)
  implicit val UserSignUpFormat = jsonFormat7(UserSignUp)
  implicit val UserAuthReturnFormat = jsonFormat8(UserAuthReturn)
  implicit val UserSignUpReturnFormat = jsonFormat8(UserSignUpReturn)
}
