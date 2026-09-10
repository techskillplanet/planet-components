---
name: integrate-ios-swiftui
description: >-
  Integrate PlanetComponents into an iOS SwiftUI app via SPM or CocoaPods.
  Use when adding Tsp* SwiftUI controls from the TechSkillPlanet package.
---

# Integrate iOS SwiftUI

## SPM

```swift
.package(url: "https://github.com/techskillplanet/planet-components", from: "0.2.1")
```

Add product **`PlanetComponents`** to the app target.

```swift
import PlanetComponents

TspButton("Get Started", variant: .primary) { }
```

## CocoaPods

```ruby
pod 'PlanetComponents', '~> 0.2.1'
```

```bash
pod install
```

## Coverage

≈ **57** `Tsp*` SwiftUI controls. Includes **DatePicker** (`TspDatePicker`) and **Domain-7**: `TspChildSwitcher`, `TspScoreRuleGrid`, `TspRedeemCardGrid`, `TspCalendarHeatmap`, `TspPrintSheet`, `TspBalanceHero`, `TspCheckInStreakCard`. W1: Avatar, Skeleton, Tooltip, Slider, TextArea, Drawer, InputNumber, Swiper. W2: Tag, Fab, TimePicker, Upload, Table, Tree, Cascader.

## Notes

- Themes: `StarPlanetTheme.sky|night|mint|sunrise`.
- `TspButton(..., loading: true)` for loading state.
- Same git tag drives SPM + CocoaPods. Library version **0.2.1** (publish after `./tools/headless-check.sh` is green).
- Pod page: https://cocoapods.org/pods/PlanetComponents
- Monorepo sources: `ios-swiftui/library` (root `Package.swift` / `PlanetComponents.podspec`).
