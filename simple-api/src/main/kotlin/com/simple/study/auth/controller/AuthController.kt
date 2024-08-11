package com.simple.study.auth.controller

import com.simple.study.auth.dto.request.AuthRequest
import com.simple.study.auth.dto.request.SignUpRequest
import com.simple.study.auth.dto.response.SignInResponse
import com.simple.study.auth.dto.response.SignUpResponse
import com.simple.study.auth.service.AuthServiceFinder
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원가입 및 로그인")
@RestController
class AuthController(
    private val authServiceFinder: AuthServiceFinder,
) {

    @Operation(summary = "소셜 로그인 또는 일반 로그인")
    @PostMapping("/auth")
    fun authenticate(@Valid @RequestBody request: AuthRequest): SignInResponse {
        val authService = authServiceFinder.getService(request.socialType)
        return authService.authentication(request)
    }

    @Operation(summary = "회원 회원가입")
    @PostMapping("/signup")
    fun signUp(@Valid @RequestBody request: SignUpRequest): SignUpResponse {
        val authService = authServiceFinder.getService(request.socialType)
        return authService.signUp(request)
    }

    /*@Operation("로그아웃을 요청합니다")
    //@Auth
    @PostMapping("/logout")
    fun logout() {

    }*/
}