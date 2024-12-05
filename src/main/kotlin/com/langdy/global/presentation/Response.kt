package com.langdy.global.presentation

import com.langdy.global.exception.ErrorCode
import org.springframework.http.HttpStatus

typealias SuccessResponse<T> = Response<T>
typealias ErrorResponse = Response<*>

data class Response<T>(
    val code: Int,
    val message: String,
    val data: T?,
)

fun <T> SuccessResponse(
    data: T,
    status: HttpStatus = HttpStatus.OK,
): SuccessResponse<T> =
    Response(
        code = status.value(),
        message = status.reasonPhrase,
        data = data
    )

fun ErrorResponse(
    errorCode: ErrorCode,
    message: String? = null,
): ErrorResponse =
    Response(
        code = errorCode.status.value(),
        message = if (message.isNullOrBlank()) errorCode.message else message,
        data = null
    )
