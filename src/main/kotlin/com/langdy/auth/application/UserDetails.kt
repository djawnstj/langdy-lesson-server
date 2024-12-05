package com.langdy.auth.application

import java.security.Principal

class UserDetails(
    val id: Long,
    private val username: String,
) : Principal {
    override fun getName(): String = username
}
