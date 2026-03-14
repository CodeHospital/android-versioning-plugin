package com.codehospital.versioning;

import org.gradle.api.Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Versioning utility for managing version codes and names in Android projects.
 */
public final class Versioning {
    private static final String VERSION_PROPERTIES_FILE = "version.properties";
    private static final String VERSION_BUILD = "Version_Build";
    private static final String VERSION_PATCH = "Version_Patch";
    private static final String VERSION_MINOR = "Version_Minor";
    private static final String VERSION_MAJOR = "Version_Major";

    private Versioning() {
    }

    public static int getVersionCode(Project project, String buildType) {
        Properties properties = loadVersionProperties(project);
        int major = Integer.parseInt(properties.getProperty(VERSION_MAJOR, "0"));
        int minor = Integer.parseInt(properties.getProperty(VERSION_MINOR, "0"));
        int patch = Integer.parseInt(properties.getProperty(VERSION_PATCH, "1"));
        int build = Integer.parseInt(properties.getProperty(VERSION_BUILD, "1"));
        int code = major * 1_000_000 + minor * 1_000 + patch;

        if ("debug".equals(buildType)) {
            return code * 1_000 + build;
        }

        return code;
    }

    public static String getVersionName(Project project, String buildType) {
        Properties properties = loadVersionProperties(project);
        String major = properties.getProperty(VERSION_MAJOR, "0");
        String minor = properties.getProperty(VERSION_MINOR, "0");
        String patch = properties.getProperty(VERSION_PATCH, "1");
        String build = properties.getProperty(VERSION_BUILD, "1");

        if ("debug".equals(buildType)) {
            return major + "." + minor + "." + patch + " v" + build;
        }

        return major + "." + minor + "." + patch;
    }

    public static String getDebugSuffix(Project project) {
        Properties properties = loadVersionProperties(project);
        return " v" + properties.getProperty(VERSION_BUILD, "1");
    }

    public static int getVersionBuild(Project project) {
        Properties properties = loadVersionProperties(project);
        return Integer.parseInt(properties.getProperty(VERSION_BUILD, "1"));
    }

    public static void incrementVersionBuild(Project project) {
        Properties properties = loadVersionProperties(project);
        int build = Integer.parseInt(properties.getProperty(VERSION_BUILD, "0")) + 1;
        properties.setProperty(VERSION_BUILD, Integer.toString(build));
        saveVersionProperties(project, properties);
    }

    public static void incrementVersionPatch(Project project) {
        Properties properties = loadVersionProperties(project);
        int patch = Integer.parseInt(properties.getProperty(VERSION_PATCH, "0")) + 1;
        properties.setProperty(VERSION_PATCH, Integer.toString(patch));
        saveVersionProperties(project, properties);
    }

    private static Properties loadVersionProperties(Project project) {
        Properties properties = new Properties();
        File versionPropertiesFile = resolveVersionPropertiesFile(project);

        if (versionPropertiesFile.exists()) {
            try (InputStream inputStream = new FileInputStream(versionPropertiesFile)) {
                properties.load(inputStream);
            } catch (IOException exception) {
                throw new RuntimeException("Failed to read " + versionPropertiesFile, exception);
            }
        } else {
            properties.setProperty(VERSION_MAJOR, "1");
            properties.setProperty(VERSION_MINOR, "0");
            properties.setProperty(VERSION_PATCH, "1");
            properties.setProperty(VERSION_BUILD, "1");
            saveVersionProperties(project, properties);
        }

        return properties;
    }

    private static void saveVersionProperties(Project project, Properties properties) {
        File versionPropertiesFile = resolveVersionPropertiesFile(project);
        try (OutputStream outputStream = new FileOutputStream(versionPropertiesFile)) {
            properties.store(outputStream, "Version properties for " + project.getName());
        } catch (IOException exception) {
            throw new RuntimeException("Failed to write " + versionPropertiesFile, exception);
        }
    }

    private static File resolveVersionPropertiesFile(Project project) {
        File rootVersionPropertiesFile = project.getRootProject().file(VERSION_PROPERTIES_FILE);
        File projectVersionPropertiesFile = project.file(VERSION_PROPERTIES_FILE);

        if (rootVersionPropertiesFile.getAbsoluteFile().equals(projectVersionPropertiesFile.getAbsoluteFile())) {
            return rootVersionPropertiesFile;
        }
        if (rootVersionPropertiesFile.exists()) {
            return rootVersionPropertiesFile;
        }
        if (projectVersionPropertiesFile.exists()) {
            return projectVersionPropertiesFile;
        }
        return rootVersionPropertiesFile;
    }
}
