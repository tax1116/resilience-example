package kr.co.taek.dev.resilience4j.example.circuitbreaker.event

import java.time.LocalDateTime

data class CircuitOpenEvent(
    val circuitBreakerName: String,
    val publishAt: LocalDateTime,
)
