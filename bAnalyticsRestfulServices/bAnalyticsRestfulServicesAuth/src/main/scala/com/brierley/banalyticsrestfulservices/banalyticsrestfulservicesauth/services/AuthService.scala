package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.services
import scala.concurrent.{Await, ExecutionContext, Future}
import org.apache.hadoop.hbase.util.Bytes
import spray.json._

import collection.JavaConversions._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.models._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.utils.{Config, DatabaseService, PasswordHash, JwtTokenUtil}
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

    val userInfoFamily = Bytes.toBytes("UserInfo")
    val clientFamily = Bytes.toBytes("ClientInfo")
    val clientFamilyMap = userInfo.getFamilyMap(clientFamily)

    val userPassword = Bytes.toBytes("userPassword")

    val tmpHashedPassword = userAuth.userPassword
    val hashedPasswordFromDB = Bytes.toString(userInfo.getValue(userInfoFamily, userPassword))

    if (PasswordHash.validatePassword(tmpHashedPassword, hashedPasswordFromDB)) {
      val token = JwtTokenUtil.generateTokens(userAuth.userId)
      val clientInfoVector = for (c: Array[Byte] <- clientFamilyMap.keySet)
        yield ClientInfo(Bytes.toString(c), Bytes.toBoolean(userInfo.getValue(clientFamily, c)))

      val authenticatedUser = User(
        Bytes.toString(userInfo.getValue(userInfoFamily, Bytes.toBytes("firstName"))),
        Bytes.toString(userInfo.getValue(userInfoFamily, Bytes.toBytes("lastName"))),
        Bytes.toString(userInfo.getValue(userInfoFamily, Bytes.toBytes("emailAddress"))),
        Bytes.toBoolean(userInfo.getValue(userInfoFamily, Bytes.toBytes("powerUser"))),
        Bytes.toBoolean(userInfo.getValue(userInfoFamily, Bytes.toBytes("admin"))),
        clientInfoVector.toList,
        token)
      Future.successful[UserAuthReturn](UserAuthReturn("false", authenticatedUser, "", "Login successful", "", 0, 200, ""))
    } else {
      Future.successful[UserAuthReturn](UserAuthReturn("true", User("","","",false,false,List[ClientInfo](ClientInfo("",false)),Token("","")), "", "Invalid User Name or Password", "", -1000, 200, ""))
    }
  }


    def signUp(userSignUp:UserSignUp): Future[UserSignUpReturn] = {

      val userTable = getTableRef("UserInfo")
      val userInfoFamily = Bytes.toBytes("UserInfo")
      val clientInfoFamily = Bytes.toBytes("ClientInfo")

      try {
        val newUser = new Put(Bytes.toBytes(userSignUp.emailAddress))

        newUser.addColumn(userInfoFamily, Bytes.toBytes("firstName"), Bytes.toBytes(userSignUp.firstName))
        newUser.addColumn(userInfoFamily, Bytes.toBytes("lastName"), Bytes.toBytes(userSignUp.lastName))
        newUser.addColumn(userInfoFamily, Bytes.toBytes("emailAddress"), Bytes.toBytes(userSignUp.emailAddress))
        newUser.addColumn(userInfoFamily, Bytes.toBytes("powerUser"), Bytes.toBytes(userSignUp.powerUser))
        newUser.addColumn(userInfoFamily, Bytes.toBytes("admin"), Bytes.toBytes(userSignUp.admin))
        newUser.addColumn(userInfoFamily, Bytes.toBytes("userPassword"), Bytes.toBytes(PasswordHash.createHash(userSignUp.userPassword)))
        userSignUp.clients.foreach(x => newUser.addColumn(clientInfoFamily, Bytes.toBytes(x.name), Bytes.toBytes(x.enabled)))

        userTable.put(newUser)

        userTable.close()

        Future.successful[UserSignUpReturn](UserSignUpReturn("false", userSignUp, "", "User Added Successfully", "", 0, 200, ""))
      } catch {
        case e:Exception=> Future.successful[UserSignUpReturn](UserSignUpReturn("true", userSignUp, e.getMessage, "User Add failed", e.getStackTrace.toString, -1100, 200, e.toString))
      }


    }
}
