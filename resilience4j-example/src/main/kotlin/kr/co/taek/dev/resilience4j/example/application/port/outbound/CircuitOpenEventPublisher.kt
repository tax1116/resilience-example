package kr.co.taek.dev.resilience4j.example.application.port.outbound

import java.time.LocalDateTime

interface CircuitOpenEventPublisher {
    fun publish(circuitBreakerName: String, publishAt: LocalDateTime)
}
