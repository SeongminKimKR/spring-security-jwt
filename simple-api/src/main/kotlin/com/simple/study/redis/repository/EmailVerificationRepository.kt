package com.simple.study.redis.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class EmailVerificationRepository (
    private val redisTemplate: RedisTemplate<String, String>,
        ){
}