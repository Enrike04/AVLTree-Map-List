import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.research.code.submissions.clustering.buildutils.configureDiktat
import org.jetbrains.research.code.submissions.clustering.buildutils.createDiktatTask

plugins {
    kotlin("jvm")
    application
    id("sample")
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
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
    abstract val n: Property<Int>

    @TaskAction
    fun execute() {
        if (n.get() < 0) {
            throw StopExecutionException("n must be non-negative")
        }
        var first = 0
        var second = 1
        for (i in 1..n.get()) {
            second += first
            first = second - first
        }
        println("Result = $first")
    }
}

tasks.register<FibonacciTask>("Fib_9") {
    n.set(9)
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config = files("$projectDir/config/detekt.yml")
    debug = true
}

configureDiktat()
createDiktatTask()