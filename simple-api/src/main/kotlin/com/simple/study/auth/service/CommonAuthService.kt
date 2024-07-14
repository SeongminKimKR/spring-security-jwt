package com.simple.study.auth.service

import com.simple.study.auth.dto.request.AuthRequest
import com.simple.study.auth.dto.request.CommonSignUpRequest
import com.simple.study.auth.dto.response.SignInResponse
import com.simple.study.domain.member.domain.Member
import com.simple.study.domain.member.domain.storage.jpa.MemberRepository
import com.simple.study.jwt.TokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CommonAuthService (
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: TokenProvider
): AuthService{

    override fun authentication(request: AuthRequest): SignInResponse {
        val member : Member = memberRepository.findByAccount(request.account)
            .takeIf { it.password == passwordEncoder.encode(request.password)}
            ?: throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.")

        val token = tokenProvider.createToken("{$member.id}:${request.socialType}")

        return SignInResponse(member.id, token)
    }

    fun signUp(request: CommonSignUpRequest): SignInResponse {
        val member = Member.newInstance(
            account = request.account,
            socialType = request.socialType,
            nickname =  request.nickname,
            password = passwordEncoder.encode(request.password)
        )

        memberRepository.save(member)

        val token = tokenProvider.createToken("{$member.id}:${request.socialType}")

        return SignInResponse(member.id, token)
    }
}
