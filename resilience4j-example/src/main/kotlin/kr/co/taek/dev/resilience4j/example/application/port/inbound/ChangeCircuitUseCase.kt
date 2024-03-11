package kr.co.taek.dev.resilience4j.example.application.port.inbound

interface ChangeCircuitUseCase {
    fun change(circuitBreakerName: String, state: String)
}
