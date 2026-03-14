rootProject.name = "versioning-plugin-sample"

pluginManagement {
    repositories {
        google()
        maven { url = uri("https://jitpack.io") }
        gradlePluginPortal()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.codehospital.versioning") {
                val version = requested.version
                    ?: error("Version is required for com.codehospital.versioning")
                useModule("com.github.CodeHospital.versioning:library:$version")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

include(":app")
