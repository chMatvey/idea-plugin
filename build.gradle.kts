plugins {
    id("org.jetbrains.intellij") version "0.4.18"
    kotlin("jvm") version "1.3.71"
}

group = "com.github.chMatvey"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "2019.3.2"
    setPlugins("gradle")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

buildscript {
    repositories {
        flatDir {
            dirs("$projectDir/lib")
        }
    }
    dependencies {
        classpath("com.github.chMatvey:gradle-plugin-1.0-SNAPSHOT")
    }
}

apply(plugin = "com.github.chMatvey.basic")
