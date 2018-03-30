package com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.utils

import com.brierley.banalyticsrestfulservices.banalyticsrestfulservicesauth.models.Token
import pdi.jwt.{JwtAlgorithm, JwtClaim, Jwt}

object JwtTokenUtil extends Config {

  private val algorithm = JwtAlgorithm.HS512

  def generateTokens(userId:String): Token = {
    val authClaim = JwtClaim(userId)
      .expiresIn(authExpiration)
      .issuedNow

    val refreshClaim = JwtClaim(userId)
      .expiresIn(refreshExpiration)
      .issuedNow

    val authToken = Jwt.encode(authClaim, secret, algorithm)
    val refreshToken = Jwt.encode(refreshClaim, secret, algorithm)

    Token(authToken = authToken,
      refreshToken = refreshToken)

  }
}
