package com.brierley.bAnalytics.microservice.App

import com.brierley.bAnalytics.microservice.balor.{balor, cadence}
import com.brierley.bAnalytics.microservice.kafka.kafkaConsumer

/**
  * Created by rwalden on 6/8/17.
  */
object BalorApp extends App{

  if(args.length != 4) {
    println("You need to pass in all 4 arguments")
  } else {
    val consumer = new kafkaConsumer(args(0), args(1), args(2), args(3))
    if(args(2)=="Balor") {
      consumer.run(balor.processBalorMessages)
    } else {
      consumer.run(cadence.processCadenceMessages)
    }
  }
}
