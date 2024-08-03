package com.simple.study.redis.dto

data class EmailVerificationDto(
    val email: String = "",
    val verificationToken: String = "",
    val verificationCode: String = "",
    var attemptCount: Int = 0,
    var isVerified: Boolean = false
)
