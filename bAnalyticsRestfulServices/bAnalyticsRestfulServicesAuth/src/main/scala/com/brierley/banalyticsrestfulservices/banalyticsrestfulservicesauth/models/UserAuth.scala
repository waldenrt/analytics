package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.models

case class UserAuth(userId:String, userPassword:String)

case class UserSignUp(firstName:String,
                      lastName:String,
                      emailAddress:String,
                      powerUser:Boolean,
                      userId:String,
                      userPassword:String)
case class ClientInfo(name:String, enabled:Boolean)
case class User(firstName:String,
                lastName:String,
                emailAddress:String,
                powerUser:Boolean,
                clients:List[ClientInfo])
case class UserAuthReturn(  isError: String,
                              data: User,
                              developerMessage:String,
                              userMessage:String,
                              moreInfo:String,
                              responseCode:Int,
                              httpStatusCode:Int,
                              errors:String
                           )