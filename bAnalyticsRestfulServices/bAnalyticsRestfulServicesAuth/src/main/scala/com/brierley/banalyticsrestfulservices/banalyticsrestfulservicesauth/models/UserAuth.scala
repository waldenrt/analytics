package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.models

case class UserAuth(userId:String, userPassword:String)

case class UserSignUp(firstName:String,
                      lastName:String,
                      emailAddress:String,
                      powerUser:Boolean,
                      admin:Boolean,
                      userPassword:String,
                      clients:List[ClientInfo])

case class ClientInfo(name:String, enabled:Boolean)

case class User(firstName:String,
                lastName:String,
                emailAddress:String,
                powerUser:Boolean,
                admin:Boolean,
                clients:List[ClientInfo],
                tokens:Token)

case class Token(authToken: String,
                 refreshToken: String)

case class UserAuthReturn(  isError: String,
                              data: User,
                              developerMessage:String,
                              userMessage:String,
                              moreInfo:String,
                              responseCode:Int,
                              httpStatusCode:Int,
                              errors:String
                           )

case class UserSignUpReturn(  isError: String,
                              data: UserSignUp,
                              developerMessage:String,
                              userMessage:String,
                              moreInfo:String,
                              responseCode:Int,
                              httpStatusCode:Int,
                              errors:String
                         )