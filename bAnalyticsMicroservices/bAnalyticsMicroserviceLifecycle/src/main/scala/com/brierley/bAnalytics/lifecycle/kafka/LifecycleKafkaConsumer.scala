package com.brierley.bAnalytics.lifecycle.kafka

//Java imports
import java.util.concurrent._
import java.util.{Collections, Properties}

//Kafka imports
import kafka.utils.Logging
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer, ConsumerRecord}

//Avro imports
import org.apache.avro.generic.GenericRecord

//Scala imports
import scala.collection.JavaConversions._

class LifecycleKafkaConsumer(val groupID: String,
                    val brokers: String,
                    val topic: String,
                    val schemaURL: String) extends Logging  {

  val props:Properties = createConsumerConifg(groupID, brokers)
  val consumer = new KafkaConsumer[String, GenericRecord](props)
  var executor: ExecutorService = _

  def createConsumerConifg(groupId: String, brokers: String): Properties = {
    val props = new Properties()

    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000")
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroDeserializer")
    props.put("schema.registry.url", schemaURL)

    props

  }

  def run(fx: ConsumerRecord[String, GenericRecord] => Unit): Unit = {

    consumer.subscribe(Collections.singletonList(this.topic))

    Executors.newSingleThreadExecutor.execute( new Runnable {
      override def run(): Unit = {
        while(true) {
          val records = consumer.poll(1000)

          for (record <- records) {
            fx(record)
          }
        }
      }
    })


  }
}
