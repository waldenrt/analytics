package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.http

import akka.http.scaladsl.server.Directives._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.utils.CorsSupport
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.http.routes.JobServiceRoute
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.services.JobService

import scala.concurrent.ExecutionContext

class HttpService(jobService:JobService
                 )(implicit executionContext: ExecutionContext) extends CorsSupport {

  val jobRouter = new JobServiceRoute(jobService)

  val routes =
    pathPrefix("api"/"v1") {
      corseHandler {
        jobRouter.route
      }
    }
}
