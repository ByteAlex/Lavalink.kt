plugins {
    `lavalink-module`
    `lavalink-publishing`
}

repositories {
    maven {
        name = "ZeroTwo Public Snapshots"
        url = uri("https://nexus.zerotwo.bot/repository/m2-public-snapshots/")
    }
    maven {
        name = "Rythm Public"
        url = uri("https://nexus.rythm.dev/repository/maven-public/")
        credentials {
            username = System.getenv("NEXUS_USERNAME")
            password = System.getenv("NEXUS_PASSWORD")
        }
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
                implementation("dev.bitflow.dispers:dispers-client-kt:1.1-SNAPSHOT")
            }
        }
    }
}
