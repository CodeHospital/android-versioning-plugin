plugins {
    `maven-publish`
}

group = "com.codehospital"
version = "1.0.5"

// Disable publishing for root project
publishing {
    publications.clear()
}
