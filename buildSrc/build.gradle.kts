import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    `java-gradle-plugin`
}

gradlePlugin {
    plugins {
        create("samplePlugin") {
            id = "sample"
            implementationClass = "SamplePlugin"
        }
    }
}

group = "bremen.kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}