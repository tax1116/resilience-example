package kr.co.taek.dev.resilience4j.example.adapter.outbound.redis

import io.github.resilience4j.circuitbreaker.CircuitBreaker.State
import kr.co.taek.dev.resilience4j.example.application.port.outbound.ChangedCircuitEventPublisher
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class RedisChangedChangedCircuitEventPublisher(
    private val redisTemplate: RedisTemplate<String, ChangedCircuitEvent>,
    private val circuitBreakerOpenTopic: ChannelTopic,
): ChangedCircuitEventPublisher {

    override fun publish(
        circuitBreakerName: String,
        state: String,
        publishAt: LocalDateTime
    ) {
        redisTemplate.convertAndSend(
            circuitBreakerOpenTopic.topic,
            ChangedCircuitEvent(circuitBreakerName, state, publishAt)
        )
    }

}

data class ChangedCircuitEvent(
    val circuitBreakerName: String,
    val state: String,
    val publishAt: LocalDateTime,
)