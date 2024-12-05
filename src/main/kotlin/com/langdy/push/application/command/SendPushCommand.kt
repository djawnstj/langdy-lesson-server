package com.langdy.push.application.command

data class SendPushCommand(
    val subject: String,
    val message: String,
)
