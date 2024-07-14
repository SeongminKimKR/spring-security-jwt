package com.simple.study.domain.member.domain.storage.jpa

import com.simple.study.domain.member.domain.Social
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Social, Long>