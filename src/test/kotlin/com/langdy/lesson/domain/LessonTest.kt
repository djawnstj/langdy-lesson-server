package com.langdy.lesson.domain

import com.langdy.global.exception.ApplicationException
import com.langdy.lesson.fixture.LessonFixture
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.throwable.shouldHaveMessage

class LessonTest : BehaviorSpec({
    Given("수업 신청 엔티티를 생성할 때") {
        When("수업 ID, 선생님 ID, 학습자 ID, 수업 신청 상태, 수업 시작 시간, 수업 종료 시간 값이 올바르면") {
            val lessonFixture = LessonFixture.`수업 신청 1`

            Then("수업 신청 엔티티를 생성할 수 있다.") {
                shouldNotThrowAny {
                    Lesson(lessonFixture.courseId, lessonFixture.teacherId, lessonFixture.studentId, lessonFixture.status, lessonFixture.startAt, lessonFixture.endAt)
                }
            }
        }

        When("수업 ID 가 null 이라면") {
            val lessonFixture = LessonFixture.`수업 ID NULL 수업 신청`

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    Lesson(lessonFixture.courseId, lessonFixture.teacherId, lessonFixture.studentId, lessonFixture.status, lessonFixture.startAt, lessonFixture.endAt)
                } shouldHaveMessage "수업 ID가 없습니다."
            }
        }

        When("선생님 ID 가 null 이라면") {
            val lessonFixture = LessonFixture.`선생님 ID NULL 수업 신청`

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    Lesson(lessonFixture.courseId, lessonFixture.teacherId, lessonFixture.studentId, lessonFixture.status, lessonFixture.startAt, lessonFixture.endAt)
                } shouldHaveMessage "선생님 ID가 없습니다."
            }
        }

        When("학습자 ID 가 null 이라면") {
            val lessonFixture = LessonFixture.`학습자 ID NULL 수업 신청`

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    Lesson(lessonFixture.courseId, lessonFixture.teacherId, lessonFixture.studentId, lessonFixture.status, lessonFixture.startAt, lessonFixture.endAt)
                } shouldHaveMessage "학습자 ID가 없습니다."
            }
        }

        When("수업 신청 상태가 null 이라면") {
            val lessonFixture = LessonFixture.`수업 신청 상태 NULL 수업 신청`

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    Lesson(lessonFixture.courseId, lessonFixture.teacherId, lessonFixture.studentId, lessonFixture.status, lessonFixture.startAt, lessonFixture.endAt)
                } shouldHaveMessage "수업 신청 상태값이 없습니다."
            }
        }

        When("수업 시작 시간이 null 이라면") {
            val lessonFixture = LessonFixture.`수업 시작 시간 NULL 수업 신청`

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    Lesson(lessonFixture.courseId, lessonFixture.teacherId, lessonFixture.studentId, lessonFixture.status, lessonFixture.startAt, lessonFixture.endAt)
                } shouldHaveMessage "수업 신청 시작 시간이 없습니다."
            }
        }

        When("수업 종료 시간이 null 이라면") {
            val lessonFixture = LessonFixture.`수업 종료 시간 NULL 수업 신청`

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    Lesson(lessonFixture.courseId, lessonFixture.teacherId, lessonFixture.studentId, lessonFixture.status, lessonFixture.startAt, lessonFixture.endAt)
                } shouldHaveMessage "수업 신청 종료 시간이 없습니다."
            }
        }
    }
 })
