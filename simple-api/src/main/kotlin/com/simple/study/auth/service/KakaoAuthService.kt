package com.simple.study.auth.service

import com.simple.study.auth.dto.request.AuthRequest
import com.simple.study.auth.dto.response.SignInResponse
import com.simple.study.domain.member.domain.storage.jpa.MemberRepository
import org.springframework.stereotype.Service

@Service
class KakaoAuthService (
    private val memberRepository: MemberRepository,
        ): AuthService{

    override fun authentication(request: AuthRequest): SignInResponse {
        TODO("Not yet implemented")
    }
}