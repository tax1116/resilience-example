package kr.co.taek.dev.resilience4j.example.adapter.inbound.redis

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.co.taek.dev.resilience4j.example.application.port.inbound.OpenCircuitUseCase
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class RedisOpenEventListener(
    private val openCircuitUseCase: OpenCircuitUseCase
): MessageListenerAdapter() {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        log.info { "Received message: ${String(message.body)}" }
        openCircuitUseCase.open("receiver")
    }
}