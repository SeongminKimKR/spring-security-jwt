package com.simple.study.domain.member.domain

enum class Social(
    val desc: String
) {
    NONE("SNS 연계 없음"),
    GOOGLE("구글"),
    NAVER("네이버"),
    KAKAO("카카오"),
}