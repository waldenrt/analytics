package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.http.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.services.JobService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.mappings._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.models._
import spray.json._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import scala.concurrent.ExecutionContext

class JobServiceRoute (jobService:JobService)
(implicit executionContext: ExecutionContext) extends JobJsonSupport {

  import StatusCodes._
  import jobService._

  val route = pathPrefix("jobs") {
    (path("jobhistory" / "client" / Segment) & get) { clientID =>
      complete(getJobHistoryByClient(clientID).map(_.toJson))
    } ~
    (path("jobhistory" / "client" / Segment / "user" / Segment) & get) { (clientID, user) =>
      complete(getJobHistoryByClientUser(clientID, user).map(_.toJson))
    } ~
      (path("jobhistory" / "client" / Segment / "user" / Segment / "app" / Segment) & get) { (clientID, user, app) =>
        complete(getJobHistoryByClientUserApp(clientID, user, app).map(_.toJson))
      } ~
      (path("jobhistory" / "user" / Segment) & get) { user =>
        complete(getJobHistoryByUser(user).map(_.toJson))
      } ~
      (path("jobhistory" / "app" / Segment) & get) { app =>
        complete(getJobHistoryByApp(app).map(_.toJson))
      } ~
      (path("jobhistory" / "user" / Segment / "app" / Segment) & get) { (user, app) =>
        complete(getJobHistoryByUserApp(user, app).map(_.toJson))
      } ~
      (path("jobhistory" / "client" / Segment / "app" / Segment) & get) { (client, app) =>
        complete(getJobHistoryByClientApp(client, app).map(_.toJson))
      } ~
      (path("jobhistory") & post & entity(as[JobHistory])) { jobHistory =>
        complete(addJobHistory(jobHistory).map(_.toJson))
      } ~
      (path("job") & post & entity(as[Job])) { job =>
        complete(submitJob(job).map(_.toJson))
      }
  }

}
