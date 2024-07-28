package com.simple.study.jwt

import com.simple.study.member.service.CustomUserDetail
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.SecretKey

@Service
class TokenProvider(
    @Value("\${jwt.access-key}")
    private val accessSecretKey: String,
    @Value("\${jwt.refresh-key}")
    private val refreshSecretKey: String,
    @Value("\${jwt.access-expires-seconds}")
    private val accessExpiresSeconds: Long,
    @Value("\${jwt.refresh-expires-seconds}")
    private val refreshExpiresSeconds: Long,

    ) {

    private val accessKey by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecretKey)) }
    private val refreshKey by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecretKey)) }
    fun createToken(authentication: Authentication): TokenInfo {
        val userDetail = authentication.principal as CustomUserDetail
        val authorities = userDetail
            .authorities
            .joinToString(",", transform = GrantedAuthority::getAuthority)

        val accessToken =
            createJwtToken(userDetail.username, userDetail.email, userDetail.nickname, authorities, accessKey)
        val refreshToken =
            createJwtToken(userDetail.username, userDetail.email, userDetail.nickname, authorities, refreshKey)

        return TokenInfo(userDetail.username, "Bearer", accessToken, refreshToken)
    }


    fun validateAccessToken(token: String) = runCatching {
        getClaims(token, accessKey) }
        .onFailure {
            when (it) {
                is SecurityException -> {}  // Invalid JWT Token
                is MalformedJwtException -> {}  // Invalid JWT Token
                is ExpiredJwtException -> {}    // Expired JWT Token
                is UnsupportedJwtException -> {}    // Unsupported JWT Token
                is IllegalArgumentException -> {}   // JWT claims string is empty
                else -> {}  // els
            }
            throw it
        }.isSuccess

    fun validateRefreshToken(token: String): TokenInfo {
        val refreshTokenClaims = run { getClaims(token, refreshKey) }

        val newAccessToken = createJwtToken(
            refreshTokenClaims.subject,
            refreshTokenClaims["nick"] as String,
            refreshTokenClaims["email"] as String,
            refreshTokenClaims["auth"] as String,
            accessKey
        )

        val newRefreshToken = createJwtToken(
            refreshTokenClaims.subject,
            refreshTokenClaims["nick"] as String,
            refreshTokenClaims["email"] as String,
            refreshTokenClaims["auth"] as String,
            refreshKey
        )
        return TokenInfo(refreshTokenClaims.subject, "Bearer", newAccessToken, newRefreshToken)
    }

    fun getAuthentication(token: String): Authentication {
        val claims: Claims = getClaims(token, accessKey)

        val auth = claims["auth"] ?: throw RuntimeException("잘못된 토큰입니다.")
        val email = claims["email"] ?: throw RuntimeException("잘못된 토큰입니다.")
        val nickname = claims["nickname"] ?: throw RuntimeException("잘못된 토큰입니다.")

        val authorities: Collection<GrantedAuthority> = (auth as String)
            .split(",")
            .map { SimpleGrantedAuthority(it) }

        val principal = CustomUserDetail(claims.subject, null, authorities, email as String, nickname as String)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    private fun createJwtToken(
        userId: String,
        email: String,
        nickname: String,
        authorities: String,
        key: SecretKey,
    ): String {
        val now = Date()
        val expirationSeconds = if (key == accessKey) accessExpiresSeconds else refreshExpiresSeconds
        val expirationTime = Date.from(now.toInstant().plus(expirationSeconds, ChronoUnit.SECONDS))

        return Jwts
            .builder()
            .setSubject(userId)
            .claim("email", email)
            .claim("nickname", nickname)
            .claim("authority", authorities)
            .setIssuedAt(now)
            .setExpiration(expirationTime)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    private fun getClaims(token: String, key: SecretKey): Claims =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

}