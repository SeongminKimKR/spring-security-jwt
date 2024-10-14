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
import com.simple.study.jwt.TokenInfo
import com.simple.study.jwt.TokenProvider
import com.simple.study.mail.service.MailService
import com.simple.study.redis.repository.RefreshTokenRepository
import com.simple.study.redis.repository.SocialAuthTokenRepositoryRedis
import jakarta.servlet.http.HttpServletRequest
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
        require(member.password == passwordEncoder.encode(request.password)) { "아이디 또는 패스워드가 일치하지 않습니다." }
        val authenticationToken = UsernamePasswordAuthenticationToken(request.userId, request.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        val token = tokenProvider.createToken(authentication)

        refreshTokenRepository.save(token.refreshToken, request.userId)

        return SignInResponse(member.userId, token.accessToken, token.refreshToken)
    }

    fun signUp(request: CommonSignUpRequest): SignUpResponse {

        checkDuplicateNickname(request.nickname)
        require(mailService.isCompletedVerification(request.emailVerificationToken)) { "이메일 인증이 완료되지 않았습니다." }

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

    fun oAuth2SingUp(request: Oauth2SignUpRequest, httpRequest: HttpServletRequest): TokenInfo {

        val userId = socialAuthTokenRepositoryRedis.findBySocialToken(request.token)
            ?: throw IllegalArgumentException("존재하지 않는 토큰 입니다.")
        val member = memberRepository.findByUserIdAndIsSocialGuest(userId, true)
            ?: throw IllegalArgumentException("존재하지 않는 회원입니다.")

        checkDuplicateNickname(request.nickname)
        member.createMemberAfterAuthorizeSocial(request.nickname, request.name, request.gender)
        memberRepository.save(member)
        socialAuthTokenRepositoryRedis.deleteBySocialToken(request.token)

        return loginForOAuth2(member, httpRequest)
    }

    fun loginForOAuth2(member: Member, httpRequest: HttpServletRequest): TokenInfo {
        val createToken: TokenInfo = tokenProvider.createTokenForOAuth2(member)
        createRefreshTokenProcess(httpRequest, member.userId, createToken)

        return createToken
    }

    fun oAuth2Authentication(request: OAuth2AuthRequest, httpRequest: HttpServletRequest): TokenInfo {
        val member = checkSocialToken(request.token)
        socialAuthTokenRepositoryRedis.deleteBySocialToken(request.token)
        return loginForOAuth2(member, httpRequest)
    }

    private fun createRefreshTokenProcess(request: HttpServletRequest, userId: String, createToken: TokenInfo) {
        TODO()
    }

    private fun checkDuplicateNickname(nickname: String): Boolean {
        require(memberRepository.findByNickname(nickname) == null) {
            "중복된 닉네임입니다."
        }
        return true
    }

    private fun checkSocialToken(token: String): Member {
        val userId: String = socialAuthTokenRepositoryRedis.findBySocialToken(token)
            ?: throw IllegalArgumentException("만료 시간이 지났습니다. 다시 시도해주세요.")

        return memberRepository.findByUserIdAndIsSocialGuest(userId, false)
            ?: throw IllegalArgumentException("존재하지 않는 회원입니다.")
    }
}
