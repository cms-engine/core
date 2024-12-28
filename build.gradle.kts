@file:Suppress("SpellCheckingInspection")

import com.github.gradle.node.npm.task.NpmTask
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude

plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.diffplug.spotless") version "6.25.0"
    id("com.github.node-gradle.node") version "7.1.0"
}

group = "com.ecommerce"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
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
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("io.github.lipiridi:hibernate-search-engine:1.0.4")

    compileOnly("org.projectlombok:lombok")

    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Amapstruct.defaultComponentModel=spring")
    options.compilerArgs.add("-Amapstruct.unmappedTargetPolicy=ERROR")
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
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}

val createBuildPropertiesFileTask: TaskProvider<Task> =
    tasks.register("createVersionPropertiesFile") {
        group = "build"

        // define a local val, to be compatible with Configuration Cache
        val projectVersion = project.version
        inputs.property("projectVersion", projectVersion)

        // tell Gradle about the output directory
        outputs.dir(temporaryDir)

        doLast {
            val resource = File(temporaryDir, "build.properties")
            resource.writeText("version=$projectVersion")
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

val feFolder = "${project.projectDir}/frontend"

node {
    // node modules directory
    nodeProjectDir = file(feFolder)
}

tasks.register<NpmTask>("appNpmInstall") {
    description = "Reads package.json and installs all dependencies"
    workingDir = file(feFolder)
    args = listOf("install")

    inputs.file("$feFolder/package.json")
    outputs.dir("$feFolder/node_modules")
}

tasks.register<NpmTask>("appNpmBuild") {
    description = "Builds application for your frontend"
    workingDir = file(feFolder)
    args = listOf("run", "build")

    inputs.dir(fileTree(feFolder) {
        exclude("out/**", ".next/**", "*.md")
    })
    outputs.dir("$feFolder/out")
}

tasks.register<Copy>("copyToFrontend") {
    doFirst {
        delete("${project.projectDir}/build/resources/main/public")
    }

    from("$feFolder/out")
    into("${project.projectDir}/build/resources/main/public")

    inputs.dir("$feFolder/out")
    outputs.dir("${project.projectDir}/build/resources/main/public")
}

tasks.named("appNpmBuild") {
    dependsOn("appNpmInstall")
}

tasks.named("copyToFrontend") {
    dependsOn("appNpmBuild")
}

tasks.named("compileJava") {
    dependsOn("copyToFrontend")
}
