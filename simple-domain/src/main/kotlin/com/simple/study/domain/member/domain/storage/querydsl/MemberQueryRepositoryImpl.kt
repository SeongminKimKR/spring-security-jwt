package com.simple.study.domain.member.domain.storage.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class MemberQueryRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : MemberQueryRepository {

}