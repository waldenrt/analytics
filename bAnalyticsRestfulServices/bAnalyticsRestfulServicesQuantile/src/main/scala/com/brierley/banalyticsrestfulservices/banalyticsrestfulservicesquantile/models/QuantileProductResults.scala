package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesquantile.models

case class QuantileProductCount(product:String, rank:Int, count:Long)
case class QuantileProductSpend(product:String, rank:Int, spend:Double)

case class QuantileProducts(timePeriod:Int,
                            anchorDate:String,
                            quantile:Int,
                            columnName:String,
                            ProductSpend: List[QuantileProductSpend],
                            ProductCount: List[QuantileProductCount])

case class QuantileProductView(quantileDimension: QuantileDimension,
                               quantileProducts: List[QuantileProducts])


case class QuantileProductsReturn(isError: String,
                                  data: QuantileProductView,
                                  developerMessage: String,
                                  userMessage: String,
                                  moreInfo: String,
                                  responseCode: Int,
                                  httpStatusCode: Int,
                                  errors: String)
