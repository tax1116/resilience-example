package kr.co.taek.dev.circuitbreaker.example.application.port.inbound

interface ChangeCircuitUseCase {
    fun change(circuitBreakerName: String, state: String)
}
