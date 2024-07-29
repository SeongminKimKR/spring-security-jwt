package com.simple.study.jwt

data class TokenInfo(
    val userId: String,
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
)