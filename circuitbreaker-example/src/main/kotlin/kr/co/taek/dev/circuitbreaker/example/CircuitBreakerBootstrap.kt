package kr.co.taek.dev.circuitbreaker.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CircuitBreakerBootstrap

fun main(args: Array<String>) {
    runApplication<CircuitBreakerBootstrap>(*args)
}
