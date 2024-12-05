package com.langdy.auth.application

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.langdy.auth.fake.TestIpBlacklistRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

class IpBlacklistCheckFilterTest : BehaviorSpec({
    val ipBlacklistRepository = TestIpBlacklistRepository()

    val filter = IpBlacklistCheckFilter(ipBlacklistRepository, jacksonObjectMapper())

    afterContainer {
        ipBlacklistRepository.init()
    }

    class MockFilterChain : FilterChain {
        var filterCalled = false

        override fun doFilter(request: ServletRequest, response: ServletResponse) {
            filterCalled = true
        }
    }

    Given("요청한 ip 가 차단되었는지 확인할 때") {
        When("X-Forwarded-For 헤더에 하나의 ip 가 존재하고 해당 ip 가 차단 된 경우") {
            val request = MockHttpServletRequest()
            val response = MockHttpServletResponse()
            val blockedIp = "127.0.0.1"
            request.addHeader("X-Forwarded-For", blockedIp)
            ipBlacklistRepository.blockedIps.add(blockedIp)

            filter.doFilter(request, response, MockFilterChain())

            Then("에러 응답을 반환 한다.") {
                response.status shouldBe HttpStatus.UNAUTHORIZED.value()
            }
        }

        When("X-Forwarded-For 헤더에 두개 이상의 ip 가 존재하고 첫번째 ip 가 차단 된 경우") {
            val request = MockHttpServletRequest()
            val response = MockHttpServletResponse()
            val blockedIp = "127.0.0.1"
            request.addHeader("X-Forwarded-For", "${blockedIp},192.168.0.1")
            ipBlacklistRepository.blockedIps.add(blockedIp)

            filter.doFilter(request, response, MockFilterChain())

            Then("에러 응답을 반환 한다.") {
                response.status shouldBe HttpStatus.UNAUTHORIZED.value()
            }
        }

        When("X-Forwarded-For 헤더에 두개 이상의 ip 가 존재하고 첫번째 이후 ip 가 차단 된 경우") {
            val request = MockHttpServletRequest()
            val filterChain = MockFilterChain()
            val blockedIp = "127.0.0.1"
            request.addHeader("X-Forwarded-For", "192.168.0.1,${blockedIp}")
            ipBlacklistRepository.blockedIps.add(blockedIp)

            filter.doFilter(request, MockHttpServletResponse(), filterChain)

            Then("필터를 통과 한다.") {
                filterChain.filterCalled shouldBe true
            }
        }

        When("Proxy-Client-IP 헤더에 ip 가 차단 된 경우") {
            val request = MockHttpServletRequest()
            val response = MockHttpServletResponse()
            val blockedIp = "127.0.0.1"
            request.addHeader("Proxy-Client-IP", blockedIp)
            ipBlacklistRepository.blockedIps.add(blockedIp)

            filter.doFilter(request, response, MockFilterChain())

            Then("에러 응답을 반환 한다.") {
                response.status shouldBe HttpStatus.UNAUTHORIZED.value()
            }
        }

        When("WL-Proxy-Client-IP 헤더에 ip 가 차단 된 경우") {
            val request = MockHttpServletRequest()
            val response = MockHttpServletResponse()
            val blockedIp = "127.0.0.1"
            request.addHeader("WL-Proxy-Client-IP", blockedIp)
            ipBlacklistRepository.blockedIps.add(blockedIp)

            filter.doFilter(request, response, MockFilterChain())

            Then("에러 응답을 반환 한다.") {
                response.status shouldBe HttpStatus.UNAUTHORIZED.value()
            }
        }

        When("HTTP_CLIENT_IP 헤더에 ip 가 차단 된 경우") {
            val request = MockHttpServletRequest()
            val response = MockHttpServletResponse()
            val blockedIp = "127.0.0.1"
            request.addHeader("HTTP_CLIENT_IP", blockedIp)
            ipBlacklistRepository.blockedIps.add(blockedIp)

            filter.doFilter(request, response, MockFilterChain())

            Then("에러 응답을 반환 한다.") {
                response.status shouldBe HttpStatus.UNAUTHORIZED.value()
            }
        }

        When("HTTP_X_FORWARDED_FOR 헤더에 ip 가 차단 된 경우") {
            val request = MockHttpServletRequest()
            val response = MockHttpServletResponse()
            val blockedIp = "127.0.0.1"
            request.addHeader("HTTP_X_FORWARDED_FOR", blockedIp)
            ipBlacklistRepository.blockedIps.add(blockedIp)

            filter.doFilter(request, response, MockFilterChain())

            Then("에러 응답을 반환 한다.") {
                response.status shouldBe HttpStatus.UNAUTHORIZED.value()
            }
        }

        When("헤더에 ip 가 차단 된 경우") {
            val request = MockHttpServletRequest()
            val response = MockHttpServletResponse()
            val blockedIp = "127.0.0.1"
            request.remoteAddr = blockedIp
            ipBlacklistRepository.blockedIps.add(blockedIp)

            filter.doFilter(request, response, MockFilterChain())

            Then("에러 응답을 반환 한다.") {
                response.status shouldBe HttpStatus.UNAUTHORIZED.value()
            }
        }

        When("프록시를 포함한 모든 IP 가 차단 되지 않은 경우") {
            val request = MockHttpServletRequest()
            val filterChain = MockFilterChain()
            request.addHeader("X-Forwarded-For", "192.168.0.1,192.168.0.2")
            request.addHeader("Proxy-Client-IP", "192.168.0.3")
            request.addHeader("WL-Proxy-Client-IP", "192.168.0.4")
            request.addHeader("HTTP_CLIENT_IP", "192.168.0.5")
            request.addHeader("HTTP_X_FORWARDED_FOR", "192.168.0.6")
            request.remoteAddr = "192.168.0.7"

            filter.doFilter(request, MockHttpServletResponse(), filterChain)

            Then("필터를 통과 한다.") {
                filterChain.filterCalled shouldBe true
            }
        }
    }
})
