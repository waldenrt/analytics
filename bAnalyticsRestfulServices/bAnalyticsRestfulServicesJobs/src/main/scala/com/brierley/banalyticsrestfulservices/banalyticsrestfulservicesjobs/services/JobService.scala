package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.services


import scala.concurrent.{Await, ExecutionContext, Future}
import org.apache.hadoop.hbase.util.Bytes
import spray.json._

import collection.JavaConversions._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.models._
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.utils.{Config, DatabaseService}
import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.mappings._

import scala.collection.mutable.ListBuffer
import java.util.Calendar

import akka.actor.ActorSystem
import scala.util.{Failure, Success}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.util.ByteString
import org.apache.hadoop.hbase.client.{Get, Put}

class JobService(val databaseService: DatabaseService
                )(implicit executionContext: ExecutionContext) extends JobJsonSupport
                  with Config {

  import databaseService._

  private def getJobHistory: List[JobHistory] = {

    val result = getRows("JobHistory")
    val jobHistoryFamily = Bytes.toBytes("JobHistory")
    var jobHistoryList = new ListBuffer[JobHistory]

    var tempResult = result.next
    while (tempResult != null) {

      jobHistoryList += new JobHistory(
        Bytes.toString(tempResult.getValue(jobHistoryFamily, Bytes.toBytes("client"))),
        Bytes.toString(tempResult.getValue(jobHistoryFamily, Bytes.toBytes("user"))),
        Bytes.toString(tempResult.getRow),
        Bytes.toString(tempResult.getValue(jobHistoryFamily, Bytes.toBytes("jobName"))),
        Bytes.toString(tempResult.getValue(jobHistoryFamily, Bytes.toBytes("app"))),
        Bytes.toBoolean(tempResult.getValue(jobHistoryFamily, Bytes.toBytes("powerUser"))),
        Bytes.toString(tempResult.getValue(jobHistoryFamily, Bytes.toBytes("fileName"))),
        Bytes.toString(tempResult.getValue(jobHistoryFamily, Bytes.toBytes("delimiter"))),
        Bytes.toString(tempResult.getValue(jobHistoryFamily, Bytes.toBytes("jobStatus"))),
        Bytes.toString(tempResult.getValue(jobHistoryFamily, Bytes.toBytes("lastDate"))),
        Bytes.toLong(tempResult.getValue(jobHistoryFamily, Bytes.toBytes("recordCount"))),
        Bytes.toString(tempResult.getValue(jobHistoryFamily, Bytes.toBytes("args"))),
        Bytes.toString(tempResult.getValue(jobHistoryFamily, Bytes.toBytes("livyId"))),
        Bytes.toString(tempResult.getValue(jobHistoryFamily, Bytes.toBytes("errorMsg")))
      )

      tempResult = result.next
    }

    result.close()

    jobHistoryList.toList
  }

  def getJobHistoryByClient(client:String): Future[JobHistoryReturn] = {
    val fullJobHistory = getJobHistory

    val jobHistory = fullJobHistory.filter(x=>x.client == client)

    Future.successful[JobHistoryReturn](JobHistoryReturn("false", jobHistory, "", "Success", "", 0, 200, ""))
  }

  def getJobHistoryByClientUser(client:String, user:String): Future[JobHistoryReturn] = {
    val fullJobHistory = getJobHistory

    val jobHistory = fullJobHistory.filter(x=>x.client == client && x.user == user)

    Future.successful[JobHistoryReturn](JobHistoryReturn("false", jobHistory, "", "Success", "", 0, 200, ""))
  }

  def getJobHistoryByClientUserApp(client:String, user:String, app:String): Future[JobHistoryReturn]= {
    val fullJobHistory = getJobHistory

    val jobHistory = fullJobHistory.filter(x=>x.client == client && x.user == user && x.app == app)

    Future.successful[JobHistoryReturn](JobHistoryReturn("false", jobHistory, "", "Success", "", 0, 200, ""))
  }

  def getJobHistoryByUser(user:String): Future[JobHistoryReturn]= {
    val fullJobHistory = getJobHistory

    val jobHistory = fullJobHistory.filter(x=>x.user == user)

    Future.successful[JobHistoryReturn](JobHistoryReturn("false", jobHistory, "", "Success", "", 0, 200, ""))
  }

  def getJobHistoryByApp(app:String): Future[JobHistoryReturn] = {
    val fullJobHistory = getJobHistory

    val jobHistory = fullJobHistory.filter(x=>x.app == app)

    Future.successful[JobHistoryReturn](JobHistoryReturn("false", jobHistory, "", "Success", "", 0, 200, ""))
  }

  def getJobHistoryByUserApp(user:String, app:String): Future[JobHistoryReturn] = {
    val fullJobHistory = getJobHistory

    val jobHistory = fullJobHistory.filter(x=>x.user == user && x.app ==app)

    Future.successful[JobHistoryReturn](JobHistoryReturn("false", jobHistory, "", "Success", "", 0, 200, ""))
  }

  def getJobHistoryByClientApp(client:String, app:String): Future[JobHistoryReturn] = {
    val fullJobHistory = getJobHistory

    val jobHistory = fullJobHistory.filter(x=>x.client == client && x.app == app)

    Future.successful[JobHistoryReturn](JobHistoryReturn("false", jobHistory, "", "Success", "", 0, 200, ""))
  }

  def addJobHistory(job:JobHistory): Future[JobHistoryReturn] = {


    if(databaseService.jobNameExists(job.client, job.jobName)) {

      Future.successful[JobHistoryReturn](JobHistoryReturn("true", Nil, "Job name already exists", "Job name already exists", "", -100, 200, ""))

    } else {
      val jobHistory = upsertJobHistory(job)

      Future.successful[JobHistoryReturn](JobHistoryReturn("false", List[JobHistory](jobHistory), "", "Success", "", 0, 200, ""))
    }
  }

  def submitJob(job:Job): Future[JobReturn] = {

    if (job.jobId == "" && databaseService.jobNameExists(job.client, job.jobName)) {

      Future.successful[JobReturn](JobReturn("true", JobId(""), "Job name already exists", "Job name already exists", "", -100, 200, ""))

    }
     else {

      val jobHistory = JobHistory(job.client,
        job.user,
        job.jobId,
        job.jobName,
        job.app,
        job.powerUser,
        job.fileName,
        job.delimiter,
        "Awaiting " + job.app,
        Calendar.getInstance().getTime().toString,
        0,
        job.args.toJson.toString())

      val jobHistoryResult = upsertJobHistory(jobHistory)

      submitJobToSpark(job,jobHistoryResult.jobId)

      Future.successful[JobReturn](JobReturn("false", JobId(jobHistoryResult.jobId), "", "Success", "", 0, 200, ""))
    }
  }

  private def upsertJobHistory(job:JobHistory): JobHistory = {

    val table = getTableRef("JobHistory")
    val family = Bytes.toBytes("JobHistory")

    val jobId = if(job.jobId != null & job.jobId != "") {
      job.jobId
    } else {
      job.client + ":" + job.user + ":" + job.jobName + ":" + Calendar.getInstance().getTime().toString()
    }

    val put = new Put(Bytes.toBytes(jobId))

    put.addColumn(family, Bytes.toBytes("client"), Bytes.toBytes(job.client))
    put.addColumn(family, Bytes.toBytes("user"), Bytes.toBytes(job.user))
    put.addColumn(family, Bytes.toBytes("app"), Bytes.toBytes(job.app))
    put.addColumn(family, Bytes.toBytes("powerUser"), Bytes.toBytes(job.powerUser))
    put.addColumn(family, Bytes.toBytes("fileName"), Bytes.toBytes(job.fileName))
    put.addColumn(family, Bytes.toBytes("delimiter"), Bytes.toBytes(job.delimiter))
    put.addColumn(family, Bytes.toBytes("jobName"), Bytes.toBytes(job.jobName))
    put.addColumn(family, Bytes.toBytes("jobStatus"), Bytes.toBytes(job.jobStatus))
    put.addColumn(family, Bytes.toBytes("lastDate"), Bytes.toBytes(job.lastDate))
    put.addColumn(family, Bytes.toBytes("recordCount"), Bytes.toBytes(job.recordCount))
    put.addColumn(family, Bytes.toBytes("args"), Bytes.toBytes(job.args))
    put.addColumn(family, Bytes.toBytes("livyId"), Bytes.toBytes(job.livyId))
    put.addColumn(family, Bytes.toBytes("errorMsg"), Bytes.toBytes(job.errorMsg))

    table.put(put)

    table.close()

    JobHistory(job.client,
      job.user,
      jobId,
      job.jobName,
      job.app,
      job.powerUser,
      job.fileName,
      job.delimiter,
      job.jobStatus,
      job.lastDate,
      job.recordCount,
      job.args,
      job.livyId,
      job.errorMsg)
  }

  def submitJobToSpark(job:Job, jobId: String): Unit = {


    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    import system.dispatcher

    def getJarFileName(app:String) = {

      app match {
        case "cadence" => cadenceJar
        case "balor" => balorJar
        case "pareto" => paretoJar
        case "lifecycle" => lifecycleJar
      }
    }

    def getMainClassName(app:String) = {

      app match {
        case "cadence" => cadenceMain
        case "balor" => balorMain
        case "pareto" => paretoMain
        case "lifecycle" => lifecycleMain
      }
    }

    def getParameters(app:String) = {
      app match {
        case "cadence" => {
          val percentile = job.args.filter(x=>x.name=="percentile").map(x=>x.value)
          List(job.fileName, job.delimiter, jobId, percentile.head)
        }
        case "balor" => {
          val cadenceValue = job.args.filter(x=>x.name=="cadenceValue").map(x=>x.value)
          List(job.fileName, job.delimiter, jobId, cadenceValue.head)
        }
        case "pareto" => {
          val timePeriod = job.args.filter(x=>x.name=="timePeriod").map(x=>x.value)
          val quantValue = job.args.filter(x=>x.name=="quantValue").map(x=>x.value)
          val numProds = job.args.filter(x=>x.name=="numProds").map(x=>x.value)
          val dimension = job.args.filter(x=>x.name=="dimension").map(x=>x.value)
          val productColumns = job.args.filter(x=>x.name=="productColumns").map(x=>x.value)

          val paramList = ListBuffer(job.fileName, job.delimiter, jobId,
            timePeriod.head, quantValue.head, numProds.head,
            dimension.head)

          for (col <- productColumns.head.split(" ")) paramList += col

          paramList.toList
        }
        case "lifecycle" => {
          val timePeriod = job.args.filter(x=>x.name=="timePeriod").map(x=>x.value)
          val productColumn = job.args.filter(x=>x.name=="productColumn").map(x=>x.value)

          List(job.fileName, job.delimiter, jobId, timePeriod.head, productColumn.head.split(" ").mkString(" "))
        }
      }
    }

    val jobSubmitData = JobSubmit(
      getJarFileName(job.app),
      getMainClassName(job.app),
      getParameters(job.app),
      driverMemory,
      executorMemory,
      numExecutors,
      jobId
    )

    val request = HttpRequest(
      method = HttpMethods.POST,
      uri = "http://" + livyServerIP + ":" + livyServerPort + "/batches",
      entity = HttpEntity(MediaTypes.`application/json`,jobSubmitData.toJson.toString())
    )

    Http().singleRequest(request) onComplete {
      case Success(response) => {

        val batchInfo = Unmarshal(response.entity).to[livyBatchInfo].value.get.get

        val updateJobHistory = JobHistory(
          job.client,
          job.user,
          jobId,
          job.jobName,
          job.app,
          job.powerUser,
          job.fileName,
          job.delimiter,
          "Running " + job.app,
          Calendar.getInstance().getTime().toString(),
          0,
          job.args.toJson.toString,
          batchInfo.id.toString,
          ""
        )

        upsertJobHistory(updateJobHistory)
      }
      case Failure(ex) => {

        val updateJobHistory = JobHistory(
          job.client,
          job.user,
          jobId,
          job.jobName,
          job.app,
          job.powerUser,
          job.fileName,
          job.delimiter,
          "Error",
          Calendar.getInstance().getTime().toString(),
          0,
          job.args.toJson.toString(),
          "",
          ex.getMessage()
        )

        upsertJobHistory(updateJobHistory)
      }
    }
  }

}
