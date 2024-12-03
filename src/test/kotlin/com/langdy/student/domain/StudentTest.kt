package com.langdy.student.domain

import com.langdy.global.exception.ApplicationException
import com.langdy.student.fixture.StudentFixture
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.throwable.shouldHaveMessage

class StudentTest: BehaviorSpec({
    Given("학습자 엔티티를 생성할 때") {
        When("학습자 이름, 모바일 운영체제 정보 값이 올바르면") {
            val studentFixture = StudentFixture.`아이폰 학습자 1`

            Then("학습자 엔티티를 생성할 수 있다.") {
                shouldNotThrowAny {
                    Student(studentFixture.studentName, studentFixture.os)
                }
            }
        }

        When("학습자 이름이 null 이면") {
            val studentFixture = StudentFixture.`이름 NULL 학습자`

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    Student(studentFixture.studentName, studentFixture.os)
                } shouldHaveMessage "학습자 이름이 없습니다."
            }
        }

        When("모바일 운영체제 정보가 null 이면") {
            val studentFixture = StudentFixture.`모바일 운영체제 NULL 학습자`

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    Student(studentFixture.studentName, studentFixture.os)
                } shouldHaveMessage "학습자 모바일 기기 운영체제 정보가 없습니다."
            }
        }
    }
})
