# Planet Components

Cross-platform basic component libraries for TechSkillPlanet.

Every technology stack is kept at the same level. Each stack contains:

- `library`: independent publishable component library.
- `samples`: runnable sample project that depends on the local `library`.

## Platform Layout

| Platform | Library | Runnable samples | Package target |
| --- | --- | --- | --- |
| Android View | `android/library` | `android/samples` | Maven Central |
| React Native | `react-native/library` | `react-native/samples` | npm |
| React Web | `react-web/library` | `react-web/samples` | npm |
| Vue Web | `vue-web/library` | `vue-web/samples` | npm |
| Flutter | `flutter/library` | `flutter/samples` | pub.dev |
| iOS SwiftUI | `ios-swiftui/library` | `ios-swiftui/samples` | Swift Package Manager / CocoaPods |
| WeChat Mini Program | `miniprogram/library` | `miniprogram/samples` | npm / miniprogram package |
| Kuikly | `kuikly/library` | `kuikly/samples` | Maven / internal Kuikly package |

Shared design tokens are under `design/tokens`.

## Android（Maven）

当前库版本 **0.2.0**（坐标已从 `basic-controls-android` 更名）：

```gradle
implementation "io.github.techskillplanet:planet-components-android:0.2.0"
```

- Maven Central：https://central.sonatype.com/artifact/io.github.techskillplanet/planet-components-android
- 源码：https://github.com/techskillplanet/planet-components/tree/main/android/library
- 说明：[`android/library/README.md`](android/library/README.md)

历史坐标 `io.github.techskillplanet:basic-controls-android:0.1.0` 已停用；`0.2.0` 需按 [`PUBLISHING.md`](PUBLISHING.md) / [`android/library/README.md`](android/library/README.md) 发布到 Central。

## iOS（SPM / CocoaPods）

包名 `PlanetComponents`，目标版本 **0.2.0**。**验证通过前不打 tag、不推 Trunk。**

步骤文档：[`ios-swiftui/library/PUBLISHING.md`](ios-swiftui/library/PUBLISHING.md)

## License

[MIT License](LICENSE)

## Reusable Agent Context

The accumulated implementation rules have been migrated into this repository:

- `AGENTS.md`: high-level repository context and engineering rules.
- `.agents/skills/use-planet-components/SKILL.md`: **consume** the published library in an app (install, theme, contract APIs).
- `.agents/skills/build-planet-components/SKILL.md`: cross-platform component library workflow.
- `.agents/skills/build-android-view-ui/SKILL.md`: Android View Java/XML workflow.
- `docs/AI_PLUGIN.md`: install the Cursor / Claude / Codex plugin and skills.
- `docs/PLATFORM_STRUCTURE.md`: one-component-one-file, one-page-one-file, routing, and sample rules.
- `docs/COMPONENT_CONTRACT.md`: human-readable component API and variant contract.
- `component_contract.json`: machine-readable component API and variant contract.

## Rules

- One component per source file where the platform implementation has been split.
- One sample page per file.
- Samples must depend on the local library rather than owning the library implementation.
- Barrel/index files should only export public APIs.
- Build outputs and IDE/cache files are not part of the open-source package surface.

## Local Checks

```bash
node tools/check-structure.cjs
```

Platform-specific checks:

```bash
cd android && ./gradlew :library:assembleRelease :samples:assembleDebug
cd react-native/library && npm run pack:dry
cd react-native/samples && npm install && npm run check
cd react-web/library && npm run check && npm run pack:dry
cd vue-web/library && npm run check && npm run pack:dry
cd flutter/library && flutter analyze
cd flutter/samples && flutter analyze
cd ios-swiftui/library && swift build
cd ios-swiftui/samples && swift build
swift build
pod lib lint PlanetComponents.podspec --allow-warnings
cd miniprogram && node -e "const fs=require('fs'); JSON.parse(fs.readFileSync('samples/app.json','utf8')); if(!fs.lstatSync('samples/planet-components').isSymbolicLink()) throw new Error('samples/planet-components should be a symlink')"
cd kuikly && ./gradlew :shared:compileKotlinJs :miniApp:compileKotlinJs :shared:compileDebugKotlinAndroid :androidApp:assembleDebug
```
