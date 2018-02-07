package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.http

import akka.http.scaladsl.server.Directives._

import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.services.BalorService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.utils.CorsSupport
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.http.routes.BalorServiceRoute

import scala.concurrent.ExecutionContext

class HttpService(balorService: BalorService
                 )(implicit executionContext: ExecutionContext) extends CorsSupport {

  val balorRouter = new BalorServiceRoute(balorService)

  val routes  =
    pathPrefix("api" / "v1") {
          corsHandler {
            balorRouter.route
        }
    }
}
