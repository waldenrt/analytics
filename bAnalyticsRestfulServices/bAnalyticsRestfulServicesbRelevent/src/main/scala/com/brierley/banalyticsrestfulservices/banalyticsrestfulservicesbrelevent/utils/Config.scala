package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbrelevent.utils

import com.typesafe.config.ConfigFactory

trait Config {

  private val config = ConfigFactory.load()
  private val httpConfig = config.getConfig("http")
  private val databaseConfig = config.getConfig("database")
  private val restApiConfig = config.getConfig("restApi")

  val httpHost = httpConfig.getString("interface")
  val httpPort = httpConfig.getInt("port")

  val zkQuorum = databaseConfig.getString("zkQuorum")
  val zkPort = databaseConfig.getString("zkPort")
  val zkPath = databaseConfig.getString("zkPath")

  val corsSupport = restApiConfig.getString("corsSupport")
}
