package kr.co.taek.dev.resilience4j.example.application.event

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.co.taek.dev.resilience4j.example.application.port.outbound.CircuitOpenEventPublisher
import kr.co.taek.dev.resilience4j.example.circuitbreaker.event.CircuitOpenEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {  }

@Component
class CircuitOpenEventListener(
    private val circuitOpenEventPublisher: CircuitOpenEventPublisher
) {

    @EventListener
    fun handle(event: CircuitOpenEvent) {
        log.info { "${event.circuitBreakerName} CircuitBreaker is OPEN! at ${event.publishAt}"}
        circuitOpenEventPublisher.publish(event.circuitBreakerName, event.publishAt)
    }
}