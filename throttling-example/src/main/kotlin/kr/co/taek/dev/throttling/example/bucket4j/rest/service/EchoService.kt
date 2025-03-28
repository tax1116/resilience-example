package kr.co.taek.dev.throttling.example.bucket4j.rest.service

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.oshai.kotlinlogging.KotlinLogging
import kr.co.taek.dev.throttling.example.bucket4j.rest.enumeration.PricingPlan
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

private val logger = KotlinLogging.logger {}

interface EchoService {
    fun echo(apiKey: String, message: String): String
}

@Service
class  EchoServiceImpl : EchoService {
    private val bucketCache = ConcurrentHashMap<String, Bucket>()

    override fun echo(apiKey: String, message: String): String {
        val bucket = resolveBucket(apiKey)

        logger.info { "남은 토큰 숫자: ${bucket.availableTokens}" }

        return if (bucket.tryConsume(1)) {
            message
        } else {
            throw RuntimeException("API 사용량을 초과하였습니다.")
        }
    }

    private fun resolveBucket(apiKey: String): Bucket {
        return bucketCache.computeIfAbsent(apiKey) {
            val pricePlan = PricingPlan.resolvePlanFromApiKey(apiKey)
            newBucket(pricePlan.bandwidth)
        }
    }

    private fun newBucket(bandwidth: Bandwidth): Bucket {
        return Bucket.builder()
            .addLimit(bandwidth)
            .build()
    }
}