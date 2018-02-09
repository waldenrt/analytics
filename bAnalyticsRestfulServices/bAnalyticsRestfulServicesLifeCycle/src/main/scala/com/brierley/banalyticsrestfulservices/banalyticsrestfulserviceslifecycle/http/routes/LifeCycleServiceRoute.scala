package com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.PathMatchers.IntNumber
import com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.services.LifecycleService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.mappings._
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import scala.concurrent.ExecutionContext


class LifeCycleServiceRoute(val lifeCycleUserService:LifecycleService)
                           (implicit executionContext: ExecutionContext) extends LifeCycleResultJsonSupport
                            with LifeCycleProdResultJsonSupport with MigrationResultsJsonSupport {

  import StatusCodes._
  import lifeCycleUserService._

  val route = pathPrefix("lifecycle") {
    (path("lifecycleresults" / Segment) & get) { jobId =>
      complete(getLifeCycleResults(jobId).map(_.toJson))
    } ~
      (path("lifecycleresults" / Segment / IntNumber) & get) { (jobId, timePeriod) =>
        complete(getLifeCycleResults(jobId, timePeriod).map(_.toJson))
      } ~
      (path("lifecycleprodresults" / Segment) & get) { jobId =>
        complete(getLifeCycleProdResults(jobId).map(_.toJson))
      } ~
      (path("lifecycleprodresults"/ Segment / IntNumber) & get) {(jobId, timePeriod) =>
        complete(getLifeCycleProdResults(jobId, timePeriod).map(_.toJson))
      }~
      (path("migrationresults" / Segment)) { jobId =>
        complete(getMigrationResults(jobId).map(_.toJson))
      }~
      (path("migrationresults" / Segment / IntNumber) & get) {(jobId, timePeriod) =>
        complete(getMigrationResults(jobId, timePeriod).map(_.toJson))
      }
  }
}
