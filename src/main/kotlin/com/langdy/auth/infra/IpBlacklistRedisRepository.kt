package com.langdy.auth.infra

import com.langdy.auth.application.IpBlacklistRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class IpBlacklistRedisRepository(
    private val redisTemplate: RedisTemplate<String, String>,
) : IpBlacklistRepository {
    override fun isIpBlocked(ip: String): Boolean {
        val key = KEY_PREFIX + ip
        return redisTemplate.hasKey(key)
    }

    companion object {
        private const val KEY_PREFIX = "BLOCKED_IP::"
    }
}
