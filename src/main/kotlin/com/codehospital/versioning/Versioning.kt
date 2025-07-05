package com.codehospital.versioning

import org.gradle.api.Project
import java.util.Properties

/**
 * Versioning utility for managing version codes and names in Android projects.
 * This is a placeholder implementation to allow the project to compile.
 */
object Versioning {
    private const val VERSION_PROPERTIES_FILE = "version.properties"
    private const val VERSION_BUILD = "versionBuild"
    private const val VERSION_PATCH = "versionPatch"
    private const val VERSION_MINOR = "versionMinor"
    private const val VERSION_MAJOR = "versionMajor"

    /**
     * Gets the current version code from the version.properties file.
     */
    @JvmStatic
    fun getVersionCode(project: Project, buildType: String = "debug"): Int {
        val properties = loadVersionProperties(project)
        val major = properties.getProperty(VERSION_MAJOR, "0").toInt()
        val minor = properties.getProperty(VERSION_MINOR, "0").toInt()
        val patch = properties.getProperty(VERSION_PATCH, "1").toInt()
        val build = properties.getProperty(VERSION_BUILD, "1").toInt()
        val code = major * 1000_000 + minor * 1000 + patch
        if (buildType == "debug")
            return code * 1000 + build
        return code
    }

    /**
     * Gets the current version name in the format major.minor.patch.
     */
    @JvmStatic
    fun getVersionName(project: Project, buildType: String = "debug"): String {
        val properties = loadVersionProperties(project)
        val major = properties.getProperty(VERSION_MAJOR, "0")
        val minor = properties.getProperty(VERSION_MINOR, "0")
        val patch = properties.getProperty(VERSION_PATCH, "1")
        val build = properties.getProperty(VERSION_BUILD, "1")
        if (buildType == "debug")
            return "$major.$minor.$patch v$build"
        return "$major.$minor.$patch"
    }

    /**
     * Gets the current build number.
     */
    @JvmStatic
    fun getVersionBuild(project: Project): Int {
        val properties = loadVersionProperties(project)
        return properties.getProperty(VERSION_BUILD, "1").toInt()
    }

    /**
     * Increments the build number.
     */
    @JvmStatic
    fun incrementVersionBuild(project: Project) {
        val properties = loadVersionProperties(project)
        val build = properties.getProperty(VERSION_BUILD, "0").toInt() + 1
        properties.setProperty(VERSION_BUILD, build.toString())
        saveVersionProperties(project, properties)
    }

    /**
     * Increments the patch version and resets the build number.
     */
    @JvmStatic
    fun incrementVersionPatch(project: Project) {
        val properties = loadVersionProperties(project)
        val patch = properties.getProperty(VERSION_PATCH, "0").toInt() + 1
        properties.setProperty(VERSION_PATCH, patch.toString())
        saveVersionProperties(project, properties)
    }

    /**
     * Loads the version properties file. Creates one with default values if it doesn't exist.
     */
    private fun loadVersionProperties(project: Project): Properties {
        val properties = Properties()
        val versionPropertiesFile = project.file(VERSION_PROPERTIES_FILE)
        
        if (versionPropertiesFile.exists()) {
            versionPropertiesFile.inputStream().use { properties.load(it) }
        } else {
            // Initialize with default values if the file doesn't exist
            properties.setProperty(VERSION_MAJOR, "1")
            properties.setProperty(VERSION_MINOR, "0")
            properties.setProperty(VERSION_PATCH, "1")
            properties.setProperty(VERSION_BUILD, "1")
            saveVersionProperties(project, properties)
        }
        
        return properties
    }

    /**
     * Saves the version properties to the version.properties file.
     */
    private fun saveVersionProperties(project: Project, properties: Properties) {
        val versionPropertiesFile = project.file(VERSION_PROPERTIES_FILE)
        versionPropertiesFile.outputStream().use {
            properties.store(it, "Version properties for ${project.name}")
        }
    }
}
