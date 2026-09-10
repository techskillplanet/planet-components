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
    implementation "io.github.techskillplanet:planet-components-android:0.2.1"
}
```

## Usage

```java
BasicThemeManager.init(context, "sky_planet_day", "island_raised");
// Color keys: sky_planet_day | star_planet_night | mint_planet_day | sunrise_planet_day
// then inflate / construct library widgets and call refreshTheme() after theme switch
```

## Coverage

≈ **57** `Basic*View` controls. Includes **DatePicker** (`BasicDatePickerView`) and **Domain-7**: `BasicChildSwitcherView`, `BasicScoreRuleGridView`, `BasicRedeemCardGridView`, `BasicCalendarHeatmapView`, `BasicPrintSheetView`, `BasicBalanceHeroView`, `BasicCheckInStreakCardView`. W1: Avatar, Skeleton, Tooltip, Slider, TextArea, Drawer, InputNumber, Swiper. W2: Tag, Fab, TimePicker, Upload, Table, Tree, Cascader.

## Notes

- Java + traditional View only in the library; do not pull Compose for consumption.
- `BasicButton.setLoading(boolean)` for loading state.
- Library version **0.2.1** (publish after `./tools/headless-check.sh` is green).
- Central: https://central.sonatype.com/artifact/io.github.techskillplanet/planet-components-android
- To **extend** the library source, use [build-android-view-ui](../build-android-view-ui/SKILL.md).
