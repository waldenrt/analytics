package com.brierley.bAnalytics.bRelevent.bRelevent

import org.apache.avro.Schema.Field
import org.apache.avro.generic.{GenericData, GenericRecord}
import org.apache.avro.util.Utf8
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.kafka.clients.consumer.ConsumerRecord

import collection.JavaConversions._

object bRelevent {

  def processbReleventMessages(message: ConsumerRecord[String, GenericRecord]): Unit = {

  }

  def updateJobHistoryJobComplete(jobKey: Array[Byte], records:Long) = {

    val jobHistoryTable = TableName.valueOf("JobHistory")
    val jobHistoryFamily = Bytes.toBytes("JobHistory")
    val jobHistoryStatus = Bytes.toBytes("jobStatus")
    val jobHistoryRecCount = Bytes.toBytes("recordCount")


    val connection = ConnectionFactory.createConnection(conf)
    val myJobHistory = connection.getTable(jobHistoryTable)

    val updateJobSummary = new Put(jobKey)
    updateJobSummary.addColumn(jobHistoryFamily, jobHistoryStatus, Bytes.toBytes("Finished"))
    updateJobSummary.addColumn(jobHistoryFamily, jobHistoryRecCount, Bytes.toBytes(records))

    myJobHistory.put(updateJobSummary)

    if(myJobHistory != null)
      myJobHistory.close()

    if(connection != null)
      connection.close()
  }

  def convertAvroToScalaDataTypes(schemaType:String, element:Object):Array[Byte] = {

    schemaType match {
      case "boolean" => Bytes.toBytes(element.asInstanceOf[Boolean])
      case "int" => Bytes.toBytes(element.asInstanceOf[Int])
      case "long" => Bytes.toBytes(element.asInstanceOf[Long])
      case "float" => Bytes.toBytes(element.asInstanceOf[Float])
      case "double" => Bytes.toBytes(element.asInstanceOf[Double])
      case "string" => element.asInstanceOf[Utf8].getBytes
      case _ => element.toString.getBytes
    }
  }
}
