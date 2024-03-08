package kr.co.taek.dev.resilience4j.example.adapter.outbound.redis

import kr.co.taek.dev.resilience4j.example.application.port.outbound.CircuitOpenEventPublisher
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class RedissonCircuitOpenEventPublisher(
    private val redissonClient: RedissonClient
): CircuitOpenEventPublisher {
    override fun publish(circuitBreakerName: String, publishAt: LocalDateTime) {
        redissonClient.getTopic("circuit-breaker-open-event")
            .publish(OpenEvent(circuitBreakerName, publishAt))
    }

}

data class OpenEvent(
    val circuitBreakerName: String,
    val publishAt: LocalDateTime,
)