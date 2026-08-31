# PlanetComponents (SwiftUI)

TechSkillPlanet Planet Components 的 SwiftUI 实现。同一套源码同时支持 **Swift Package Manager** 和 **CocoaPods**。

Product、module、CocoaPods pod 名均为 `PlanetComponents`。公开类型使用 `Tsp*` 前缀，对齐 `docs/COMPONENT_CONTRACT.md`。本仓库 samples 通过 `path: ../library` 依赖本地包。

Version: **0.2.0**（SPM：git tag `0.2.0`；CocoaPods Trunk 可随后单独推送）。

发布步骤见 [`PUBLISHING.md`](PUBLISHING.md)。总览见仓库根目录 [`PUBLISHING.md`](../../PUBLISHING.md)。

## Install

### Swift Package Manager

```swift
.package(url: "https://github.com/techskillplanet/planet-components", from: "0.2.0")
```

然后把 product `PlanetComponents` 加到 target。

### CocoaPods

```ruby
pod 'PlanetComponents', '~> 0.2.0'
```

## Usage

```swift
import PlanetComponents

TspButton("Get Started", variant: .primary) {
    // tap
}
```

## Layout

- One component per file under `Sources/PlanetComponents/`
- 本地 `Package.swift` 只声明产品；仓库根目录 `Package.swift` / `PlanetComponents.podspec` 是对外发布入口
- Theme: `StarPlanetTheme` (`sky` / `night` / `mint`)
