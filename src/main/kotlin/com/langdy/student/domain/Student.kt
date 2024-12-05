package com.langdy.student.domain

import com.langdy.global.domain.BaseEntity
import com.langdy.global.exception.ApplicationException
import com.langdy.global.exception.ErrorCode
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class Student(
    name: String?,
    os: MobileOS?,
) : BaseEntity() {
    var name: String = validateName(name)
        protected set
    @field:Enumerated(EnumType.STRING)
    var os: MobileOS = validateOs(os)
        protected set

    private fun validateName(name: String?): String {
        requireNotNull(name) {
            throw ApplicationException(ErrorCode.EMPTY_STUDENT_NAME)
        }

        return name
    }

    private fun validateOs(os: MobileOS?): MobileOS {
        requireNotNull(os) {
            throw ApplicationException(ErrorCode.EMPTY_STUDENT_OS)
        }

        return os
    }
}
