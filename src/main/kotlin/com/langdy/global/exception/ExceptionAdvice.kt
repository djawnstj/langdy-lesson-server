package com.langdy.global.exception

import com.langdy.global.presentation.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {

    @ExceptionHandler(ApplicationException::class)
    fun handleApplicationException(e: ApplicationException): ResponseEntity<ErrorResponse> =
        ErrorResponseEntity(e.errorCode, e)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> =
        ErrorResponseEntity(ErrorCode.NO_CONTENT_HTTP_BODY, e)

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponse> =
        ErrorResponseEntity(ErrorCode.NOT_SUPPORTED_METHOD, e)

    @ExceptionHandler(Throwable::class)
    fun handleThrowable(t: Throwable): ResponseEntity<ErrorResponse> =
        ErrorResponseEntity(ErrorCode.INTERNAL_SEVER_ERROR, t)

    private fun ErrorResponseEntity(
        errorCode: ErrorCode,
        cause: Throwable,
        message: String? = errorCode.message,
    ): ResponseEntity<ErrorResponse> {
        log.error(
            """
                server error
                cause: {}
                message: {}
                errorCode: {}
            """.trimIndent(),
            cause,
            message,
            errorCode
        )
        return ResponseEntity(ErrorResponse(errorCode, message), errorCode.status)
    }

    companion object {
        private val log = LoggerFactory.getLogger(ExceptionAdvice::class.java)
    }
}
