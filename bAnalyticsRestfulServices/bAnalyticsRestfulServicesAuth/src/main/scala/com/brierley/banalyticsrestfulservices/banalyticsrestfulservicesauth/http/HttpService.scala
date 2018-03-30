package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.http

import akka.http.scaladsl.server.Directives._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.utils.CorsSupport
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.http.routes.AuthServiceRoute
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.services.AuthService

import scala.concurrent.ExecutionContext

class HttpService(jobService:AuthService
                 )(implicit executionContext: ExecutionContext) extends CorsSupport {

  val authRouter = new AuthServiceRoute(jobService)

  val routes =
    pathPrefix("api" / "v1") {
      corseHandler {
        authRouter.route
      }
    }
}
