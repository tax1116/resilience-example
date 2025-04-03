package kr.co.taek.dev.throttling.integration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ThrottlingExBootstrap

fun main(args: Array<String>) {
    runApplication<ThrottlingExBootstrap>(*args)
}
