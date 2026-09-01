pluginManagement {
    repositories {
        // Avoid Aliyun for plugins: incomplete KSP metadata / intermittent 502 breaks resolution.
        gradlePluginPortal()
        mavenCentral()
        google()
        maven { url = uri("https://mirrors.tencent.com/repository/maven-tencent/") }
        maven { url = uri("https://mirrors.tencent.com/nexus/repository/gradle-plugins/") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        maven { url = uri("https://maven.aliyun.com/repository/google") }
        maven { url = uri("https://maven.aliyun.com/repository/central") }
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        maven { url = uri("https://mirrors.tencent.com/repository/maven-tencent/") }
        google()
        mavenCentral()
    }
}

rootProject.name = "PlanetComponentsKuikly"

include(":shared")
include(":miniApp")
include(":androidApp")

project(":shared").projectDir = file("library/shared")
project(":miniApp").projectDir = file("samples/miniApp")
project(":androidApp").projectDir = file("samples/androidApp")
