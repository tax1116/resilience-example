package kr.co.taek.dev.throttling.integration.enumeration

import io.github.bucket4j.Bandwidth
import java.time.Duration

enum class PricingPlan(
    val bandwidth: Bandwidth,
) {
    FREE(
        Bandwidth.builder()
            .capacity(60)
            .refillIntervally(1, Duration.ofSeconds(1))
            .build(),
    ),
    STANDARD(
        Bandwidth.builder()
            .capacity(300)
            .refillIntervally(5, Duration.ofSeconds(1))
            .build(),
    ),
    PREMIUM(
        Bandwidth.builder()
            .capacity(1200)
            .refillIntervally(20, Duration.ofSeconds(1))
            .build(),
    ),
    ;

    companion object {
        fun resolvePlanFromApiKey(apiKey: String): PricingPlan {
            return when {
                apiKey.startsWith("STD") -> STANDARD
                apiKey.startsWith("PREM") -> PREMIUM
                else -> FREE
            }
        }
    }
}
