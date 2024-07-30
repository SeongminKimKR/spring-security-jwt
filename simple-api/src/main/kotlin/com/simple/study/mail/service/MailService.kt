package com.simple.study.mail.service

import com.simple.study.domain.member.domain.storage.jpa.MemberRepository
import com.simple.study.mail.config.MailProperties
import com.simple.study.mail.dto.request.CheckEmailForSignUpRequest
import com.simple.study.mail.dto.request.VerifyEmailForSignUpRequest
import com.simple.study.mail.dto.response.CheckEmailForSignUpResponse
import com.simple.study.mail.dto.response.VerifyEmailForSignUpResponse
import com.simple.study.redis.repository.EmailVerificationRepository
import org.springframework.stereotype.Service

@Service
class MailService (
    private val memberRepository: MemberRepository,
    private val emailVerificationRepository: EmailVerificationRepository,
    private val mailProperties: MailProperties
        ){

    fun sendVerificationEmailForSignUp(request: VerifyEmailForSignUpRequest): VerifyEmailForSignUpResponse {
        checkDuplicateEmail(request.email)

        return sendVerificationEmail(request.email)
    }

    private fun sendVerificationEmail(email: String): VerifyEmailForSignUpResponse {
        TODO("Not yet implemented")
    }

    private fun checkDuplicateEmail(email: String){
        require(memberRepository.findByEmail(email) == null) {
            "이미 존재하는 이메일 입니다."
        }
    }

    fun checkVerificationEmailForSignUp(request: CheckEmailForSignUpRequest): CheckEmailForSignUpResponse {
        TODO("Not yet implemented")
    }
}
