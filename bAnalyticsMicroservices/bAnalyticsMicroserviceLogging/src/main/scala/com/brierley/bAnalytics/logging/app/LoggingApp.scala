package com.brierley.bAnalytics.logging.app

import com.brierley.bAnalytics.logging.kafka._
import com.brierley.bAnalytics.logging.logging._

object LoggingApp extends App {

  if (args.length != 4) {
    println("You need to pass in all 4 arguments")
  } else {
    val consumer = new LoggingKafkaConsumer(args(0), args(1), args(2), args(3))

    consumer.run(logging.processLoggingMessages)

  }
}
