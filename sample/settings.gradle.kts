rootProject.name = "versioning-plugin-sample"

pluginManagement {
    repositories {
        google()
        maven { url = uri("https://jitpack.io") }
        gradlePluginPortal()
        mavenCentral()
    }
    // Use local build for development; JitPack when consumed externally.
    includeBuild("..")
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
