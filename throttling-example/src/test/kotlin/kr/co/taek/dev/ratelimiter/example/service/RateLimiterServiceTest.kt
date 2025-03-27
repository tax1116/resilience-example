package kr.co.taek.dev.ratelimiter.example.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RateLimiterServiceTest(
    @Autowired private val rateLimiterService: RateLimiterService
) {

    @Test
    fun rateLimit() {
        for (i in 1 until 100) {
            rateLimiterService.rateLimit()
            Thread.sleep(1000)
        }
    }
}