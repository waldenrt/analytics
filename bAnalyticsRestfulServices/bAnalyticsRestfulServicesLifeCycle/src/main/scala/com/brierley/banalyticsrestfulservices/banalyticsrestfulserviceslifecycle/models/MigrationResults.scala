package com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.models

case class MigrationResultsTimePeriod(timePeriod:Int,
                            migrationData:List[MigrationData],
                            segmentTotals:List[SegmentTotals]
                           )

case class MigrationData(
                        fromSegment:String,
                        currentSegment:String,
                        migrationCount:Long
                        )

case class SegmentTotals(
                        bestTotalCount:Long,
                        bestNewCount:Long,
                        risingTotalCount:Long,
                        risingNewCount:Long,
                        middleTotalCount:Long,
                        middleNewCount:Long,
                        lapsingTotalCount:Long,
                        lapsingNewCount:Long,
                        deeplyTotalCount:Long,
                        deeplyNewCount:Long
                        )

case class MigrationResults(
                             minDate:String,
                             maxDate:String,
                             numRecords:Long,
                             timePeriodLength:Long,
                             timePeriods:Option[List[MigrationResultsTimePeriod]]
                           )

case class MigrationResultsReturn(
                                   isError: String,
                                   data: MigrationResults,
                                   developerMessage:String,
                                   userMessage:String,
                                   moreInfo:String,
                                   responseCode:Int,
                                   httpStatusCode:Int,
                                   errors:String
                                 )