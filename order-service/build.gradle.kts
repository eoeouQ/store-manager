plugins {
    java
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "org.izouir"
version = "1.0-INTERNSHIP"

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.2"

dependencies {
    // Shared Lib
    implementation(project(":shared-lib"))
    testImplementation(project(":shared-lib"))

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    // Spring
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.cloud:spring-cloud-starter-config:4.1.2")
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("org.springframework.kafka:spring-kafka:3.2.2")

    // PostgreSQL
    implementation("org.postgresql:postgresql")
    implementation("org.hibernate.orm:hibernate-core:6.5.2.Final")
    implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.8.2")

    // Open API
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

configurations {
    all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
    }
}
