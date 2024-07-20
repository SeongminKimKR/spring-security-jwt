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
class CommonAuthService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: TokenProvider,
) : AuthService {

    override fun authentication(request: AuthRequest): SignInResponse {
        val member = memberRepository.findByUserId(request.userId)

        requireNotNull(member) { "존재하지 않는 계정입니다." }
        require(member.password == passwordEncoder.encode(request.password)) {"아이디 또는 패스워드가 일치하지 않습니다."}

        val token = tokenProvider.createToken("{$member.id}:${request.socialType}")

        return SignInResponse(member.id, token)
    }

    fun signUp(request: CommonSignUpRequest): SignInResponse {
        val member = Member.newInstance(
            userId = request.userId,
            email = request.email,
            socialType = request.socialType,
            nickname = request.nickname,
            password = passwordEncoder.encode(request.password),
            gender = request.gender,
            name = request.name
        )

        memberRepository.save(member)

        val token = tokenProvider.createToken("{$member.id}:${request.socialType}")

        return SignInResponse(member.id, token)
    }
}
