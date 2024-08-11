package com.simple.study.auth.service

import com.simple.study.auth.dto.request.AuthRequest
import com.simple.study.auth.dto.request.SignUpRequest
import com.simple.study.auth.dto.response.SignInResponse
import com.simple.study.auth.dto.response.SignUpResponse
import org.springframework.stereotype.Service

@Service
class GoogleAuthService : AuthService {
    override fun authentication(request: AuthRequest): SignInResponse {
        TODO("Not yet implemented")
    }

    override fun signUp(request: SignUpRequest): SignUpResponse {
        TODO("Not yet implemented")
    }
}