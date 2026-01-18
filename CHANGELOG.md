# Changelog

All notable changes to this project will be documented in this file.

## [1.0.5] - 2026-01-18

### Added
- Auto-increment patch version on Release assemble/bundle tasks
- Auto-increment build version on Debug/other assemble/bundle tasks

## [1.0.4] - 2026-01-18

### Fixed
- Fixed duplicate class issue by disabling root project publishing
- Updated jitpack.yml to only publish library module

## [1.0.3] - 2026-01-18

### Changed
- Removed Gradle Plugin Portal publishing (com.gradle.plugin-publish)
- Simplified gradlePlugin configuration for JitPack-only publishing

## [1.0.2] - 2026-01-18

### Fixed
- Synced version between root and library modules

## [1.0.1] - 2026-01-17

### Changed
- Configured project for JitPack publishing
- Added proper maven-publish configuration with POM metadata
- Added sources and javadoc jars for publication
- Removed hardcoded credentials from build files
- Cleaned up build.gradle.kts files
- Added library module to settings.gradle.kts
- Added jitpack.yml for JitPack build configuration

## [1.0.0] - Initial Release

### Added
- Initial versioning plugin implementation
- Support for semantic versioning
- Android project version management
