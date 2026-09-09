---
name: integrate-kuikly
description: >-
  Integrate io.github.techskillplanet:planet-components-kuikly from Maven Central
  into a Tencent Kuikly / KMP project. Use when adding Tsp* Kuikly Compose controls.
---

# Integrate Kuikly

## Install

```gradle
repositories {
    mavenCentral()
    maven { url = uri("https://mirrors.tencent.com/repository/maven-tencent/") }
}

dependencies {
    implementation("io.github.techskillplanet:planet-components-kuikly:0.2.1")
}
```

Tencent mirror is required for `com.tencent.kuikly-open` transitive deps.

## Coverage

≈ **35** `Tsp*` Kuikly controls. Includes **DatePicker** (`TspDatePicker`) and **Domain-7**: `TspChildSwitcher`, `TspScoreRuleGrid`, `TspRedeemCardGrid`, `TspCalendarHeatmap`, `TspPrintSheet`, `TspBalanceHero`, `TspCheckInStreakCard`.

## Notes

- Theme keys include `sky` / `night` / `mint` / `sunrise` via `PhonicsTheme`.
- `TspButton` supports `loading`.
- KMP metadata + android / ios / js artifacts are published together.
- iOS local framework name when assembling from source: `PlanetComponentsKuiklyShared`.
- Library version **0.2.1** (publish after `./tools/headless-check.sh` is green).
- Central: https://central.sonatype.com/artifact/io.github.techskillplanet/planet-components-kuikly
- To change library source / publish, use [build-planet-components](../build-planet-components/SKILL.md) + [publish-planet-components](../publish-planet-components/SKILL.md).
