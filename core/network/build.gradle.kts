plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.example.network"
    compileSdk = 36

    buildFeatures {
        buildConfig = true
    }


}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    // networking
    implementation(platform(libs.retrofit.bom))
    implementation(platform (libs.okhttp.bom))
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
}