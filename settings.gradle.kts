plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "circuit-breaker-chain-example"

include("resilience4j-example")
