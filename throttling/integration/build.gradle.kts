plugins {
    id("spring-boot-convention")
    id("io.gatling.gradle") version "3.13.5.1"
}

dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.actuator)

    implementation(libs.resilience4j.spring.boot3)
    implementation(libs.resilience4j.kotlin)

    implementation(libs.concurrency.limits.core)
    implementation(libs.concurrency.limits.grpc)
    implementation(libs.concurrency.limits.spectator)
    implementation(libs.concurrency.limits.servlet.jakarta)
    implementation(libs.spectator.reg.micrometer)

    implementation(libs.bucket4j.spring.boot.starter)

    testImplementation(libs.spring.boot.starter.test)

    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.bucket4j.redis)

    implementation(libs.bundles.observability)
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    archiveFileName.set("throttling-integration.jar")
}
