package com.codehospital.versioning;

import org.gradle.api.Project;

/**
 * Gradle extension for accessing versioning functionality in build scripts.
 * This makes Versioning accessible as `versioning` in build.gradle.kts.
 */
public class VersioningExtension {
    public static final String NAME = "versioning";

    private final Project project;

    public VersioningExtension(Project project) {
        this.project = project;
    }

    public int getVersionCode(String buildType) {
        return Versioning.getVersionCode(project, buildType);
    }

    public String getVersionName(String buildType) {
        return Versioning.getVersionName(project, buildType);
    }

    public String getDebugSuffix() {
        return Versioning.getDebugSuffix(project);
    }

    public int getVersionBuild() {
        return Versioning.getVersionBuild(project);
    }

    public void incrementVersionBuild() {
        Versioning.incrementVersionBuild(project);
    }

    public void incrementVersionPatch() {
        Versioning.incrementVersionPatch(project);
    }
}
