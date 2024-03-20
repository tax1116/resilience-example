package kr.co.taek.dev.circuitbreaker.example.application.service

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import kr.co.taek.dev.circuitbreaker.example.application.port.inbound.ChangeCircuitUseCase
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger { }

@Service
class ChangeCircuitService(
    private val circuitBreakerRegistry: CircuitBreakerRegistry,
) : ChangeCircuitUseCase {
    override fun change(circuitBreakerName: String, state: String) {
        log.info { "$circuitBreakerName 서킷 브레이커를 $state 합니다." }
        val cb = circuitBreakerRegistry.circuitBreaker(circuitBreakerName)

        val toState = CircuitBreaker.State.valueOf(state)
        when (toState) {
            CircuitBreaker.State.CLOSED -> cb.transitionToClosedState()
            CircuitBreaker.State.OPEN -> cb.transitionToOpenState()
            else -> log.error { "지원하지 않는 서킷 브레이커 상태입니다." }
        }
    }
}
