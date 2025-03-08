@file:Suppress("SpellCheckingInspection")

import com.github.gradle.node.npm.task.NpmTask

plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.diffplug.spotless") version "6.25.0"
    id("com.github.node-gradle.node") version "7.1.0"
    id("org.panteleyev.jpackageplugin") version "1.6.0"
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
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.session:spring-session-jdbc")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("io.github.lipiridi:hibernate-search-engine:1.0.4")

    compileOnly("org.projectlombok:lombok")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

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

tasks.register("printVersion") {
    println(project.version)
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

tasks.jpackage {
    dependsOn("build")

    appName = "CMS Engine"
    appVersion = project.version.toString()
    description = "CMS-Engine is a powerful and flexible Content Management System"
    aboutUrl = "https://github.com/cms-engine/core"
    licenseFile = "LICENSE"
    input = "${layout.buildDirectory.get()}/libs"
    destination = "${layout.buildDirectory.get()}"
    mainJar = tasks.bootJar.get().archiveFileName.get()
    icon = "icon.ico"
    arguments = listOf("--spring.profiles.active=h2", "--logging.file.path=./logs")

    windows {
        winConsole = true
        winMenu = true
        winShortcut = true
        winShortcutPrompt = true
        winDirChooser = true
        winUpdateUrl = "https://github.com/cms-engine"
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

    inputs.dir(
        fileTree(feFolder) {
            exclude("out/**", ".next/**", "*.md")
        },
    )
    outputs.dir("$feFolder/out")
}

tasks.register<Copy>("copyToFrontend") {
    val publicDir = "${layout.buildDirectory.get()}/resources/main/public"

    doFirst {
        delete(publicDir)
    }

    from("$feFolder/out")
    into(publicDir)

    inputs.dir("$feFolder/out")
    outputs.dir(publicDir)
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
