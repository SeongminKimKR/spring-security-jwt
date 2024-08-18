package com.simple.study.domain.member.domain

import com.simple.study.domain.common.domain.BaseEntity
import jakarta.persistence.*

@Table(name = "member")
@Entity
class Member(

    @Column(nullable = false, length = 30, updatable = false)
    val userId: String,

    @Column(nullable = false, scale = 20, unique = true)
    val email: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false, length = 30, unique = true)
    var nickname: String,

    @Column(nullable = false, length = 20)
    var name: String,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    var gender: Gender? = Gender.MALE,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    var role: Role? = Role.MEMBER,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    var social: Social? = Social.NONE,

    ) : BaseEntity() {

    fun update(nickname: String, password: String) {
        this.nickname = nickname
        this.password = password
    }

    companion object {

        fun newInstance(
            userId: String,
            email: String,
            password: String,
            nickname: String,
            name: String,
            gender: Gender,
            role: Role,
            social: Social,
        ): Member {

            return Member(
                userId = userId,
                email = email,
                password = password,
                nickname = nickname,
                name = name,
                gender = gender,
                role = role,
                social = social,
            )
        }

    }
}