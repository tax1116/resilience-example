package kr.co.taek.dev.resilience4j.example.adapter.inbound.redis

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import kr.co.taek.dev.resilience4j.example.adapter.outbound.redis.ChangedCircuitEvent
import kr.co.taek.dev.resilience4j.example.application.port.inbound.ChangeCircuitUseCase
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class RedisChangedCircuitEventListener(
    private val changeCircuitUseCase: ChangeCircuitUseCase,
    private val objectMapper: ObjectMapper
): MessageListenerAdapter() {
    override fun onMessage(message: Message, pattern: ByteArray?) {
        log.info { "Received message: ${String(message.body)}" }
        val event = objectMapper.readValue(String(message.body), ChangedCircuitEvent::class.java)
        changeCircuitUseCase.change("receiver", event.state)
    }
}