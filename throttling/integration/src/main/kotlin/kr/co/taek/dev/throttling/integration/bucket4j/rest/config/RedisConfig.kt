package kr.co.taek.dev.throttling.integration.bucket4j.rest.config

import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy
import io.github.bucket4j.distributed.proxy.ClientSideConfig
import io.github.bucket4j.distributed.proxy.ProxyManager
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager
import io.github.oshai.kotlinlogging.KotlinLogging
import io.lettuce.core.RedisClient
import io.lettuce.core.codec.ByteArrayCodec
import io.lettuce.core.codec.RedisCodec
import io.lettuce.core.codec.StringCodec
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

private val logger = KotlinLogging.logger {}

@Configuration
class RedisConfig {
    @Bean
    fun proxyManager(redisProperties: RedisProperties): ProxyManager<String> {
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
