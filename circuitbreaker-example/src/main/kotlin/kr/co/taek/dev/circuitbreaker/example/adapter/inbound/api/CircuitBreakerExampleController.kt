package kr.co.taek.dev.circuitbreaker.example.adapter.inbound.api

import kr.co.taek.dev.circuitbreaker.example.application.port.inbound.ChangeCircuitUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CircuitBreakerExampleController(
    private val changeCircuitUseCase: ChangeCircuitUseCase,
) {

    @PostMapping("/circuit-breaker/change")
    fun open(@RequestBody request: ChangeRequest): ResponseEntity<String> {
        changeCircuitUseCase.change(request.circuitBreakerName, request.state)
        return ResponseEntity.ok("OK")
    }

    data class ChangeRequest(
        val circuitBreakerName: String,
        val state: String,
    )
}
