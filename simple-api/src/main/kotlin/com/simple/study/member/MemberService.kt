package com.simple.study.member

import com.simple.study.domain.member.domain.storage.jpa.MemberRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService (
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
        ){


}