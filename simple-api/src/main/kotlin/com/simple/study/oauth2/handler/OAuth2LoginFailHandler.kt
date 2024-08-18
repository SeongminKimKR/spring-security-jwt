package com.simple.study.oauth2.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler

class OAuth2LoginFailHandler :AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?,
    ) {
        requireNotNull(response) {"response is null"}
        requireNotNull(exception) {"Invalid is null"}

        response.status = HttpServletResponse.SC_BAD_REQUEST
        response.writer.write("Social Login Failed : ${exception.message}")
    }


}