package com.langdy.lesson.domain

import com.langdy.global.domain.BaseEntity
import com.langdy.global.exception.ApplicationException
import com.langdy.global.exception.ErrorCode
import com.langdy.teacher.domain.Teacher
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

@Entity
class Lesson(
    courseId: Long?,
    teacher: Teacher,
    studentId: Long?,
    status: LessonStatus?,
    startAt: LocalDateTime?,
    endAt: LocalDateTime?,
) : BaseEntity() {
    var courseId: Long = validateCourseId(courseId)
        protected set
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    var teacher: Teacher = teacher
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

    fun getTeacherId(): Long = teacher.id

    fun cancel(cancelTime: LocalDateTime) {
        checkActiveLesson()
        checkCancellationTime(cancelTime)

        this.status = LessonStatus.CANCELED
    }

    private fun checkActiveLesson() {
        check(status.isEnrolled()) {
            throw ApplicationException(ErrorCode.ALREADY_CANCELED_LESSON)
        }
    }

    private fun checkCancellationTime(cancelTime: LocalDateTime) {
        check(isCancelableTime(cancelTime)) {
            throw ApplicationException(ErrorCode.INVALID_LESSON_CANCELLATION_TIME)
        }
    }

    private fun isCancelableTime(cancelTime: LocalDateTime): Boolean {
        val secondsDifference = ChronoUnit.SECONDS.between(cancelTime, this.startAt)
        return secondsDifference > (CANCELLATION_TIME_DIFF)
    }

    fun isEnrolledStudent(studentId: Long): Boolean = this.studentId == studentId

    companion object {
        private val CANCELLATION_TIME_DIFF = TimeUnit.HOURS.toSeconds(12)
    }
}
