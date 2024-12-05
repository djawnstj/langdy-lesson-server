package com.langdy.auth.fake

import com.langdy.auth.application.IpBlacklistRepository

class TestIpBlacklistRepository : IpBlacklistRepository {

    val blockedIps = mutableListOf<String>()

    override fun isIpBlocked(ip: String): Boolean {
        return blockedIps.contains(ip)
    }

    fun init() {
        this.blockedIps.clear()
    }
}
