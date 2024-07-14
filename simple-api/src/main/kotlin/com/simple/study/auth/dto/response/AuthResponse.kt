package com.simple.study.auth.dto.response

data class SignInResponse (
        val memberId: Long,
        val accessToken: String,
        val refreshToken: String
        )
