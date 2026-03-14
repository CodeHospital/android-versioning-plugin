package com.codehospital.versioning

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.util.Properties

class VersioningTest {

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Test
    fun `uses root version properties when both root and module files exist`() {
        val (rootProject, moduleProject) = createProjectHierarchy()
        val rootVersionFile = rootProject.file("version.properties")
        val moduleVersionFile = moduleProject.file("version.properties")

        writeProperties(rootVersionFile, major = 2, minor = 5, patch = 8, build = 13)
        writeProperties(moduleVersionFile, major = 9, minor = 9, patch = 9, build = 9)

        assertEquals("2.5.8", Versioning.getVersionName(moduleProject))
        assertEquals("2.5.8 v13", Versioning.getVersionName(moduleProject, "debug"))

        Versioning.incrementVersionBuild(moduleProject)

        assertEquals("14", readProperties(rootVersionFile).getProperty("Version_Build"))
        assertEquals("9", readProperties(moduleVersionFile).getProperty("Version_Build"))
    }

    @Test
    fun `falls back to module version properties when root file is missing`() {
        val (_, moduleProject) = createProjectHierarchy()
        val moduleVersionFile = moduleProject.file("version.properties")

        writeProperties(moduleVersionFile, major = 4, minor = 1, patch = 7, build = 3)

        assertEquals("4.1.7", Versioning.getVersionName(moduleProject))

        Versioning.incrementVersionBuild(moduleProject)

        assertEquals("4", readProperties(moduleVersionFile).getProperty("Version_Build"))
    }

    @Test
    fun `creates version properties in root project when no file exists`() {
        val (rootProject, moduleProject) = createProjectHierarchy()
        val rootVersionFile = rootProject.file("version.properties")
        val moduleVersionFile = moduleProject.file("version.properties")

        assertEquals("1.0.1", Versioning.getVersionName(moduleProject))

        assertTrue(rootVersionFile.exists())
        assertFalse(moduleVersionFile.exists())
    }

    private fun createProjectHierarchy(): Pair<Project, Project> {
        val rootDir = temporaryFolder.newFolder()
        val moduleDir = File(rootDir, "app").apply { mkdirs() }

        val rootProject = ProjectBuilder.builder()
            .withName("root")
            .withProjectDir(rootDir)
            .build()

        val moduleProject = ProjectBuilder.builder()
            .withName("app")
            .withProjectDir(moduleDir)
            .withParent(rootProject)
            .build()

        return rootProject to moduleProject
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
