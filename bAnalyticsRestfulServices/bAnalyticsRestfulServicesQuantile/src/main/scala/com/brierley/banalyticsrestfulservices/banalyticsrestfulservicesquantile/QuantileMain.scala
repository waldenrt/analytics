package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContext
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.http.HttpService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.services.QuantileService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.utils.{Config,DatabaseService}

object QuantileMain extends App with Config {

  implicit val actorSystem = ActorSystem()
  implicit val executor: ExecutionContext = actorSystem.dispatcher
  implicit val log: LoggingAdapter = Logging(actorSystem, getClass)
  implicit val materializer: ActorMaterializer = ActorMaterializer()


  val databaseService = new DatabaseService()
  val quantileService = new QuantileService(databaseService)
  val httpService = new HttpService(quantileService)

  Http().bindAndHandle(httpService.routes, httpHost, httpPort)
}
