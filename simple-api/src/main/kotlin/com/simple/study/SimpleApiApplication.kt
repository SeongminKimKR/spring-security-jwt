package com.simple.study

import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["com.simple.study"])
class SimpleApiApplication

fun main(args: Array<String>) {
    runApplication<SimpleApiApplication>(*args)
}
