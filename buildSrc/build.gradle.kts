plugins {
    kotlin("jvm") version "1.9.20"
    `kotlin-dsl`
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.9.20"))
    }
}

repositories {
    // Add any required repositories
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("assetManagement") {
            id = "io.violabs.minecraft.plugins.assert-management"
            implementationClass = "io.violabs.plugins.AssetManagementPlugin"
        }
    }
}