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

rootProject.name = "PhonicsPlanetKuikly"

include(":shared")
include(":miniApp")

project(":shared").projectDir = file("library/shared")
project(":miniApp").projectDir = file("samples/miniApp")
