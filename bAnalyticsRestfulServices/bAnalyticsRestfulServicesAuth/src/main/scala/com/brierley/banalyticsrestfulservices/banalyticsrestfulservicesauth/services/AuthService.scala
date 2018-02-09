package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.services
import scala.concurrent.{Await, ExecutionContext, Future}
import org.apache.hadoop.hbase.util.Bytes
import spray.json._

import collection.JavaConversions._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.models._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.utils.{Config, DatabaseService}
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.mappings._

import scala.collection.mutable.ListBuffer
import java.util.Calendar

import akka.actor.ActorSystem
import scala.util.{Failure, Success}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.util.ByteString
import org.apache.hadoop.hbase.client.{Get, Put}

class AuthService(val databaseService: DatabaseService
                 )(implicit executionContext: ExecutionContext) extends UserAuthJsonSupport
  with Config  {

  import databaseService._

  def authenticate(userAuth:UserAuth): Future[UserAuthReturn] = {

    val userInfo = getData("UserInfo", userAuth.userId)


  }
}
