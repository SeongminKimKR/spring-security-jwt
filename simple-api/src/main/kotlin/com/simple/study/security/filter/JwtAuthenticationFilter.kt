package com.simple.study.security.filter

import com.simple.study.jwt.TokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean

class JwtAuthenticationFilter(
    private val tokenProvider: TokenProvider,
) : GenericFilterBean() {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        runCatching {
            val token = resolveToken(request as HttpServletRequest)

            if (tokenProvider.validateAccessToken(token)) {
                val authentication = tokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }.onFailure {
            request?.setAttribute("exception", it)
        }

        chain?.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String {
        val bearer = request.getHeader("Authorization")

        require(StringUtils.hasText(bearer) && bearer.startsWith("Bearer"))

        return bearer.substring(7)
    }
}