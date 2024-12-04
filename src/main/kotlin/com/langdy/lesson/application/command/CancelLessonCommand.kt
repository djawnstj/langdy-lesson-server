package com.langdy.lesson.application.command

data class CancelLessonCommand(
    val id: Long,
    val studentId: Long,
)
