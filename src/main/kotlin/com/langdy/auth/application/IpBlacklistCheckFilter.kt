package com.langdy.auth.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.langdy.global.exception.ApplicationException
import com.langdy.global.exception.ErrorCode
import com.langdy.global.presentation.ErrorResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets

class IpBlacklistCheckFilter(
    private val ipBlacklistRepository: IpBlacklistRepository,
    private val objectMapper: ObjectMapper,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val ip = extractClientIp(request)

        checkBlockedIp(ip, response)

        filterChain.doFilter(request, response)
    }

    private fun checkBlockedIp(ip: String, response: HttpServletResponse) {
        if (ipBlacklistRepository.isIpBlocked(ip)) {
            sendError(response)
        }
    }

    private fun sendError(response: HttpServletResponse) {
        val errorCode = ErrorCode.UNAUTHORIZED_IP

        response.status = errorCode.status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()
        objectMapper.writeValue(response.writer, ErrorResponse(errorCode))
    }

    private fun extractClientIp(request: HttpServletRequest): String {
        val clientIp = (IP_HEADERS
            .asSequence()
            .map(request::getHeader)
            .firstOrNull(this::hasIpHeader)
            ?: request.remoteAddr)
            .split(",")
            .firstOrNull()

        requireNotNull(clientIp) {
            throw ApplicationException(ErrorCode.UNAUTHORIZED_IP)
        }

        return clientIp
    }

    private fun hasIpHeader(it: String?) = !it.isNullOrEmpty() && !it.equals("unknown", ignoreCase = true)

    companion object {
        private val IP_HEADERS = listOf(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
        )
    }
}
