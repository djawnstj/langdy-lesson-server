package com.langdy.teacher.application

import com.langdy.support.KotestIntegrationTestSupport
import com.langdy.teacher.fixture.TeacherFixture
import com.langdy.teacher.infra.TeacherRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired

class TeacherQueryRepositoryIntegrationTest : KotestIntegrationTestSupport() {

    @Autowired
    private lateinit var teacherQueryRepository: TeacherQueryRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    init {
        Given("선생님 ID 로 선생님을 조회할 때") {
            When("선생님을 찾을 수 없으면") {
                val actual = teacherQueryRepository.findById(1)

                Then("null 을 반환 한다.") {
                    actual shouldBe null
                }

                When("선생님을 찾을 수 있으면") {
                    teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

                    val actual = teacherQueryRepository.findById(1)

                    Then("선생님 엔티티를 반환 한다.") {
                        actual shouldNotBe null
                    }
                }
            }
        }
    }
}
