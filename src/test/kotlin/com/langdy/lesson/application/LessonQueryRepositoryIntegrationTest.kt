package com.langdy.lesson.application

import com.langdy.lesson.fixture.LessonFixture
import com.langdy.lesson.infra.LessonRepository
import com.langdy.support.KotestIntegrationTestSupport
import com.langdy.teacher.fixture.TeacherFixture
import com.langdy.teacher.infra.TeacherRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

class LessonQueryRepositoryIntegrationTest : KotestIntegrationTestSupport() {

    @Autowired
    private lateinit var lessonQueryRepository: LessonQueryRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    init {
        Given("학습자의 수강 신청 중 시간이 겹치는 수업 신청 내역이 존재하는지 여부를 조회할 때") {
            When("이미 같은 시간에 수업 신청 내역이 있다면") {
                val teacher = teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`(teacher)
                lessonRepository.saveAndFlush(lesson)

                val actual = lessonQueryRepository.existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
                    lesson.studentId,
                    lesson.startAt,
                    lesson.endAt
                )

                Then("true 를 반환 한다.") {
                    actual shouldBe true
                }
            }

            When("종료 시간이 이미 저장된 수업 신청 내역의 시작 시간과 동일하다면") {
                val teacher = teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`(teacher)
                lessonRepository.saveAndFlush(lesson)

                val startAt = LocalDateTime.of(2024, 12, 1, 14, 59, 59)
                val endAt = LocalDateTime.of(2024, 12, 1, 15, 0)

                val actual = lessonQueryRepository.existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
                    lesson.studentId,
                    startAt,
                    endAt
                )

                Then("true 를 반환 한다.") {
                    actual shouldBe true
                }
            }

            When("시작 시간이 이미 저장된 수업 신청 내역의 종료 시간과 동일하다면") {
                val teacher = teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`(teacher)
                lessonRepository.saveAndFlush(lesson)

                val startAt = LocalDateTime.of(2024, 12, 1, 16, 0)
                val endAt = LocalDateTime.of(2024, 12, 1, 16, 0, 1)

                val actual = lessonQueryRepository.existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
                    lesson.studentId,
                    startAt,
                    endAt
                )

                Then("true 를 반환 한다.") {
                    actual shouldBe true
                }
            }

            When("이미 같은 시간에 수업 신청 내역이 없다면") {
                val lesson = LessonFixture.`수업 신청 1`

                val actual = lessonQueryRepository.existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
                    lesson.studentId!!,
                    lesson.startAt!!,
                    lesson.endAt!!
                )

                Then("false 를 반환 한다.") {
                    actual shouldBe false
                }
            }

            When("시작 시간이 이미 저장된 수업 신청 내역의 종료 시간 1초 이후 라면") {
                val teacher = teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`(teacher)
                lessonRepository.saveAndFlush(lesson)

                val startAt = LocalDateTime.of(2024, 12, 1, 16, 0, 1)
                val endAt = LocalDateTime.of(2024, 12, 1, 16, 0, 2)

                val actual = lessonQueryRepository.existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
                    lesson.studentId,
                    startAt,
                    endAt
                )

                Then("false 를 반환 한다.") {
                    actual shouldBe false
                }
            }

            When("종료 시간이 이미 저장된 수업 신청 내역의 시작 시간 1초 이전 이라면") {
                val teacher = teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`(teacher)
                lessonRepository.saveAndFlush(lesson)

                val startAt = LocalDateTime.of(2024, 12, 1, 14, 59, 58)
                val endAt = LocalDateTime.of(2024, 12, 1, 14, 59, 59)

                val actual = lessonQueryRepository.existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
                    lesson.studentId,
                    startAt,
                    endAt
                )

                Then("false 를 반환 한다.") {
                    actual shouldBe false
                }
            }
        }

        Given("선생님의 수강 신청 중 시간이 겹치는 수업 신청 내역이 존재하는지 여부를 조회할 때") {
            When("이미 같은 시간에 수업 신청 내역이 있다면") {
                val teacher = teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`(teacher)
                lessonRepository.saveAndFlush(lesson)

                val actual = lessonQueryRepository.existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
                    lesson.getTeacherId(),
                    lesson.startAt,
                    lesson.endAt
                )

