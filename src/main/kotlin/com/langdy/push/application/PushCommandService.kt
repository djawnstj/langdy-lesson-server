package com.langdy.push.application

import com.langdy.push.application.command.SendPushCommand

interface PushCommandService {
    fun sendPush(command: SendPushCommand)
}
