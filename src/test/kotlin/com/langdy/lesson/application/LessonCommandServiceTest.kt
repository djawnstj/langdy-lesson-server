package com.langdy.lesson.application

import com.langdy.global.exception.ApplicationException
import com.langdy.lesson.fake.TestLessonRepository
import com.langdy.lesson.fixture.LessonFixture
import com.langdy.teacher.application.TeacherQueryService
import com.langdy.teacher.fake.TestTeacherRepository
import com.langdy.teacher.fixture.TeacherFixture
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.throwable.shouldHaveMessage
import io.mockk.every
import io.mockk.mockkStatic
import java.time.LocalDateTime

class LessonCommandServiceTest : BehaviorSpec({
    val teacherRepository = TestTeacherRepository()
    val teacherQueryService = TeacherQueryService(teacherRepository)
    val lessonRepository = TestLessonRepository()

    val service = LessonCommandService(teacherQueryService, lessonRepository, lessonRepository)

    afterContainer {
        lessonRepository.init()
        teacherRepository.init()
    }

    Given("수업 신청을 할 때") {
        When("신청한 학습자가 같은 시간에 이미 신청한 수업 내역이 있다면") {
            teacherRepository.teachers[1] = TeacherFixture.`선생님 1`.`엔티티 생성`()

            lessonRepository.save(LessonFixture.`수업 신청 1`.`엔티티 생성`())

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    service.enroll(LessonFixture.`수업 신청 1 학습자 시간 중복`.`수업 신청 COMMAND 생성`())
                } shouldHaveMessage "해당 시간에 이미 예약된 수업이 있습니다."
            }
        }

        When("신청한 선생님이 같은 시간에 이미 신청한 수업 내역이 있다면") {
            teacherRepository.teachers[1] = TeacherFixture.`선생님 1`.`엔티티 생성`()

            lessonRepository.save(LessonFixture.`수업 신청 1`.`엔티티 생성`())

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    service.enroll(LessonFixture.`수업 신청 1 선생님 시간 중복`.`수업 신청 COMMAND 생성`())
                } shouldHaveMessage "선생님이 다른 수업이 예약되어 있습니다."
            }
        }

        When("선생님과 학습자 모두 신청 내역이 없다면") {
            teacherRepository.teachers[1] = TeacherFixture.`선생님 1`.`엔티티 생성`()

            Then("수업 신청을 할 수 있다.") {
                shouldNotThrowAny {
                    service.enroll(LessonFixture.`수업 신청 1 선생님 시간 중복`.`수업 신청 COMMAND 생성`())
                }
            }
        }
    }

    Given("예약된 수업을 취소할 때") {
        When("예약 내역을 찾을 수 없으면") {
            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    service.cancel(LessonFixture.`수업 신청 1`.`수업 취소 COMMAND 생성`())
                } shouldHaveMessage "등록된 수업 예약이 없습니다."
            }
        }

        When("취소 요청 학습자와 예약 내역의 학습자가 일치하지 않으면") {
            lessonRepository.save(LessonFixture.`수업 신청 1`.`엔티티 생성`())

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    service.cancel(LessonFixture.`학습자 2 수업 신청`.`수업 취소 COMMAND 생성`())
                } shouldHaveMessage "수업 신청한 본인만 취소할 수 있습니다."
            }
        }

        When("예약 내역의 학습자와 취소 요청 학습자가 일치하면") {
            val lessonFixture = LessonFixture.`수업 신청 1`
            lessonRepository.save(lessonFixture.`엔티티 생성`())

            mockkStatic(LocalDateTime::class)
            val fixedTime = LocalDateTime.of(2024, 12, 1, 0, 0)

            every { LocalDateTime.now() } returns fixedTime

            Then("예약을 취소할 수 있다.") {
                shouldNotThrowAny {
                    service.cancel(lessonFixture.`수업 취소 COMMAND 생성`())
                }
            }
        }
    }
})
