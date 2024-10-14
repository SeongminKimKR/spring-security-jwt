package com.simple.study.auth.dto.response

data class SignInResponse(
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
)

data class SignUpResponse(
    val memberId: Long,
)
