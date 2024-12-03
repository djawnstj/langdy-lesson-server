package com.langdy.lesson.infra

import com.langdy.lesson.application.LessonCommandRepository
import com.langdy.lesson.application.LessonQueryRepository
import com.langdy.lesson.domain.Lesson
import org.springframework.data.jpa.repository.JpaRepository

interface LessonRepository : JpaRepository<Lesson, Long>, LessonQueryRepository, LessonCommandRepository {
}
