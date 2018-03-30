package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbrelevent
import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContext
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbrelevent.services.QuantileService
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbrelevent.utils.{Config,DatabaseService}

class bReleventMain extends App with Config {
  implicit val actorSystem = ActorSystem()
  implicit val executor: ExecutionContext = actorSystem.dispatcher
  implicit val log: LoggingAdapter = Logging(actorSystem, getClass)
  implicit val materializer: ActorMaterializer = ActorMaterializer()


  val databaseService = new DatabaseService()
  val quantileService = new bReleventService(databaseService)
  val httpService = new HttpService(quantileService)

  Http().bindAndHandle(httpService.routes, httpHost, httpPort)

}
