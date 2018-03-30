package com.brierley.bAnalytics.bRelevent.app

import com.brierley.bAnalytics.bRelevent.kafka.bReleventKafkaConsumer
import com.brierley.bAnalytics.bRelevent.bRelevent.bRelevent

object bReleventApp extends App {

  if (args.length != 4) {
    println("You need to pass in all 4 arguments")
  } else {
    val consumer = new bReleventKafkaConsumer(args(0), args(1), args(2), args(3))

    consumer.run(bRelevent.processbReleventMessages)

}