                Then("true 를 반환 한다.") {
                    actual shouldBe true
                }
            }

            When("종료 시간이 이미 저장된 수업 신청 내역의 시작 시간과 동일하다면") {
                val teacher = teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`(teacher)
                lessonRepository.saveAndFlush(lesson)

                val endAt = LocalDateTime.of(2024, 12, 1, 15, 0)
                val startAt = LocalDateTime.of(2024, 12, 1, 14, 59, 59)

                val actual = lessonQueryRepository.existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
                    lesson.getTeacherId(),
                    startAt,
                    endAt
                )

                Then("true 를 반환 한다.") {
                    actual shouldBe true
                }
            }

            When("시작 시간이 이미 저장된 수업 신청 내역의 종료 시간과 동일하다면") {
                val teacher = teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`(teacher)
                lessonRepository.saveAndFlush(lesson)

                val startAt = LocalDateTime.of(2024, 12, 1, 16, 0)
                val endAt = LocalDateTime.of(2024, 12, 1, 16, 0, 1)

                val actual = lessonQueryRepository.existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
                    lesson.getTeacherId(),
                    startAt,
                    endAt
                )

                Then("true 를 반환 한다.") {
                    actual shouldBe true
                }
            }

            When("이미 같은 시간에 수업 신청 내역이 없다면") {
                val lesson = LessonFixture.`수업 신청 1`

                val actual = lessonQueryRepository.existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
                    lesson.teacherId!!,
                    lesson.startAt!!,
                    lesson.endAt!!
                )

                Then("false 를 반환 한다.") {
                    actual shouldBe false
                }
            }

            When("시작 시간이 이미 저장된 수업 신청 내역의 종료 시간 1초 이후 라면") {
                val teacher = teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`(teacher)
                lessonRepository.saveAndFlush(lesson)

                val startAt = LocalDateTime.of(2024, 12, 1, 16, 0, 1)
                val endAt = LocalDateTime.of(2024, 12, 1, 16, 0, 2)

                val actual = lessonQueryRepository.existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
                    lesson.getTeacherId(),
                    startAt,
                    endAt
                )

                Then("false 를 반환 한다.") {
                    actual shouldBe false
                }
            }

            When("종료 시간이 이미 저장된 수업 신청 내역의 시작 시간 1초 이전 이라면") {
                val teacher = teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())

                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`(teacher)
                lessonRepository.saveAndFlush(lesson)

                val startAt = LocalDateTime.of(2024, 12, 1, 14, 59, 58)
                val endAt = LocalDateTime.of(2024, 12, 1, 14, 59, 59)

                val actual = lessonQueryRepository.existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(
                    lesson.getTeacherId(),
                    startAt,
                    endAt
                )

                Then("false 를 반환 한다.") {
                    actual shouldBe false
                }
            }
        }

        Given("수업 신청 내역 ID 로 수업 신청 내역을 조회할 때") {
            When("수업 신청 내역을 찾을 수 없으면") {
                val actual = lessonQueryRepository.findById(1)

                Then("null 을 반환 한다.") {
                    actual shouldBe null
                }

                When("수업 신청 내역을 찾을 수 있으면") {
                    val teacher = teacherRepository.save(TeacherFixture.`선생님 1`.`엔티티 생성`())
                    lessonRepository.save(LessonFixture.`수업 신청 1`.`엔티티 생성`(teacher))

                    val actual = lessonQueryRepository.findById(1)

                    Then("수업 신청 내역 엔티티를 반환 한다.") {
                        actual shouldNotBe null
                    }
                }
            }
        }
    }
}
