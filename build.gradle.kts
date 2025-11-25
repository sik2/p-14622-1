plugins {
    java
    id("org.springframework.boot") version "3.5.8"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com"
version = "0.0.1-SNAPSHOT"
description = "rag"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springAiVersion"] = "1.0.3"

dependencies {
    // Spring Boot 기본
    implementation("org.springframework.boot:spring-boot-starter-web")              // Web MVC
    developmentOnly("org.springframework.boot:spring-boot-devtools")                // 개발 도구
    runtimeOnly("org.springframework.boot:spring-boot-docker-compose")              // Docker Compose 지원

    // Lombok
    compileOnly("org.projectlombok:lombok")                                         // Lombok 컴파일 타임
    annotationProcessor("org.projectlombok:lombok")                                 // Lombok 어노테이션 처리

    // Database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")         // JPA/Hibernate
    runtimeOnly("com.mysql:mysql-connector-j")                                      // MySQL 드라이버

    // Elasticsearch
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch") // Elasticsearch

    // Spring AI
    implementation("org.springframework.ai:spring-ai-starter-model-openai")         // OpenAI 모델
    implementation("org.springframework.ai:spring-ai-starter-model-transformers")   // 임베딩 모델

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")         // 테스트 도구
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    maxHeapSize = "2048m"
}