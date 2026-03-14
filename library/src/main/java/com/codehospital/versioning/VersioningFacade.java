package com.codehospital.versioning;

import org.gradle.api.Project;

/**
 * Backward-compatible instance facade for builds that access `Versioning`
 * through Gradle extra properties.
 */
public final class VersioningFacade {
    public int getVersionCode(Project project) {
        return Versioning.getVersionCode(project);
    }

    public int getVersionCode(Project project, String buildType) {
        return Versioning.getVersionCode(project, buildType);
    }

    public String getVersionName(Project project) {
        return Versioning.getVersionName(project);
    }

    public String getVersionName(Project project, String buildType) {
        return Versioning.getVersionName(project, buildType);
    }

    public String getDebugSuffix(Project project) {
        return Versioning.getDebugSuffix(project);
    }

    public int getVersionBuild(Project project) {
        return Versioning.getVersionBuild(project);
    }

    public void incrementVersionBuild(Project project) {
        Versioning.incrementVersionBuild(project);
    }

    public void incrementVersionPatch(Project project) {
        Versioning.incrementVersionPatch(project);
    }
}
