package com.langdy.global.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseEntity {
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        protected set
}
