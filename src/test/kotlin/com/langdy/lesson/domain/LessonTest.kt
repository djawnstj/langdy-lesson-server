package com.langdy.lesson.domain

import com.langdy.global.exception.ApplicationException
import com.langdy.lesson.fixture.LessonFixture
import com.langdy.teacher.fixture.TeacherFixture
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.throwable.shouldHaveMessage
import java.time.LocalDateTime

class LessonTest : BehaviorSpec({
    Given("수업 신청 엔티티를 생성할 때") {
        When("수업 ID, 선생님 ID, 학습자 ID, 수업 신청 상태, 수업 시작 시간, 수업 종료 시간 값이 올바르면") {
            val lessonFixture = LessonFixture.`수업 신청 1`

            Then("수업 신청 엔티티를 생성할 수 있다.") {
                shouldNotThrowAny {
                    Lesson(lessonFixture.courseId, TeacherFixture.`선생님 1`.`엔티티 생성`(), lessonFixture.studentId, lessonFixture.status, lessonFixture.startAt, lessonFixture.endAt)
                }
            }
        }

        When("수업 ID 가 null 이라면") {
            val lessonFixture = LessonFixture.`수업 ID NULL 수업 신청`

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    Lesson(lessonFixture.courseId, TeacherFixture.`선생님 1`.`엔티티 생성`(), lessonFixture.studentId, lessonFixture.status, lessonFixture.startAt, lessonFixture.endAt)
                } shouldHaveMessage "수업 ID가 없습니다."
            }
        }

        When("학습자 ID 가 null 이라면") {
            val lessonFixture = LessonFixture.`학습자 ID NULL 수업 신청`

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    Lesson(lessonFixture.courseId, TeacherFixture.`선생님 1`.`엔티티 생성`(), lessonFixture.studentId, lessonFixture.status, lessonFixture.startAt, lessonFixture.endAt)
                } shouldHaveMessage "학습자 ID가 없습니다."
            }
        }

        When("수업 신청 상태가 null 이라면") {
            val lessonFixture = LessonFixture.`수업 신청 상태 NULL 수업 신청`

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    Lesson(lessonFixture.courseId, TeacherFixture.`선생님 1`.`엔티티 생성`(), lessonFixture.studentId, lessonFixture.status, lessonFixture.startAt, lessonFixture.endAt)
                } shouldHaveMessage "수업 신청 상태값이 없습니다."
            }
        }

        When("수업 시작 시간이 null 이라면") {
            val lessonFixture = LessonFixture.`수업 시작 시간 NULL 수업 신청`

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    Lesson(lessonFixture.courseId, TeacherFixture.`선생님 1`.`엔티티 생성`(), lessonFixture.studentId, lessonFixture.status, lessonFixture.startAt, lessonFixture.endAt)
                } shouldHaveMessage "수업 신청 시작 시간이 없습니다."
            }
        }

        When("수업 종료 시간이 null 이라면") {
            val lessonFixture = LessonFixture.`수업 종료 시간 NULL 수업 신청`

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    Lesson(lessonFixture.courseId, TeacherFixture.`선생님 1`.`엔티티 생성`(), lessonFixture.studentId, lessonFixture.status, lessonFixture.startAt, lessonFixture.endAt)
                } shouldHaveMessage "수업 신청 종료 시간이 없습니다."
            }
        }
    }

    Given("수업 신청을 취소할 때") {
        When("이미 취소된 수업이라면") {
            val lesson = LessonFixture.`취소된 수업 신청`.`엔티티 생성`()

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    lesson.cancel(LocalDateTime.now())
                } shouldHaveMessage "이미 취소된 수업입니다."
            }
        }
        When("수업 시작 시간이 12시간 이내인 수업이라면") {
            val lesson = LessonFixture.`24년 12월 4일 12시 0분 시작 수업 신청`.`엔티티 생성`()

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    lesson.cancel(LocalDateTime.of(2024, 12, 4, 0, 0, 0))
                } shouldHaveMessage "수업 시작 12시간 이내에는 취소할 수 없습니다."
            }
        }

        When("12시간 이후로 예약된 상태라면") {
            val lesson = LessonFixture.`24년 12월 4일 12시 0분 시작 수업 신청`.`엔티티 생성`()

            lesson.cancel(LocalDateTime.of(2024, 12, 3, 23, 59, 59))

            Then("수업 상태를 취소로 변경한다.") {
                lesson.status shouldBe LessonStatus.CANCELED
            }
        }
    }
 })
