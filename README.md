# Planet Components

Cross-platform basic component libraries for TechSkillPlanet.

Every technology stack is kept at the same level. Each stack contains:

- `library`: independent publishable component library.
- `samples`: runnable sample project that depends on the local `library`.

## Platform Layout

| Platform | Library | Runnable samples | Future package target |
| --- | --- | --- | --- |
| Android View | `android-view/library` | `android-view/samples` | Maven / Gradle |
| React Native | `react-native/library` | `react-native/samples` | npm |
| React Web | `react-web/library` | `react-web/samples` | npm |
| Vue Web | `vue-web/library` | `vue-web/samples` | npm |
| Flutter | `flutter/library` | `flutter/samples` | pub.dev |
| iOS SwiftUI | `ios-swiftui/library` | `ios-swiftui/samples` | Swift Package Manager |
| WeChat Mini Program | `miniprogram/library` | `miniprogram/samples` | npm / miniprogram package |
| Kuikly | `kuikly/library` | `kuikly/samples` | Maven / internal Kuikly package |

Shared design tokens are under `design/tokens`.

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
cd android-view && ./gradlew :library:assembleRelease :samples:assembleDebug
cd react-native/library && npm run pack:dry
cd react-native/samples && npm install && npm run check
cd react-web/library && npm run check && npm run pack:dry
cd vue-web/library && npm run check && npm run pack:dry
cd flutter/library && flutter analyze
cd flutter/samples && flutter analyze
cd ios-swiftui/library && swift build
cd ios-swiftui/samples && swift build
cd miniprogram && node -e "const fs=require('fs'); JSON.parse(fs.readFileSync('samples/app.json','utf8')); if(!fs.lstatSync('samples/basic-controls').isSymbolicLink()) throw new Error('samples/basic-controls should be a symlink')"
cd kuikly && ./gradlew :shared:compileKotlinJs :miniApp:compileKotlinJs :shared:compileDebugKotlinAndroid
```
