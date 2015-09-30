# Binder - Android App

## Setup

1. Install [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
2. Install the [Android SDK](http://developer.android.com/sdk/index.html)
3. Run `android` and install the following packages:

    * Tools
      * Android SDK Tools
      * Android SDK Platform-tools
      * Android SDK Build-tools
    * Android 6.0 (API 23)
      * SDK Platform
    * Extras
      * Android Support Repository

4. (Optional) Install "Kotlin" plugin for Android Studio
5. (Optional) Add the following line to your `~/.gradle/gradle.properties`
`org.gradle.daemon=true`

## Build

1. `gradle build`

## Test

1. Ensure that you've got an android device connected that shows up when you `adb devices`
2. `gradle connectedAndroidTest`

## Deploy to device

1. `gradle installDebug`