package kr.co.taek.dev.chaosmonkey.example.application.port.inbound

interface EchoUseCase {
    fun echo(message: String): String
}