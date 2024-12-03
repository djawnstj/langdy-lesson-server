package com.langdy.lesson.application

import com.langdy.global.exception.ApplicationException
import com.langdy.lesson.fake.TestLessonRepository
import com.langdy.lesson.fixture.LessonFixture
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.throwable.shouldHaveMessage

class LessonCommandServiceTest : BehaviorSpec({
    val lessonRepository = TestLessonRepository()

    val service = LessonCommandService(lessonRepository, lessonRepository)

    afterContainer {
        lessonRepository.init()
    }

    Given("수업 신청을 할 때") {
        When("신청한 학습자가 같은 시간에 이미 신청한 수업 내역이 있다면") {
            lessonRepository.save(LessonFixture.`수업 신청 1`.`엔티티 생성`())

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    service.enroll(LessonFixture.`수업 신청 1 학습자 시간 중복`.`수업 신청 COMMAND 생성`())
                } shouldHaveMessage "해당 시간에 이미 예약된 수업이 있습니다."
            }
        }

        When("신청한 선생님이 같은 시간에 이미 신청한 수업 내역이 있다면") {
            lessonRepository.save(LessonFixture.`수업 신청 1`.`엔티티 생성`())

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    service.enroll(LessonFixture.`수업 신청 1 선생님 시간 중복`.`수업 신청 COMMAND 생성`())
                } shouldHaveMessage "선생님이 다른 수업이 예약되어 있습니다."
            }
        }

        When("선생님과 학습자 모두 신청 내역이 없다면") {
            Then("수업 신청을 할 수 있다.") {
                shouldNotThrowAny {
                    service.enroll(LessonFixture.`수업 신청 1 선생님 시간 중복`.`수업 신청 COMMAND 생성`())
                }
            }
        }
    }
})
