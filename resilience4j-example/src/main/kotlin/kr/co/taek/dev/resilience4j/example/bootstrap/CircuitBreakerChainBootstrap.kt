package kr.co.taek.dev.resilience4j.example.bootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan("kr.co.taek.dev.resilience4j.example")
@SpringBootApplication
class CircuitBreakerChainBootstrap

fun main(args: Array<String>) {
    runApplication<CircuitBreakerChainBootstrap>(*args)
}