# Binder - Android App

by
Chris Foster, Mitch Hentges, Sheldon Roddick and Steve Lyall

Binder is currently under development. We appreciate your feedback on the app as well as any input and suggestions you might have. Feel free to email us at binderappdevelopment@gmail.com or connect with us on GitHub at https://github.com/binder-app/android.


Binder is an Android application designed to match students with study buddies. It is accessible to anyone with an Android device running Android 4.1 or later.

##Initial Set-Up
When you first launch the app, you will be asked to complete your profile, which includes your name, photo, phone number (this lets you get in touch with your matches), program of study, academic year, courses in progress and a short biography.

To take a photo, tap the grey camera icon.

To select your courses, begin typing your course name in the Find Courses box. A list of possible courses is shown. Once you choose one, tap the plus sign to add it to your course list. To remove a course from your list of selected courses, just tap and hold the course in the course list.

When you have completed your profile, tap the green Start button to start looking for matches!

## Browsing Suggestions
Binder shows you profiles of other Binder users, one at a time. To read more about them, tap the info icon in the top right-hand corner of the screen.

If you'd be interested in connecting with the user you're currently looking at, swipe their profile card to the right. If not, swipe to the left to ignore them. Choose carefully, as you only get to review users' profiles once!

## Reviewing Matches
If Binder finds that a user you like has also liked you, you'll be notified of the match when you launch the app. To get in touch, tap the green button to add the user to your phone contacts. You can then send the user a text to hook up to study when it works best for you both!

#Development

Project source available at https://github.com/binder-app/android

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

4. (Optional) Add the following line to your `~/.gradle/gradle.properties`
`org.gradle.daemon=true`

## Build

1. `gradle build`

## Test

1. Ensure that you've got an android device connected that shows up when you `adb devices`
2. `gradle connectedAndroidTest`

## Deploy to device

1. `gradle installDebug`