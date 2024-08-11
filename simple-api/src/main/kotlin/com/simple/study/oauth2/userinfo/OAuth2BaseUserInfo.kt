package com.simple.study.oauth2.userinfo

abstract class OAuth2BaseUserInfo(
    val attributes: Map<String, Any>,
) {
    abstract val id: String
    abstract val nickname: String
    abstract val email: String
    abstract val imageUrl: String?
}