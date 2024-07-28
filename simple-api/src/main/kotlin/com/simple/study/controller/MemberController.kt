package com.simple.study.member.controller

import com.simple.study.member.service.MemberService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 정보 조회, 수정, 삭제")
@RestController
class MemberController (
    private val memberService: MemberService
        ){
}