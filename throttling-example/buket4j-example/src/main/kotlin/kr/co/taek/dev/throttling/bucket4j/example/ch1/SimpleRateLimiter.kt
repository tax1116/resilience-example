package kr.co.taek.dev.throttling.bucket4j.example.ch1

import io.github.bucket4j.Bucket
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Duration

private val logger = KotlinLogging.logger {}

/**
 * 간단한 토큰 버킷 RateLimiter를 테스트합니다.
 *
 */
class SimpleRateLimiter {
    private val bucket =
        Bucket.builder()
            .addLimit { limit ->
                limit.capacity(10).refillGreedy(1, Duration.ofSeconds(1))
            }
            .build()

    fun tryConsume(numTokens: Long): Boolean {
        return bucket.tryConsume(numTokens)
    }
}

fun main() {
    val rateLimiter = SimpleRateLimiter()
    for (i in 1..100) {
        Thread.sleep(100)
        if (rateLimiter.tryConsume(1)) {
            logger.info { "Consume $i" }
        } else {
            logger.info { "Reject $i" }
        }
    }
}
