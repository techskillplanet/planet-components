---
name: integrate-android-view
description: >-
  Integrate io.github.techskillplanet:planet-components-android from Maven Central
  into an Android View (Java) app. Use when adding Basic* / token-driven controls
  without Compose.
---

# Integrate Android View

## Install

```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation "io.github.techskillplanet:planet-components-android:0.2.0"
}
```

## Usage

```java
BasicThemeManager.init(context, "sky_planet_day", "island_raised");
// then inflate / construct library widgets and call refreshTheme() after theme switch
```

## Notes

- Java + traditional View only in the library; do not pull Compose for consumption.
- Central: https://central.sonatype.com/artifact/io.github.techskillplanet/planet-components-android
- To **extend** the library source, use [build-android-view-ui](../build-android-view-ui/SKILL.md).
