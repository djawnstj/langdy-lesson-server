package com.langdy.lesson.application

import com.langdy.email.application.EmailCommandService
import com.langdy.email.application.command.SendEmailCommand
import com.langdy.global.exception.ApplicationException
import com.langdy.global.exception.ErrorCode
import com.langdy.lesson.application.command.CancelLessonCommand
import com.langdy.lesson.application.command.EnrollLessonCommand
import com.langdy.lesson.domain.Lesson
import com.langdy.push.application.PushCommandService
import com.langdy.push.application.command.SendPushCommand
import com.langdy.teacher.application.TeacherQueryService
import com.langdy.teacher.application.query.GetTeacherQuery
import com.langdy.teacher.domain.Teacher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class LessonCommandService(
    private val teacherQueryService: TeacherQueryService,
    private val lessonQueryRepository: LessonQueryRepository,
    private val lessonCommandRepository: LessonCommandRepository,
    private val emailCommandService: EmailCommandService,
    private val pushCommandService: PushCommandService,
) {
    fun enroll(command: EnrollLessonCommand) {
        val teacher = teacherQueryService.getTeacher(GetTeacherQuery(command.teacherId))
        val lesson = command.toEntity(teacher)

        checkExistsSameTimeLesson(lesson)

        lessonCommandRepository.save(lesson)

        sendEnrolledEmail(teacher)
        sendEnrolledPush()
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
                lesson.getTeacherId(),
                lesson.endAt,
                lesson.startAt
            )

        check(!existsSameTimeLessonByTeacher) {
            throw ApplicationException(ErrorCode.EXISTS_TEACHER_LESSON_TIME)
        }
    }

    private fun sendEnrolledEmail(teacher: Teacher) {
        emailCommandService.sendEmail(
            SendEmailCommand("${teacher.name} 선생님, 새로운 수업이 예약되었습니다.", "<html>" /* 적절한 이메일 폼 html 생성 */)
        )
    }

    private fun sendEnrolledPush() {
        pushCommandService.sendPush(SendPushCommand("수업 신청이 완료되었습니다.", "약속한 시간에 수업을 진행하세요."))
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
