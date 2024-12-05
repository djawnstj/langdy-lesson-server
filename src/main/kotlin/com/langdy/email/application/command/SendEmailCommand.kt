package com.langdy.email.application.command

data class SendEmailCommand(
    val subject: String,
    val body: String,
)
