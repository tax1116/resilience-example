package kr.co.taek.dev.throttling.bucket4j.example.ch4

import io.github.bucket4j.Bucket
import io.github.bucket4j.SimpleBucketListener
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Duration

private val logger = KotlinLogging.logger {}

/**
 * Bucket 에 Listener를 등록하는 예제
 * 8.11.0 version 이후로는 withListener() 메소드를 통해 Listener를 등록할 수 있다.
 * 8.10.1 version 이전에는 버킷을 만든 후에 toListener() 메소드를 통해 Listener를 등록할 수 있다.
 *
 * 8.10.1 이전에는 bucket4j-core 라는 모듈을 사용
 * 8.11.0 이후에는 jdk 버전에 따라 분리된 모듈을 사용
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
