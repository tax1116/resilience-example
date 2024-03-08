package kr.co.taek.dev.resilience4j.example.adapter.inbound.api

import kr.co.taek.dev.resilience4j.example.application.port.inbound.OpenCircuitUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CircuitBreakerExampleController(
    private val openCircuitUseCase: OpenCircuitUseCase
) {

    @PostMapping("/circuit-breaker/open")
    fun open(@RequestBody request: OpenRequest): ResponseEntity<String> {
        openCircuitUseCase.open(request.circuitBreakerName)
        return ResponseEntity.ok("OK")
    }

    data class OpenRequest(
        val circuitBreakerName: String
    )
}