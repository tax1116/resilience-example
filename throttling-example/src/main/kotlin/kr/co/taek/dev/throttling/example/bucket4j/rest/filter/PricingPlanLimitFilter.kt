package kr.co.taek.dev.throttling.example.bucket4j.rest.filter

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.taek.dev.throttling.example.bucket4j.rest.enumeration.PricingPlan
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger { }

@ConditionalOnProperty(value = ["redis.rate.limiter.enabled"], havingValue = "false")
@Component
class PricingPlanLimitFilter : Filter {
    private val bucketCache = ConcurrentHashMap<String, Bucket>()

    override fun doFilter(
        p0: ServletRequest,
        p1: ServletResponse,
        p2: FilterChain,
    ) {
        val httpRequest = p0 as HttpServletRequest
        val apiKey = httpRequest.getHeader("X-API-KEY")
        val bucket = resolveBucket(apiKey)

        val httpResponse = p1 as HttpServletResponse
        val probe = bucket.tryConsumeAndReturnRemaining(1)
        httpResponse.setHeader("X-Rate-Limit-Remaining", probe.remainingTokens.toString())

        if (probe.isConsumed) {
            p2.doFilter(httpRequest, httpResponse)
        } else {
            httpResponse.contentType = "application/json"
            httpResponse.status = 429
            httpResponse.addHeader("X-Rate-Limit-Retry-After-Millis", TimeUnit.NANOSECONDS.toMillis(probe.nanosToWaitForRefill).toString())
            httpResponse.writer.write("Too Many Requests")
            logger.warn { "Rate limit exceeded!" }
        }
    }

    private fun resolveBucket(apiKey: String): Bucket {
        return bucketCache.computeIfAbsent(apiKey) {
            val pricePlan = PricingPlan.resolvePlanFromApiKey(apiKey)
            createNewBucket(pricePlan.bandwidth)
        }
    }

    private fun createNewBucket(bandwidth: Bandwidth): Bucket =
        Bucket.builder()
            .addLimit(bandwidth)
            .build()
}
