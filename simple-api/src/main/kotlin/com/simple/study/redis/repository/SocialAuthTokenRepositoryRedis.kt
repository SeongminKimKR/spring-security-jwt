package com.simple.study.redis.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class SocialAuthTokenRepositoryRedis (
    private val redisTemplate: RedisTemplate<String, String>
        ){

    companion object {
        private const val KEY_PREFIX = "socialAuthToken"
        private const val TTL = 600L
    }

    init {
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
    }

    fun save(socialToken: String, userId: String) {
        val key = "$KEY_PREFIX:${socialToken}"
        redisTemplate.opsForValue().set(key, userId, TTL, TimeUnit.SECONDS)
    }

    fun findBySocialToken(socialToken: String): String? {
        val key = redisTemplate.keys("$KEY_PREFIX:$socialToken").firstOrNull()
        return key?.let { redisTemplate.opsForValue().get(it) }
    }

    fun deleteBySocialToken(socialToken: String) {
        val keys = redisTemplate.keys("$KEY_PREFIX:$socialToken")
        redisTemplate.delete(keys)
    }
}