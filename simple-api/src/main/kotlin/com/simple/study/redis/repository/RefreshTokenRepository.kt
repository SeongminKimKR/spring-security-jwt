package com.simple.study.redis.repository

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RefreshTokenRepository(
    private val redisTemplate: RedisTemplate<String, String>,
    @Value("\${jwt.refresh-expires-seconds}")
    private val TTL: Long
) {

    companion object {
        private const val KEY_PREFIX = "refreshToken"
    }

    fun save(refreshToken: String, userId: String) {
        val key = "$KEY_PREFIX:${userId}:${refreshToken}"
        redisTemplate.opsForValue().set(key, "", TTL, TimeUnit.SECONDS)
    }

    fun findByRefreshToken(refreshToken: String): String? {
        val key = redisTemplate.keys("$KEY_PREFIX:*:$refreshToken").firstOrNull()
        return key?.let { redisTemplate.opsForValue().get(it) }
    }

    fun deleteByRefreshToken(refreshToken: String) {
        val keys = redisTemplate.keys("$KEY_PREFIX:*:$refreshToken")
        redisTemplate.delete(keys)
    }

    fun deleteByUserId(userId: String) {
        val keys = redisTemplate.keys("$KEY_PREFIX:$userId:*")
        redisTemplate.delete(keys)
    }
}