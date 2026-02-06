package com.codehospital.versioning

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Plugin that adds versioning functionality to Gradle projects.
 * This is a placeholder implementation to allow the project to compile.
 */
class VersioningPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.logger.lifecycle("Versioning plugin applied to project ${project.name}")
        
        // Initialize version.properties if it doesn't exist
        Versioning.getVersionName(project)
       
        // Register the extension for Gradle 9+ compatibility
        val extension = project.extensions.create(
            VersioningExtension.NAME,
            VersioningExtension::class.java,
            project
        )
        
        // Also keep extraProperties for backward compatibility
        project.extensions.extraProperties.set("Versioning", Versioning)

        // Add BUILD_TIME to Android buildConfigFields
        project.afterEvaluate {
            project.extensions.findByName("android")?.let { android ->
                val buildTime = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    .format(Date(System.currentTimeMillis()))
                
                try {
                    val defaultConfig = android.javaClass.getMethod("getDefaultConfig").invoke(android)
                    defaultConfig.javaClass.getMethod("buildConfigField", String::class.java, String::class.java, String::class.java)
                        .invoke(defaultConfig, "String", "BUILD_TIME", "\"$buildTime\"")
                    project.logger.lifecycle("Added BUILD_TIME: $buildTime")
                } catch (e: Exception) {
                    project.logger.warn("Could not add BUILD_TIME buildConfigField: ${e.message}")
                }
            }
        }

        // Auto-increment version on assemble/bundle tasks
        project.tasks.whenTaskAdded {
            if (name.contains("assemble", true) || name.contains("bundle", ignoreCase = true)) {
                doLast {
                    if (name.contains("Release", true)) {
                        Versioning.incrementVersionPatch(project)
                        project.logger.lifecycle("Incremented patch version: ${Versioning.getVersionName(project)}")
                    } else {
                        Versioning.incrementVersionBuild(project)
                        project.logger.lifecycle("Incremented build version: ${Versioning.getVersionName(project, "debug")}")
                    }
                }
            }
        }
    }
}
