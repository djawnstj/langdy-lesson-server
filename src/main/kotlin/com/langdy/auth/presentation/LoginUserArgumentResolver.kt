package com.langdy.auth.presentation

import com.langdy.auth.application.UserDetailsService
import com.langdy.global.exception.ApplicationException
import com.langdy.global.exception.ErrorCode
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class LoginUserArgumentResolver(
    private val userDetailsService: UserDetailsService,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.hasParameterAnnotation(LoginUser::class.java)

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any {
        val username = extractAuthHeader(webRequest)

        val userDetails = userDetailsService.loadUserByUsername(username) ?: throw ApplicationException(ErrorCode.INVALID_AUTH)

        return userDetails
    }

    private fun extractAuthHeader(webRequest: NativeWebRequest): String {
        val username = webRequest.getHeader(AUTH_HEADER)

        requireNotNull(username) {
            throw ApplicationException(ErrorCode.INVALID_AUTH)
        }

        return username
    }

    companion object {
        private const val AUTH_HEADER = "Authorization"
    }
}
