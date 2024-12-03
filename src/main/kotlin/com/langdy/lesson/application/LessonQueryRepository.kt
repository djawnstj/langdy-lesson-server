package com.langdy.lesson.application

import java.time.LocalDateTime

interface LessonQueryRepository {
    fun existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(

        studentId: Long, startAt: LocalDateTime, endAt: LocalDateTime
    ): Boolean

    fun existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
        teacherId: Long, startAt: LocalDateTime, endAt: LocalDateTime
    ): Boolean
}
