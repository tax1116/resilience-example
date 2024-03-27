plugins {
    id("spring-boot-convention")
}

dependencies {
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.actuator)

    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.2.3")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.16.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")

    implementation(libs.resilience4j.kotlin)
    implementation(libs.resilience4j.spring.boot3)
}
