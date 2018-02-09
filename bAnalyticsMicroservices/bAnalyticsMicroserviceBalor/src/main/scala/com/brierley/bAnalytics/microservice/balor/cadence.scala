package com.brierley.bAnalytics.microservice.balor

//Avro imports
import org.apache.avro.Schema.Field
import org.apache.avro.generic.{GenericData, GenericRecord}
import org.apache.avro.util.Utf8

//Kafka imports
import org.apache.kafka.clients.consumer.ConsumerRecord

//Scala/Java imports
import scala.collection.JavaConversions._

//HBase Imports
import org.apache.hadoop.hbase.client.{ConnectionFactory, Put, Get}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}

//Akka HTTP and Model Support
import com.brierley.bAnalytics.microservice.balor.models._
import scalaj.http._

/**
  * Created by rwalden on 6/7/17.
  */
object cadence extends JobJsonSupport {

  val conf = HBaseConfiguration.create

  conf.clear
  conf.set("hbase.zookeeper.quorum","10.4.3.24,10.4.3.25,10.4.3.26")
  conf.set("hbase.zookeeper.property.clientPort","2181")
  conf.set("zookeeper.znode.parent", "/hbase")

  def processCadenceMessages(message: ConsumerRecord[String, GenericRecord]): Unit = {

    //Avro variables
    val cadenceMessage = message.value()
    val fields = cadenceMessage.getSchema.getFields

    //HBase variables
    val tableName = TableName.valueOf("Cadence")
    val cadenceSummary = Bytes.toBytes("CadenceSummary")
    val cadenceDetails = Bytes.toBytes("CadenceDetails")

    val balor = TableName.valueOf("Balor")
    val balorSummary = Bytes.toBytes("BalorSummary")

    val jobKey = Bytes.toBytes(cadenceMessage.get("jobKey").asInstanceOf[Utf8].toString)
    val numRec = cadenceMessage.get("numRecords").asInstanceOf[Long]
    val normalizedCadence = cadenceMessage.get("normalizedCadence").asInstanceOf[Utf8].toString


    val connection = ConnectionFactory.createConnection(conf)
    val admin = connection.getAdmin

    val table = connection.getTable(tableName)
    val balorTable = connection.getTable(balor)

    val balorPut = new Put(jobKey)

    balorPut.addColumn(balorSummary, Bytes.toBytes("singleVisit"),
      convertAvroToScalaDataTypes(cadenceMessage.getSchema.getField("singleVisit").schema().getType.getName, cadenceMessage.get("singleVisit")))

    balorPut.addColumn(balorSummary, Bytes.toBytes("totalCusts"),
      convertAvroToScalaDataTypes(cadenceMessage.getSchema.getField("totalCusts").schema().getType.getName, cadenceMessage.get("totalCusts")))

    balorPut.addColumn(balorSummary, Bytes.toBytes("normalizedCadence"),
      convertAvroToScalaDataTypes(cadenceMessage.getSchema.getField("normalizedCadence").schema().getType.getName, cadenceMessage.get("normalizedCadence")))



    balorTable.put(balorPut)

    if(balorTable != null)
      balorTable.close

    val put = new Put(jobKey)

    for(field: Field <- fields) {
      if(field.schema.getType.getName=="array") {

        val elements = cadenceMessage.get(field.name).asInstanceOf[GenericData.Array[GenericRecord]]

        for(element <- elements) {
          val columnName = "Cadence-" + element.get("cadence").asInstanceOf[Int]
          put.addColumn(cadenceDetails, Bytes.toBytes(columnName), convertAvroToScalaDataTypes(element.getSchema.getType.getName,element))
        }
      } else {
        put.addColumn(cadenceSummary, Bytes.toBytes(field.name), convertAvroToScalaDataTypes(field.schema.getType.getName,cadenceMessage.get(field.name)))
      }
    }

    table.put(put)

    if(table != null) {
      table.close()
    }

    if(connection != null) {
      connection.close()
    }

    updateJobHistoryCadenceComplete(jobKey, numRec)
    startBalorProcess(jobKey, normalizedCadence)
  }

  def updateJobHistoryCadenceComplete(jobKey: Array[Byte], records:Long) = {

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

  def startBalorProcess(jobKey: Array[Byte], normalizedCadence:String) = {

    import spray.json._
    import com.brierley.bAnalytics.microservice.balor.models.JobJsonSupport

    val jobHistoryTable = TableName.valueOf("JobHistory")
    val jobHistoryFamily = Bytes.toBytes("JobHistory")

    val connection = ConnectionFactory.createConnection(conf)
    val myJobHistory = connection.getTable(jobHistoryTable)

    val get = new Get(jobKey)
    val result = myJobHistory.get(get)

    val params = List(JobParameter("cadenceValue", getCadenceValueFromNormalizedCadence(normalizedCadence)))

    val jobRequest = Job(
      Bytes.toString(result.getValue(jobHistoryFamily, Bytes.toBytes("client"))),
      Bytes.toString(result.getValue(jobHistoryFamily, Bytes.toBytes("user"))),
      Bytes.toBoolean(result.getValue(jobHistoryFamily, Bytes.toBytes("powerUser"))),
      "balor",
      Bytes.toString(result.getValue(jobHistoryFamily, Bytes.toBytes("jobName"))),
      Bytes.toString(result.getRow),
      Bytes.toString(result.getValue(jobHistoryFamily, Bytes.toBytes("fileName"))),
      Bytes.toString(result.getValue(jobHistoryFamily, Bytes.toBytes("delimiter"))),
      params
    )

    myJobHistory.close()
    connection.close()

    Http("http://10.4.3.18:9003/api/v1/jobs/job")
      .postData(jobRequest.toJson.toString())
      .header("content-type", "application/json")
      .asString
      .code

  }

  def getCadenceValueFromNormalizedCadence(normaizedCadence:String): String = {

    normaizedCadence match {
      case "1 Week" => OneWeek.name.toString
      case "2 Weeks" => TwoWeeks.name.toString
      case "1 Month" => OneMonth.name.toString
      case "2 Months" => TwoMonths.name.toString
      case "3 Months" => ThreeMonths.name.toString
      case "6 Months" => SixMonths.name.toString
      case "1 Year" => OneYear.name.toString
    }

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
  sealed abstract class CadenceValues(val name:Int, val conversion:String, val somethingElse:Int)
  case object OneWeek extends CadenceValues(1, "1 Week", 7)
  case object TwoWeeks extends CadenceValues(2, "2 Weeks", 14)
  case object OneMonth extends CadenceValues(3, "1 Month", 1)
  case object TwoMonths extends CadenceValues(4, "2 Months", 2)
  case object ThreeMonths extends CadenceValues(5, "3 Months", 3)
  case object SixMonths extends CadenceValues(6, "6 months", 6)
  case object OneYear extends CadenceValues(7, "1 year", 12)

}
