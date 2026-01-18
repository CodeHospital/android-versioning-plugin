plugins {
    `maven-publish`
}

group = "com.codehospital"
version = "1.0.4"

// Disable publishing for root project
publishing {
    publications.clear()
}
