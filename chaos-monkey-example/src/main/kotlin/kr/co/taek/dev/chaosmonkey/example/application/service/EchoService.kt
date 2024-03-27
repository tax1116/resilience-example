package kr.co.taek.dev.chaosmonkey.example.application.service

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.co.taek.dev.chaosmonkey.example.application.port.inbound.EchoUseCase
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger { }

@Service
class EchoService: EchoUseCase {
    override fun echo(message: String): String {
        log.info { "Echo message: $message" }
        return message
    }
}