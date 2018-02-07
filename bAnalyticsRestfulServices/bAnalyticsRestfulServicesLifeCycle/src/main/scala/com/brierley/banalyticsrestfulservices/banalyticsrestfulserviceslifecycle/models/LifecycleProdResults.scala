package com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.models

case class LifeCycleProdResultsTimePeriod( timePeriod:Int,
                                 productCat:String,
                                 prodTotalSales:Double,
                                 prodPercentSales:Double,
                                 totalSales:Double,
                                 lifeCycleProdMetrics: LifeCycleProdMetrics
                               )


case class LifeCycleProdMetrics(
                               bestProdSales:Double,
                               bestTotalSales:Double,
                               bestPercentSales:Double,
                               bestIndex:Double,
                               risingProdSales:Double,
                               risingTotalSales:Double,
                               risingPercentSales:Double,
                               risingIndex:Double,
                               middleProdSales:Double,
                               middleTotalSales:Double,
                               middlePercentSales:Double,
                               middleIndex:Double,
                               lapsingProdSales:Double,
                               lapsingTotalSales:Double,
                               lapsingPercentSales:Double,
                               lapsingIndex:Double,
                               deeplyProdSales:Double,
                               deeplyTotalSales:Double,
                               deeplyPercentSales:Double,
                               deeplyIndex:Double
                               )

case class LifeCycleProdResults(
                             minDate:String,
                             maxDate:String,
                             numRecords:Long,
                             timePeriodLength:Long,
                             timePeriods:Option[List[LifeCycleProdResultsTimePeriod]]
                           )

case class LifeCycleProdResultsReturn(
                                   isError: String,
                                   data: LifeCycleProdResults,
                                   developerMessage:String,
                                   userMessage:String,
                                   moreInfo:String,
                                   responseCode:Int,
                                   httpStatusCode:Int,
                                   errors:String
                                 )