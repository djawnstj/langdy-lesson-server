package com.langdy.teacher.fixture

import com.langdy.teacher.domain.Teacher

enum class TeacherFixture(
    val teacherName: String,
    val email: String,
) {
    // 정상
    `선생님 1`("선생님1", "email@domain.com"),
    ;

    fun `엔티티 생성`(): Teacher = Teacher(teacherName, email)
}
