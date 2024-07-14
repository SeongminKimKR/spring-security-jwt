package com.simple.study.domain.member.domain

import com.simple.study.domain.common.domain.BaseEntity
import jakarta.persistence.*

@Table(name = "member")
@Entity
class Member(

    @Column(nullable = false, scale = 20, unique = true)
    val account: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false, length = 20)
    var nickname: String,
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
            account: String,
            socialType: SocialType,
            password: String,
            nickname: String,
        ) :Member {
            val member = Member(
            account = account,
            password = password,
            nickname = nickname,
            )
            member.addSocial(account, socialType)

            return member
        }

    }
}