package com.simple.study.auth.service

import com.simple.study.auth.dto.request.AuthRequest
import com.simple.study.auth.dto.request.CommonSignUpRequest
import com.simple.study.auth.dto.request.SignUpRequest
import com.simple.study.auth.dto.response.SignInResponse
import com.simple.study.auth.dto.response.SignUpResponse
import com.simple.study.domain.member.domain.Member
import com.simple.study.domain.member.domain.storage.jpa.MemberRepository
import com.simple.study.jwt.TokenProvider
import com.simple.study.mail.service.MailService
import com.simple.study.redis.repository.RefreshTokenRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CommonAuthService(
    private val mailService: MailService,
    private val memberRepository: MemberRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: TokenProvider,
) : AuthService {

    override fun authentication(request: AuthRequest): SignInResponse {
        val member = memberRepository.findByUserId(request.userId)

        requireNotNull(member) { "존재하지 않는 계정입니다." }
        require(member.password == passwordEncoder.encode(request.password)) {"아이디 또는 패스워드가 일치하지 않습니다."}

        val authenticationToken = UsernamePasswordAuthenticationToken(request.userId, request.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        val token = tokenProvider.createToken(authentication)

        refreshTokenRepository.save(token.refreshToken, request.userId)

        return SignInResponse(member.id, token.accessToken,token.refreshToken)
    }

    override fun signUp(request: SignUpRequest): SignUpResponse {
        require(request is CommonSignUpRequest) {"올바른 형태의 가입 신청이 아닙니다. ${request.socialType}"}

        require(mailService.isCompletedVerification(request.emailVerificationToken)) {"이메일 인증이 완료되지 않았습니다."}

        val member = Member.newInstance(
            userId = request.userId,
            email = request.email,
            socialType = request.socialType,
            nickname = request.nickname,
            password = passwordEncoder.encode(request.password),
            gender = request.gender,
            name = request.name,
            role = request.role
        )

        memberRepository.save(member)

        return SignUpResponse(member.id)
    }
}
