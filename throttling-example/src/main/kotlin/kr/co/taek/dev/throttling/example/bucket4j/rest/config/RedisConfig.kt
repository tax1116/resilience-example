package kr.co.taek.dev.throttling.example.bucket4j.rest.config

import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy
import io.github.bucket4j.distributed.proxy.ClientSideConfig
import io.github.bucket4j.distributed.proxy.ProxyManager
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager
import io.lettuce.core.RedisClient
import io.lettuce.core.codec.ByteArrayCodec
import io.lettuce.core.codec.RedisCodec
import io.lettuce.core.codec.StringCodec
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class RedisConfig {
    @Bean
    fun proxyManager(): ProxyManager<String> {
        val redisClient = RedisClient.create("redis://localhost:6379")
        val redisConnection = redisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE))

        return LettuceBasedProxyManager.builderFor(redisConnection)
            .withClientSideConfig(
                ClientSideConfig.getDefault()
                    .withExpirationAfterWriteStrategy(
                        ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofSeconds(10))
                    )
            ).build()
    }
}