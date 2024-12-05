package com.langdy.auth.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.langdy.auth.application.IpBlacklistCheckFilter
import com.langdy.auth.application.IpBlacklistRepository
import com.langdy.auth.application.UserDetailsService
import com.langdy.auth.presentation.LoginUserArgumentResolver
import jakarta.servlet.Filter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AuthConfig(
    private val userDetailsService: UserDetailsService
): WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(LoginUserArgumentResolver(userDetailsService))
    }

    @Bean
    fun authCustomFilterRegistration(ipBlacklistRepository: IpBlacklistRepository, objectMapper: ObjectMapper): FilterRegistrationBean<Filter> {
        val registration = FilterRegistrationBean<Filter>()
        registration.filter = IpBlacklistCheckFilter(ipBlacklistRepository, objectMapper)
        registration.order = IP_BLACKLIST_CHECKER_FILTER_ORDER
        registration.urlPatterns = listOf(IP_BLACKLIST_CHECKER_FILTER_PATTERN)

        return registration
    }

    companion object {
        private const val IP_BLACKLIST_CHECKER_FILTER_ORDER = 0
        private const val IP_BLACKLIST_CHECKER_FILTER_PATTERN = "/*"

    }
}
