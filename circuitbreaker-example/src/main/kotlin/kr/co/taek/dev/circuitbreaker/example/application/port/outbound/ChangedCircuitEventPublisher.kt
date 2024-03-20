package kr.co.taek.dev.circuitbreaker.example.application.port.outbound

import java.time.LocalDateTime

interface ChangedCircuitEventPublisher {
    fun publish(circuitBreakerName: String, state: String, publishAt: LocalDateTime)
}
