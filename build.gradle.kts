import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
	jacoco
	war
}

group = "grupo-5-yo-me-sumo"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	dependencies {

		val kotestVersion = "5.4.2"

		// Librerias
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.springframework.boot:spring-boot-starter-security")
		implementation("org.springframework.boot:spring-boot-starter-hateoas")
		implementation("org.springframework.boot:spring-boot-starter-data-rest")
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		implementation("org.springframework.boot:spring-boot-starter-web-services")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
		implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")
		implementation("org.springdoc:springdoc-openapi-starter-common:2.2.0")
		implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
		implementation("org.springframework.boot:spring-boot-devtools")

		implementation ("io.jsonwebtoken:jjwt:0.9.1")
		implementation ("javax.xml.bind:jaxb-api:2.3.1")
		implementation ("org.glassfish.jaxb:jaxb-runtime:2.3.1")



		// Conexión a la base de datos
		runtimeOnly("org.postgresql:postgresql")
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
		//Mongo
		implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

		//Netty Socket IO
		implementation("com.corundumstudio.socketio:netty-socketio:2.0.3")
		implementation("org.mongodb:mongodb-driver-sync")

		// Librerias para tests
		testImplementation("org.springframework.boot:spring-boot-starter-test")
//		testImplementation("io.mockk:mockk:1.12.8")
		testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
		testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
