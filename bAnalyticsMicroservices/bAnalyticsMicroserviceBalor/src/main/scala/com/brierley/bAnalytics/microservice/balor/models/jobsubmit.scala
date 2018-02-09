package com.brierley.bAnalytics.microservice.balor.models

import spray.json._

case class JobParameter (name:String, value:String)

case class Job(client: String,
               user: String,
               powerUser:Boolean,
               app: String,
               jobName: String,
               jobId: String,
               fileName: String,
               delimiter: String,
               args: List[JobParameter])

trait JobJsonSupport extends  DefaultJsonProtocol {
  implicit val jobParameterFormat = jsonFormat2(JobParameter)
  implicit val JobFormat = jsonFormat9(Job)
}
