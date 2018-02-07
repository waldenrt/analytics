package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.utils

import com.typesafe.config.ConfigFactory

trait Config {

  private val config = ConfigFactory.load()
  private val httpConfig = config.getConfig("http")
  private val databaseConfig = config.getConfig("database")
  private val restApiConfig = config.getConfig("restApi")
  private val cadenceConfig = config.getConfig("cadence")
  private val balorConfig = config.getConfig("balor")
  private val paretoConfig = config.getConfig("pareto")
  private val lifecycleConfig = config.getConfig("lifecycle")
  private val sparkDefaultConfig = config.getConfig("sparkDefault")
  private val livyServerConfig = config.getConfig("livyServer")

  val httpHost = httpConfig.getString("interface")
  val httpPort = httpConfig.getInt("port")

  val zkQuorum = databaseConfig.getString("zkQuorum")
  val zkPort = databaseConfig.getString("zkPort")
  val zkPath = databaseConfig.getString("zkPath")

  val corsSupport = restApiConfig.getString("corsSupport")

  val cadenceJar = cadenceConfig.getString("jar")
  val cadenceMain = cadenceConfig.getString("main")

  val balorJar = balorConfig.getString("jar")
  val balorMain = balorConfig.getString("main")

  val paretoJar = paretoConfig.getString("jar")
  val paretoMain = paretoConfig.getString("main")

  val lifecycleJar = lifecycleConfig.getString("jar")
  val lifecycleMain = lifecycleConfig.getString("main")

  val driverMemory = sparkDefaultConfig.getString("driverMemory")
  val executorMemory = sparkDefaultConfig.getString("executorMemory")
  val numExecutors = sparkDefaultConfig.getInt("numExecutors")

  val livyServerIP = livyServerConfig.getString("livyServerIP")
  val livyServerPort = livyServerConfig.getString("livyServerPort")


}
