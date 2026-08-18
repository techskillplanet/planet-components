pluginManagement {
    repositories {
        mavenCentral()
        google()
        maven {
            url = uri("https://mirrors.tencent.com/repository/maven-tencent/")
        }
        maven {
            url = uri("https://mirrors.tencent.com/nexus/repository/gradle-plugins/")
        }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://mirrors.tencent.com/repository/maven-tencent/")
        }
    }
}

rootProject.name = "PlanetComponentsKuikly"

include(":shared")
include(":miniApp")
include(":androidApp")

project(":shared").projectDir = file("library/shared")
project(":miniApp").projectDir = file("samples/miniApp")
project(":androidApp").projectDir = file("samples/androidApp")
