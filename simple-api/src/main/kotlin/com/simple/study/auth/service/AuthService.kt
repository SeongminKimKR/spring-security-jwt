package com.simple.study.auth.service

import com.simple.study.auth.dto.request.CommonAuthRequest
import com.simple.study.auth.dto.request.CommonSignUpRequest
import com.simple.study.auth.dto.request.OAuth2AuthRequest
import com.simple.study.auth.dto.request.Oauth2SignUpRequest
import com.simple.study.auth.dto.response.SignInResponse
import com.simple.study.auth.dto.response.SignUpResponse
import com.simple.study.domain.member.domain.Member
import com.simple.study.domain.member.domain.Role
import com.simple.study.domain.member.domain.Social
import com.simple.study.domain.member.domain.storage.jpa.MemberRepository
import com.simple.study.jwt.TokenProvider
import com.simple.study.mail.service.MailService
import com.simple.study.redis.repository.RefreshTokenRepository
import com.simple.study.redis.repository.SocialAuthTokenRepositoryRedis
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val mailService: MailService,
    private val memberRepository: MemberRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val socialAuthTokenRepositoryRedis: SocialAuthTokenRepositoryRedis,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: TokenProvider,
) {

    fun authentication(request: CommonAuthRequest): SignInResponse {
        val member = memberRepository.findByUserId(request.userId)

        requireNotNull(member) { "존재하지 않는 계정입니다." }
        require(member.password == passwordEncoder.encode(request.password)) {"아이디 또는 패스워드가 일치하지 않습니다."}
        val authenticationToken = UsernamePasswordAuthenticationToken(request.userId, request.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        val token = tokenProvider.createToken(authentication)

        refreshTokenRepository.save(token.refreshToken, request.userId)

        return SignInResponse(member.id, token.accessToken,token.refreshToken)
    }

    fun signUp(request: CommonSignUpRequest): SignUpResponse {

        require(mailService.isCompletedVerification(request.emailVerificationToken)) {"이메일 인증이 완료되지 않았습니다."}

        val member = Member.newInstance(
            userId = request.userId,
            email = request.email,
            social = Social.NONE,
            nickname = request.nickname,
            password = passwordEncoder.encode(request.password),
            gender = request.gender,
            name = request.name,
            role = Role.MEMBER
        )

        memberRepository.save(member)

        return SignUpResponse(member.id)
    }

    fun oauth2SingUp(request: Oauth2SignUpRequest): SignUpResponse {
        val member = socialAuthTokenRepositoryRedis.findBySocialToken(request.token)
        TODO("Not yet implemented")
    }

    fun oauth2Authentication(request: OAuth2AuthRequest): SignInResponse {
        TODO("Not yet implemented")
    }


}
