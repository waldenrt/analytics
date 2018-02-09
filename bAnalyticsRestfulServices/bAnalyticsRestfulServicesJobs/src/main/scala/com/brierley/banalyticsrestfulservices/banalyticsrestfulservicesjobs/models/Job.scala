package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesjobs.models

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

case class JobId(jobId: String)

case class JobReturn(  isError: String,
                       data: JobId,
                       developerMessage:String,
                       userMessage:String,
                       moreInfo:String,
                       responseCode:Int,
                       httpStatusCode:Int,
                       errors:String
                    )

case class JobHistory(client: String,
                      user: String,
                      jobId: String = "",
                      jobName: String,
                      app: String,
                      powerUser: Boolean,
                      fileName: String,
                      delimiter: String,
                      jobStatus: String,
                      lastDate:String,
                      recordCount:Long,
                      args:String,
                      livyId:String = "",
                      errorMsg:String = "")

case class JobHistoryReturn(  isError: String,
                              data: List[JobHistory],
                              developerMessage:String,
                              userMessage:String,
                              moreInfo:String,
                              responseCode:Int,
                              httpStatusCode:Int,
                              errors:String
                            )

case class JobSubmit(file: String,
                    className: String,
                    args: List[String],
                    driverMemory: String,
                    executorMemory: String,
                    numExecutors: Int,
                    name: String)

case class livyBatchInfo(id:Int,
                         log:List[String],
                         state:String)