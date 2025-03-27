package kr.co.taek.dev.ratelimiter.example.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

private val logger = KotlinLogging.logger {}

@SpringBootTest
class ThrottlingServiceTest(
    @Autowired private val throttlingService: ThrottlingService
) {
    @Test
    fun vegas() {
        repeat(1000) {
            try {
                throttlingService.vegas()
            } catch (e: Exception) {
                logger.error(e) { "Throttled exception" }
            }
        }
    }

    @Test
    fun gradient2() {
        repeat(1000) {
            try {
                throttlingService.gradient2()
            } catch (e: Exception) {
                logger.error(e) { "Gradient2 exception" }
            }
        }
    }

    @Test
    fun fixed() {
        repeat(1000) {
            try {
                throttlingService.fixed()
            } catch (e: Exception) {
                logger.error(e) { "Fixed exception" }
            }
        }
    }
}