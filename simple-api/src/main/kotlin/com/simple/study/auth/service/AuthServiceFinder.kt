package com.simple.study.auth.service

import com.simple.study.domain.member.domain.SocialType
import jakarta.annotation.PostConstruct
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Component
import java.util.*

private val authServiceMap: MutableMap<SocialType, AuthService> = EnumMap(SocialType::class.java)

