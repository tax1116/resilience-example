package kr.co.taek.dev.ratelimiter.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RateLimiterExBootstrap 

fun main(args: Array<String>) {
    runApplication<RateLimiterExBootstrap>(*args)
}