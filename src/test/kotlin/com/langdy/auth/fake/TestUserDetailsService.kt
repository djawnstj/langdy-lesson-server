package com.langdy.auth.fake

import com.langdy.auth.application.UserDetails
import com.langdy.auth.application.UserDetailsService

class TestUserDetailsService : UserDetailsService {

    val users: MutableMap<String, UserDetails> = mutableMapOf()

    override fun loadUserByUsername(username: String): UserDetails? {
        return users[username]
    }

    fun init() {
        this.users.clear()
    }
}
