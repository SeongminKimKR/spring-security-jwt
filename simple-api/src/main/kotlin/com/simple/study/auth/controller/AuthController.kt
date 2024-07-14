package com.simple.study.auth.controller

import com.simple.study.auth.dto.request.AuthRequest
import com.simple.study.auth.dto.request.CommonSignUpRequest
import com.simple.study.auth.dto.response.SignInResponse
import com.simple.study.auth.service.AuthServiceFinder
import com.simple.study.auth.service.CommonAuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원가입 및 로그인")
@RestController
class AuthController (
    private val authServiceFinder :AuthServiceFinder,
    private val commonAuthService: CommonAuthService
        ){

    @Operation(summary = "소셜 로그인/회원가입 또는 일반 로그인")
    @PostMapping("/auth")
    fun socialAuthenticate(@Valid @RequestBody request: AuthRequest): SignInResponse{
        val authService = authServiceFinder.getService(request.socialType)
        return authService.authentication(request)
    }

    @Operation(summary = "일반 회원 회원가입")
    @PostMapping("/common-signup")
    fun createCommonMember(@Valid @RequestBody request: CommonSignUpRequest): SignInResponse {
        return commonAuthService.signUp(request)
    }
}