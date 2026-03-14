# Sample Android App

This sample app applies the local `com.codehospital.versioning` plugin via `includeBuild("..")`.

## Build it

```bash
./gradlew -p sample :app:assembleDebug
./gradlew -p sample :app:assembleRelease
```

## What to check

- `sample/version.properties` is used as the source of truth.
- Successful build-style tasks increment `Version_Build`.
- Release builds also increment `Version_Patch`.
- Launching the app shows the plugin-generated release/debug version values and build time.
