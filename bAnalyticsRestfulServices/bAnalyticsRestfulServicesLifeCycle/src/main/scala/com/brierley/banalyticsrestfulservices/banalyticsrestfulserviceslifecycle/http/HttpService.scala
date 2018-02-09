package com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.http

import akka.http.scaladsl.server.Directives._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.utils.CorsSupport
import com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.http.routes.LifeCycleServiceRoute
import com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.services.LifecycleService

import scala.concurrent.ExecutionContext

class HttpService(lifeCycleService:LifecycleService
                 )(implicit executionContext: ExecutionContext) extends CorsSupport {

  val lifeCycleRouter = new LifeCycleServiceRoute(lifeCycleService)

  val routes =
    pathPrefix("api"/"v1") {
      corseHandler {
        lifeCycleRouter.route
      }
    }
}
