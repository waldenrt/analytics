package com.brierley.utils

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * Created by amerrill on 3/30/17.
  */
object BalorProducer {

  def sendBalor(results: Array[Byte], key: String): Unit = {
    val brokers = "localhost:9092"
    val topic = "test"


    val props = new Properties()
    props.put("bootstrap.servers", brokers)
    props.put("client.id", "b-AnalyticsBalor")
    props.put("request.required.acks", "1")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")

    val producer = new KafkaProducer[String, Array[Byte]](props)

    val data = new ProducerRecord[String, Array[Byte]](topic, results)

    producer.send(data)

    producer.close()
  }
}
