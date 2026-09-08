# Burn Subtitle — agent memory

Offline Android app that hard-burns subtitles into video using FFmpeg packaged in the APK.

Read `.cursor/rules/project-memory.mdc` for architecture, what is already built, and how to continue.

## Constraints

- Kotlin, minSdk 26, Compose, Hilt, WorkManager
- No internet permission, no root
- Users never install FFmpeg
- Release/FFmpeg native builds: GitHub Actions only

## Current gap

CI `build-apk.yml` now restores FFmpeg `jniLibs` + headers from `native-cache.yml` before assemble. Local CMake still stubs (exit 64) until those artifacts exist on the machine.
