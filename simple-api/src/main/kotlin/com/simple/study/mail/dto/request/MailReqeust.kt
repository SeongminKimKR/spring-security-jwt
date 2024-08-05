package com.simple.study.mail.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class VerifyEmailForSignUpRequest(

    @JsonProperty("email")
    @NotBlank
    @Email
    val email:String,
)

data class CheckEmailForSignUpRequest(
    @NotBlank
    val token:String,

    @NotBlank
    val code:String,
)

data class SendMailRequest(
    val subject: String,
    val email: String,
    val body: String
)