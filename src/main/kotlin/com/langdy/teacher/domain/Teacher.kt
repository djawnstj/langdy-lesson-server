package com.langdy.teacher.domain

import com.langdy.global.domain.BaseEntity
import jakarta.persistence.Entity

@Entity
class Teacher(
    val name: String,
    val email: String,
) : BaseEntity()
