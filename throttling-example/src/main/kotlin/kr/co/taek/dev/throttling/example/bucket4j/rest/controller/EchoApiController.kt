package kr.co.taek.dev.throttling.example.bucket4j.rest.controller

import kr.co.taek.dev.throttling.example.bucket4j.rest.service.EchoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class EchoApiController(
    private val echoService: EchoService,
) {
    @PostMapping("/echo")
    fun echo(
        @RequestHeader("X-API-KEY") apiKey: String,
        @RequestBody message: String,
    ): ResponseEntity<String> {
        return ResponseEntity.ok(echoService.echo(apiKey, message))
    }
}
