package kr.co.taek.dev.resilience4j.example.application.port.inbound

interface OpenCircuitUseCase {
    fun open(circuitBreakerName: String)
}

