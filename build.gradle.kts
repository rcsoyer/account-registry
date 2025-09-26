plugins {
    java
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "8.14.2"
}

group = "org.acme"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val iban4JVersion by extra { "3.2.11-RELEASE" }
val i18nVersion by extra { "1.29" }
val mapStructVersion by extra { "1.6.3" }
val passWayVersion by extra { "1.6.6" }
val problemVersion by extra { "0.29.1" }
val problemJacksonVersion by extra { "0.27.1" }
val jjwtVersion by extra { "0.13.0" }
val springdocVersion by extra { "2.8.13" }
val commonsTextVersion by extra {"1.14.0"}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.apache.commons:commons-lang3")
    implementation("org.iban4j:iban4j:$iban4JVersion")
    implementation("com.neovisionaries:nv-i18n:$i18nVersion")
    implementation("org.mapstruct:mapstruct:$mapStructVersion")
    implementation("org.passay:passay:$passWayVersion")
    implementation("org.zalando:problem-spring-web:$problemVersion")
    implementation("org.zalando:jackson-datatype-problem:$problemJacksonVersion")
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
    implementation("org.apache.commons:commons-text:$commonsTextVersion")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapStructVersion")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.liquibase:liquibase-core")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")

    testAnnotationProcessor("org.mapstruct:mapstruct-processor:$mapStructVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<JavaCompile>() {
    options.compilerArgs.add("-Amapstruct.defaultComponentModel=spring")
    options.compilerArgs.add("-Amapstruct.unmappedTargetPolicy=IGNORE")
}

springBoot.buildInfo()