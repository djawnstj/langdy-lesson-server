package com.langdy.lesson.fake

import com.langdy.lesson.application.LessonCommandRepository
import com.langdy.lesson.application.LessonQueryRepository
import com.langdy.lesson.domain.Lesson
import java.time.LocalDateTime

class TestLessonRepository : LessonCommandRepository, LessonQueryRepository {

    private val lessons: MutableMap<Long, Lesson> = mutableMapOf()

    override fun save(lesson: Lesson): Lesson {
        lessons[lessons.size.toLong() + 1] = lesson

        return lesson
    }

    override fun existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
        studentId: Long,
        startAt: LocalDateTime,
        endAt: LocalDateTime
    ): Boolean {
        return lessons.values.any {
            it.studentId == studentId && (startAt in it.startAt..it.endAt || endAt in it. startAt .. it.endAt)
        }
    }

    override fun existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
        teacherId: Long,
        startAt: LocalDateTime,
        endAt: LocalDateTime
    ): Boolean {
        return lessons.values.any {
            it.teacherId == teacherId && (startAt in it.startAt..it.endAt || endAt in it. startAt .. it.endAt)
        }
    }

    override fun findById(id: Long): Lesson? = lessons[id]

    fun init() {
        lessons.clear()
    }
}
