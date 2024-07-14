import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("application")
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
	kotlin("plugin.jpa") version "1.9.22"
	kotlin("kapt") version "1.9.22"

}

repositories {
	mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_17

application {
	mainClass.set("com.simple.study.StudyApplication.kt")
}

subprojects {
	group = "com.simple"

	repositories {
		mavenCentral()
	}

	apply(plugin = "java-library")
	apply(plugin = "kotlin")
	apply(plugin = "kotlin-jpa")
	apply(plugin = "kotlin-spring")
	apply(plugin = "kotlin-kapt")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	dependencies {
		// Kotlin
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}