# AetherPlayer 3.0

A native Android local music player built with Kotlin, Jetpack Compose, Media3 and Google Cast.

## Build with GitHub Actions

1. Import this project into the existing GitHub repository.
2. Keep the `.github/workflows/build.yml` workflow directory and file.
3. Push to `main` or `master`, or open **Actions → AetherPlayer 3.0 - Build APK → Run workflow**.
4. Download the `AetherPlayer-3.0-debug-apk` artifact from the completed run.
5. Install the APK over the previous installation when signing keys are compatible.

The workflow uses Java 17 and Gradle 8.7. No local Android Studio build is required.

## Project identity

- Application ID: `com.aether.player`
- Version name: `3.0`
- Version code: `5`
- Minimum SDK: 26
- Compile / target SDK: 34

## Preserved foundations

The original Media3 playback service, MediaSession, Google Cast integration, local HTTP file server, FLAC MIME fallback, equalizer, lyrics parser/repository, M3U playlist storage and play statistics are retained in the project. Casting continues to use the existing original-file delivery path; this update does not add a transcoding pipeline.

## AetherPlayer 3.0 additions in this source update

- AMOLED-first dark theme and a light theme option.
- Liquid-glass-inspired reusable surfaces with a low-cost fallback and adjustable intensity.
- Floating glass-style mini-player and navigation surface.
- Animated navigation transitions with a reduced-motion switch.
- Animated album-art replacement in Now Playing.
- Appearance Studio for theme, glass, motion and haptic preferences.
- Library search across title, artist, album, folder and detected format.
- Sorting by title, artist, album, date added, date modified, last played, play count, duration, file size, format and folder.
- Optional secondary sort condition and format filtering.
- Persistent primary sorting and appearance preferences.
- Additional MediaStore metadata in the song model: date modified, file size, duration and MIME type.
- Track audio-information panel showing metadata actually available to the library index.
- GitHub Actions workflow that runs the available debug unit-test task and builds/uploads the debug APK.

## Important implementation and verification notes

The source archive is an incremental major-update foundation, not a claim that every item in the broader 12-phase product specification is finished. Advanced codec probing (sample rate, bit depth and encoded bitrate), full smart-playlist rule builder, batch metadata editing, queue drag-and-drop, listening analytics dashboards, and a true player-session audio visualiser remain future implementation work. The audio information panel explicitly identifies unavailable fields rather than inventing values.

The original cast server, Media3 Cast integration, EQ, lyrics and playlist code were retained rather than rewritten. A successful GitHub build verifies compilation and APK packaging, but it cannot by itself verify physical-device playback, FLAC receiver compatibility, EQ behaviour or network casting. Those still require testing on the target phone and a real Cast receiver.

## Compatibility

The application ID and minimum SDK are unchanged. The version code has been incremented from 4 to 5. Seamless installation over an existing app also requires the same signing key used for the currently installed APK.
