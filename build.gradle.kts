@file:Suppress("SpellCheckingInspection")

import org.gradle.api.file.ArchiveOperations
import java.net.URI

plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.diffplug.spotless") version "6.25.0"
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

abstract class DownloadAdminUiTask
    @Inject
    constructor(
        private val archiveOperations: ArchiveOperations,
        private val fileSystemOperations: FileSystemOperations,
    ) : DefaultTask() {
        @get:OutputDirectory
        abstract val outputDir: DirectoryProperty

        @get:OutputFile
        abstract val cacheFile: RegularFileProperty

        @TaskAction
        fun downloadAndExtract() {
            val apiUrl = "https://api.github.com/repos/cms-engine/admin-ui/releases/latest"

            // Fetch release info from GitHub API
            val json = URI(apiUrl).toURL().readText()
            val regex = """"browser_download_url":\s*"([^"]*nextjs-out-[\d.]+.tar.gz)"""".toRegex()
            val match = regex.find(json) ?: throw GradleException("Admin UI release URL not found")

            val downloadUrl = match.groupValues[1]
            val versionRegex = """nextjs-out-([\d.]+).tar.gz""".toRegex()
            val versionMatch = versionRegex.find(downloadUrl) ?: throw GradleException("Cannot extract version")

            val adminUiVersion = versionMatch.groupValues[1]

            // Download Admin UI archive
            val archiveFile = temporaryDir.resolve("nextjs-out.tar.gz")
            println("⬇️ Downloading Admin UI version $adminUiVersion from $downloadUrl")
            archiveFile.writeBytes(URI(downloadUrl).toURL().readBytes())

            // Extract with tarTree
            fileSystemOperations.copy {
                from(archiveOperations.tarTree(archiveFile))
                into(temporaryDir) // Extract to the build directory
            }

            // Move all files from the "out" folder to the public directory
            val outFolder = File(temporaryDir, "out")
            if (outFolder.exists() && outFolder.isDirectory) {
                outFolder.copyRecursively(outputDir.get().asFile, overwrite = true) // Copy all files to the public directory
                outFolder.deleteRecursively() // Optionally delete the "out" folder after copying
            }

            // Cache the downloaded version
            cacheFile.asFile.get().writeText(adminUiVersion)
            println("✅ Admin UI $adminUiVersion extracted to ${outputDir.get().asFile}")
        }
    }

tasks.register<DownloadAdminUiTask>("downloadAdminUi") {
    cacheFile.set(layout.buildDirectory.file("admin-ui-version.txt"))
    outputDir.set(layout.buildDirectory.dir("resources/main/public/admin"))
}

tasks.named("classes").configure {
    dependsOn("downloadAdminUi")
}

tasks.register("forceUpdateAdminUi") {
    doLast {
        val cacheFile = layout.buildDirectory.file("admin-ui-version.txt").get().asFile
        val outputDir = layout.buildDirectory.dir("resources/main/public/admin").get().asFile

        if (cacheFile.exists()) {
            cacheFile.delete()
            println("🗑️ Deleted cache file: ${cacheFile.absolutePath}")
        }

        if (outputDir.exists()) {
            outputDir.deleteRecursively()
            println("🗑️ Deleted output directory: ${outputDir.absolutePath}")
        }
    }
}.configure {
    finalizedBy("downloadAdminUi")
}
