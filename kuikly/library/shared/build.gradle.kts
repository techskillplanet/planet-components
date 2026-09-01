plugins {
    kotlin("multiplatform")
    kotlin("plugin.compose")
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("org.jetbrains.compose")
    id("com.tencent.kuikly-open.kuikly")
    id("maven-publish")
    id("signing")
}

group = "io.github.techskillplanet"
version = "0.2.0"

description = "TechSkillPlanet Planet Components Kuikly shared UI library."

val kuiklyVersion = "2.4.0-2.0.21"
val githubRepoUrl = "https://github.com/techskillplanet/planet-components"
val mavenArtifactBase = "planet-components-kuikly"

kotlin {
    androidTarget {
        // Required for maven-publish to emit Android AAR publications (parity with Android View stack).
        publishLibraryVariants("release")
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { target ->
        target.binaries.framework {
            baseName = "PlanetComponentsKuiklyShared"
            isStatic = true
            binaryOption("bundleId", "com.techskillplanet.planetcomponents.kuikly.shared")
        }
    }

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
    namespace = "com.techskillplanet.planetcomponents.kuikly"
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

    publishing {
        singleVariant("release") {
            withSourcesJar()
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
        add("kspAndroid", this)
    }
}

fun Project.publishProp(name: String): String? {
    (findProperty(name) as String?)?.takeIf { it.isNotBlank() }?.let { return it }
    val files = listOf(
        rootProject.file("../android/gradle.properties"),
        file("${System.getProperty("user.home")}/.gradle/gradle.properties"),
    )
    for (f in files) {
        if (!f.isFile) continue
        val line = f.readLines().lastOrNull { it.startsWith("$name=") } ?: continue
        val value = line.substringAfter("=").trim()
        if (value.isNotEmpty()) return value
    }
    return null
}

fun Project.resolveSigningKeyFile(): java.io.File? {
    val configured = publishProp("signingKeyFile")
    val candidates = listOfNotNull(
        configured,
        "${System.getProperty("user.home")}/.gnupg/planet-components-signing.asc",
        "/home/litingzhe/.gnupg/planet-components-signing.asc",
    )
    return candidates.map { file(it) }.firstOrNull { it.isFile }
}

val ossrhUsernameValue = publishProp("ossrhUsername").orEmpty()
val ossrhPasswordValue = publishProp("ossrhPassword").orEmpty()
val signingKeyId = publishProp("signing.keyId")
val signingPassword = publishProp("signing.password")
val signingKeyFileResolved = resolveSigningKeyFile()
val signingInMemoryKey = publishProp("signingInMemoryKey") ?: publishProp("signing.secretKey")
val hasSigningKey = !signingKeyId.isNullOrBlank() &&
    (signingKeyFileResolved != null || !signingInMemoryKey.isNullOrBlank())

publishing {
    publications.withType<MavenPublication>().configureEach {
        // KMP defaults to project.name ("shared"); align with Android-style artifact id.
        artifactId = artifactId.replace(Regex("^${Regex.escape(project.name)}"), mavenArtifactBase)

        pom {
            name.set("TechSkillPlanet Planet Components Kuikly")
            description.set(
                "Cross-platform Planet Components UI library for Tencent Kuikly " +
                    "(Kotlin Multiplatform, token-driven Sky Planet theme)."
            )
            url.set(githubRepoUrl)
            inceptionYear.set("2026")
            licenses {
                license {
                    name.set("The MIT License")
                    url.set("https://opensource.org/licenses/MIT")
                    distribution.set("repo")
                }
            }
            developers {
                developer {
                    id.set("techskillplanet")
                    name.set("TechSkillPlanet")
                    organization.set("TechSkillPlanet")
                    organizationUrl.set("https://github.com/techskillplanet")
                }
            }
            scm {
                connection.set("scm:git:git://github.com/techskillplanet/planet-components.git")
                developerConnection.set("scm:git:ssh://git@github.com:techskillplanet/planet-components.git")
                url.set("$githubRepoUrl/tree/main/kuikly/library/shared")
            }
        }
    }

    repositories {
        maven {
            name = "MavenCentral"
            // OSSRH Staging API compatibility layer for Central Portal (same as Android).
            url = uri("https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/")
            credentials {
                username = ossrhUsernameValue
                password = ossrhPasswordValue
            }
        }
    }
}

// DomainObjectCollection overload signs current + future KMP publications.
if (hasSigningKey) {
    val keyMaterial = when {
        signingKeyFileResolved != null -> signingKeyFileResolved.readText(Charsets.UTF_8)
        else -> signingInMemoryKey!!
    }
    val pass = signingPassword ?: ""
    signing {
        // Prefer 2-arg overload so keyId is derived from the key material itself.
        useInMemoryPgpKeys(keyMaterial, pass)
        require(signatory != null) {
            "useInMemoryPgpKeys did not produce a signatory (check key material / passphrase)"
        }
        sign(publishing.publications)
    }
    logger.lifecycle(
        "Kuikly publish: signing enabled (keyId=$signingKeyId, keyFile=${signingKeyFileResolved?.absolutePath}, signatoryOk=true)",
    )
} else {
    logger.warn("Kuikly publish: no signing key configured; publications will be unsigned.")
}
