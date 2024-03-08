import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

plugins {
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint")
}

group = "kr.co.taek.dev"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of("21"))
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
//    jvmArgs = listOf("--add-opens", "java.base/java.lang.invoke=ALL-UNNAMED")
    useJUnitPlatform()
}

dependencies {
    implementation(libs.kotlin.logging)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.runner.junit5)
}

ktlint {
    version.set("0.50.0")
    filter {
        exclude {
            it.file.path.startsWith(project.layout.buildDirectory.get().dir("generated").toString())
        }
    }
}

inline val Project.libs: LibrariesForLibs get() = the<LibrariesForLibs>()