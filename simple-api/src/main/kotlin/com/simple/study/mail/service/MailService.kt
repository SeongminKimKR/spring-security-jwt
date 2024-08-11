package com.simple.study.mail.service

import com.simple.study.domain.member.domain.storage.jpa.MemberRepository
import com.simple.study.mail.config.MailProperties
import com.simple.study.mail.dto.request.CheckEmailForSignUpRequest
import com.simple.study.mail.dto.request.SendMailRequest
import com.simple.study.mail.dto.request.VerifyEmailForSignUpRequest
import com.simple.study.mail.dto.response.CheckEmailForSignUpResponse
import com.simple.study.mail.dto.response.VerifyEmailForSignUpResponse
import com.simple.study.redis.dto.EmailVerificationDto
import com.simple.study.redis.repository.EmailVerificationRepository
import com.simple.study.util.RandomUtil.getRandomNumber
import com.simple.study.util.RandomUtil.getRandomString
import jakarta.mail.internet.InternetAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine

@Service
class MailService(
    private val memberRepository: MemberRepository,
    private val emailVerificationRepository: EmailVerificationRepository,
    private val mailProperties: MailProperties,
    private val mailSender: JavaMailSender,
    private val templateEngine: SpringTemplateEngine,
) {

    companion object {
        const val REDIS_KEY_SUB_NAME = "SingUp"

        private const val VERIFICATION_CODE_LENGTH = 6
        private const val VERIFICATION_TOKEN_LENGTH = 32
        private const val REDIS_DATA_TTL = 10L
        private const val ATTEMPT_MAX_COUNT = 10
        private const val SIGN_UP_CODE_TITLE = "회원가입을 위한 인증번호 메일입니다."
        private const val AUTH_TEMPLATE_NAME = "email-auth"
        private const val MAIL_SENDER_NAME = "익명의 발신자"

    }

    suspend fun sendVerificationEmailForSignUp(request: VerifyEmailForSignUpRequest): VerifyEmailForSignUpResponse {
        checkDuplicateEmail(request.email)

        val verificationToken = getRandomString(VERIFICATION_TOKEN_LENGTH)
        val verificationCode = getRandomNumber(VERIFICATION_CODE_LENGTH)
        val context = Context().apply {
            setVariable("authCode", verificationCode)
        }

        val body = templateEngine.process(AUTH_TEMPLATE_NAME, context)

        val mail = SendMailRequest(
            subject = SIGN_UP_CODE_TITLE,
            email = request.email,
            body = body
        )

        CoroutineScope(Dispatchers.IO).launch {
            sendMail(mail)
        }

        val emailVerificationDto = EmailVerificationDto(
            email = request.email,
            verificationToken = verificationToken,
            verificationCode = verificationCode
        )

        emailVerificationRepository.save(REDIS_KEY_SUB_NAME, emailVerificationDto, REDIS_DATA_TTL)

        return VerifyEmailForSignUpResponse(token = verificationToken)
    }

    fun checkVerificationEmailForSignUp(request: CheckEmailForSignUpRequest): CheckEmailForSignUpResponse {
        val emailVerificationDto =
            requireNotNull(emailVerificationRepository.findByVerificationToken(REDIS_KEY_SUB_NAME, request.token)) {
                "존재하지 않는 정보거나 이메일 인증 시간이 초과되었습니다."
            }

        require(emailVerificationDto.attemptCount <= ATTEMPT_MAX_COUNT) {
            "인증 시도 최대 횟수를 초과했습니다. 새로운 인증코드를 발급 받으세요."
        }

        require(!emailVerificationDto.isVerified) {
            "이미 인증을 완료했습니다."
        }

        emailVerificationDto.isVerified = emailVerificationDto.verificationCode == request.code
        emailVerificationDto.attemptCount++
        emailVerificationRepository.save(REDIS_KEY_SUB_NAME, emailVerificationDto, REDIS_DATA_TTL)

        return CheckEmailForSignUpResponse(success = emailVerificationDto.isVerified)
    }

    private fun sendMail(mailDetail: SendMailRequest) = runCatching {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")
            .apply {
                setSubject(mailDetail.subject)
                setTo(mailDetail.email)
                setFrom(InternetAddress(mailProperties.username, MAIL_SENDER_NAME))
                setText(mailDetail.body, true)
            }
        mailSender.send(message)
    }.onFailure {
        throw it
    }


    private fun checkDuplicateEmail(email: String) {
        require(memberRepository.findByEmail(email) == null) {
            "이미 존재하는 이메일 입니다."
        }
    }

    fun isCompletedVerification(token: String): Boolean {
        val emailVerificationDto= requireNotNull(emailVerificationRepository.findByVerificationToken(REDIS_KEY_SUB_NAME, token)) {
            "존재하지 않는 정보거나 이메일 인증 시간이 초과되었습니다."
        }

        if(emailVerificationDto.isVerified) {
            emailVerificationRepository.deleteByVerificationToken(REDIS_KEY_SUB_NAME, token)
            return true
        }

        return false
    }
}
