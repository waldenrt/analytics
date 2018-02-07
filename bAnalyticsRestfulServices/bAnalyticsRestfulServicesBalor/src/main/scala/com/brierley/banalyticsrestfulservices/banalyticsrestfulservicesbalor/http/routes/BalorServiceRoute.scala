package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.mappings._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.services.BalorService
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import scala.concurrent.ExecutionContext

class BalorServiceRoute(val balorService: BalorService
                       )(implicit executionContext: ExecutionContext) extends BalorSupport {

  import StatusCodes._
  import balorService._

  val route = pathPrefix("balor") {
    ///This route retrieves the data to support the graphs for the Balor Summary page
    (path("balorsummary" / Segment) & get) { jobId =>
      complete(getBalorSummary(jobId).map(_.toJson))
    } ~
      (path("balorsegment" / Segment) & get) { jobId =>
        complete(getBalorSegment(jobId).map(_.toJson))
      } ~
      (path("balorcadence" / Segment) & get) { jobId =>
        complete(getBalorCadence(jobId).map(_.toJson))
      }

  }
}
