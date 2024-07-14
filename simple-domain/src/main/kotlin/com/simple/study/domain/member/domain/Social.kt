package com.simple.study.domain.member.domain

import com.simple.study.domain.common.domain.BaseEntity
import jakarta.persistence.*

@Table(name = "account")
@Entity
class Social(
    @Embedded
    val socialInfo: SocialInfo,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,
) : BaseEntity() {

    companion object {
        fun of(member: Member, socialId: String, socialType: SocialType): Social {
            return Social(
                member = member,
                socialInfo = SocialInfo.of(socialType, socialId)
            )
        }
    }
}