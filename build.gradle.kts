plugins {
    java
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    id("com.epages.restdocs-api-spec") version "0.19.4"
    id("com.diffplug.spotless") version "7.1.0"
    id("org.flywaydb.flyway") version "11.10.4"
}

// Flyway 플러그인을 위한 별도 구성
buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-mysql:11.10.4")
        classpath("com.mysql:mysql-connector-j:9.1.0")
    }
}

group = "com.nexters"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    encoding("UTF-8")
    java {
        toggleOffOn()
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
        googleJavaFormat().aosp()
    }
}

repositories {
    mavenCentral()
}

extra["springAiVersion"] = "1.0.0"
extra["snippetsDir"] = file("build/generated-snippets")

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // spring validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // db
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")

    // flyway
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")

    // p6spy
    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.9.1")

    // spring security
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    // jwt
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    // spring ai
    implementation("org.springframework.ai:spring-ai-starter-model-openai")

    // google gen ai
    implementation("com.google.genai:google-genai:1.10.0")

    // spring actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    // restdocs-api-spec
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.19.4")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    // fixture monkey
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter:1.1.4")

    // testcontainers
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:junit-jupiter")

    // rest-assured for E2E testing
    testImplementation("io.rest-assured:rest-assured")
}

tasks {
    compileJava {
        dependsOn("spotlessApply")
    }

    withType<Test> {
        useJUnitPlatform()
    }

    test {
        outputs.dir(project.extra["snippetsDir"]!!)
    }
}

openapi3 {
    title = "Gamchi API"
    description = "Swagger Docs"
    version = "0.0.1"
    format = "yaml"
    outputFileNamePrefix = "openapi3"
}

flyway {
    url = "jdbc:mysql://${System.getenv("DB_HOST") ?: "localhost"}:3306/gamchi"
    user = System.getenv("DB_USERNAME") ?: "root"
    password = System.getenv("DB_PASSWORD") ?: "1234"
    locations = arrayOf("classpath:db/migration")
}
