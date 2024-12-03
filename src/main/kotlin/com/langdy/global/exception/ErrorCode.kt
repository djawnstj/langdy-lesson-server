package com.langdy.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String,
) {
    // COURSE
    EMPTY_COURSE_ID(HttpStatus.BAD_REQUEST, "수업 ID가 없습니다."),

    // TEACHER
    EMPTY_TEACHER_ID(HttpStatus.BAD_REQUEST, "선생님 ID가 없습니다."),

    // STUDENT
    EMPTY_STUDENT_ID(HttpStatus.BAD_REQUEST, "학습자 ID가 없습니다."),
    EMPTY_STUDENT_NAME(HttpStatus.BAD_REQUEST, "학습자 이름이 없습니다."),
    EMPTY_STUDENT_OS(HttpStatus.BAD_REQUEST, "학습자 모바일 기기 운영체제 정보가 없습니다."),

    // LESSON
    EMPTY_LESSON_STATUS(HttpStatus.BAD_REQUEST, "수업 신청 상태값이 없습니다."),
    EMPTY_LESSON_START_TIME(HttpStatus.BAD_REQUEST, "수업 신청 시작 시간이 없습니다."),
    EMPTY_LESSON_END_TIME(HttpStatus.BAD_REQUEST, "수업 신청 종료 시간이 없습니다."),
    EXISTS_STUDENT_LESSON_TIME(HttpStatus.BAD_REQUEST, "해당 시간에 이미 예약된 수업이 있습니다."),
    EXISTS_TEACHER_LESSON_TIME(HttpStatus.BAD_REQUEST, "선생님이 다른 수업이 예약되어 있습니다."),

    // AUTH
    INVALID_AUTH(HttpStatus.UNAUTHORIZED, "접근할 수 없는 요청입니다."),

    // COMMON
    INTERNAL_SEVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "관리자에게 문의 바랍니다."),
    NO_CONTENT_HTTP_BODY(HttpStatus.BAD_REQUEST, "정상적인 요청 본문이 아닙니다."),
    NOT_SUPPORTED_METHOD(HttpStatus.METHOD_NOT_ALLOWED, "정상적인 요청이 아닙니다."),
}
