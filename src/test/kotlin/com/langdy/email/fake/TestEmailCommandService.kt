package com.langdy.email.fake

import com.langdy.email.application.EmailCommandService
import com.langdy.email.application.command.SendEmailCommand

class TestEmailCommandService : EmailCommandService {
    var isMailSent: Boolean = false

    override fun sendEmail(command: SendEmailCommand) {
        isMailSent = true
    }
}
