plugins {
    kotlin("jvm") version "2.0.21"
    id("io.qameta.allure") version "2.12.0"
    kotlin("plugin.serialization") version "2.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.11.3"
val jacksonVersion = "2.18.1"
val allureVersion = "2.29.0"

dependencies {
    testImplementation(kotlin("test"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")

    implementation("io.qameta.allure:allure-rest-assured:$allureVersion")
    testImplementation("io.qameta.allure:allure-junit5:$allureVersion")

    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
    implementation("ch.qos.logback:logback-classic:1.4.12")

    implementation("org.assertj:assertj-core:3.23.1")

    implementation("io.ktor:ktor-client-core:3.0.1")
    implementation("io.ktor:ktor-client-cio:3.0.1")
    implementation("io.ktor:ktor-client-logging:3.0.1")
    implementation("io.ktor:ktor-client-auth:3.0.1")
    implementation("io.ktor:ktor-client-websockets:3.0.1")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
}

val allureConfig = allure {
    version = allureVersion
}

tasks.test {
    jvmArgs = jvmArgs?.plus("-noverify")!!
    allureConfig
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}