plugins {
    alias(libs.plugins.kotlinJvm)
    kotlin("plugin.serialization") version "1.9.10"
    alias(libs.plugins.ktor)
    application
}

group = "com.mwaibanda.htmx_playground"
version = "1.0.0"
application {
    mainClass.set("com.mwaibanda.htmx_playground.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["development"] ?: "false"}")
}

dependencies {
    
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
    implementation("io.ktor:ktor-server-html-builder:${libs.versions.ktor}")
    implementation("io.ktor:ktor-client-core:${libs.versions.ktor}")
    implementation("io.ktor:ktor-client-content-negotiation:${libs.versions.ktor}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${libs.versions.ktor}")
    implementation("io.ktor:ktor-client-logging:${libs.versions.ktor}")
    implementation("io.ktor:ktor-client-cio:${libs.versions.ktor}")
    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
}