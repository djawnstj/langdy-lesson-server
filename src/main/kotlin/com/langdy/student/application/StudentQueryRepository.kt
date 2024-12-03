package com.langdy.student.application

import com.langdy.student.domain.Student

interface StudentQueryRepository {
    fun findByName(name: String): Student?
}
