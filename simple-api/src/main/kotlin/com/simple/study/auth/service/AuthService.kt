package com.simple.study.auth.service

import com.simple.study.auth.dto.request.AuthRequest
import com.simple.study.auth.dto.response.SignInResponse

interface AuthService {

    fun authentication(request: AuthRequest): SignInResponse
}