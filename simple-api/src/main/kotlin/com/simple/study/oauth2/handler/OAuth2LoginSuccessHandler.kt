package com.simple.study.oauth2.handler

import com.simple.study.member.service.CustomUserDetail
import com.simple.study.redis.repository.SocialAuthTokenRepositoryRedis
import com.simple.study.util.RandomUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

class OAuth2LoginSuccessHandler(
    private val socialAuthTokenRepositoryRedis: SocialAuthTokenRepositoryRedis,
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?,
    ) {
        requireNotNull(response) {"response is null"}
        requireNotNull(authentication) {"Invalid is null"}

        runCatching {
            val authUser = authentication.principal as CustomUserDetail

            val token = RandomUtil.getRandomString(32)
            socialAuthTokenRepositoryRedis.save(token, authUser.username)


        }
    }
}