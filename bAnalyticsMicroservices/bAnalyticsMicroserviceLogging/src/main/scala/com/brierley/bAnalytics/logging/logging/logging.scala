package com.brierley.bAnalytics.logging.logging

import org.apache.avro.Schema.Field
import org.apache.avro.generic.{GenericData, GenericRecord}
import org.apache.avro.util.Utf8
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.kafka.clients.consumer.ConsumerRecord

import collection.JavaConversions._

object logging {
  //HBase setup
  val conf = HBaseConfiguration.create

  conf.clear
  conf.set("hbase.zookeeper.quorum","10.4.3.24,10.4.3.25,10.4.3.26")
  conf.set("hbase.zookeeper.property.clientPort","2181")
  conf.set("zookeeper.znode.parent", "/hbase")

  def processLoggingMessages(message: ConsumerRecord[String, GenericRecord]): Unit = {

    val loggingMessage = message.value()
    val fields = loggingMessage.getSchema().getFields()

    //HBase variables
    val tableName = TableName.valueOf("JobHistory")
    val jobHistory = Bytes.toBytes("JobHistory")
    val errorMsg = Bytes.toBytes("errorMsg")
    val jobStatus = Bytes.toBytes("jobStatus")


    val connection = ConnectionFactory.createConnection(conf)
    val admin = connection.getAdmin

    if(!admin.tableExists(tableName)) {
      println("Table does not exist")
    } else {
      println("Table does exist")
    }
    val table = connection.getTable(tableName)

    val jobKey = Bytes.toBytes(loggingMessage.get("jobKey").asInstanceOf[Utf8].toString)
    val errorInfo = Bytes.toBytes(loggingMessage.get("errorInfo").toString)

    val put = new Put(jobKey)
    put.addColumn(jobHistory,errorMsg,errorInfo)
    put.addColumn(jobHistory,jobStatus,Bytes.toBytes("Error"))

    table.put(put)

    if(table != null) {
      table.close()
    }

    if(connection != null) {
      connection.close()
    }

  }
}
