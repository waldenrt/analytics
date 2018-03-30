package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.utils

import com.typesafe.config.ConfigFactory

trait Config {

  private val config = ConfigFactory.load()
  private val httpConfig = config.getConfig("http")
  private val databaseConfig = config.getConfig("database")
  private val restApiConfig = config.getConfig("restApi")
  private val jwtToken = config.getConfig("jwtToken")

  val httpHost = httpConfig.getString("interface")
  val httpPort = httpConfig.getInt("port")

  val zkQuorum = databaseConfig.getString("zkQuorum")
  val zkPort = databaseConfig.getString("zkPort")
  val zkPath = databaseConfig.getString("zkPath")

  val corsSupport = restApiConfig.getString("corsSupport")

  val secret = jwtToken.getString("secret")
  val authExpiration = jwtToken.getInt("authExpirationInSeconds")
  val refreshExpiration = jwtToken.getInt("refreshExpirationInSeconds")

}
