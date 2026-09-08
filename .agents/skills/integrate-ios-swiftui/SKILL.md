---
name: integrate-ios-swiftui
description: >-
  Integrate PlanetComponents into an iOS SwiftUI app via SPM or CocoaPods.
  Use when adding Tsp* SwiftUI controls from the TechSkillPlanet package.
---

# Integrate iOS SwiftUI

## SPM

```swift
.package(url: "https://github.com/techskillplanet/planet-components", from: "0.2.0")
```

Add product **`PlanetComponents`** to the app target.

```swift
import PlanetComponents

TspButton("Get Started", variant: .primary) { }
```

## CocoaPods

```ruby
pod 'PlanetComponents', '~> 0.2.0'
```

```bash
pod install
```

## Notes

- Same git tag drives SPM + CocoaPods.
- Pod page: https://cocoapods.org/pods/PlanetComponents
- Monorepo sources: `ios-swiftui/library` (root `Package.swift` / `PlanetComponents.podspec`).
