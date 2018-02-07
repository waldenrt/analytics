package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.IntNumber
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import scala.concurrent.ExecutionContext
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.services.QuantileService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.mappings._

class QuantileServiceRoute(val quantileService:QuantileService)
                          (implicit executionContext: ExecutionContext) extends
  QuantileDimensionResultJsonSupport with
  QuantileMigrationResultJsonSupport with
  QuantileProductResultJsonSupport with
  QuantileSummaryResultJsonSupport{

  import StatusCodes._
  import quantileService._

  val route = pathPrefix("Quantile") {
      (path("quantilesummary"/Segment) & get) {jobId =>
        complete(getQuantileSummary(jobId).map(_.toJson))
    } ~
      (path("quantilemigration"/Segment) & get) { jobId =>
        complete(getQuantileMigrations(jobId).map(_.toJson))
      } ~
      (path("quantileproduct"/Segment) & get) {jobId =>
        complete(getQuantileProducts(jobId).map(_.toJson))
      }
  }
}
