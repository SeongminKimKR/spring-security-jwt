package com.simple.study.mail.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class VerifyEmailForSignUpRequest(
    @NotBlank
    @Email
    val email:String,
)

data class CheckEmailForSignUpRequest(
    @NotBlank
    val token:String,

    @NotBlank
    val number:String,
)