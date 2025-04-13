// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    //firebase
    id("com.google.gms.google-services") version "4.4.2" apply false
    //Compose 컴파일러 Gradle 플러그인
    alias(libs.plugins.compose.compiler) apply false
    //Hilt
    id("com.google.dagger.hilt.android") version "2.56.1" apply false
}