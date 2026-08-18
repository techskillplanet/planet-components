plugins {
    id("com.android.application")
    kotlin("android")
}

val kuiklyVersion = "2.4.0-2.0.21"

android {
    namespace = "com.techskillplanet.planetcomponents.kuikly.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.techskillplanet.planetcomponents.kuikly.sample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.2.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("com.tencent.kuikly-open:core-render-android:$kuiklyVersion")
    implementation("com.tencent.kuikly-open:core:$kuiklyVersion")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.7.0")
}
