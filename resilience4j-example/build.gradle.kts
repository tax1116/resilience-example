plugins {
    id("spring-boot-convention")
}

dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.actuator)

    // implementation("org.redisson:redisson:3.27.1")
    // implementation("io.netty:netty-all:4.1.107.Final")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.2.3")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")

    implementation("io.github.resilience4j:resilience4j-circuitbreaker:2.2.0")
    implementation("io.github.resilience4j:resilience4j-kotlin:2.2.0")
    implementation("org.springframework.cloud:spring-cloud-circuitbreaker-resilience4j:3.1.0")
}
