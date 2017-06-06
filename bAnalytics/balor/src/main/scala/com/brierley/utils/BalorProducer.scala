package com.brierley.utils

import java.util.Properties

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.spark.rdd.RDD

/**
  * Created by amerrill on 3/30/17.
  */
object BalorProducer {

  def sendBalor(topicProp: String, propsList: RDD[(String, String)], results: GenericRecord): Unit = {

    val topic = propsList.lookup("analytics." + topicProp + ".topic").head
    val brokers = propsList.lookup("analytics.bootstrap.servers").head
    val schemaRegistry = propsList.lookup("analytics.schema.registry.url").head

    val props = new Properties()
    props.put("bootstrap.servers", brokers)
    props.put("client.id", "b-AnalyticsBalor")
    props.put("acks", "1")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer")
    props.put("schema.registry.url", schemaRegistry)

    val producer = new KafkaProducer[String, GenericRecord](props)

    val data = new ProducerRecord[String, GenericRecord](topic, topic, results)

    producer.send(data)

    producer.close()
  }

  def sendError(error: GenericRecord, propsList: RDD[(String, String)]): Unit = {

    val topic = propsList.lookup("analytics.error.topic").head
    val brokers = propsList.lookup("analytics.bootstrap.servers").head
    val schemaRegistry = propsList.lookup("analytics.schema.registry.url").head

    val props = new Properties()
    props.put("bootstrap.servers", brokers)
    props.put("client.id", "b-AnalyticsBalor")
    props.put("acks", "1")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer")
    props.put("schema.registry.url", schemaRegistry)

    val producer = new KafkaProducer[String, GenericRecord](props)

    val data = new ProducerRecord[String, GenericRecord](topic, topic, error)

    producer.send(data)

    producer.close()
  }
}
