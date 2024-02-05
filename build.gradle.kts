import io.micronaut.gradle.MicronautRuntime.LAMBDA_JAVA
import io.micronaut.gradle.MicronautTestRuntime.KOTEST_5
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

val kotlinVersion: String by project
val javaVersion: String by project

plugins {
    val kotlinVersion = "1.9.21"
    val micronautVersion = "4.2.1"
    val ktlintVersion = "11.5.0"
    val jacocoLogVersion = "3.1.0"
    val shadowVersion = "8.1.1"
    val kspVersion = "1.9.21-1.0.16"

    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    id("com.google.devtools.ksp") version kspVersion
    id("io.micronaut.library") version micronautVersion
    id("com.github.johnrengelman.shadow") version shadowVersion
    id("java-test-fixtures")
    id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
    id("org.barfuin.gradle.jacocolog") version jacocoLogVersion
    jacoco
}

version = "0.1"
group = "br.com.amarques"

repositories {
    mavenCentral()
}

dependencies {
    ksp("io.micronaut.serde:micronaut-serde-processor")

    implementation("com.amazonaws:aws-lambda-java-events")
    implementation("io.micronaut.aws:micronaut-aws-lambda-events-serde")
    implementation("io.micronaut.aws:micronaut-function-aws")
    implementation("io.micronaut.crac:micronaut-crac")
    implementation("io.micronaut.kafka:micronaut-kafka")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    runtimeOnly("org.yaml:snakeyaml")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("io.micronaut.aws:micronaut-function-aws-test")
    testImplementation("org.testcontainers:kafka")
    testImplementation("io.micronaut.test:micronaut-test-kotest5")

    testFixturesImplementation("org.jetbrains.kotlin:kotlin-reflect")
    testFixturesImplementation("io.micronaut.test:micronaut-test-kotest5")
    testFixturesImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

java {
    sourceCompatibility = JavaVersion.toVersion(javaVersion)
}

ktlint {
    android.set(false)
    debug.set(false)
    enableExperimentalRules.set(false)
    ignoreFailures.set(false)
    outputColorName.set("RED")
    outputToConsole.set(true)
    verbose.set(true)

    filter {
        exclude("**/build/**")
        include("**/kotlin/**")
    }
}

micronaut {
    runtime(LAMBDA_JAVA)
    testRuntime(KOTEST_5)
    processing {
        incremental(true)
        annotations("br.com.amarques.*")
    }
}

tasks {
    shadowJar {
        archiveBaseName.set("app")
        archiveVersion.set("")
        archiveAppendix.set("")
        archiveClassifier.set("")
    }
    test {
        useJUnitPlatform()
        jvmArgs = listOf("-Duser.timezone=UTC")

        testLogging {
            showStandardStreams = true
            events = setOf(TestLogEvent.PASSED, TestLogEvent.FAILED, TestLogEvent.STARTED, TestLogEvent.SKIPPED)
            exceptionFormat = TestExceptionFormat.FULL
        }

        reports {
            html.apply {
                outputLocation.set(unitTestReportingDirectory.dir("html").asFile)
                required.set(true)
            }

            junitXml.apply {
                outputLocation.set(unitTestReportingDirectory.dir("xml").asFile)
                required.set(true)
                isOutputPerTestCase = false
                mergeReruns.set(true)
            }
        }

        finalizedBy(
            jacocoTestReport,
            jacocoTestCoverageVerification
        )
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    compileKotlin {
        kotlinOptions {
            jvmTarget = javaVersion
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = javaVersion
        }
    }
}

val Project.reportingDirectory: Directory
    get() = layout.buildDirectory.dir("report").get()

val Project.unitTestReportingDirectory: Directory
    get() = reportingDirectory.dir("test")
