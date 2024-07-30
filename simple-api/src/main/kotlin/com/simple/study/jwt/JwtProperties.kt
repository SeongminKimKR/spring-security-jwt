package com.simple.study.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties @ConstructorBinding constructor(
    val accessSecretKey: String,
    val refreshSecretKey: String,
    val refreshExpiresSeconds: Long,
    val accessExpiresSeconds: Long,
)