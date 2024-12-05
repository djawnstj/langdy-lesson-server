package com.langdy.push.fake

import com.langdy.push.application.PushCommandService
import com.langdy.push.application.command.SendPushCommand

class TestPushCommandService : PushCommandService {

    var isPushed = false

    override fun sendPush(command: SendPushCommand) {
        this.isPushed = true
    }
}
