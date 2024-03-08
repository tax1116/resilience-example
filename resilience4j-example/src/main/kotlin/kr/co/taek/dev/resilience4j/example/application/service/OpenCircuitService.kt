package kr.co.taek.dev.resilience4j.example.application.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import kr.co.taek.dev.resilience4j.example.application.port.inbound.OpenCircuitUseCase
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {  }

@Service
class OpenCircuitService(
    private val circuitBreakerRegistry: CircuitBreakerRegistry
): OpenCircuitUseCase {
    override fun open(circuitBreakerName: String) {
        log.info { "$circuitBreakerName 서킷 브레이커를 OPEN 합니다." }
        val cb = circuitBreakerRegistry.circuitBreaker(circuitBreakerName)
        cb.transitionToOpenState()
    }
}