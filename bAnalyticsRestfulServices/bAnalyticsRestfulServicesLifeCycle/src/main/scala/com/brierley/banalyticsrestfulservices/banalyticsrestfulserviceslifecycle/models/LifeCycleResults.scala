package com.brierley.banalyticsrestfulservices.banalyticsrestfulserviceslifecycle.models

case class LifeCycleResultsTimePeriod
                          (
                           timePeriod:Int,
                           totalCustCount:Long,
                           totalTxnCount:Long,
                           totalSales:Double,
                           totalItems:Long,
                           lifeCycleResultsBest:LifeCycleResultsBest,
                           lifeCycleResultsRising:LifeCycleResultsRising,
                           lifeCycleResultsMiddle:LifeCycleResultsMiddle,
                           lifeCycleResultsLapsing:LifeCycleResultsLapsing,
                           lifeCycleResultsDeeply:LifeCycleResultsDeeply
                           )


case class LifeCycleResultsBest(
                               bestCustTotal:Long,
                               bestTxnTotal:Long,
                               bestSalesTotal:Double,
                               bestRecencyTotal:Long,
                               bestItemTotal:Long,
                               bestPercentCustBase:Double,
                               bestPercentTxnBase:Double,
                               bestPercentSalesBase:Double,
                               bestAvgFreq:Double,
                               bestAvgRecency:Double,
                               bestAvgSales:Double,
                               bestAvgItems:Double,
                               bestVisitSpend:Double
                               )

case class LifeCycleResultsRising(
                                 risingCustTotal:Long,
                                 risingTxnTotal:Long,
                                 risingSalesTotal:Double,
                                 risingRecencyTotal:Long,
                                 risingItemTotal:Long,
                                 risingPercentCustBase:Double,
                                 risingPercentTxnBase:Double,
                                 risingPercentSalesBase:Double,
                                 risingAvgFreq:Double,
                                 risingAvgRecency:Double,
                                 risingAvgSales:Double,
                                 risingAvgItems:Double,
                                 risingVisitSpend:Double
                               )

case class LifeCycleResultsMiddle(
                                   middleCustTotal:Long,
                                   middleTxnTotal:Long,
                                   middleSalesTotal:Double,
                                   middleRecencyTotal:Long,
                                   middleItemTotal:Long,
                                   middlePercentCustBase:Double,
                                   middlePercentTxnBase:Double,
                                   middlePercentSalesBase:Double,
                                   middleAvgFreq:Double,
                                   middleAvgRecency:Double,
                                   middleAvgSales:Double,
                                   middleAvgItems:Double,
                                   middleVisitSpend:Double
                                 )

case class LifeCycleResultsLapsing(
                                   lapsingCustTotal:Long,
                                   lapsingTxnTotal:Long,
                                   lapsingSalesTotal:Double,
                                   lapsingRecencyTotal:Long,
                                   lapsingItemTotal:Long,
                                   lapsingPercentCustBase:Double,
                                   lapsingPercentTxnBase:Double,
                                   lapsingPercentSalesBase:Double,
                                   lapsingAvgFreq:Double,
                                   lapsingAvgRecency:Double,
                                   lapsingAvgSales:Double,
                                   lapsingAvgItems:Double,
                                   lapsingVisitSpend:Double
                                 )

case class LifeCycleResultsDeeply(
                                   deeplyCustTotal:Long,
                                   deeplyTxnTotal:Long,
                                   deeplySalesTotal:Double,
                                   deeplyRecencyTotal:Long,
                                   deeplyItemTotal:Long,
                                   deeplyPercentCustBase:Double,
                                   deeplyPercentTxnBase:Double,
                                   deeplyPercentSalesBase:Double,
                                   deeplyAvgFreq:Double,
                                   deeplyAvgRecency:Double,
                                   deeplyAvgSales:Double,
                                   deeplyAvgItems:Double,
                                   deeplyVisitSpend:Double
                                 )
case class LifeCycleResults(
                             minDate:String,
                             maxDate:String,
                             numRecords:Long,
                             timePeriodLength:Long,
                             timePeriods:Option[List[LifeCycleResultsTimePeriod]]
                           )

case class LifeCycleResultsReturn(
                                    isError: String,
                                    data: LifeCycleResults,
                                    developerMessage:String,
                                    userMessage:String,
                                    moreInfo:String,
                                    responseCode:Int,
                                    httpStatusCode:Int,
                                    errors:String
                                 )