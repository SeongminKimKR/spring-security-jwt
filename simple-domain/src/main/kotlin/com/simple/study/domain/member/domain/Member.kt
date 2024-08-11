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

    @Column(nullable = false, length = 30)
    var nickname: String,

    @Column(nullable = false, length = 20)
    var name: String,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    var gender: Gender? = Gender.MALE,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    var role: Role? = Role.MEMBER,

    ) : BaseEntity() {


    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    val socials: MutableList<Social> = mutableListOf()

    fun update(nickname: String, password: String) {
        this.nickname = nickname
        this.password = password
    }

    fun addSocial(socialId: String, socialType: SocialType) {
        this.socials.add(Social.of(member = this, socialId = socialId, socialType = socialType))
    }

    companion object {

        fun newInstance(
            userId: String,
            email: String,
            socialType: SocialType,
            password: String,
            nickname: String,
            name: String,
            gender: Gender,
            role: Role
        ): Member {
            val member = Member(
                userId = userId,
                email = email,
                password = password,
                nickname = nickname,
                name = name,
                gender = gender,
                role = role
            )
            member.addSocial(email, socialType)

            return member
        }

    }
}