package kr.co.taek.dev.circuitbreaker.example.application.event

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.co.taek.dev.circuitbreaker.example.application.port.outbound.ChangedCircuitEventPublisher
import kr.co.taek.dev.circuitbreaker.example.circuitbreaker.event.ChangedCircuitEvent
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
