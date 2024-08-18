package com.simple.study.auth.controller

import com.simple.study.auth.dto.request.CommonAuthRequest
import com.simple.study.auth.dto.request.CommonSignUpRequest
import com.simple.study.auth.dto.request.OAuth2AuthRequest
import com.simple.study.auth.dto.request.Oauth2SignUpRequest
import com.simple.study.auth.dto.response.SignInResponse
import com.simple.study.auth.dto.response.SignUpResponse
import com.simple.study.auth.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원가입 및 로그인")
@RestController
class AuthController(
    private val authService: AuthService
) {

    @Operation(summary = "일반 로그인")
    @PostMapping("/auth/common")
    fun commonAuthenticate(@Valid @RequestBody request: CommonAuthRequest): SignInResponse {
        return authService.authentication(request)
    }

    @Operation(summary = "소셜 로그인")
    @PostMapping("/auth/social")
    fun socialAuthenticate(@Valid @RequestBody request: OAuth2AuthRequest): SignInResponse {
        return authService.oauth2Authentication(request)
    }

    @Operation(summary = "일반 회원 회원가입")
    @PostMapping("/signup/common")
    fun commonSignUp(@Valid @RequestBody request: CommonSignUpRequest): SignUpResponse {
        return authService.signUp(request)
    }

    @Operation(summary = "소셜 회원 회원가입")
    @PostMapping("/signup/oauth2")
    fun oauth2SignUp(@Valid @RequestBody request: Oauth2SignUpRequest): SignUpResponse {
        return authService.oauth2SingUp(request)
    }
    /*@Operation("로그아웃을 요청합니다")
    //@Auth
    @PostMapping("/logout")
    fun logout() {

    }*/
}