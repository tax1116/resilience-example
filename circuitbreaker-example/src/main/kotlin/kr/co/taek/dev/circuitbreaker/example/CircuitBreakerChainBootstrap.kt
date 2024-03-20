package kr.co.taek.dev.circuitbreaker.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CircuitBreakerChainBootstrap

fun main(args: Array<String>) {
    runApplication<CircuitBreakerChainBootstrap>(*args)
}
