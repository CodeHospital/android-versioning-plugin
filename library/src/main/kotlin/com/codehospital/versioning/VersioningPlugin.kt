package com.codehospital.versioning

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Plugin that adds versioning functionality to Gradle projects.
 * This is a placeholder implementation to allow the project to compile.
 */
class VersioningPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.logger.lifecycle("Versioning plugin applied to project ${project.name}")
        
        // Initialize version.properties if it doesn't exist
        Versioning.getVersionName(project)
       
        // Make the Versioning object available at the top level in the build script
        project.extensions.extraProperties.set("Versioning", Versioning)

        // Auto-increment version on assemble/bundle tasks
        project.tasks.whenTaskAdded {
            if (name.contains("assemble", true) || name.contains("bundle", ignoreCase = true)) {
                doLast {
                    if (name.contains("Release", true)) {
                        Versioning.incrementVersionPatch(project)
                        project.logger.lifecycle("Incremented patch version: ${Versioning.getVersionName(project, "release")}")
                    } else {
                        Versioning.incrementVersionBuild(project)
                        project.logger.lifecycle("Incremented build version: ${Versioning.getVersionName(project)}")
                    }
                }
            }
        }
    }
}
