package com.simple.study.util

object RandomUtil {

    fun getRandomString(length: Int): String {
        val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    fun getRandomNumber(length: Int): String {
        val charset = "0123456789"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }
}