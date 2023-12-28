
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    //ksp -(if error then check new version)
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
    //hilt
    id("com.google.dagger.hilt.android") version "2.44" apply false

}
buildscript {
    repositories {
        maven {
            url = java.net.URI("https://jitpack.io")
        }
    }
}