package com.simple.study.auth.dto.request

import com.simple.study.domain.member.domain.Gender
import com.simple.study.domain.member.domain.SocialType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CommonSignUpRequest(
    @NotBlank
    val socialType: SocialType,
    @NotBlank
    val userId: String,
    @NotBlank
    val email: String,
    @NotBlank
    val password: String,
    @NotBlank
    val nickname: String,
    @NotBlank
    val name: String,
    @NotNull
    val gender: Gender
    )

data class AuthRequest(
    @NotBlank
    val socialType: SocialType,
    @NotBlank
    val userId: String,
    @NotBlank
    val password: String,
    )

data class SocialAuthRequest(

    @NotBlank
    val socialId: String,
)