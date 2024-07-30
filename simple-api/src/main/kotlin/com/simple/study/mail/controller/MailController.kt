package com.simple.study.mail.controller

import com.simple.study.mail.dto.request.CheckEmailForSignUpRequest
import com.simple.study.mail.dto.request.VerifyEmailForSignUpRequest
import com.simple.study.mail.dto.response.CheckEmailForSignUpResponse
import com.simple.study.mail.dto.response.VerifyEmailForSignUpResponse
import com.simple.study.mail.service.MailService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "메일 인증 관련 서드파티 관련")
@RestController
class MailController (
    private val mailService: MailService
        ) {

    @Operation(summary = "회원가입 과정에서 이메일 인증을 위한 인증번호 전송")
    @PostMapping("/mail/verify")
    fun verifyEmailVerificationForSignUp(@Valid @RequestBody request: VerifyEmailForSignUpRequest): VerifyEmailForSignUpResponse {
        return mailService.sendVerificationEmailForSignUp(request)
    }

    @Operation(summary = "이메일 인증번호 확인")
    @PostMapping("/mail/check")
    fun checkEmailVerificationForSignUp(@Valid @RequestBody request: CheckEmailForSignUpRequest): CheckEmailForSignUpResponse {
        return mailService.checkVerificationEmailForSignUp(request)
    }
}