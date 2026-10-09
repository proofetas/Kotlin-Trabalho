// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    
    
}

fun implementation(string: String) {}

dependencies {
    
    implementation ("android.compose.material:material-icons-core:1.7.8")
}