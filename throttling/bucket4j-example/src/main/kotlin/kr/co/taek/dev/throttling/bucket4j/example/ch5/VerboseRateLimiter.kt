package kr.co.taek.dev.throttling.bucket4j.example.ch5

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

/**
 * Bucket4j에서 제공하는 디버깅용 API
 *
 * asVerbose()를 통해 생성된 버킷에서는 VerboseResult<T>라는 상세 정보가 포함된 결과 객체를 리턴.
 *
 * @constructor Create empty Verbose rate limiter
 */
class VerboseRateLimiter {
    private val bucket =
        Bucket.builder()
            .addLimit {
                it.capacity(10)
                    .refillGreedy(1, Duration.ofSeconds(5))
            }
            .addLimit { limit ->
                limit.capacity(10).refillGreedy(1, Duration.ofSeconds(1))
            }
            .build()
            .asVerbose()

    fun tryConsume(numTokens: Long): Boolean {
        return bucket.tryConsume(numTokens).let {
            val operationTimeMillis = it.operationTimeNanos / 1_000_000
            logger.info { """
                
                [Verbose diagnotics Info]
                    Operation time : ${LocalDateTime.ofInstant(Instant.ofEpochMilli(operationTimeMillis), ZoneId.systemDefault())}
                    Available tokens : ${it.diagnostics.availableTokens}
                    Full refilling time : ${TimeUnit.MILLISECONDS.convert(Duration.ofNanos(it.diagnostics.calculateFullRefillingTime()))} ms
                    Available tokens per each bandwidth : ${it.diagnostics.availableTokensPerEachBandwidth.joinToString(
                        prefix = "[",
                        postfix = "]"
                    ) { token -> token.toString() }}
                    Bandwidths : ${it.state.configuration.bandwidths.joinToString(
                        prefix = "[",
                        postfix = "]"
                    ) { bandwidth: Bandwidth? -> bandwidth.toString() }}
            """.trimIndent() }
            it.value
        }
    }
}

fun main() {
    val rateLimiter = VerboseRateLimiter()
    for (i in 1..100) {
        Thread.sleep(100)
        if (rateLimiter.tryConsume(1)) {
            logger.info { "Consume $i" }
        } else {
            logger.info { "Reject $i" }
        }
    }
}