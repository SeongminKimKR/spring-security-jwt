package com.simple.study.config.security

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig{

    private val allowedUrls = arrayOf("/", "/swagger-ui/**", "/v3/**", "/common-signup")

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain = http
        .csrf {
            it.disable()
        }
        .headers {
            it.frameOptions{
                    frame -> frame.sameOrigin()
            }
        }
        .authorizeHttpRequests {
            it.requestMatchers(*allowedUrls).permitAll()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated()
        }
        .sessionManagement{it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}
        .build()

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}