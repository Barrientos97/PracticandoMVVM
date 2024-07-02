// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    // hilt
    alias(libs.plugins.dagger.hilt.android) apply false

    //Para las anotaciones KSP
    alias(libs.plugins.ksp) apply false

    // Para las anotaciones kotlin-kapt
    alias(libs.plugins.kotlin.kapt) apply false
}