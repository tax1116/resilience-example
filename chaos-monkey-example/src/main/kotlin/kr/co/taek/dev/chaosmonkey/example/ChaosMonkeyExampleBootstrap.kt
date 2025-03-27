package kr.co.taek.dev.chaosmonkey.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChaosMonkeyExampleBootstrap

fun main(args: Array<String>) {
    runApplication<ChaosMonkeyExampleBootstrap>(*args)
}