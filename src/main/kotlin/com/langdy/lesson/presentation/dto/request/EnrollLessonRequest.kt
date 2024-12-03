package com.langdy.lesson.presentation.dto.request

import com.langdy.lesson.application.command.EnrollLessonCommand
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class EnrollLessonRequest(
    val courseId: Long?,
    val teacherId: Long?,
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    val startAt: LocalDateTime?,
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    val endAt: LocalDateTime?,
) {
    fun toCommand(studentId: Long): EnrollLessonCommand = EnrollLessonCommand(courseId, teacherId, studentId, startAt, endAt)
}
