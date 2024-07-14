package com.simple.study.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Service
class TokenProvider (
    @Value("\${jwt.secret-key}")
    private val secretKey: String,
    @Value("\${jwt.issuer}")
    private val issuer: String,
    @Value("\${jwt.access-expires-seconds}")
    private val accessExpiresSeconds: Long,
    @Value("\${jwt.refresh-expires-seconds}")
    private val refreshExpiresSeconds: Long,
        ){

    fun createToken(subject: String): String = Jwts.builder()
        .signWith(SecretKeySpec(secretKey.toByteArray(), SignatureAlgorithm.HS512.jcaName))
        .setSubject(subject)
        .setIssuer(issuer)
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plus(expiresSeconds, ChronoUnit.SECONDS)))
        .compact()
}