package kr.co.taek.dev.circuitbreaker.example.adapter.outbound.redis

import kr.co.taek.dev.circuitbreaker.example.application.port.outbound.ChangedCircuitEventPublisher
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class RedisChangedChangedCircuitEventPublisher(
    private val redisTemplate: RedisTemplate<String, ChangedCircuitEvent>,
    private val circuitBreakerOpenTopic: ChannelTopic,
) : ChangedCircuitEventPublisher {

    override fun publish(
        circuitBreakerName: String,
        state: String,
        publishAt: LocalDateTime,
    ) {
        redisTemplate.convertAndSend(
            circuitBreakerOpenTopic.topic,
            ChangedCircuitEvent(circuitBreakerName, state, publishAt),
        )
    }
}

data class ChangedCircuitEvent(
    val circuitBreakerName: String,
    val state: String,
    val publishAt: LocalDateTime,
)
