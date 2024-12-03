package com.langdy.student.fixture

import com.langdy.student.domain.MobileOS
import com.langdy.student.domain.Student

enum class StudentFixture(
    val studentName: String?,
    val os: MobileOS?,
) {
    // 정상
    `아이폰 학습자 1`("kim", MobileOS.IOS),

    // 비정상
    `이름 NULL 학습자`(null, MobileOS.ANDROID),
    `모바일 운영체제 NULL 학습자`("kim", null)
    ;

    fun `엔티티 생성`(): Student = Student(studentName, os)
}
