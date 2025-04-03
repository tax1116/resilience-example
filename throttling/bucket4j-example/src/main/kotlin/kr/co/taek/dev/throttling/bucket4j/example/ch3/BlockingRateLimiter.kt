package kr.co.taek.dev.throttling.bucket4j.example.ch3

import io.github.bucket4j.Bucket
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Duration

private val logger = KotlinLogging.logger {}

class BlockingRateLimiter {
    private val bucket =
        Bucket.builder()
            .addLimit { limit ->
                limit.capacity(10).refillGreedy(1, Duration.ofSeconds(1))
            }.build()

    /**
     * maxWait가 만료될 때까지 대기하며 numTokens 만큼 소비합니다.
     *
     * @param numTokens
     * @param maxWait
     * @return
     */
    fun blockingConsume(
        numTokens: Long,
        maxWait: Duration,
    ): Boolean {
        return bucket.asBlocking().tryConsume(numTokens, maxWait)
    }

    /**
     *
     * @param numTokens
     */
    fun blockingConsume(numTokens: Long) {
        bucket.asBlocking().consume(numTokens)
    }
}

fun main() {
    val rateLimiter = BlockingRateLimiter()
    for (i in 1..100) {
        Thread.sleep(100)
        val start = System.currentTimeMillis()
        rateLimiter.blockingConsume(1)
        val end = System.currentTimeMillis()
        logger.info { "Consume $i, took ${end - start}ms" }
    }
}
