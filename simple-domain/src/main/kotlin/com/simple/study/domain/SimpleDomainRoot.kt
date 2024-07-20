package com.simple.study.domain

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@ComponentScan(basePackageClasses = [SimpleDomainRoot::class])
@Configuration
@EnableJpaAuditing
class SimpleDomainRoot {
}