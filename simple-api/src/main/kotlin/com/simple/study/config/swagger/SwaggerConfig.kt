package com.simple.study.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun api() : OpenAPI = OpenAPI()
        .components(Components())
        .info(Info()
            .title("스프링시큐리티 + JWT")
            .description("스프링시큐리티와 JWT를 이용한 사용자 인증 예제")
            .version("1.0.0"))


}