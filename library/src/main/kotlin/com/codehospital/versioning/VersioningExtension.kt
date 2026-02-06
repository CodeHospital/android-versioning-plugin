package com.codehospital.versioning

import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider

/**
 * Gradle extension for accessing versioning functionality in build scripts.
 * This makes Versioning accessible as `versioning` in build.gradle.kts
 */
abstract class VersioningExtension(private val project: Project) {

    fun getVersionCode(buildType: String = "debug"): Int {
        return Versioning.getVersionCode(project, buildType)
    }

    fun getVersionName(buildType: String = "release"): String {
        return Versioning.getVersionName(project, buildType)
    }

    fun getDebugSuffix(): String {
        return Versioning.getDebugSuffix(project)
    }

    fun getVersionBuild(): Int {
        return Versioning.getVersionBuild(project)
    }

    fun incrementVersionBuild() {
        Versioning.incrementVersionBuild(project)
    }

    fun incrementVersionPatch() {
        Versioning.incrementVersionPatch(project)
    }

    companion object {
        const val NAME = "versioning"
    }
}
