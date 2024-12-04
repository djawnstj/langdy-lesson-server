package com.langdy.lesson.application

import com.langdy.lesson.domain.Lesson
import java.time.LocalDateTime

interface LessonQueryRepository {
    fun existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
        studentId: Long, startAt: LocalDateTime, endAt: LocalDateTime
    ): Boolean

    fun existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
        teacherId: Long, startAt: LocalDateTime, endAt: LocalDateTime
    ): Boolean

    fun findById(id: Long): Lesson?
}
