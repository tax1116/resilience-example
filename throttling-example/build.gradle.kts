plugins {
    id("spring-boot-convention")
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

    implementation(libs.bucket4j.spring.boot.starter)

    testImplementation(libs.spring.boot.starter.test)
}