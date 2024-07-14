package com.simple.study.domain

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@ComponentScan(basePackageClasses = [SimpleDomainRoot::class])
@Configuration
class SimpleDomainRoot {
}