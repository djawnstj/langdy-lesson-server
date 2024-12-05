package com.langdy.lesson.application

import com.langdy.lesson.domain.Lesson

interface LessonCommandRepository {
    fun save(lesson: Lesson): Lesson
}
