package com.langdy.auth.config

import com.langdy.auth.application.UserDetailsService
import com.langdy.auth.presentation.LoginUserArgumentResolver
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
}
