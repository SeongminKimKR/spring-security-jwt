package com.simple.study.domain.member.domain

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class SocialInfo(
    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    val socialType: SocialType,

    @Column(nullable = false, length = 300)
    val socialId: String,
) {

    companion object {
        fun of(socialType: SocialType, socialId: String): SocialInfo {
            return SocialInfo(
                socialType = socialType,
                socialId = socialId
            )
        }
    }
}