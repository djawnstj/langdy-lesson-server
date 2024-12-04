package com.langdy.lesson.application

import com.langdy.lesson.fixture.LessonFixture
import com.langdy.lesson.infra.LessonRepository
import com.langdy.support.KotestIntegrationTestSupport
import com.langdy.teacher.fixture.TeacherFixture
import com.langdy.teacher.infra.TeacherRepository
import io.mockk.every
import io.mockk.mockkStatic
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

class LessonCommandServiceIntegrationTest : KotestIntegrationTestSupport() {

    @Autowired
    private lateinit var service: LessonCommandService

    @Autowired
    private lateinit var repository: LessonRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    init {
        Given("새로운 수업 신청을 할 때") {
            When("겹치는 시간의 수업 신청 내역이 없으면") {
                teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

                Then("수업 신청을 할 수 있다.") {
                    service.enroll(LessonFixture.`수업 신청 1`.`수업 신청 COMMAND 생성`())
                }
            }
        }

        Given("예약한 수업을 취소할 때") {
            When("수업 취소 학습자와 수업 신청 학습자가 일치하면") {
                val teacher = teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

                repository.save(LessonFixture.`수업 신청 1`.`엔티티 생성`(teacher))

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
