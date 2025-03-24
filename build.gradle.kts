import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

val minecraftVersion = project.properties["minecraftVersion"] as String
val yarnMappings = project.properties["yarnMappings"] as String
val loaderVersion = project.properties["loaderVersion"] as String
val fabricKotlinVersion = project.properties["fabricKotlinVersion"] as String
val modVersion = project.properties["modVersion"] as String
val mavenGroup = project.properties["mavenGroup"] as String
val archiveBaseName = project.properties["archivesBaseName"] as String
val fabricVersion = project.properties["fabricVersion"] as String


plugins {
	id("fabric-loom") version "1.10-SNAPSHOT"
	id("maven-publish")
	id("org.jetbrains.kotlin.jvm") version "2.1.20"
}

version = modVersion
group = mavenGroup

base {
	archivesName = archiveBaseName
}

repositories {
	maven {
		name = "Fabric"
		url = URI("https://maven.fabricmc.net")
	}
	mavenCentral()
}


loom {
	splitEnvironmentSourceSets()

	mods {
		create("beginner") {
			sourceSet(sourceSets.main.get())
			sourceSet(sourceSets.findByName("client"))
		}
	}
}

fabricApi {
	configureDataGeneration {
		client = true
	}
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("net.minecraft:minecraft:$minecraftVersion")
	mappings("net.fabricmc:yarn:$yarnMappings:v2")
	modImplementation("net.fabricmc:fabric-loader:$loaderVersion")

	// Fabric API. This is technically optional, but you probably want it anyway.
	modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
	modImplementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion")

	val ktorClientVersion = "3.0.1"
	val kotlinJacksonVersion = "2.18.0"

	implementation("io.ktor:ktor-client-cio:$ktorClientVersion") // or another engine like ktor-client-apache, ktor-client-okhttp, etc.
	implementation("io.ktor:ktor-client-json:$ktorClientVersion")
	implementation("io.ktor:ktor-client-serialization:$ktorClientVersion")

	implementation("io.ktor:ktor-client-core:$ktorClientVersion")
	implementation("io.ktor:ktor-client-okhttp:$ktorClientVersion") // Using OkHttp for better security
	implementation("io.ktor:ktor-client-content-negotiation:$ktorClientVersion")
	implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorClientVersion")
	implementation("io.ktor:ktor-client-logging:$ktorClientVersion")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$kotlinJacksonVersion")
	annotationProcessor("com.fasterxml.jackson.module:jackson-module-kotlin:$kotlinJacksonVersion")
}

tasks.processResources {
	inputs.property("version", version)

	filesMatching("fabric.mod.json") {
		expand("version" to version)
	}
}

tasks.withType(JavaCompile::class.java) {
	options.release.set(21)
}

tasks.withType<KotlinCompile>().all {
	compilerOptions {
		jvmTarget.set(JvmTarget.JVM_21)
	}
}

java {
	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
	// if it is present.
	// If you remove this line, sources will not be generated.
	withSourcesJar()

	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

tasks.jar {
	inputs.property("archivesName", archiveBaseName)

	from("LICENSE") {
		rename { "${it}_${inputs.properties["archivesName"]}"}
	}
}

tasks.withType(JavaExec::class.java) {
	if (name == "runServer") {
		workingDir = file("run")
	}
}

//// configure the maven publication
//publishing {
//	publications {
//		create("mavenJava", MavenPublication) {
//			artifactId = project.archives_base_name
//			from components.java
//		}
//	}
//
//	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
//	repositories {
//		// Add repositories to publish to here.
//		// Notice: This block does NOT have the same function as the block in the top level.
//		// The repositories here will be used for publishing your artifact, not for
//		// retrieving dependencies.
//	}
//}