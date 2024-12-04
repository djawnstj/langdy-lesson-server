package com.langdy.lesson.application.command

import com.langdy.lesson.domain.Lesson
import com.langdy.lesson.domain.LessonStatus
import com.langdy.teacher.domain.Teacher
import java.time.LocalDateTime

data class EnrollLessonCommand(
    val courseId: Long?,
    val teacherId: Long?,
    val studentId: Long,
    val startAt: LocalDateTime?,
    val endAt: LocalDateTime?,
) {
    fun toEntity(teacher: Teacher): Lesson = Lesson(courseId, teacher, studentId, LessonStatus.ENROLLED, startAt, endAt)
}
