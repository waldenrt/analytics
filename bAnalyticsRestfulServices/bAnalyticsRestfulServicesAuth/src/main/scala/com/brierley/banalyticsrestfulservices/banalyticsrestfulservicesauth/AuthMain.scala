package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContext

import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.http.HttpService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.services.AuthService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.utils.{Config,DatabaseService}

object AuthMain extends App with Config {

  implicit val actorSystem = ActorSystem()
  implicit val executor: ExecutionContext = actorSystem.dispatcher
  implicit val log: LoggingAdapter = Logging(actorSystem, getClass)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val databaseService = new DatabaseService()
  val jobService = new AuthService(databaseService)
  val httpService = new HttpService(jobService)

  Http().bindAndHandle(httpService.routes, httpHost, httpPort)

}
