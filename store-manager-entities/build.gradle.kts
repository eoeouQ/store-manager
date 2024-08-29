import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("java")
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "org.izouir"
version = "1.0"

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}
tasks.getByName<Jar>("jar") {
    enabled = true
}

repositories {
    mavenCentral()
}

dependencies {
    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}