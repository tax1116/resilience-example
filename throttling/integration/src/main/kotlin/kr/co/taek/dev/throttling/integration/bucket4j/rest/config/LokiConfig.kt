package kr.co.taek.dev.throttling.integration.bucket4j.rest.config

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LokiConfig {

    // https://docs.spring.io/spring-boot/reference/actuator/loggers.html#actuator.loggers.opentelemetry
    @Bean
    fun logbackOtelAppenderInitializer(openTelemetry: OpenTelemetry): ApplicationListener<ApplicationReadyEvent> {
        return ApplicationListener { OpenTelemetryAppender.install(openTelemetry) }
    }
}