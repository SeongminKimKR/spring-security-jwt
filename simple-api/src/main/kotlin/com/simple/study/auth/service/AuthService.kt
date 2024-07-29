package com.simple.study.auth.service

import com.simple.study.auth.dto.request.AuthRequest
import com.simple.study.auth.dto.request.SignUpRequest
import com.simple.study.auth.dto.response.SignInResponse
import com.simple.study.auth.dto.response.SignUpResponse

interface AuthService {

    fun authentication(request: AuthRequest): SignInResponse
    fun signUp(request: SignUpRequest): SignUpResponse

}