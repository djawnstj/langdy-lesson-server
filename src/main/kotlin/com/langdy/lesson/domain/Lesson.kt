package com.langdy.lesson.domain

import com.langdy.global.domain.BaseEntity
import com.langdy.global.exception.ApplicationException
import com.langdy.global.exception.ErrorCode
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDateTime

@Entity
class Lesson(
    courseId: Long?,
    teacherId: Long?,
    studentId: Long?,
    status: LessonStatus?,
    startAt: LocalDateTime?,
    endAt: LocalDateTime?,
) : BaseEntity() {
    var courseId: Long = validateCourseId(courseId)
        protected set
    var teacherId: Long = validateTeacherId(teacherId)
        protected set
    var studentId: Long = validateStudentId(studentId)
        protected set
    @field:Enumerated(EnumType.STRING)
    var status: LessonStatus = validateLessonStatus(status)
        protected set
    var startAt: LocalDateTime = validateStartAt(startAt)
        protected set
    var endAt: LocalDateTime = validateEndAt(endAt)
        protected set

    private fun validateCourseId(courseId: Long?): Long {
        requireNotNull(courseId) {
            throw ApplicationException(ErrorCode.EMPTY_COURSE_ID)
        }

        return courseId
    }

    private fun validateTeacherId(teacherId: Long?): Long {
        requireNotNull(teacherId) {
            throw ApplicationException(ErrorCode.EMPTY_TEACHER_ID)
        }

        return teacherId
    }

    private fun validateStudentId(studentId: Long?): Long {
        requireNotNull(studentId) {
            throw ApplicationException(ErrorCode.EMPTY_STUDENT_ID)
        }

        return studentId
    }

    private fun validateLessonStatus(lessonStatus: LessonStatus?): LessonStatus {
        requireNotNull(lessonStatus) {
            throw ApplicationException(ErrorCode.EMPTY_LESSON_STATUS)
        }

        return lessonStatus
    }

    private fun validateStartAt(startAt: LocalDateTime?): LocalDateTime {
        requireNotNull(startAt) {
            throw ApplicationException(ErrorCode.EMPTY_LESSON_START_TIME)
        }

        return startAt
    }

    private fun validateEndAt(endAt: LocalDateTime?): LocalDateTime {
        requireNotNull(endAt) {
            throw ApplicationException(ErrorCode.EMPTY_LESSON_END_TIME)
        }

        return endAt
    }
}
