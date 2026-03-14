package com.codehospital.versioning

import org.gradle.BuildResult
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.execution.TaskExecutionGraph
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
        val extension = project.extensions.findByName(VersioningExtension.NAME) as? VersioningExtension
            ?: project.extensions.create(
                VersioningExtension.NAME,
                VersioningExtension::class.java,
                project
            )
        if (project.extensions.findByName(VersioningExtension.NAME) == null) {
            project.extensions.add(VersioningExtension.NAME, extension)
        }

        // Also keep extraProperties for backward compatibility
        project.extensions.extraProperties.set("Versioning", VersioningFacade())

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

        configureVersionIncrement(project)
    }

    private fun configureVersionIncrement(project: Project) {
        var shouldIncrementBuild = false
        var shouldIncrementPatch = false

        project.gradle.taskGraph.whenReady(object : Action<TaskExecutionGraph> {
            override fun execute(taskGraph: TaskExecutionGraph) {
                val projectTaskNames = taskGraph.allTasks
                    .filter { it.project == project }
                    .map { it.name }

                shouldIncrementBuild = projectTaskNames.any(::isVersioningBuildTask)
                shouldIncrementPatch = projectTaskNames.any(::isReleaseVersioningTask)
            }
        })

        project.gradle.buildFinished(object : Action<BuildResult> {
            override fun execute(result: BuildResult) {
                if (result.failure != null || !shouldIncrementBuild) {
                    return
                }

                if (shouldIncrementPatch) {
                    Versioning.incrementVersionPatch(project)
                    project.logger.lifecycle("Incremented patch version: ${Versioning.getVersionName(project)}")
                }

                Versioning.incrementVersionBuild(project)
                project.logger.lifecycle("Incremented build version: ${Versioning.getVersionName(project, "debug")}")
            }
        })
    }

    private fun isVersioningBuildTask(taskName: String): Boolean {
        return taskName.equals("build", ignoreCase = true) ||
            taskName.startsWith("assemble", ignoreCase = true) ||
            taskName.startsWith("bundle", ignoreCase = true)
    }

    private fun isReleaseVersioningTask(taskName: String): Boolean {
        return taskName.contains("release", ignoreCase = true) &&
            (taskName.startsWith("assemble", ignoreCase = true) ||
                taskName.startsWith("bundle", ignoreCase = true))
    }
}
