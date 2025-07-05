package com.codehospital.versioning

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Plugin that adds versioning functionality to Gradle projects.
 * This is a placeholder implementation to allow the project to compile.
 */
class VersioningPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Plugin application logic would go here
        project.logger.lifecycle("Versioning plugin applied to project ${project.name}")
        
        // Create version.properties if it doesn't exist
        val properties = Versioning.getVersionName(project)
        
        // Make the Versioning object available at the top level in the build script
        // This allows calling it as just Versioning instead of com.codehospital.versioning.Versioning
        project.extensions.extraProperties.set("Versioning", Versioning)
    }
}
