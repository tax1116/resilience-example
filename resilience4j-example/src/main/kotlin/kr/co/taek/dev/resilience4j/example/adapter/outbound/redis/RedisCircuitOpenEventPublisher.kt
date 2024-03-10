package kr.co.taek.dev.resilience4j.example.adapter.outbound.redis

import kr.co.taek.dev.resilience4j.example.application.port.outbound.CircuitOpenEventPublisher
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class RedisCircuitOpenEventPublisher(
    private val redisTemplate: RedisTemplate<String, OpenEvent>,
    private val circuitBreakerOpenTopic: ChannelTopic,
): CircuitOpenEventPublisher {

    override fun publish(circuitBreakerName: String, publishAt: LocalDateTime) {
        redisTemplate.convertAndSend(circuitBreakerOpenTopic.topic, OpenEvent(circuitBreakerName, publishAt))
    }

}

data class OpenEvent(
    val circuitBreakerName: String,
    val publishAt: LocalDateTime,
)