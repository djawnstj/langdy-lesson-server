package com.langdy.student.infra

import com.langdy.student.application.StudentQueryRepository
import com.langdy.student.domain.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository : JpaRepository<Student, Long>, StudentQueryRepository {
}
