package kr.co.taek.dev.throttling.integration.config

import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy
import io.github.bucket4j.distributed.proxy.ClientSideConfig
import io.github.bucket4j.distributed.proxy.ProxyManager
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager
import io.github.oshai.kotlinlogging.KotlinLogging
import io.lettuce.core.RedisClient
import io.lettuce.core.codec.ByteArrayCodec
import io.lettuce.core.codec.RedisCodec
import io.lettuce.core.codec.StringCodec
import jakarta.servlet.Filter
import kr.co.taek.dev.throttling.integration.filter.PricingPlanLimitFilter
import kr.co.taek.dev.throttling.integration.filter.RedisPricePlanLimitFilter
import org.apache.catalina.filters.RateLimitFilter
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

private val logger = KotlinLogging.logger {}

@EnableConfigurationProperties(RateLimitProperties::class)
@Configuration
class RateLimitFilterConfig(
    private val redisProperties: RedisProperties,
) {
    @ConditionalOnProperty(value = ["rate-limit.enabled"], havingValue = "true")
    @Bean
    fun rateLimitFilter(props: RateLimitProperties) : FilterRegistrationBean<*> {
        val filter = when (props.mode) {
            RateLimitProperties.Mode.IN_MEMORY -> PricingPlanLimitFilter()
            RateLimitProperties.Mode.REDIS -> RedisPricePlanLimitFilter(proxyManager())
        }

        return FilterRegistrationBean<Filter>().apply {
            this.filter = filter
            this.order = 1
            this.addUrlPatterns("/*")
            this.setName("rateLimitFilter")
        }
    }

    @Bean
    fun proxyManager(): ProxyManager<String> {
        val redisClient = RedisClient.create("redis://${redisProperties.host}:${redisProperties.port}")
        val redisConnection = redisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE))

        return LettuceBasedProxyManager.builderFor(redisConnection)
            .withClientSideConfig(
                ClientSideConfig.getDefault()
                    .withExpirationAfterWriteStrategy(
                        ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofSeconds(10)),
                    ),
            ).build()
    }
}


@ConfigurationProperties("rate-limit")
data class RateLimitProperties(
    val enabled: Boolean = false,
    val mode: Mode = Mode.IN_MEMORY,
) {
    enum class Mode {
        IN_MEMORY,
        REDIS,
    }
}