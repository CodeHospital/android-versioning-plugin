plugins {
    `maven-publish`
}

group = "com.codehospital"
version = "1.0.7"

// Disable publishing for root project
publishing {
    publications.clear()
}
