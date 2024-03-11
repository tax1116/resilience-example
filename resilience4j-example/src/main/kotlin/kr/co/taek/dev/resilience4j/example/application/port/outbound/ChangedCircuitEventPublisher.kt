package kr.co.taek.dev.resilience4j.example.application.port.outbound

import io.github.resilience4j.circuitbreaker.CircuitBreaker.State
import java.time.LocalDateTime

interface ChangedCircuitEventPublisher {
    fun publish(circuitBreakerName: String, state: String, publishAt: LocalDateTime)
}
