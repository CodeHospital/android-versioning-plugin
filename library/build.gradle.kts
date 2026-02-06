plugins {
    `kotlin-dsl`
    `maven-publish`
    id("java-gradle-plugin")
}

group = "com.codehospital"
version = "1.0.7"

gradlePlugin {
    plugins {
        create("versioningPlugin") {
            id = "com.codehospital.versioning"
            implementationClass = "com.codehospital.versioning.VersioningPlugin"
        }
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        // The java-gradle-plugin automatically creates "pluginMaven" publication
        // We just need to configure the POM for it
        withType<MavenPublication> {
            pom {
                name.set("Versioning Plugin")
                description.set("A plugin to manage versioning for Android and other projects")
                url.set("https://github.com/codehospital/android-versioning-plugin")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/codehospital/android-versioning-plugin.git")
                    developerConnection.set("scm:git:ssh://github.com/codehospital/android-versioning-plugin.git")
                    url.set("https://github.com/codehospital/android-versioning-plugin")
                }
            }
        }
    }
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib-jdk8"))
}
