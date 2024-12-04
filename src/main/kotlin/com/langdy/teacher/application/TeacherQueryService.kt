package com.langdy.teacher.application

import com.langdy.global.exception.ApplicationException
import com.langdy.global.exception.ErrorCode
import com.langdy.teacher.application.query.GetTeacherQuery
import com.langdy.teacher.domain.Teacher
import org.springframework.stereotype.Service

@Service
class TeacherQueryService(
    private val teacherQueryRepository: TeacherQueryRepository,
) {
    fun getTeacher(query: GetTeacherQuery): Teacher {
        requireNotNull(query.id) {
            throw ApplicationException(ErrorCode.TEACHER_NOT_FOUND)
        }

        val teacher = teacherQueryRepository.findById(query.id)

        requireNotNull(teacher) {
            throw ApplicationException(ErrorCode.TEACHER_NOT_FOUND)
        }

        return teacher
    }
}
