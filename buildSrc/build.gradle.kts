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

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
}