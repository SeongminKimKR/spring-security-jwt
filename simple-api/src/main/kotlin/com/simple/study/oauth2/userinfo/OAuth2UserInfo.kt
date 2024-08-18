package com.simple.study.oauth2.userinfo

abstract class OAuth2UserInfo(
    val attributes: Map<String, Any>,
    val nameAttributeKey:String
) {
    abstract val id: String
    abstract val nickname: String
    abstract val email: String
    abstract val imageUrl: String?
}