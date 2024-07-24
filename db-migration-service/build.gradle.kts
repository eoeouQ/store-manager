plugins {
    java
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "org.izouir"
version = "1.0-INTERNSHIP"

repositories {
    mavenCentral()
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter")

    // Flyway
    implementation("org.flywaydb:flyway-core:10.7.2")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:10.7.2")
}
