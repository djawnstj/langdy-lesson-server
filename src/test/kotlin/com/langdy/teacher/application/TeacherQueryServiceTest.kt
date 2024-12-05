package com.langdy.teacher.application

import com.langdy.global.exception.ApplicationException
import com.langdy.teacher.application.query.GetTeacherQuery
import com.langdy.teacher.fake.TestTeacherRepository
import com.langdy.teacher.fixture.TeacherFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe

class TeacherQueryServiceTest : BehaviorSpec({
    val teacherRepository = TestTeacherRepository()
    val teacherQueryService = TeacherQueryService(teacherRepository)

    afterContainer {
        teacherRepository.init()
    }

    Given("선생님 ID 로 선생님 엔티티를 가져올 때") {
        When("요청한 선생님 ID 가 null 이면") {
            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    teacherQueryService.getTeacher(GetTeacherQuery(null))
                }
            }
        }

        When("선생님 ID 와 일치하는 선생님 엔티티가 없으면") {
            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    teacherQueryService.getTeacher(GetTeacherQuery(1))
                }
            }
        }

        When("선생님 ID 와 일치하는 선생님 엔티티가 있으면") {
            teacherRepository.teachers[1] = TeacherFixture.`선생님 1`.`엔티티 생성`()

            val actual = teacherQueryService.getTeacher(GetTeacherQuery(1))

            Then("선생님 엔티티를 반환 한다.") {
                actual shouldNotBe null
            }
        }
    }
})
