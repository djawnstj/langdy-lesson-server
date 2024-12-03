package com.langdy.lesson.application

import com.langdy.lesson.fixture.LessonFixture
import com.langdy.lesson.infra.LessonRepository
import com.langdy.support.KotestIntegrationTestSupport
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

class LessonQueryRepositoryIntegrationTest : KotestIntegrationTestSupport() {

    @Autowired
    private lateinit var lessonQueryRepository: LessonQueryRepository

    @Autowired
    private lateinit var lessonRepository: LessonRepository

    init {
        Given("학습자의 수강 신청 중 시간이 겹치는 수업 신청 내역이 존재하는지 여부를 조회할 때") {
            When("이미 같은 시간에 수업 신청 내역이 있다면") {
                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`()
                lessonRepository.saveAndFlush(lesson)

                val actual = lessonQueryRepository.existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(lesson.studentId, lesson.startAt, lesson.endAt)

                Then("true 를 반환 한다.") {
                    actual shouldBe true
                }
            }

            When("종료 시간이 이미 저장된 수업 신청 내역의 시작 시간과 동일하다면") {
                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`()
                lessonRepository.saveAndFlush(lesson)

                val startAt = LocalDateTime.of(2024, 12, 1, 14, 59, 59)
                val endAt = LocalDateTime.of(2024, 12, 1, 15, 0)

                val actual = lessonQueryRepository.existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(lesson.studentId, startAt, endAt)

                Then("true 를 반환 한다.") {
                    actual shouldBe true
                }
            }

            When("시작 시간이 이미 저장된 수업 신청 내역의 종료 시간과 동일하다면") {
                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`()
                lessonRepository.saveAndFlush(lesson)

                val startAt = LocalDateTime.of(2024, 12, 1, 16, 0)
                val endAt = LocalDateTime.of(2024, 12, 1, 16, 0, 1)

                val actual = lessonQueryRepository.existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(lesson.studentId, startAt, endAt)

                Then("true 를 반환 한다.") {
                    actual shouldBe true
                }
            }

            When("이미 같은 시간에 수업 신청 내역이 없다면") {
                val lesson = LessonFixture.`수업 신청 1`

                val actual = lessonQueryRepository.existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(lesson.studentId!!, lesson.startAt!!, lesson.endAt!!)

                Then("false 를 반환 한다.") {
                    actual shouldBe false
                }
            }

            When("시작 시간이 이미 저장된 수업 신청 내역의 종료 시간 1초 이후 라면") {
                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`()
                lessonRepository.saveAndFlush(lesson)

                val startAt = LocalDateTime.of(2024, 12, 1, 16, 0, 1)
                val endAt = LocalDateTime.of(2024, 12, 1, 16, 0, 2)

                val actual = lessonQueryRepository.existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(lesson.studentId, startAt, endAt)

                Then("false 를 반환 한다.") {
                    actual shouldBe false
                }
            }

            When("종료 시간이 이미 저장된 수업 신청 내역의 시작 시간 1초 이전 이라면") {
                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`()
                lessonRepository.saveAndFlush(lesson)

                val startAt = LocalDateTime.of(2024, 12, 1, 14, 59, 58)
                val endAt = LocalDateTime.of(2024, 12, 1, 14, 59, 59)

                val actual = lessonQueryRepository.existsByStudentIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(lesson.studentId, startAt, endAt)

                Then("false 를 반환 한다.") {
                    actual shouldBe false
                }
            }
        }

        Given("선생님의 수강 신청 중 시간이 겹치는 수업 신청 내역이 존재하는지 여부를 조회할 때") {
            When("이미 같은 시간에 수업 신청 내역이 있다면") {
                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`()
                lessonRepository.saveAndFlush(lesson)

                val actual = lessonQueryRepository.existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(lesson.teacherId, lesson.startAt, lesson.endAt)

                Then("true 를 반환 한다.") {
                    actual shouldBe true
                }
            }

            When("종료 시간이 이미 저장된 수업 신청 내역의 시작 시간과 동일하다면") {
                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`()
                lessonRepository.saveAndFlush(lesson)

                val endAt = LocalDateTime.of(2024, 12, 1, 15, 0)
                val startAt = LocalDateTime.of(2024, 12, 1, 14, 59, 59)

                val actual = lessonQueryRepository.existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(lesson.teacherId, startAt, endAt)

                Then("true 를 반환 한다.") {
                    actual shouldBe true
                }
            }

            When("시작 시간이 이미 저장된 수업 신청 내역의 종료 시간과 동일하다면") {
                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`()
                lessonRepository.saveAndFlush(lesson)

                val startAt = LocalDateTime.of(2024, 12, 1, 16, 0)
                val endAt = LocalDateTime.of(2024, 12, 1, 16, 0, 1)

                val actual = lessonQueryRepository.existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(lesson.teacherId, startAt, endAt)

                Then("true 를 반환 한다.") {
                    actual shouldBe true
                }
            }

            When("이미 같은 시간에 수업 신청 내역이 없다면") {
                val lesson = LessonFixture.`수업 신청 1`

                val actual = lessonQueryRepository.existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(lesson.teacherId!!, lesson.startAt!!, lesson.endAt!!)

                Then("false 를 반환 한다.") {
                    actual shouldBe false
                }
            }

            When("시작 시간이 이미 저장된 수업 신청 내역의 종료 시간 1초 이후 라면") {
                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`()
                lessonRepository.saveAndFlush(lesson)

                val startAt = LocalDateTime.of(2024, 12, 1, 16, 0, 1)
                val endAt = LocalDateTime.of(2024, 12, 1, 16, 0, 2)

                val actual = lessonQueryRepository.existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(lesson.teacherId, startAt, endAt)

                Then("false 를 반환 한다.") {
                    actual shouldBe false
                }
            }

            When("종료 시간이 이미 저장된 수업 신청 내역의 시작 시간 1초 이전 이라면") {
                val lesson = LessonFixture.`수업 신청 1`.`엔티티 생성`()
                lessonRepository.saveAndFlush(lesson)

                val startAt = LocalDateTime.of(2024, 12, 1, 14, 59, 58)
                val endAt = LocalDateTime.of(2024, 12, 1, 14, 59, 59)

                val actual = lessonQueryRepository.existsByTeacherIdAndEndAtGreaterThanEqualAndStartAtLessThanEqual(lesson.teacherId, startAt, endAt)

                Then("false 를 반환 한다.") {
                    actual shouldBe false
                }
            }
        }
    }
}
