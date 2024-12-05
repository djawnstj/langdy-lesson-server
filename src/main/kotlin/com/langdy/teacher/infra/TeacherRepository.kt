package com.langdy.teacher.infra

import com.langdy.teacher.application.TeacherQueryRepository
import com.langdy.teacher.domain.Teacher
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherRepository : JpaRepository<Teacher, Long>, TeacherQueryRepository {
}
