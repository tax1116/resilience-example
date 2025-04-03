package kr.co.taek.dev.throttling.bucket4j.example.ch4

import io.github.bucket4j.Bucket
import io.github.bucket4j.SimpleBucketListener
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Duration

private val logger = KotlinLogging.logger {}

/**
 * 간단한 토큰 버킷 RateLimiter를 테스트합니다.
 *
 */
class ListenableRateLimiter {
    private val listener = SimpleBucketListener()
    private val bucket =
        Bucket.builder()
            .addLimit { limit ->
                limit.capacity(10).refillGreedy(1, Duration.ofSeconds(1))
            }
            .withListener(listener)
            .build()

    fun tryConsume(numTokens: Long): Boolean {
        return bucket.tryConsume(numTokens)
    }

    fun logListenerInfo() {
        logger.info {
            """
                
                [Listener Info] 
                Consumed tokens: ${listener.consumed} 
                Rejected tokens: ${listener.rejected}
                Delayed nanos: ${listener.delayedNanos}
                Parked nanos: ${listener.parkedNanos}
                Interrupted: ${listener.interrupted}
            """.trimIndent()
        }
    }
}

fun main() {
    val rateLimiter = ListenableRateLimiter()
    for (i in 1..100) {
        Thread.sleep(100)
        if (rateLimiter.tryConsume(1)) {
            logger.info { "Consume $i" }
        } else {
            logger.info { "Reject $i" }
        }

        rateLimiter.logListenerInfo()
    }
}
