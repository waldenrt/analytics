package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import scala.concurrent.ExecutionContext
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.services._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.mappings._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.models._


class AuthServiceRoute(authService:AuthService)
                      (implicit executionContext: ExecutionContext) extends UserAuthJsonSupport {
  import StatusCodes._
  import authService._

  val route = pathPrefix("Auth") {
    (path("authenticate") & post & entity(as[UserAuth])) {userAuth =>
      complete(authenticate(userAuth).map(_.toJson))
    }
  }
}
