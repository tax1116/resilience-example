package kr.co.taek.dev.circuitbreaker.example.bootstrap.config

import com.fasterxml.jackson.databind.ObjectMapper
import kr.co.taek.dev.circuitbreaker.example.adapter.inbound.redis.RedisChangedCircuitEventListener
import kr.co.taek.dev.circuitbreaker.example.adapter.outbound.redis.ChangedCircuitEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Bean
    fun lettuceConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory("localhost", 6379)
    }

    @Bean
    fun circuitBreakerOpenTopic(
        @Value("\${redis.topic.circuit-breaker}") topic: String,
    ): ChannelTopic = ChannelTopic(topic)

    @Bean
    fun redisTemplate(
        objectMapper: ObjectMapper,
    ): RedisTemplate<String, ChangedCircuitEvent> {
        val redisTemplate = RedisTemplate<String, ChangedCircuitEvent>()
        redisTemplate.apply {
            this.connectionFactory = lettuceConnectionFactory()
            this.stringSerializer = StringRedisSerializer()
            this.valueSerializer = Jackson2JsonRedisSerializer(objectMapper, ChangedCircuitEvent::class.java)
        }

        return redisTemplate
    }

    @Bean
    fun redisContainer(
        redisChangedCircuitEventListener: RedisChangedCircuitEventListener,
        circuitBreakerOpenTopic: ChannelTopic,
    ): RedisMessageListenerContainer {
        val container = RedisMessageListenerContainer()
        container.setConnectionFactory(lettuceConnectionFactory())
        container.addMessageListener(redisChangedCircuitEventListener, circuitBreakerOpenTopic)
        return container
    }
}
