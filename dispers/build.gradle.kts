plugins {
    `lavalink-module`
    `lavalink-publishing`
}

repositories {
    maven {
        name = "ZeroTwo Public Snapshots"
        url = uri("https://nexus.zerotwo.bot/repository/m2-public-snapshots/")
    }
    mavenLocal()
}

kotlin {
    sourceSets {
        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        jvmMain {
            dependencies {
                implementation("dev.schlaubi.lavakord:core-jvm:3.7.0-dispers")
                implementation("dev.bitflow.dispers:dispers-client-kt:1.1-SNAPSHOT")
            }
        }
    }
}
