plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.codehospital.versioning) apply false
}

// Root build config kept minimal. App module defined in app/build.gradle.kts
