plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("org.jetbrains.compose")
    id("com.tencent.kuikly-open.kuikly")
}

group = "com.techskillplanet.phonics"
version = "1.0.0"

description = "TechSkillPlanet Kuikly shared UI library and samples."

val kuiklyVersion = "2.4.0-2.0.21"

kotlin {
    androidTarget()

    js(IR) {
        moduleName = "nativevue2"
        browser {
            webpackTask {
                outputFileName = "nativevue2.js"
            }
            commonWebpackConfig {
                output?.library = null
                devtool = null
            }
        }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.tencent.kuikly-open:core:$kuiklyVersion")
                implementation("com.tencent.kuikly-open:compose:$kuiklyVersion")
                implementation("com.tencent.kuikly-open:core-annotations:$kuiklyVersion")
            }
        }
    }
}

ksp {
    arg("pageName", "")
    arg("pageNameList", "")
    arg("packLocalJSBundle", "")
}

android {
    namespace = "com.techskillplanet.phonics.kuikly.shared"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            assets.srcDirs("src/commonMain/assets")
        }
    }
}

kuikly {
    js {
        outputName("nativevue2")
    }
}

dependencies {
    compileOnly("com.tencent.kuikly-open:core-ksp:$kuiklyVersion") {
        add("kspJs", this)
    }
}
