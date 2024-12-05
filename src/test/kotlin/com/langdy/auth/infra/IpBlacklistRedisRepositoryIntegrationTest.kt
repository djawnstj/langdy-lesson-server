package com.langdy.auth.infra

import com.langdy.support.KotestIntegrationTestSupport
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

class IpBlacklistRedisRepositoryIntegrationTest : KotestIntegrationTestSupport() {

    @Autowired
    private lateinit var ipBlacklistRedisRepository: IpBlacklistRedisRepository

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, String>

    init {
        Given("IP 가 블랙리스트에 포함되어있는지 확인할때") {
            When("블랙리스트에 포함된 IP 라면") {
                val blockedIp = "127.0.0.1"
                redisTemplate.opsForValue().set("BLOCKED_IP::$blockedIp", blockedIp)

                val actual = ipBlacklistRedisRepository.isIpBlocked(blockedIp)

                Then("true 를 반환 한다.") {
                    actual shouldBe true
                }
            }

            When("블랙리스트에 포함되지 않은 IP 라면") {
                val ip = "127.0.0.1"

                val actual = ipBlacklistRedisRepository.isIpBlocked(ip)

                Then("false 를 반환 한다.") {
                    actual shouldBe false
                }
            }
        }
    }
}
