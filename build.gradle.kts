import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
    id("sample")
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
    id("org.cqfn.diktat.diktat-gradle-plugin") version "1.2.3"
}

group = "bremen.kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testApi(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

abstract class FibonacciTask : DefaultTask() {
    @get:Input
    abstract val number: Property<Int>

    @TaskAction
    fun execute() {
        if (number.get() < 0) {
            throw StopExecutionException("n must be non-negative")
        }
        var first = 0
        var second = 1
        for (i in 1..number.get()) {
            second += first
            first = second - first
        }
        println("Result = $first")
    }
}

tasks.register<FibonacciTask>("Fib_9") {
    number.set(9)
}

detekt {
    ignoreFailures = true
    buildUponDefaultConfig = false
}

tasks.register<io.gitlab.arturbosch.detekt.Detekt>("customDetekt") {
    description = "Runs detekt"
    setSource(files("src/main/kotlin", "src/test/kotlin"))
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(files("$projectDir/config/detekt.yml"))
    debug = true
    ignoreFailures = false
    reports {
        html.outputLocation.set(file("build/reports/detekt.html"))
    }
    include("**/*.kt")
    include("**/*.kts")
    exclude("resources/")
    exclude("build/")
}

diktat {
    reporter = "html"
    output = "build/reports/diktat.html"

    diktatConfigFile = file("$projectDir/config/diktat.yml")
    inputs {
        include("**/*.kts")
        include("**/*.kt")
    }
}
