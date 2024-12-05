package com.langdy.student.application

import com.langdy.student.fixture.StudentFixture
import com.langdy.student.infra.StudentRepository
import com.langdy.support.KotestIntegrationTestSupport
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired

class StudentQueryRepositoryIntegrationTest : KotestIntegrationTestSupport() {
    @Autowired
    private lateinit var studentQueryRepository: StudentQueryRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    init {
        Given("학습자 이름으로 학습자 엔티티를 조회할 때") {

            When("같은 이름의 학습자 정보가 있다면") {
                studentRepository.saveAndFlush(StudentFixture.`아이폰 학습자 1`.`엔티티 생성`())

                val actual = studentQueryRepository.findByName(StudentFixture.`아이폰 학습자 1`.studentName!!)

                Then("학습자 엔티티를 반환 한다") {
                    actual shouldNotBe null
                }
            }

            When("같은 이름의 학습자 정보가 없다면") {
                val actual = studentQueryRepository.findByName(StudentFixture.`아이폰 학습자 1`.studentName!!)

                Then("null 을 반환 한다") {
                    actual shouldBe null
                }
            }
        }
    }
}
