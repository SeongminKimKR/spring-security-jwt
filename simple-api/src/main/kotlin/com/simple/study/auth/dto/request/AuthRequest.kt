package com.simple.study.auth.dto.request

import com.simple.study.common.annotation.ValidEnum
import com.simple.study.domain.member.domain.Gender
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

private const val USER_ID_PATTERN = "[a-z0-9_]{4,20}$"
private const val PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\",./<>?|\\\\`~])[a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{};':\",./<>?|\\\\`~]{8,20}\$"
private const val NICK_PATTERN = "[a-zA-Z0-9가-힣!@#$%^&*()-+=\\[\\]{};':\",./<>?|\\\\ㄱ-ㅎㅏ-ㅣ_ ]{2,20}$"
private const val NAME_PATTERN = "^[a-zA-Z가-힣 ]{1,20}$"
private const val BIRTH_DATE_PATTERN = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$"

private const val USER_ID_MESSAGE = "영어 소문자, 숫자, 언더바만 가능하며, 4~20자리로 입력해주세요."
private const val PASSWORD_MESSAGE = "영어, 숫자, 특정 특수문자를 포함한 8~20자리로 입력해주세요."
private const val NICK_MESSAGE = "영어, 한글, 숫자, 특정 특수문자만 가능하며, 2~20자리로 입력해주세요."
private const val NAME_MESSAGE = "영문, 한글만 가능하며, 1~20자리로 입력해주세요."
private const val BIRTH_DATE_MESSAGE = "날짜 형식(YYYY-MM-DD)을 확인해주세요."
private const val GENDER_MESSAGE = "남자, 여자 중 하나를 선택해주세요."

data class CommonSignUpRequest(
    @field:NotBlank
    @field:Pattern(regexp = USER_ID_PATTERN, message = USER_ID_MESSAGE)
    val userId: String,

    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    @field:Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_MESSAGE)
    val password: String,

    @field:NotBlank
    @field:Pattern(regexp = NICK_PATTERN, message = NICK_MESSAGE)
    val nickname: String,

    @field:NotBlank
    @field:Pattern(regexp = NAME_PATTERN, message = NAME_MESSAGE)
    val name: String,

    @field:NotNull
    @field:ValidEnum(message = GENDER_MESSAGE, enumClass = Gender::class)
    val gender: Gender,

    @field:Pattern(regexp = BIRTH_DATE_PATTERN, message = BIRTH_DATE_MESSAGE)
    val birthDate: String?,

    @field:NotBlank
    val emailVerificationToken: String,
    )

data class Oauth2SignUpRequest(
    @field:NotBlank
    val token: String,

    @field:NotBlank
    @field:Pattern(regexp = NICK_PATTERN, message = NICK_MESSAGE)
    val nickname: String,

    @field:NotBlank
    @field:Pattern(regexp = NAME_PATTERN, message = NAME_MESSAGE)
    val name: String,

    @field:NotNull
    @field:ValidEnum(message = GENDER_MESSAGE, enumClass = Gender::class)
    val gender: Gender,

    @field:Pattern(regexp = BIRTH_DATE_PATTERN, message = BIRTH_DATE_MESSAGE)
    val birthDate: String?,
)

data class CommonAuthRequest(
    @field:NotBlank
    val userId: String,

    @field:NotBlank
    val password: String,
)

data class OAuth2AuthRequest(
    @field:NotBlank
    val token: String
)