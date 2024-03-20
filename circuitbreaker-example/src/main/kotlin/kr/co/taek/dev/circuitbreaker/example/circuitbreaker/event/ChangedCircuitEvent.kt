package kr.co.taek.dev.circuitbreaker.example.circuitbreaker.event

import java.time.LocalDateTime

data class ChangedCircuitEvent(
    val circuitBreakerName: String,
    val state: String,
    val publishAt: LocalDateTime,
)
