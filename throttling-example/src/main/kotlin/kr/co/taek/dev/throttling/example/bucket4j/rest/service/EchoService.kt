package kr.co.taek.dev.throttling.example.bucket4j.rest.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

interface EchoService {
    fun echo(
        apiKey: String,
        message: String,
    ): String
}

@Service
class EchoServiceImpl : EchoService {
    override fun echo(
        apiKey: String,
        message: String,
    ): String {
        logger.info { "echo $message" }
        return message
    }
}
