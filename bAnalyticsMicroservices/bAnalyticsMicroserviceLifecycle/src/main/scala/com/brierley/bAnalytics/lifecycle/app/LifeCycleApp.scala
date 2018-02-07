package com.brierley.bAnalytics.lifecycle.app

import com.brierley.bAnalytics.lifecycle.kafka.LifecycleKafkaConsumer
import com.brierley.bAnalytics.lifecycle.lifecycle.lifecycle

object LifeCycleApp extends App {
  if (args.length != 4) {
    println("You need to pass in all 4 arguments")
  } else {
    val consumer = new LifecycleKafkaConsumer(args(0), args(1), args(2), args(3))

    consumer.run(lifecycle.processLifecycleMessages)

  }
}
