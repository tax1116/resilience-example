package kr.co.taek.dev.resilience4j.example.circuitbreaker.event

import io.github.resilience4j.circuitbreaker.CircuitBreaker.State
import java.time.LocalDateTime

data class ChangedCircuitEvent(
    val circuitBreakerName: String,
    val state: String,
    val publishAt: LocalDateTime,
)