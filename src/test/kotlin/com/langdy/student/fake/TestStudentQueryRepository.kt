package com.langdy.student.fake

import com.langdy.student.application.StudentQueryRepository
import com.langdy.student.domain.Student

class TestStudentQueryRepository : StudentQueryRepository {

    val students: MutableMap<Long, Student> = mutableMapOf()

    override fun findByName(name: String): Student? {
        return students.values.find { it.name == name }
    }

    fun init() {
        this.students.clear()
    }
}
