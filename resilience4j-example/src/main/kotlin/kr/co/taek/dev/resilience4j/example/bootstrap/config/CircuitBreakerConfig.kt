package kr.co.taek.dev.resilience4j.example.bootstrap.config

import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import kr.co.taek.dev.resilience4j.example.circuitbreaker.event.CircuitOpenEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

private val log = KotlinLogging.logger {}

@Configuration
class CircuitBreakerConfig(
    private val circuitBreakerRegistry: CircuitBreakerRegistry,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {

    @Bean
    fun exampleCircuitBreaker(): CircuitBreaker {
        val cbName = "example"
        val cb = circuitBreakerRegistry.circuitBreaker(cbName)
        cb.eventPublisher
            .onStateTransition {
                val fromState = it.stateTransition.fromState
                val toState = it.stateTransition.toState
                log.info { "[${cb.name}] CircuitBreaker state changed $fromState to $toState" }

                if (toState == CircuitBreaker.State.OPEN) {
                    applicationEventPublisher.publishEvent(CircuitOpenEvent(cbName, LocalDateTime.now()))
                }
            }
        return cb
    }

    @Bean
    fun receiverCircuitBreaker(): CircuitBreaker {
        val cbName = "receiver"
        val cb = circuitBreakerRegistry.circuitBreaker(cbName)
        cb.eventPublisher
            .onStateTransition {
                val fromState = it.stateTransition.fromState
                val toState = it.stateTransition.toState
                log.info { "[${cb.name}] CircuitBreaker state changed $fromState to $toState" }
            }
        return cb
    }
}