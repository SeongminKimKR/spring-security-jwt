package com.simple.study.auth.dto.request

import com.simple.study.domain.member.domain.SocialType
import jakarta.validation.constraints.NotBlank

data class CommonSignUpRequest(
    @NotBlank
    val socialType: SocialType,
    @NotBlank
    val account: String,
    @NotBlank
    val password: String,
    @NotBlank
    val nickname: String,
    )

data class AuthRequest(
    @NotBlank
    val socialType: SocialType,
    @NotBlank
    val account: String,
    @NotBlank
    val password: String,
    )

data class SocialAuthRequest(

    @NotBlank
    val socialId: String,
)