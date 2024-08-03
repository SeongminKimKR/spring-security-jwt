package com.simple.study.auth.dto.request

import com.simple.study.domain.member.domain.Gender
import com.simple.study.domain.member.domain.SocialType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

abstract class SignUpRequest(
    open val socialType: SocialType
)
data class CommonSignUpRequest(
    override val socialType: SocialType = SocialType.NONE,
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
    val gender: Gender,
    @NotBlank
    val emailVerificationToken: String,
    ) : SignUpRequest(socialType)

data class SocialAuthRequest(
    override val socialType: SocialType = SocialType.NONE,
    @NotBlank
    val socialId: String,
) : SignUpRequest(socialType)

data class AuthRequest(
    @NotBlank
    val socialType: SocialType,
    @NotBlank
    val userId: String,
    @NotBlank
    val password: String,
    )
