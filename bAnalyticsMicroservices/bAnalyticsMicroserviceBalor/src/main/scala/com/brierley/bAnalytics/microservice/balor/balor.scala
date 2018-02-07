package com.brierley.bAnalytics.microservice.balor

//Avro imports
import org.apache.avro.generic.{GenericData, GenericRecord}
import org.apache.avro.Schema.Field
import org.apache.avro.util.Utf8
import org.apache.hadoop.hbase.client.{Admin, Get}

//Kafka imports
import org.apache.kafka.clients.consumer.ConsumerRecord

//Scala/Java imports
import collection.JavaConversions._

//HBase Imports
import org.apache.hadoop.conf.Configured
import org.apache.hadoop.hbase.{HBaseConfiguration,TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory,Put}
import org.apache.hadoop.hbase.util.Bytes

/**
  * Created by rwalden on 6/7/17.
  */
object balor {

  //HBase setup
  val conf = HBaseConfiguration.create

  conf.clear
  conf.set("hbase.zookeeper.quorum","10.4.3.24,10.4.3.25,10.4.3.26")
  conf.set("hbase.zookeeper.property.clientPort","2181")
  conf.set("zookeeper.znode.parent", "/hbase")

  def processBalorMessages(message: ConsumerRecord[String, GenericRecord]): Unit = {

    //Avro variables
    val balorMessage = message.value()
    val fields = balorMessage.getSchema.getFields

    //HBase variables
    val tableName = TableName.valueOf("Balor")
    val jobSummary = TableName.valueOf("JobSummary")
    val balorSummary = Bytes.toBytes("BalorSummary")
    val balorResults = Bytes.toBytes("BalorResults")

    val connection = ConnectionFactory.createConnection(conf)
    val admin = connection.getAdmin

    if(!admin.tableExists(tableName)) {
      println("Table does not exist")
    } else {
      println("Table does exist")
    }
    val table = connection.getTable(tableName)

    val jobKey = Bytes.toBytes(balorMessage.get("jobKey").asInstanceOf[Utf8].toString)
    val recordCount = balorMessage.get("numRecords").asInstanceOf[Long]

    val put = new Put(jobKey)

    for(field: Field <- fields) {
      if(field.schema.getType.getName=="array") {

        val elements = balorMessage.get(field.name).asInstanceOf[GenericData.Array[GenericRecord]]

        for(element <- elements) {
          val columnName = "TimePeriod-" + element.get("timePeriod").asInstanceOf[Int]
          put.addColumn(balorResults, Bytes.toBytes(columnName), convertAvroToScalaDataTypes(element.getSchema.getType.getName,element))
        }
      } else {
        put.addColumn(balorSummary, Bytes.toBytes(field.name), convertAvroToScalaDataTypes(field.schema.getType.getName,balorMessage.get(field.name)))
      }
    }

    table.put(put)

    if(table != null) {
      table.close()
    }

    if(connection != null) {
      connection.close()
    }

    updateJobHistoryBalorComplete(jobKey, recordCount)
  }

  def updateJobHistoryBalorComplete(jobKey: Array[Byte], records:Long) = {

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
