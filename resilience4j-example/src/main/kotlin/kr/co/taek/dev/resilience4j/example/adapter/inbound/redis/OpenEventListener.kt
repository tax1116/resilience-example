package kr.co.taek.dev.resilience4j.example.adapter.inbound.redis

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PostConstruct
import kr.co.taek.dev.resilience4j.example.adapter.outbound.redis.OpenEvent
import kr.co.taek.dev.resilience4j.example.application.port.inbound.OpenCircuitUseCase
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class OpenEventListener(
    private val redissonClient: RedissonClient,
    private val openCircuitUseCase: OpenCircuitUseCase,
) {
    @PostConstruct
    fun init() {
        listen(); // 구독 시작
    }


    fun listen() {
        redissonClient.getTopic("circuit-breaker-open-event")
            .addListener(OpenEvent::class.java) { ch , message ->
                log.info { "[${ch}] CircuitBreaker Open Event: $message" }
                openCircuitUseCase.open("receiver")
            }
    }
}