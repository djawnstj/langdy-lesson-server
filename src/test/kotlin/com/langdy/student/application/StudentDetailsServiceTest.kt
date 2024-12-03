package com.langdy.student.application

import com.langdy.student.fake.TestStudentQueryRepository
import com.langdy.student.fixture.StudentFixture
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class StudentDetailsServiceTest : BehaviorSpec({
    val studentRepository = TestStudentQueryRepository()
    val studentDetailsService = StudentDetailsService(studentRepository)

    afterContainer {
        studentRepository.init()
    }

    Given("학습자 이름으로 저장된 학습자 엔티티를 찾을 때") {
        When("동일한 이름의 학습자가 존재하면") {
            val student = StudentFixture.`아이폰 학습자 1`.`엔티티 생성`()
            studentRepository.students[0] = student

            val actual = studentDetailsService.loadUserByUsername(student.name)

            Then("UserDetails 를 반환 한다") {
                actual shouldNotBe null
            }
        }

        When("동일한 이름의 학습자가 존재하지 않으면") {
            val student = StudentFixture.`아이폰 학습자 1`.`엔티티 생성`()

            val actual = studentDetailsService.loadUserByUsername(student.name)

            Then("null 을 반환 한다") {
                actual shouldBe null
            }
        }
    }
})
