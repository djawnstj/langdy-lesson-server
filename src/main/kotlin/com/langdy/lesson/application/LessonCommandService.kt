package com.langdy.lesson.application

import com.langdy.global.exception.ApplicationException
import com.langdy.global.exception.ErrorCode
import com.langdy.lesson.application.command.CancelLessonCommand
import com.langdy.lesson.application.command.EnrollLessonCommand
import com.langdy.lesson.domain.Lesson
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.absoluteValue

@Service
@Transactional
class LessonCommandService(
    private val lessonQueryRepository: LessonQueryRepository,
    private val lessonCommandRepository: LessonCommandRepository,
) {
    fun enroll(command: EnrollLessonCommand) {
        val lesson = command.toEntity()

        checkExistsSameTimeLesson(lesson)

        lessonCommandRepository.save(lesson)
    }

    private fun checkExistsSameTimeLesson(lesson: Lesson) {
        checkExistsSameTimeLessonByStudent(lesson)
        checkExistsSameTimeLessonByTeacher(lesson)
    }

    private fun checkExistsSameTimeLessonByStudent(lesson: Lesson) {
        val existsSameTimeLessonByStudent =
            lessonQueryRepository.existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
                lesson.studentId,
                lesson.endAt,
                lesson.startAt
            )

        check(!existsSameTimeLessonByStudent) {
            throw ApplicationException(ErrorCode.EXISTS_STUDENT_LESSON_TIME)
        }
    }

    private fun checkExistsSameTimeLessonByTeacher(lesson: Lesson) {
        val existsSameTimeLessonByTeacher =
            lessonQueryRepository.existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
                lesson.teacherId,
                lesson.endAt,
                lesson.startAt
            )

        check(!existsSameTimeLessonByTeacher) {
            throw ApplicationException(ErrorCode.EXISTS_TEACHER_LESSON_TIME)
        }
    }

    fun cancel(command: CancelLessonCommand) {
        val lesson = lessonQueryRepository.findById(command.id)

        requireNotNull(lesson) {
            throw ApplicationException(ErrorCode.LESSON_NOT_FOUND)
        }

        check(lesson.isEnrolledStudent(command.studentId)) {
            throw ApplicationException(ErrorCode.INVALID_ENROLLED_STUDENT)
        }

        lesson.cancel(LocalDateTime.now())
    }
}
