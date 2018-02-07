package com.brierley.bAnalytics.lifecycle.lifecycle

import org.apache.avro.Schema.Field
import org.apache.avro.generic.{GenericData, GenericRecord}
import org.apache.avro.util.Utf8
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.kafka.clients.consumer.ConsumerRecord

import collection.JavaConversions._

object lifecycle {
  //HBase setup
  val conf = HBaseConfiguration.create

  conf.clear
  conf.set("hbase.zookeeper.quorum","10.4.3.24,10.4.3.25,10.4.3.26")
  conf.set("hbase.zookeeper.property.clientPort","2181")
  conf.set("zookeeper.znode.parent", "/hbase")

  def processLifecycleMessages(message: ConsumerRecord[String, GenericRecord]): Unit = {

    //Avro variables
    val lifeCycleMessage = message.value()
    val fields = lifeCycleMessage.getSchema.getFields

    //HBase variables
    val tableName = TableName.valueOf("LifeCycle")
    val lifecycleSummary = Bytes.toBytes("LifeCycleSummary")
    val lifeCycleResults = Bytes.toBytes("LifeCycleResults")
    val lifeCycleProdResults = Bytes.toBytes("LifeCycleProdResults")
    val migrationResults = Bytes.toBytes("MigrationResults")

    val connection = ConnectionFactory.createConnection(conf)
    val admin = connection.getAdmin

    if(!admin.tableExists(tableName)) {
      println("Table does not exist")
    } else {
      println("Table does exist")
    }
    val table = connection.getTable(tableName)

    val jobKey = Bytes.toBytes(lifeCycleMessage.get("jobKey").asInstanceOf[Utf8].toString)
    val numRec = lifeCycleMessage.get("numRecords").asInstanceOf[Long]

    val put = new Put(jobKey)

    for(field: Field <- fields) {
      if(field.schema.getType.getName=="array") {

        if(field.name()=="lifecycleResults") {
          val elements = lifeCycleMessage.get(field.name).asInstanceOf[GenericData.Array[GenericRecord]]

          for(element <- elements) {
            val columnName = "TimePeriod-" + element.get("timePeriod").asInstanceOf[Int]
            put.addColumn(lifeCycleResults, Bytes.toBytes(columnName), convertAvroToScalaDataTypes(element.getSchema.getType.getName,element))
          }
        } else if(field.name()=="prodMetrics") {
          val elements = lifeCycleMessage.get(field.name).asInstanceOf[GenericData.Array[GenericRecord]]

          for(element <- elements) {
            val columnName = "TimePeriod-" + element.get("timePeriod").asInstanceOf[Int] + "-" + element.get("productCat").toString()
            put.addColumn(lifeCycleProdResults, Bytes.toBytes(columnName), convertAvroToScalaDataTypes(element.getSchema.getType.getName, element))
          }
        } else if(field.name()=="migrationResults") {
            val elements = lifeCycleMessage.get(field.name).asInstanceOf[GenericData.Array[GenericRecord]]

            for(element <- elements) {
              val columnName = "TimePeriod-" + element.get("timePeriod").asInstanceOf[Int]
              put.addColumn(migrationResults, Bytes.toBytes(columnName), convertAvroToScalaDataTypes(element.getSchema.getType.getName,element))
            }
        }

      } else {
        put.addColumn(lifecycleSummary, Bytes.toBytes(field.name), convertAvroToScalaDataTypes(field.schema.getType.getName,lifeCycleMessage.get(field.name)))
      }
    }

    table.put(put)

    if(table != null) {
      table.close()
    }

    if(connection != null) {
      connection.close()
    }

    updateJobHistoryJobComplete(jobKey, numRec)
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
