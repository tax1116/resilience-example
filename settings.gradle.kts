plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "resilience-example"

include(
    "circuitbreaker-example",
    "chaos-monkey-example",
    "throttling-example"
)
