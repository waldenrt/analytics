package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.models


case class QuantileSummaryTotals(
                                  totalCount:Long,
                                  totalVisits:Long,
                                  totalSpend:Double,
                                  totalUnits:Long,
                                  totalDisc:Double,
                                  totalCust: Option[Long]
                                )
case class QuantileSummaryAverages(
                                    avgVisits:Double,
                                    avgSpend:Double,
                                    avgUnits:Double,
                                    avgDisc:Double,
                                    avgVisitSpend:Double,
                                    avgVisitUnits:Double,
                                    avgVisitDisc:Double,
                                    avgRecency: Option[Double],
                                    avgItemSales:Double,
                                    avgItemDisc: Double,
                                    avgStoreCust: Option[Double],
                                    avgCustSales: Option[Double],
                                    avgCustVisits: Option[Double],
                                    avgCustUnits: Option[Double]
                                  )

case class QuantileResults(timePeriod:Int,
                           anchorDate: String,
                           quantile:Int,
                           summaryTotals: QuantileSummaryTotals,
                           summaryAverages: QuantileSummaryAverages
                          )



case class QuantileSummaryView(quantileDimension: QuantileDimension,
                               quantileResult: List[QuantileResults])

case class QuantileSummaryReturn(isError: String,
                                 data: QuantileSummaryView,
                                 developerMessage: String,
                                 userMessage: String,
                                 moreInfo: String,
                                 responseCode: Int,
                                 httpStatusCode: Int,
                                 errors: String)
