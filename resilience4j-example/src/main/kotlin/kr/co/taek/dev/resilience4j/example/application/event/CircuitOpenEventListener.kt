package kr.co.taek.dev.resilience4j.example.application.event

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.co.taek.dev.resilience4j.example.application.port.outbound.ChangedCircuitEventPublisher
import kr.co.taek.dev.resilience4j.example.circuitbreaker.event.ChangedCircuitEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger { }

@Component
class CircuitOpenEventListener(
    private val changedCircuitEventPublisher: ChangedCircuitEventPublisher,
) {

    @EventListener
    fun handle(event: ChangedCircuitEvent) {
        log.info { "${event.circuitBreakerName} CircuitBreaker is OPEN! at ${event.publishAt}" }
        changedCircuitEventPublisher.publish(
            event.circuitBreakerName,
            event.state,
            event.publishAt,
        )
    }
}
