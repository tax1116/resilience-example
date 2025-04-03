package kr.co.taek.dev.throttling.bucket4j.example.ch2

import io.github.bucket4j.Bucket
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Duration

private val logger = KotlinLogging.logger {}

/**
 * 초기 토큰을 0으로 설정한 RateLimiter를 테스트합니다.
 * 첫 번째 토큰이 발급되기 전까지 모든 요청을 거부합니다.
 */
class InitializedRateLimiter {
    private val bucket =
        Bucket.builder()
            .addLimit { limit ->
                limit.capacity(10).refillGreedy(1, Duration.ofSeconds(1)).initialTokens(0)
            }.build()

    fun tryConsume(numTokens: Long): Boolean {
        return bucket.tryConsume(numTokens)
    }
}

fun main() {
    val rateLimiter = InitializedRateLimiter()
    for (i in 1..100) {
        Thread.sleep(100)
        if (rateLimiter.tryConsume(1)) {
            logger.info { "Consume $i" }
        } else {
            logger.info { "Reject $i" }
        }
    }
}
