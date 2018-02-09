package com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.http.HttpService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.services.LifecycleService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.utils.{Config,DatabaseService}
import scala.concurrent.ExecutionContext

object LifeCycleMain extends App with Config {

  implicit val actorSystem = ActorSystem()
  implicit val executor: ExecutionContext = actorSystem.dispatcher
  implicit val log: LoggingAdapter = Logging(actorSystem, getClass)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val databaseService = new DatabaseService()
  val lifeCycleService = new LifecycleService(databaseService)
  val httpService = new HttpService(lifeCycleService)

  Http().bindAndHandle(httpService.routes, httpHost, httpPort)
}
