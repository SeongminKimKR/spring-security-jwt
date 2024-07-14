tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

apply(plugin = "kotlin-jpa")

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    // jpa
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    // querydsl
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    // h2
    runtimeOnly("com.h2database:h2")
}

val generated = file("src/main/generated")

tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(generated)
}

sourceSets {
    main {
        kotlin.srcDirs += generated
    }
}

tasks.named("clean") {
    doLast {
        generated.deleteRecursively()
    }
}

kapt {
    generateStubs = true
}
