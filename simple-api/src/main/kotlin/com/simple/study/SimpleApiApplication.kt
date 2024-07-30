package com.simple.study

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.simple.study"])
@ConfigurationPropertiesScan
class SimpleApiApplication

fun main(args: Array<String>) {
    runApplication<SimpleApiApplication>(*args)
}
