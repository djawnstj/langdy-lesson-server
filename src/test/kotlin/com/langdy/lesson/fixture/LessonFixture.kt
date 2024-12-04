package com.langdy.lesson.fixture

import com.langdy.lesson.application.command.CancelLessonCommand
import com.langdy.lesson.application.command.EnrollLessonCommand
import com.langdy.lesson.domain.Lesson
import com.langdy.lesson.domain.LessonStatus
import com.langdy.lesson.presentation.dto.request.EnrollLessonRequest
import com.langdy.teacher.domain.Teacher
import com.langdy.teacher.fixture.TeacherFixture
import java.time.LocalDateTime

enum class LessonFixture(
    val courseId: Long?,
    val teacherId: Long?,
    val studentId: Long?,
    val status: LessonStatus?,
    val startAt: LocalDateTime?,
    val endAt: LocalDateTime?,
){
    // 정상
    `수업 신청 1`(1, 1, 1, LessonStatus.ENROLLED, LocalDateTime.of(2024, 12, 1, 15, 0), LocalDateTime.of(2024, 12, 1, 16, 0)),
    `취소된 수업 신청`(1, 1, 1, LessonStatus.CANCELED, LocalDateTime.of(2024, 12, 1, 15, 0), LocalDateTime.of(2024, 12, 1, 16, 0)),
    `24년 12월 4일 12시 0분 시작 수업 신청`(1, 1, 1, LessonStatus.ENROLLED, LocalDateTime.of(2024, 12, 4, 12, 0), LocalDateTime.of(2024, 12, 4, 13, 0)),
    `학습자 2 수업 신청`(1, 1, 2, LessonStatus.ENROLLED, LocalDateTime.of(2024, 12, 1, 15, 0), LocalDateTime.of(2024, 12, 1, 16, 0)),

    // 비정상
    `수업 ID NULL 수업 신청`(null, 1, 1, LessonStatus.ENROLLED, LocalDateTime.of(2024, 12, 1, 15, 0), LocalDateTime.of(2024, 12, 1, 16, 0)),
    `선생님 ID NULL 수업 신청`(1, null, 1, LessonStatus.ENROLLED, LocalDateTime.of(2024, 12, 1, 15, 0), LocalDateTime.of(2024, 12, 1, 16, 0)),
    `학습자 ID NULL 수업 신청`(1, 1, null, LessonStatus.ENROLLED, LocalDateTime.of(2024, 12, 1, 15, 0), LocalDateTime.of(2024, 12, 1, 16, 0)),
    `수업 신청 상태 NULL 수업 신청`(1, 1, 1, null, LocalDateTime.of(2024, 12, 1, 15, 0), LocalDateTime.of(2024, 12, 1, 16, 0)),
    `수업 시작 시간 NULL 수업 신청`(1, 1, 1, LessonStatus.ENROLLED, null, LocalDateTime.of(2024, 12, 1, 16, 0)),
    `수업 종료 시간 NULL 수업 신청`(1, 1, 1, LessonStatus.ENROLLED, LocalDateTime.of(2024, 12, 1, 15, 0), null),
    `수업 신청 1 학습자 시간 중복`(1, 1, 1, LessonStatus.ENROLLED, LocalDateTime.of(2024, 12, 1, 15, 0), LocalDateTime.of(2024, 12, 1, 16, 0)),
    `수업 신청 1 선생님 시간 중복`(1, 1, 2, LessonStatus.ENROLLED, LocalDateTime.of(2024, 12, 1, 15, 0), LocalDateTime.of(2024, 12, 1, 16, 0)),
    ;

    fun `엔티티 생성`(teacher: Teacher = TeacherFixture.`선생님 1`.`엔티티 생성`()): Lesson = Lesson(courseId, teacher, studentId, status, startAt, endAt)
    fun `수업 신청 COMMAND 생성`(): EnrollLessonCommand = EnrollLessonCommand(courseId, teacherId, studentId!!, startAt, endAt)
    fun `수업 신청 REQUEST 생성`(): EnrollLessonRequest = EnrollLessonRequest(courseId, teacherId, startAt, endAt)
    fun `수업 취소 COMMAND 생성`(): CancelLessonCommand = CancelLessonCommand(courseId!!, studentId!!)
}
