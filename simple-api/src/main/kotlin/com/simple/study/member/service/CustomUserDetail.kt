package com.simple.study.member.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomUserDetail(
    private val userId: String,
    private val password: String?,
    private val authorities: Collection<GrantedAuthority>,
    private val oAuth2Attributes: Map<String, Any>?,
    val email: String,
    val nickname: String,
) : UserDetails, OAuth2User {
    constructor(
        userId: String,
        password: String?,
        email: String,
        nickname: String,
        authorities: Collection<GrantedAuthority>,
    ) : this(userId, password, authorities, null, email, nickname)

    constructor(
        userId: String,
        nickname: String,
        email: String,
        authorities: Collection<SimpleGrantedAuthority>,
        oAuth2Attributes: Map<String, Any>,
    ) : this(userId, null, authorities, oAuth2Attributes, email, nickname)

    override fun getName() = username

    override fun getAttributes() = oAuth2Attributes

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getPassword() = password

    override fun getUsername() = userId

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}