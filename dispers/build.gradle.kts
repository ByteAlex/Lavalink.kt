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
        commonMain {
            dependencies {
                api(projects.core)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        jvmMain {
            dependencies {
                implementation("dev.bitflow.dispers:core:1.1-SNAPSHOT")
            }
        }
    }
}
