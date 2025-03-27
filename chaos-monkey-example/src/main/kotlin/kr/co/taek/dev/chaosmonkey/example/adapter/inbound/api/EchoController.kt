package kr.co.taek.dev.chaosmonkey.example.adapter.inbound.api

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.co.taek.dev.chaosmonkey.example.application.port.inbound.EchoUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@RestController
class EchoController(
    private val echoUseCase: EchoUseCase
) {

    @PostMapping("/echo")
    fun echo(@RequestBody message: String): ResponseEntity<String> {
        val startTime = System.currentTimeMillis()
        log.info { "Echo start!" }
        val result = echoUseCase.echo(message)
        val endTime = System.currentTimeMillis()
        val elapsedTime = endTime - startTime
        log.info { "Echo end! Elapsed time: ${elapsedTime}ms" }
        return ResponseEntity.ok(result)
    }
}