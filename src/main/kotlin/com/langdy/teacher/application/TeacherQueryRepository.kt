package com.langdy.teacher.application

import com.langdy.teacher.domain.Teacher

interface TeacherQueryRepository {
    fun findById(id: Long): Teacher?
}
