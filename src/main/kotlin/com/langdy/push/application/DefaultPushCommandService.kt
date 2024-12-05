package com.langdy.push.application

import com.langdy.push.application.command.SendPushCommand
import com.langdy.push.application.dto.DefaultPushResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.util.retry.Retry
import java.time.Duration

@Service
class DefaultPushCommandService(
    private val pushWebClient: WebClient,
) : PushCommandService {
    override fun sendPush(command: SendPushCommand) {
        pushWebClient.post()
            .retrieve()
            .bodyToMono(DefaultPushResponse::class.java)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)))
            .subscribe(
                {
                    log.info("앱 푸시 전송이 성공했습니다.")
                },
                { error ->
                    log.error("앱 푸시를 전송하지 못하였습니다 - {}", error.message)
                }
            )
    }

    companion object {
        private val log = LoggerFactory.getLogger(DefaultPushCommandService::class.java)
    }
}
