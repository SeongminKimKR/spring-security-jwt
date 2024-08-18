package com.simple.study.oauth2

import com.simple.study.domain.member.domain.Social
import com.simple.study.oauth2.userinfo.GoogleOAuth2UserInfo
import com.simple.study.oauth2.userinfo.KakaoOAuth2UserInfo
import com.simple.study.oauth2.userinfo.NaverOAuth2UserInfo
import com.simple.study.oauth2.userinfo.OAuth2UserInfo

object OAuth2UserInfoFactory {

    fun getOAuth2UserInfo(
        social: Social,
        userNameAttributeName: String,
        attributes: Map<String, Any>,
    ): OAuth2UserInfo {
        return when (social) {
            Social.GOOGLE -> GoogleOAuth2UserInfo(attributes, userNameAttributeName)
            Social.KAKAO -> KakaoOAuth2UserInfo(attributes, userNameAttributeName)
            Social.NAVER -> NaverOAuth2UserInfo(attributes, userNameAttributeName)
            else -> throw IllegalArgumentException("Login with $social is not supported yet.")
        }
    }
}