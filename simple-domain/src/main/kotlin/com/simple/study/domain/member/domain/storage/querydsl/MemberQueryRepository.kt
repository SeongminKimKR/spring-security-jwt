package com.simple.study.domain.member.domain.storage.querydsl

import com.simple.study.domain.member.domain.Member
import com.simple.study.domain.member.domain.SocialType

interface MemberQueryRepository {

    fun findMemberBySocialIdAndSocialType(socialId: String, socialType: SocialType): Member?
}