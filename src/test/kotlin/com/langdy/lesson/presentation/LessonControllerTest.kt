package com.langdy.lesson.presentation

import com.langdy.lesson.fixture.LessonFixture
import com.langdy.lesson.infra.LessonRepository
import com.langdy.lesson.presentation.dto.request.EnrollLessonRequest
import com.langdy.student.fixture.StudentFixture
import com.langdy.student.infra.StudentRepository
import com.langdy.support.KotestControllerTestSupport
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockkStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class LessonControllerTest : KotestControllerTestSupport() {

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

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

        Given("수업 내역을 취소 할 때") {
            When("요청 본문 값이 정상이면") {
                val student = StudentFixture.`아이폰 학습자 1`.`엔티티 생성`()
                studentRepository.saveAndFlush(student)

                val lesson = lessonRepository.saveAndFlush(LessonFixture.`수업 신청 1`.`엔티티 생성`())

                mockkStatic(LocalDateTime::class)
                val fixedTime = LocalDateTime.of(2024, 12, 1, 0, 0)

                every { LocalDateTime.now() } returns fixedTime

                val headers = HttpHeaders().apply { set("Authorization", student.name) }
                val request = HttpEntity<EnrollLessonRequest>(headers)

                val response = restTemplate.exchange(
                    "http://localhost:${port}/lessons/${lesson.id}",
                    HttpMethod.DELETE,
                    request,
                    Unit::class.java
                )

                Then("201 상태 코드를 응답 한다.") {
                    response.statusCode shouldBe HttpStatus.NO_CONTENT
                }
            }
        }
    }
}
