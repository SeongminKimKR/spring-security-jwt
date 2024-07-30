package com.simple.study.mail.dto.response

data class VerifyEmailForSignUpResponse(
    val token: String,
)

data class CheckEmailForSignUpResponse(
    val success: Boolean,
)