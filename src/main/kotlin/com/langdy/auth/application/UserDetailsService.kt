package com.langdy.auth.application

interface UserDetailsService {
    fun loadUserByUsername(username: String): UserDetails?
}
