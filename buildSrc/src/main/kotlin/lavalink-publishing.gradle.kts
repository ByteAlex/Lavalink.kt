plugins {
    `maven-publish`
    signing
}

val dokkaJar by tasks.registering(Jar::class) {
    dependsOn("dokkaHtml")
    archiveClassifier.set("javadoc")
    from(tasks.getByName("dokkaHtml"))
}

publishing {
    repositories {
        maven {
            setUrl("https://nexus.rythm.dev/repository/maven-snapshots/")
            credentials {
                username = System.getenv("NEXUS_USERNAME")
                password = System.getenv("NEXUS_PASSWORD")
            }
        }
    }

    publications {
        withType<MavenPublication> {
            artifact(dokkaJar)
            pom {
                name.set(project.name)
                description.set("Coroutine based client for Lavalink (Kotlin and Java)")
                url.set("https://github.com/DRSchlaubi/Lavalink.kt")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/DRSchlaubi/Lavalink.kt/blob/main/LICENSE")
                    }
                }

                developers {
                    developer {
                        name.set("Michael Rittmeister")
                        email.set("mail@schlaubi.me")
                        organizationUrl.set("https://michael.rittmeister.in")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/DRSchlaubi/Lavalink.kt.git")
                    developerConnection.set("scm:git:https://github.com/DRSchlaubi/Lavalink.kt.git")
                    url.set("https://github.com/DRSchlaubi/Lavalink.kt")
                }
            }
        }
    }
}
