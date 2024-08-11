package com.simple.study.oauth2.userinfo

class KakaoOAuth2UserInfo(
    attributes: Map<String, Any>,
) : OAuth2BaseUserInfo(attributes) {
    override val id: String
        get() {
            return attributes["response"] as String
        }
    override val nickname: String
        get() {
            val account = attributes["kakao_account"] as Map<String, Any> ?: return ""
            val profile = account["profile"] as Map<String, Any> ?: return ""
            return profile["nickname"] as String
        }
    override val email: String
        get() {
            val account = attributes["kakao_account"] as Map<String, Any> ?: return ""
            return account["email"] as String
        }
    override val imageUrl: String
        get() {
            val account = attributes["kakao_account"] as Map<String, Any> ?: return ""
            val profile = account["profile"] as Map<String, Any> ?: return ""
            return profile["thumbnail_image_url"] as String
        }
}