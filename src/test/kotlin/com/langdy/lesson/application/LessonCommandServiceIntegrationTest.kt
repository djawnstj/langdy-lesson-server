package com.langdy.lesson.application

import com.langdy.lesson.fixture.LessonFixture
import com.langdy.support.KotestIntegrationTestSupport
import org.springframework.beans.factory.annotation.Autowired

class LessonCommandServiceIntegrationTest : KotestIntegrationTestSupport() {

    @Autowired
    private lateinit var service: LessonCommandService

    init {
        Given("새로운 수업 신청을 할 때") {
            When("겹치는 시간의 수업 신청 내역이 없으면") {
                Then("수업 신청을 할 수 있다.") {
                    service.enroll(LessonFixture.`수업 신청 1`.`수업 신청 COMMAND 생성`())
                }
            }
        }
    }
}
