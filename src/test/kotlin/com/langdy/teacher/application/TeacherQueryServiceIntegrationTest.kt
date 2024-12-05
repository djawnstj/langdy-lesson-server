package com.langdy.teacher.application

import com.langdy.support.KotestIntegrationTestSupport
import com.langdy.teacher.application.query.GetTeacherQuery
import com.langdy.teacher.fixture.TeacherFixture
import com.langdy.teacher.infra.TeacherRepository
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired

class TeacherQueryServiceIntegrationTest : KotestIntegrationTestSupport() {

    @Autowired
    private lateinit var teacherQueryService: TeacherQueryService

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    init {
        Given("선생님 ID 로 선생님 엔티티를 가져올 때") {
            When("일치하는 선생님 엔티티가 있다면") {
                val teacher = teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

                val actual = teacherQueryService.getTeacher(GetTeacherQuery(teacher.id))

                Then("엔티티를 반환 한다.") {
                    actual shouldNotBe null
                }
            }
        }
    }
}
