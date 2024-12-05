package com.langdy.teacher.fake

import com.langdy.teacher.application.TeacherQueryRepository
import com.langdy.teacher.domain.Teacher

class TestTeacherRepository : TeacherQueryRepository {
    val teachers: MutableMap<Long, Teacher> = mutableMapOf()

    override fun findById(id: Long): Teacher? = teachers[id]

    fun init() {
        this.teachers.clear()
    }
}
