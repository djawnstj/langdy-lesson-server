package com.langdy.lesson.presentation

import com.langdy.lesson.fixture.LessonFixture
import com.langdy.lesson.presentation.dto.request.EnrollLessonRequest
import com.langdy.student.fixture.StudentFixture
import com.langdy.support.KotestControllerTestSupport
import io.kotest.matchers.shouldBe
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

class LessonControllerTest : KotestControllerTestSupport() {

    init {
        Given("수업 신청을 할 때") {
            When("요청 본문 값이 정상이면") {
                val student = StudentFixture.`아이폰 학습자 1`.`엔티티 생성`()
                studentRepository.saveAndFlush(student)

                val headers = HttpHeaders().apply { set("Authorization", student.name) }
                val requestBody = LessonFixture.`수업 신청 1`.`수업 신청 REQUEST 생성`()
                val request = HttpEntity<EnrollLessonRequest>(requestBody, headers)

                val response = restTemplate.exchange(
                    "http://localhost:${port}/lessons",
                    HttpMethod.POST,
                    request,
                    Unit::class.java
                )
                Then("201 상태 코드를 응답 한다.") {
                    response.statusCode shouldBe HttpStatus.CREATED
                }
            }

        }
    }
}
