# HealthTracker

A mobile health tracking project with:
- an **Android app** (`AndroidApp`) built with Kotlin, Jetpack libraries, Room, Hilt, Firebase, and Google Play services
- an **iOS app** (`iosApp`) built with SwiftUI
- a **Kotlin Multiplatform shared module** (`shared`) containing common models/repositories/domain code

## Features

### Android (`AndroidApp`)
- Authentication flows (email/password, Google Sign-In, Facebook auth path in repository)
- Dashboard, trends, profile, vitals logging, sleep analysis, medication tracking/reminders
- Food logging with barcode scanning (ML Kit)
- Document vault with PDF generation and file sharing
- Local persistence with Room
- Cloud sync with Firebase Auth, Firestore, and Storage
- Background services/workers for step counting and reminders

### iOS (`iosApp`)
- SwiftUI tab-based interface (Dashboard, Trends, Tools, Profile)
- Onboarding + settings-driven goals
- In-app sample health metric/log state management

## Technology Stack

- **Languages:** Kotlin, Swift
- **Android:** Android SDK (minSdk 26, targetSdk 34), AndroidX, Material Components, Navigation, Room, WorkManager, DataStore, CameraX
- **Dependency Injection:** Dagger Hilt
- **Backend/Cloud:** Firebase Authentication, Cloud Firestore, Firebase Storage
- **Google Services:** Google Sign-In, Location Services, ML Kit Barcode Scanning
- **Charts/PDF/Utilities:** MPAndroidChart, iText 7, Coil, CircleImageView
- **Shared Module:** Kotlin Multiplatform + kotlinx.coroutines + kotlinx.serialization + kotlinx.datetime + Koin
- **iOS UI:** SwiftUI
- **Build Tools:** Gradle (Kotlin DSL), Android Gradle Plugin 8.7.3, Kotlin 2.0.21, Xcode project for iOS

## Project Structure

```text
HealthTrackerApp/
├── AndroidApp/        # Android application module
├── iosApp/            # iOS Xcode project and SwiftUI app
├── shared/            # Kotlin Multiplatform shared code
├── build.gradle.kts   # Root Gradle config
├── settings.gradle.kts
└── gradle/            # Gradle wrapper and version catalog
```

## Setup

### Prerequisites
- **JDK 17**
- **Android Studio** (for Android development)
- **Xcode** (for iOS development)
- Android SDK matching project settings (compileSdk 34)

### Clone and open
```bash
git clone https://github.com/majidali1256/HealthTrackerApp.git
cd HealthTrackerApp
```

## Configuration / Environment

This repository does not use `.env` files.

For Android Firebase features, ensure Firebase is configured for your app package (`com.healthtracker.app`) and that `AndroidApp/google-services.json` contains the correct project credentials for your environment.

If Google Sign-In is not fully configured, the app falls back to basic Google sign-in options (see `FirebaseModule`).

## Run

### Android
Build debug APK:
```bash
./gradlew :AndroidApp:assembleDebug
```

Install/run from Android Studio by opening the project root and running the `AndroidApp` configuration.

### iOS
Open and run in Xcode:
```bash
open iosApp/iosApp.xcodeproj
```

Then select a simulator/device and run the `iosApp` target.

## Usage Examples

### Android flow
1. Launch app (Splash)
2. Sign up / sign in
3. Complete profile setup
4. Use dashboard/tools to log vitals, symptoms, food, medications, sleep, and documents
5. Review trends and profile data

### iOS flow
1. Launch app and complete onboarding
2. Navigate tabs (Home, Summary, Tools, Profile)
3. Update goals and add logs to see score/trend updates

## Testing and Build Commands

Run Android unit tests:
```bash
./gradlew :AndroidApp:testDebugUnitTest
```

Run Android instrumentation tests:
```bash
./gradlew :AndroidApp:connectedDebugAndroidTest
```

Build Android release APK:
```bash
./gradlew :AndroidApp:assembleRelease
```

## Deployment Notes

- No CI/CD workflows are defined in this repository.
- Android release artifacts are produced via Gradle/Android Studio (`assembleRelease` / signed release process).
- iOS distribution follows the standard Xcode archive/export workflow.
