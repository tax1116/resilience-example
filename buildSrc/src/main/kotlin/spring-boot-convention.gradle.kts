import kr.co.taek.dev.resilience.example.libs

plugins {
    id("global-convention")
    id("org.springframework.boot")
    kotlin("plugin.spring")
}

dependencies {
    implementation(platform(libs.spring.boot.dependencies))
}