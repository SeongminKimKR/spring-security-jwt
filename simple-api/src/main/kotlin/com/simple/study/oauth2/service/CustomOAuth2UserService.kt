package com.simple.study.oauth2.service

import com.simple.study.domain.member.domain.Member
import com.simple.study.domain.member.domain.Role
import com.simple.study.domain.member.domain.Social
import com.simple.study.domain.member.domain.storage.jpa.MemberRepository
import com.simple.study.member.service.CustomUserDetail
import com.simple.study.oauth2.OAuth2UserInfoFactory
import com.simple.study.oauth2.userinfo.OAuth2UserInfo
import com.simple.study.util.RandomUtil
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomOAuth2UserService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        requireNotNull(userRequest) { "userRequest is null" }

        /**
         * OAuth 유저 정보 파싱
         */
        val defaultOAuth2UserService = DefaultOAuth2UserService()
        val oAuth2User = defaultOAuth2UserService.loadUser(userRequest)
        val registrationId = userRequest.clientRegistration.registrationId
        val userNameAttributeName =
            userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
        val social = getSocial(registrationId)
        val attributes = oAuth2User.attributes
        val oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(social, userNameAttributeName, attributes)

        /**
         * 어플리케이션 멤버 조회 또는 생성
         */
        val member = getOrSaveMember(oAuth2UserInfo, social)

        return CustomUserDetail(
            userId = member.userId,
            nickname = member.nickname,
            email = member.email,
            authorities = listOf(SimpleGrantedAuthority("ROLE_${member.role}")),
            oAuth2Attributes = attributes,
        )
    }

    private fun getOrSaveMember(oAuth2UserInfo: OAuth2UserInfo, social: Social) =
        memberRepository.findByUserIdAndSocial(oAuth2UserInfo.id, social)
            ?: saveSocialMember(oAuth2UserInfo, social)

    private fun getSocial(registrationId: String) =
        when (registrationId) {
            "google" -> Social.GOOGLE
            "naver" -> Social.NAVER
            "kakao" -> Social.KAKAO
            else -> throw IllegalArgumentException("Unsupported registrationId: $registrationId")
        }

    private fun saveSocialMember(oAuthUserInfo: OAuth2UserInfo, social: Social): Member {
        val findMember = memberRepository.findByEmail(oAuthUserInfo.email)

        if (findMember != null) {
            if(findMember.isSocialGuest) {
                memberRepository.save(findMember)

            }else {
                memberRepository.deleteByEmail(oAuthUserInfo.email)
                memberRepository.flush()
            }
        }

        val createMember = oAuthUserInfo.toMember(social)
        memberRepository.save(createMember)

        return createMember
    }

    fun OAuth2UserInfo.toMember(social: Social): Member = Member(
        userId = social.name.lowercase(Locale.getDefault()) + "_" + RandomUtil.getRandomString(12),
        password = passwordEncoder.encode(RandomUtil.getRandomString(12)),
        email = email,
        nickname = nickname + "_" + RandomUtil.getRandomString(12),
        role = Role.MEMBER,
        name = social.name + RandomUtil.getRandomString(10)
    )
}