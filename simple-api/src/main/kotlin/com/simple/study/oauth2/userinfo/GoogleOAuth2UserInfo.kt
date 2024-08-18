package com.simple.study.oauth2.userinfo

class GoogleOAuth2UserInfo(
    attributes: Map<String, Any>,
    nameAttributeKey: String,
    override val id: String = attributes["sub"] as String,
    override val nickname: String = attributes["name"] as String,
    override val email: String = attributes["email"] as String,
    override val imageUrl: String = attributes["picture"] as String,
) : OAuth2UserInfo(attributes, nameAttributeKey)