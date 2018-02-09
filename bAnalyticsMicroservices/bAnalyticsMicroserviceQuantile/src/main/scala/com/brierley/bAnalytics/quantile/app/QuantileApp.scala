package com.brierley.bAnalytics.quantile.app

import com.brierley.bAnalytics.quantile.kafka.QuantileKafkaConsumer
import com.brierley.bAnalytics.quantile.quantile.quantile

object QuantileApp extends App {

  if (args.length != 4) {
    println("You need to pass in all 4 arguments")
  } else {
    val consumer = new QuantileKafkaConsumer(args(0), args(1), args(2), args(3))

    consumer.run(quantile.processQuantileMessages)

  }
}
