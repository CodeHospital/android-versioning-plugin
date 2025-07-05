plugins {
    `kotlin-dsl`
    `maven-publish`
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "com.codehospital"
version = "1.0.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    website.set("https://codehospital.com")
    vcsUrl.set("https://github.com/codehospital/android-versioning-plugin")
    
    plugins {
        create("versioningPlugin") {
            id = "com.codehospital.versioning"
            displayName = "Versioning Plugin"
            description = "A plugin to manage versioning for Android and other projects"
            tags.set(listOf("versioning", "android", "semver"))
            implementationClass = "com.codehospital.versioning.VersioningPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "LocalRepo"
            url = uri("${rootProject.layout.buildDirectory}/repo")
        }
        // Add your remote repository configuration here if you have one
        // maven {
        //     name = "CompanyRepository"
        //     url = uri("https://repo.example.com")
        //     credentials {
        //         username = property("repoUsername") as String
        //         password = property("repoPassword") as String
        //     }
        // }
    }
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib-jdk8"))
}
