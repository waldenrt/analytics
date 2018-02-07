package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesbalor.models

case class BalorSummary(minDateBalor: String,
                        maxDateBalor: String,
                        numRecords: Long,
                        singleVisit: Long,
                        totalCusts: Long,
                        normalizedCadence: String,
                        timePeriods: Option[List[TimePeriod]])

case class TimePeriod(timePeriod: Int,
                      anchorDate: Option[String],
                      newCustCount: Long,
                      newTxnCount: Long,
                      newTxnAmt: Double,
                      reactCustCount: Long,
                      reactTxnCount: Long,
                      reactTxnAmt: Double,
                      returnCustCount: Long,
                      returnTxnCount: Long,
                      returnTxnAmt: Double,
                      lapsedCustCount: Long,
                      lapsedTxnCount: Long,
                      lapsedTxnAmt: Double,
                      custBalor: Double,
                      txnBalor: Double,
                      spendBalor: Double,
                      retention: RetentionTimePeriodFull)

case class BalorSummaryReturn(isError: String,
                              data: BalorSummary,
                              developerMessage: String,
                              userMessage: String,
                              moreInfo: String,
                              responseCode: Int,
                              httpStatusCode: Int,
                              errors: String)

case class BalorResponse(balorSummaryReturn: BalorSummaryReturn)

//Balor Segment Case Classes
case class BalorSegment(minDateBalor: String,
                        maxDateBalor: String,
                        numRecords: Long,
                        singleVisit: Long,
                        totalCust: Long,
                        normalizedCadence: String,
                        timePeriods: Option[List[TimePeriodFull]])

case class NewCustTimePeriodFull(newCustCount: Long,
                                 newTxnCount: Long,
                                 newTxnAmt: Double,
                                 newItemQty: Long,
                                 newDiscAmt: Double,
                                 newCustSpendAvg: Double,
                                 newCustVisitAvg: Double,
                                 newCustItemAvg: Double,
                                 newCustDiscAvg: Double,
                                 newVisitSpendAvg: Double,
                                 newVisitDiscAvg: Double,
                                 newVisitItemAvg: Double
                                )

case class ReactCustTimePeriodFull(reactCustCount: Long,
                                   reactTxnCount: Long,
                                   reactTxnAmt: Double,
                                   reactItemQty: Long,
                                   reactDiscAmt: Double,
                                   reactCustSpendAvg: Double,
                                   reactCustVisitAvg: Double,
                                   reactCustItemAvg: Double,
                                   reactCustDiscAvg: Double,
                                   reactVisitSpendAvg: Double,
                                   reactVisitDiscAvg: Double,
                                   reactVisitItemAvg: Double
                                  )

case class ReturnCustTimePeriodFull(returnCustCount: Long,
                                    returnTxnCount: Long,
                                    returnTxnAmt: Double,
                                    returnItemQty: Long,
                                    returnDiscAmt: Double,
                                    returnCustSpendAvg: Double,
                                    returnCustVisitAvg: Double,
                                    returnCustItemAvg: Double,
                                    returnCustDiscAvg: Double,
                                    returnVisitSpendAvg: Double,
                                    returnVisitDiscAvg: Double,
                                    returnVisitItemAvg: Double
                                   )

case class LapsedCustTimePeriodFull(lapsedCustCount: Long,
                                    lapsedTxnCount: Long,
                                    lapsedTxnAmt: Double,
                                    lapsedItemQty: Long,
                                    lapsedDiscAmt: Double,
                                    lapsedCustSpendAvg: Double,
                                    lapsedCustVisitAvg: Double,
                                    lapsedCustItemAvg: Double,
                                    lapsedCustDiscAvg: Double,
                                    lapsedVisitSpendAvg: Double,
                                    lapsedVisitDiscAvg: Double,
                                    lapsedVisitItemAvg: Double
                                   )

case class RetentionTimePeriodFull(retention: Double,
                                   retentionGrowth: Option[Double],
                                   returnNewSales: Option[Double],
                                   returnNewTxn: Option[Double],
                                   returnNewCust: Option[Double],
                                   returnReactSales:Option[Double],
                                   returnReactTxn: Option[Double],
                                   returnReactCust: Option[Double],
                                   returnReturnSales: Option[Double],
                                   returnReturnTxn: Option[Double],
                                   returnReturnCust: Option[Double],
                                   ttlSalesLift: Option[Double],
                                   avgSalesLift: Option[Double],
                                   ttlTxnLift: Option[Double],
                                   avgTxnLift: Option[Double]
                                  )

case class TimePeriodFull(timePeriod: Int,
                          anchorDate: Option[String],
                          newCustInfo: NewCustTimePeriodFull,
                          reactCustInfo: ReactCustTimePeriodFull,
                          returnCustInfo: ReturnCustTimePeriodFull,
                          lapsedCustInfo: LapsedCustTimePeriodFull,
                          custBalor: Double,
                          txnBalor: Double,
                          spendBalor: Double,
                          retention: Double)

case class BalorSegmentReturn(isError: String,
                              data: BalorSegment,
                              developerMessage: String,
                              userMessage: String,
                              moreInfo: String,
                              responseCode: Int,
                              httpStatusCode: Int,
                              errors: String)

case class BalorSegmentResponse(balorSegmentReturn: BalorSegmentReturn)

//Balor Cadence Case Classes

case class FreqTable(cadence:Int,
                     frequency:Long,
                     cumFreq:Long
                    )

case class BalorCadence(numRecords:Long,
                        singleVisit:Long,
                        minDateCadence:String,
                        maxDateCadence:String,
                        rawCadence:Double,
                        normalizedCadence:String,
                        numTimePeriods:Int,
                        percentile:Double,
                        freqTable:Option[List[FreqTable]])

case class BalorCadenceReturn(isError: String,
                              data:BalorCadence,
                              developerMessage:String,
                              userMessage:String,
                              moreInfo:String,
                              responseCode:Int,
                              httpStatusCode:Int,
                              errors:String)

case class BalorCadenceResponse(balorCadenceReturn: BalorCadenceReturn)

