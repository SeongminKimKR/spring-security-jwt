package com.simple.study.mail.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "spring.mail")
data class MailProperties @ConstructorBinding constructor(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val properties: PropertiesDetails,
    val authCodeExpirationMillis: Long
) {
    data class PropertiesDetails (
        val mail: MailPropertiesDetail
    ) {
        data class MailPropertiesDetail(
            val smtp: SmtpProperties,
        ) {
            data class SmtpProperties(
                val auth: Boolean,
                val starttls: StartTlsProperties,
                val connectiontimeout: Long,
                val timeout: Long,
                val writetimeout: Long
            ) {
                data class StartTlsProperties(
                    val enable: Boolean,
                    val required: Boolean
                )
            }
        }
    }
}