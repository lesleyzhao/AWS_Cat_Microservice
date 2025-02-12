//所有插件的dependencys
plugins {
    java
    id("org.springframework.boot") version "3.3.7"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.flywaydb.flyway") version "9.14.1"
}

group = "com.lesleyzh"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }

//    sourceSets {
//        main {
//            java {
//                setSrcDirs(listOf("src/main/java"))
//            }
//            resources {
//                setSrcDirs(listOf("src/main/resources"))
//            }
//        }
//        test {
//            java {
//                setSrcDirs(listOf("src/test/java"))
//            }
//            resources {
//                setSrcDirs(listOf("src/test/resources"))
//            }
//        }
//    }
}

repositories {
    mavenCentral()
}

//逻辑的depandency
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    //logging: 帮助我们在运行代码时，可以在terminal看见运行了哪些request
    implementation("org.springframework.boot:spring-boot-starter-logging")

    // immutables
    annotationProcessor("org.immutables:value:2.10.1")
    compileOnly("org.immutables:value:2.10.1")

    // open api swagger: specify the standards for all apis
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    // postgres driver
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")

    // cache
    implementation("org.redisson:redisson:3.39.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // AWS SDK BOM (manages consistent versions for AWS SDK modules)
    implementation(platform("software.amazon.awssdk:bom:2.25.57"))

    // AWS SDK for S3
    implementation("software.amazon.awssdk:s3:2.20.66") // Use the latest version

    // AWS SDK for SQS
    implementation("software.amazon.awssdk:sqs:2.20.66") // Use the latest version

    //kafka
    implementation("org.springframework.kafka:spring-kafka:3.2.2")

}

tasks.withType<Test> {
    useJUnitPlatform()
}

flyway {
    url = "jdbc:postgresql://localhost:5432/demo_db"
    user = "postgres"
    password = "postgres"
    schemas = arrayOf("public")
}
