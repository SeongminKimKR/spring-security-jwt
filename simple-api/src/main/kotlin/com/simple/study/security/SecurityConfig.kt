package com.simple.study.security

import com.simple.study.jwt.TokenProvider
import com.simple.study.security.filter.JwtAuthenticationFilter
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtTokenProvider: TokenProvider,
) {

    private val allowedUrls = arrayOf("/", "/swagger-ui/**", "/v3/**", "/common-signup")

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .httpBasic {
            it.disable()
        }
        .csrf {
            it.disable()
        }
        .headers {
            it.frameOptions { frame ->
                frame.sameOrigin()
            }
        }
        .authorizeHttpRequests {
            it.requestMatchers(*allowedUrls).permitAll()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated()
        }
        .addFilterBefore(
            JwtAuthenticationFilter(jwtTokenProvider),
            UsernamePasswordAuthenticationFilter::class.java
        )
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        .build()

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}