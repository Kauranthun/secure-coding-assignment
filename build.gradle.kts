import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    idea
    distribution
    id("org.springframework.boot") version "2.7.18"
    id("org.sonarqube") version "4.4.1.3373"
}
apply(plugin = "io.spring.dependency-management")

sonar {
    properties {
        property("sonar.projectKey", "javaspringvulny")
        property("sonar.projectName", "javaspringvulny")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", "sqp_24c2b41b7016fa9200f1ad7b5b884efdab4017fe")
    }
}

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17


val jjwtVersion = "0.10.7"
val springdocVersion = "1.8.0"
val resurfaceVersion = "2.2.0"
val commonsCompressVersion = "1.27.1"

val vulnerableLog4jSlf4jVersion = "2.14.1"
val vulnerableLog4jCoreVersion = "2.11.2"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity5")
    implementation("org.springframework.boot:spring-boot-actuator")
    implementation("io.jsonwebtoken:jjwt-api:${jjwtVersion}")
    implementation("io.jsonwebtoken:jjwt-impl:${jjwtVersion}")
    implementation("io.jsonwebtoken:jjwt-jackson:${jjwtVersion}")
    implementation("org.springdoc:springdoc-openapi-ui:${springdocVersion}")
    implementation("io.resurface:resurfaceio-logger:${resurfaceVersion}")
    implementation("org.apache.commons:commons-compress:${commonsCompressVersion}")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:${vulnerableLog4jSlf4jVersion}")
    implementation("org.apache.logging.log4j:log4j-core:${vulnerableLog4jCoreVersion}")
    implementation("org.apache.logging.log4j:log4j-api:${vulnerableLog4jCoreVersion}")

    // leave for log4shell test never change... meant to be vulnerable


    implementation("com.h2database:h2")

    runtimeOnly("org.postgresql:postgresql")

    testCompileOnly("junit:junit")

    compileOnly("org.springframework.boot:spring-boot-devtools")
    compileOnly("org.projectlombok:lombok")

    annotationProcessor("org.projectlombok:lombok")


}

configurations.implementation {
    exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
}

tasks.named<BootJar>("bootJar") {
    archiveBaseName.set("java-spring-vuly")
    archiveVersion.set("0.2.0")
}
