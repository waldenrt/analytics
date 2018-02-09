package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.models

case class QuantileMigrationData(fromQuantile:Int, currentQuantile:Int, migrationCount:Long)
case class QuantileTotals(quantile:Int, newCount:Long)

case class QuantileMigrationResults(timePeriod:Int,
                                    migrationData: List[QuantileMigrationData],
                                    quantileTotals: List[QuantileTotals])

case class QuantileMigrationView(quantileDimension: QuantileDimension,
                                 quantileMigration: List[QuantileMigrationResults])

case class QuantileMigrationReturn(isError: String,
                                   data: QuantileMigrationView,
                                   developerMessage: String,
                                   userMessage: String,
                                   moreInfo: String,
                                   responseCode: Int,
                                   httpStatusCode: Int,
                                   errors: String)
