package com.simple.study.domain.member.domain.storage.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.simple.study.domain.member.domain.Member
import com.simple.study.domain.member.domain.QMember.member
import com.simple.study.domain.member.domain.QSocial.social
import com.simple.study.domain.member.domain.SocialType
import org.springframework.stereotype.Repository

@Repository
class MemberQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : MemberQueryRepository {

    override fun findMemberBySocialIdAndSocialType(socialId: String, socialType: SocialType): Member? =
        queryFactory.selectFrom(member)
            .innerJoin(member.socials, social)
            .where(
                social.socialInfo.socialId.eq(socialId),
                social.socialInfo.socialType.eq(socialType)
            ).fetchOne()
}