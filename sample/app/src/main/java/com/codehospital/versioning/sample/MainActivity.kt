package com.codehospital.versioning.sample

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            val padding = (1+24 * resources.displayMetrics.density).toInt()
            setPadding(padding, padding, padding, padding)
        }

        val lines = listOf(
            "Versioning Plugin Sample",
            "",
            "Build variant: ${if (BuildConfig.DEBUG) "debug" else "release"}",
            "Release version name: ${BuildConfig.PLUGIN_RELEASE_VERSION_NAME}",
            "Release version code: ${BuildConfig.PLUGIN_RELEASE_VERSION_CODE}",
            "Debug version name: ${BuildConfig.PLUGIN_DEBUG_VERSION_NAME}",
            "Debug version code: ${BuildConfig.PLUGIN_DEBUG_VERSION_CODE}",
            "Version build: ${BuildConfig.PLUGIN_VERSION_BUILD}",
            "Debug suffix: ${BuildConfig.PLUGIN_DEBUG_SUFFIX}",
            "Build time: ${BuildConfig.BUILD_TIME}",
        )

        lines.forEach { line ->
            container.addView(
                TextView(this).apply {
                    text = line
                    textSize = if (line == "Versioning Plugin Sample") 22f else 16f
                }
            )
        }

        setContentView(
            ScrollView(this).apply {
                addView(container)
            }
        )
    }
}
