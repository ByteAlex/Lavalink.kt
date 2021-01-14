plugins {
    kotlin("jvm")
    kotlin("kapt")
}

group = "me.schlaubi.lavakord"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://dl.bintray.com/kordlib/Kord")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    jcenter()
}

dependencies {
    implementation(project(":kord"))
    implementation("org.slf4j", "slf4j-simple", "1.7.30")

    implementation("dev.kord", "kord-core", "0.7.0-SNAPSHOT")

//    implementation("com.gitlab.kordlib.kordx", "kordx-commands-runtime-kord", "0.3.3")
//    kapt("com.gitlab.kordlib.kordx", "kordx-commands-processor", "0.3.3")
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}
