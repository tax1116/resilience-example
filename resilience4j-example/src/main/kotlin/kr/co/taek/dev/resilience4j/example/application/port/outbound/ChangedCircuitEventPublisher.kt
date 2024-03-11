package kr.co.taek.dev.resilience4j.example.application.port.outbound

import java.time.LocalDateTime

interface ChangedCircuitEventPublisher {
    fun publish(circuitBreakerName: String, state: String, publishAt: LocalDateTime)
}
