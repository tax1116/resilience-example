package kr.co.taek.dev.resilience4j.example.bootstrap.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedissonConfig(
    private val objectMapper: ObjectMapper
) {

    @Bean
    fun redissonClient(): RedissonClient {

        val config = Config()
            .apply {
                this.useSingleServer()
                    .setAddress("redis://127.0.0.1:6379")
            }

        return Redisson.create(config)
    }
}