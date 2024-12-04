package com.langdy.lesson.application

import com.langdy.lesson.fixture.LessonFixture
import com.langdy.lesson.infra.LessonRepository
import com.langdy.support.KotestIntegrationTestSupport
import io.mockk.every
import io.mockk.mockkStatic
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

class LessonCommandServiceIntegrationTest : KotestIntegrationTestSupport() {

    @Autowired
    private lateinit var service: LessonCommandService

    @Autowired
    private lateinit var repository: LessonRepository

    init {
        Given("새로운 수업 신청을 할 때") {
            When("겹치는 시간의 수업 신청 내역이 없으면") {
                Then("수업 신청을 할 수 있다.") {
                    service.enroll(LessonFixture.`수업 신청 1`.`수업 신청 COMMAND 생성`())
                }
            }
        }

        Given("예약한 수업을 취소할 때") {
            When("수업 취소 학습자와 수업 신청 학습자가 일치하면") {
                repository.save(LessonFixture.`수업 신청 1`.`엔티티 생성`())

                mockkStatic(LocalDateTime::class)
                val fixedTime = LocalDateTime.of(2024, 12, 1, 0, 0)

                every { LocalDateTime.now() } returns fixedTime

                Then("수업 취소를 할 수 있다.") {
                    service.cancel(LessonFixture.`수업 신청 1`.`수업 취소 COMMAND 생성`())
                }
            }
        }
    }
}
