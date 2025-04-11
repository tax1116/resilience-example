package kr.co.taek.dev.throttling.integration.filter

import io.github.bucket4j.Bucket
import io.github.bucket4j.BucketConfiguration
import io.github.bucket4j.distributed.proxy.ProxyManager
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.co.taek.dev.throttling.integration.enumeration.PricingPlan
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

class RedisPricePlanLimitFilter(
    private val proxyManager: ProxyManager<String>,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val apiKey = request.getHeader("X-API-KEY")
        val bucket = resolveBucket(apiKey)

        val probe = bucket.tryConsumeAndReturnRemaining(1)
        response.setHeader("X-Rate-Limit-Remaining", probe.remainingTokens.toString())

        if (probe.isConsumed) {
            filterChain.doFilter(request, response)
        } else {
            response.contentType = "application/json"
            response.status = 429
            response.addHeader("X-Rate-Limit-Retry-After-Millis", probe.nanosToWaitForRefill.toString())
            response.writer.write("Too Many Requests")
            logger.warn("Rate limit exceeded!")
        }
    }

    private fun resolveBucket(apiKey: String): Bucket {
        val pricePlan = PricingPlan.resolvePlanFromApiKey(apiKey)
        return proxyManager.builder().build(apiKey) {
            BucketConfiguration.builder()
                .addLimit(pricePlan.bandwidth)
                .build()
        }
    }
}
