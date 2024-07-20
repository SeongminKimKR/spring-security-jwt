package com.simple.study.member.service

import com.simple.study.domain.member.domain.Member
import com.simple.study.domain.member.domain.storage.jpa.MemberRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailService (
    private val memberRepository: MemberRepository
        ): UserDetailsService{
    override fun loadUserByUsername(username: String?): UserDetails {

        requireNotNull(username) { "username이 null입니다."}

        val member = memberRepository.findByUserId(username)

        requireNotNull(member)

        return member.convert()
    }

    fun Member.convert (): UserDetails =
        CustomUserDetail(
            userId = userId,
            password = password,
            email = email,
            nickname = nickname,
            authorities = mutableListOf(SimpleGrantedAuthority("ROLE_MEMBER"))
        )
}