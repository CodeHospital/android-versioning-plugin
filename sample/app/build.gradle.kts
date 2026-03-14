plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.codehospital.versioning)
}

android {
    namespace = "com.codehospital.versioning.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.codehospital.versioning.sample"
        minSdk = 24
        targetSdk = 34

        versionCode = versioning.getVersionCode("release")
        versionName = versioning.getVersionName("release")
        buildConfigField("String", "PLUGIN_RELEASE_VERSION_NAME", "\"${versioning.getVersionName("release")}\"")
        buildConfigField("int", "PLUGIN_RELEASE_VERSION_CODE", "${versioning.getVersionCode("release")}")
        buildConfigField("String", "PLUGIN_DEBUG_VERSION_NAME", "\"${versioning.getVersionName("debug")}\"")
        buildConfigField("int", "PLUGIN_DEBUG_VERSION_CODE", "${versioning.getVersionCode("debug")}")
        buildConfigField("int", "PLUGIN_VERSION_BUILD", "${versioning.getVersionBuild()}")
        buildConfigField("String", "PLUGIN_DEBUG_SUFFIX", "\"${versioning.getDebugSuffix()}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            versionNameSuffix = versioning.getDebugSuffix()
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(platform(libs.kotlin.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
}
