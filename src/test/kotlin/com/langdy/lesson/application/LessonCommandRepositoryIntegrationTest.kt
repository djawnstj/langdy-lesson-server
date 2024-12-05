package com.langdy.lesson.application

import com.langdy.lesson.fixture.LessonFixture
import com.langdy.support.KotestIntegrationTestSupport
import com.langdy.teacher.fixture.TeacherFixture
import com.langdy.teacher.infra.TeacherRepository
import io.kotest.assertions.throwables.shouldNotThrowAny
import org.springframework.beans.factory.annotation.Autowired

class LessonCommandRepositoryIntegrationTest : KotestIntegrationTestSupport() {

    @Autowired
    private lateinit var repository: LessonCommandRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    init {
        Given("Lesson 엔티티를 저장할 수 있디.") {
            val teacher = teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

            shouldNotThrowAny {
                repository.save(LessonFixture.`수업 신청 1`.`엔티티 생성`(teacher))
            }
        }
    }
}
