@file:Suppress("SpellCheckingInspection")

plugins {
	java
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.diffplug.spotless") version "6.25.0"
}

group = "com.ecommerce"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation("org.apache.commons:commons-lang3:3.14.0")
	implementation("org.yaml:snakeyaml:2.2")
	implementation("io.github.lipiridi:hibernate-search-engine:1.0.3")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	compileOnly("org.projectlombok:lombok")

	runtimeOnly("org.postgresql:postgresql")

	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

springBoot {
	buildInfo()
}

spotless {
	java {
		target("src/*/java/**/*.java")

		palantirJavaFormat()
		removeUnusedImports()
		formatAnnotations()
	}
}

val createBuildPropertiesFileTask: TaskProvider<Task> = tasks.register("createVersionPropertiesFile") {
	group = "build"

	// define a local val, to be compatible with Configuration Cache
	val projectVersion = project.version
	inputs.property("projectVersion", projectVersion)

	// tell Gradle about the output directory
	outputs.dir(temporaryDir)

	doLast {
		val resource = File(temporaryDir, "build.properties")
		resource.writeText("version=${projectVersion}")
	}
}

sourceSets {
	main {
		resources {
			// add a new resource dir that is produced by the task
			srcDir(createBuildPropertiesFileTask.map { it.temporaryDir })
		}
	}
}
