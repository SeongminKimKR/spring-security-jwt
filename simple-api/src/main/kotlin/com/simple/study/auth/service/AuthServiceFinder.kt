package com.simple.study.auth.service

import com.simple.study.domain.member.domain.SocialType
import jakarta.annotation.PostConstruct
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Component
import java.util.*

private val authServiceMap: MutableMap<SocialType, AuthService> = EnumMap(SocialType::class.java)

@Component
class AuthServiceFinder (
    private val kakaoAuthService: KakaoAuthService,
    private val commonAuthService: CommonAuthService
        ){

    @PostConstruct
    fun initAuthService() {
        authServiceMap[SocialType.KAKAO] = kakaoAuthService
        authServiceMap[SocialType.NONE] = commonAuthService
        validateInitializeAuthService()
    }

    private fun validateInitializeAuthService() {
        for ( socialType in SocialType.values()) {
            if (authServiceMap[socialType] == null) {
                throw NotFoundException()
            }
        }
    }

    fun getService(socialType: SocialType): AuthService {
        return authServiceMap[socialType]
            ?: throw NotFoundException()
    }
}