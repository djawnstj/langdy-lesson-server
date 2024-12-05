package com.langdy.auth.application

interface IpBlacklistRepository {
    fun isIpBlocked(ip: String): Boolean
}
