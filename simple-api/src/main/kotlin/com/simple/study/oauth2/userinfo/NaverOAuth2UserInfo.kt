package com.simple.study.oauth2.userinfo

class NaverOAuth2UserInfo(
    attributes: Map<String, Any>,
    nameAttributeKey: String,
) : OAuth2UserInfo(attributes, nameAttributeKey) {
    override val id: String
        get() {
            val response = attributes["response"] as Map<String, Any> ?: return ""
            return response["id"] as String
        }
    override val nickname: String
        get() {
            val response = attributes["response"] as Map<String, Any> ?: return ""
            return response["nickname"] as String
        }
    override val email: String
        get() {
            val response = attributes["response"] as Map<String, Any> ?: return ""
            return response["email"] as String
        }
    override val imageUrl: String
        get() {
            val response = attributes["response"] as Map<String, Any> ?: return ""
            return response["profile_image"] as String
        }
}