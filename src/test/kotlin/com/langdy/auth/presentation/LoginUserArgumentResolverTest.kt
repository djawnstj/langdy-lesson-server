package com.langdy.auth.presentation

import com.langdy.auth.application.UserDetails
import com.langdy.auth.fake.TestUserDetailsService
import com.langdy.global.exception.ApplicationException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.throwable.shouldHaveMessage
import io.mockk.every
import io.mockk.mockk
import org.springframework.core.MethodParameter
import org.springframework.web.context.request.NativeWebRequest

class LoginUserArgumentResolverTest : BehaviorSpec({

    val userDetailsService = TestUserDetailsService()
    val argumentResolver = LoginUserArgumentResolver(userDetailsService)

    Given("파라미터 바인딩 가능 여부를 확인할때") {
        When("파라미터에 LoginUser 애너테이션이 있다면") {
            val methodParameter: MethodParameter = mockk()
            every { methodParameter.hasParameterAnnotation(LoginUser::class.java) } returns true

            val actual = argumentResolver.supportsParameter(methodParameter)

            Then("true 를 반환 한다.") {
                actual shouldBe true
            }
        }

        When("파라미터에 LoginUser 애너테이션이 없다면") {
            val methodParameter: MethodParameter = mockk()
            every { methodParameter.hasParameterAnnotation(LoginUser::class.java) } returns false

            val actual = argumentResolver.supportsParameter(methodParameter)

            Then("false 를 반환 한다.") {
                actual shouldBe false
            }
        }
    }

    Given("파라미터를 바인딩 할때") {
        When("요청 헤더에 Authorization 가 없다면") {
            val request = mockk<NativeWebRequest>()
            every { request.getHeader("Authorization") } returns null

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    argumentResolver.resolveArgument(mockk(), mockk(), request, mockk())
                } shouldHaveMessage "접근할 수 없는 요청입니다."
            }
        }

        When("Authorization 로 찾은 userDetails 가 없다면") {
            val request = mockk<NativeWebRequest>()
            every { request.getHeader("Authorization") } returns "username"

            Then("예외를 던진다.") {
                shouldThrow<ApplicationException> {
                    argumentResolver.resolveArgument(mockk(), mockk(), request, mockk())
                } shouldHaveMessage "접근할 수 없는 요청입니다."
            }
        }

        When("Authorization 로 찾은 userDetails 를 찾아") {
            val request = mockk<NativeWebRequest>()
            every { request.getHeader("Authorization") } returns "username"
            userDetailsService.users["username"] = UserDetails(1, "username")

            val actual = argumentResolver.resolveArgument(mockk(), mockk(), request, mockk())

            Then("반환 한다.") {
                actual shouldNotBe null
            }
        }
    }
})
