package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContext

import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.http.HttpService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.services.BalorService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.utils.{DatabaseService,Config}

object BalorMain extends App with Config{

  implicit val actorSystem = ActorSystem()
  implicit val executor: ExecutionContext = actorSystem.dispatcher
  implicit val log: LoggingAdapter = Logging(actorSystem, getClass)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val databaseService = new DatabaseService()
  val balorService = new BalorService(databaseService)
  val httpService = new HttpService(balorService)

  Http().bindAndHandle(httpService.routes, httpHost, httpPort)
}
