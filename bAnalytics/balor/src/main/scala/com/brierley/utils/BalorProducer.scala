package com.brierley.utils

import java.util.Properties

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * Created by amerrill on 3/30/17.
  */
object BalorProducer {

  def sendBalor(topic: String, results: GenericRecord): Unit = {
    //local brokers
    //val brokers = "localhost:9092"

    //dev brokers
    val brokers = "10.4.3.61:9092,10.4.3.62:9092,10.4.3.63:9092"

    val props = new Properties()
    props.put("bootstrap.servers", brokers)
    props.put("client.id", "b-AnalyticsBalor")
    props.put("acks", "1")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer")
    props.put("schema.registry.url", "http://10.4.3.63:8081")
    //props.put("schema.registry.url", "http://localhost:8081")

    val producer = new KafkaProducer[String, GenericRecord](props)

    val data = new ProducerRecord[String, GenericRecord](topic, topic, results)

    producer.send(data)

    producer.close()
  }

  def sendError(error: GenericRecord): Unit = {
    //local brokers
    //val brokers = "localhost:9092"

    //dev brokers
    val brokers = "10.4.3.61:9092,10.4.3.62:9092,10.4.3.63:9092"
    val topic = "error"

    val props = new Properties()
    props.put("bootstrap.servers", brokers)
    props.put("client.id", "b-AnalyticsBalor")
    props.put("acks", "1")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer")
    props.put("schema.registry.url", "http://10.4.3.63:8081")
    //props.put("schema.registry.url", "http://localhost:8081")

    val producer = new KafkaProducer[String, GenericRecord](props)

    val data = new ProducerRecord[String, GenericRecord](topic, topic, error)

    producer.send(data)

    producer.close()
  }
}
