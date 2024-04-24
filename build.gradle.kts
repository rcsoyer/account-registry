plugins {
    java
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("io.freefair.lombok") version "8.6"
}

group = "org.acme"
version = "0.0.1-SNAPSHOT"

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

val commonsTextVersion by extra { "1.12.0" }
val iban4JVersion by extra { "3.2.7-RELEASE" }
val i18nVersion by extra { "1.29" }
val mapStructVersion by extra { "1.5.5.Final" }
val passWayVersion by extra { "1.6.4" }
val problemVersion by extra { "0.29.1" }

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.session:spring-session-core")
    implementation("org.apache.commons:commons-lang3")
    implementation("org.apache.commons:commons-text:$commonsTextVersion")
    implementation("org.iban4j:iban4j:$iban4JVersion")
    implementation("com.neovisionaries:nv-i18n:$i18nVersion")
    implementation("org.mapstruct:mapstruct:$mapStructVersion")
    implementation("org.passay:passay:$passWayVersion")
    implementation("org.zalando:problem-spring-web-starter:$problemVersion")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapStructVersion")

    runtimeOnly("org.liquibase:liquibase-core")
    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mysql")

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