package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.http

import akka.http.scaladsl.server.Directives._
import scala.concurrent.ExecutionContext
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.utils.CorsSupport
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.services.QuantileService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.http.routes.QuantileServiceRoute

class HttpService(quantileService:QuantileService
                 )(implicit executionContext: ExecutionContext) extends CorsSupport{

  val quantileRouter = new QuantileServiceRoute(quantileService)

  val routes =
    pathPrefix("api"/"v1") {
      corseHandler {
        quantileRouter.route
      }
    }
}
