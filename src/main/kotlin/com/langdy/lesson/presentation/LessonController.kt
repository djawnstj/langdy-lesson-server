package com.langdy.lesson.presentation

import com.langdy.auth.application.UserDetails
import com.langdy.auth.presentation.LoginUser
import com.langdy.lesson.presentation.dto.request.EnrollLessonRequest
import com.langdy.lesson.application.LessonCommandService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class LessonController(
    private val lessonCommandService: LessonCommandService,
) {

    @PostMapping("/lessons")
    @ResponseStatus(HttpStatus.CREATED)
    fun enrollLesson(@LoginUser user: UserDetails, @RequestBody request: EnrollLessonRequest) {
        lessonCommandService.enroll(request.toCommand(user.id))
    }
}
