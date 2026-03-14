package com.codehospital.versioning

import org.gradle.testkit.runner.GradleRunner
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.util.Properties

class VersioningPluginFunctionalTest {

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Test
    fun `increments version build for release assemble tasks`() {
        val projectDir = temporaryFolder.newFolder()
        val versionPropertiesFile = File(projectDir, "version.properties")

        writeFile(
            File(projectDir, "settings.gradle.kts"),
            "rootProject.name = \"versioning-functional-test\""
        )
        writeFile(
            File(projectDir, "build.gradle.kts"),
            """
            plugins {
                id("com.codehospital.versioning")
            }

            tasks.register("assembleRelease")
            """.trimIndent()
        )
        writeProperties(versionPropertiesFile, major = 1, minor = 0, patch = 1, build = 7)

        GradleRunner.create()
            .withProjectDir(projectDir)
            .withArguments("assembleRelease")
            .withPluginClasspath()
            .build()

        val properties = readProperties(versionPropertiesFile)
        assertEquals("2", properties.getProperty("Version_Patch"))
        assertEquals("8", properties.getProperty("Version_Build"))
    }

    private fun writeFile(file: File, contents: String) {
        file.writeText(contents)
    }

    private fun writeProperties(file: File, major: Int, minor: Int, patch: Int, build: Int) {
        val properties = Properties().apply {
            setProperty("Version_Major", major.toString())
            setProperty("Version_Minor", minor.toString())
            setProperty("Version_Patch", patch.toString())
            setProperty("Version_Build", build.toString())
        }

        file.outputStream().use {
            properties.store(it, "Test version properties")
        }
    }

    private fun readProperties(file: File): Properties {
        return Properties().apply {
            file.inputStream().use { load(it) }
        }
    }
}
