package com.simple.study.domain.member.domain.storage.jpa

import com.simple.study.domain.member.domain.Member
import com.simple.study.domain.member.domain.storage.querydsl.MemberQueryRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository :JpaRepository<Member, Long>, MemberQueryRepository {
    fun findByUserId(userId: String) : Member?
    fun findByEmail(email: String) : Member?
}