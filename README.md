# Planet Components

技趣星球（TechSkillPlanet）跨端基础组件库。口号：**用技术创造乐趣**。

每个技术栈都是一级目录，各自包含：

- `library`：可独立发布的组件库
- `samples`：依赖本地 `library` 的可运行示例（本仓库 samples **不要**改成已发布坐标）

共享设计 Token：`design/tokens/`。

## 发布状态（公开渠道）

仓库内多数库的目标版本号是 **0.2.0**。下表以 **公开 registry 实查** 为准（2026-09-01）：

| 技术栈 | 包坐标 / 渠道 | 公开已发布 | 状态 |
| --- | --- | --- | --- |
| Android View | Maven Central `io.github.techskillplanet:planet-components-android` | **0.2.0** | 已发布 |
| iOS SwiftUI | SPM（GitHub tag） | **0.2.0** | 已发布 |
| iOS SwiftUI | CocoaPods `PlanetComponents` | **0.2.0** | 已发布 |
| React Web | npm `@techskillplanet/planet-components-react` | **0.2.0** | 已发布 |
| Vue Web | npm `@techskillplanet/planet-components-vue` | **0.2.0** | 已发布 |
| React Native | npm `@techskillplanet/planet-components-react-native` | **0.2.0** | 已发布 |
| Flutter | pub.dev `tech_skill_planet_components` | **0.2.0** | 已发布 |
| 微信小程序 | npm `@techskillplanet/planet-components-miniprogram` | **0.2.0** | 已发布 |
| Kuikly | Maven Central `io.github.techskillplanet:planet-components-kuikly` | **0.2.0**（待 Portal Publish） | Staging 已上传，需在 Central Deployments 点 Publish |

### 当前没法 / 尚未对外发布的

1. **Kuikly**  
   - 流水线已跑通：Gradle 多 publication 已上传 Staging，并进入 Central Portal Deployments。  
   - 请到 https://central.sonatype.com/publishing/deployments 对 deployment `b953dd16-74fd-44df-b2b8-19b7a743b9e9` 点 **Publish**；同步到 Maven Central 后即可按坐标消费。  
   - 临时接入：源码 include 本仓 `kuikly/library/shared`。

发版流程总览：[`PUBLISHING.md`](PUBLISHING.md)。iOS 双渠道步骤：[`ios-swiftui/library/PUBLISHING.md`](ios-swiftui/library/PUBLISHING.md)。

---

## 三方接入（按技术栈）

以下面向 **业务工程消费已发布包**。本仓库 `samples/` 请继续用本地 `library`。

### 1. Android View（Maven Central）

```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation "io.github.techskillplanet:planet-components-android:0.2.0"
}
```

```java
BasicThemeManager.init(context, "sky_planet_day", "island_raised");
```

- Central：https://central.sonatype.com/artifact/io.github.techskillplanet/planet-components-android  
- 说明：[`android/library/README.md`](android/library/README.md)

### 2. iOS SwiftUI · SPM

Xcode → Package Dependencies，或 `Package.swift`：

```swift
.package(url: "https://github.com/techskillplanet/planet-components", from: "0.2.0")
```

把 product **`PlanetComponents`** 加到 target：

```swift
import PlanetComponents

TspButton("Get Started", variant: .primary) { }
```

说明：[`ios-swiftui/library/README.md`](ios-swiftui/library/README.md)

### 3. iOS SwiftUI · CocoaPods

```ruby
pod 'PlanetComponents', '~> 0.2.0'
```

```bash
pod install
```

```swift
import PlanetComponents
```

- 包页：https://cocoapods.org/pods/PlanetComponents

### 4. React Web（npm）

当前 npm latest：**0.2.0**。

```bash
npm install @techskillplanet/planet-components-react
# 或固定：npm install @techskillplanet/planet-components-react@0.2.0
```

```jsx
import { TspButton, starPlanetTheme } from '@techskillplanet/planet-components-react';
import '@techskillplanet/planet-components-react/styles.css';

<TspButton text="Get Started" variant="primary" theme={starPlanetTheme} onTap={() => {}} />
```

- 说明：[`react-web/library/README.md`](react-web/library/README.md)  
- Agent skill：[`.agents/skills/integrate-react-components/SKILL.md`](.agents/skills/integrate-react-components/SKILL.md)

### 5. Vue Web（npm）

当前 npm latest：**0.2.0**。

```bash
npm install @techskillplanet/planet-components-vue
```

```js
import { TspButton } from '@techskillplanet/planet-components-vue';
import '@techskillplanet/planet-components-vue/styles.css';
```

在 Vue 3 应用中注册/使用组件（peer：`vue >= 3`）。详见 [`vue-web/library/README.md`](vue-web/library/README.md)。

### 6. React Native（npm）

当前 npm latest：**0.2.0**。

```bash
npm install @techskillplanet/planet-components-react-native
```

```js
import { TspButton, resolveTheme } from '@techskillplanet/planet-components-react-native';

const theme = resolveTheme('sky', 'island_raised');
<TspButton text="Get Started" variant="primary" theme={theme} onTap={() => {}} />
```

Peer：`react` / `react-native`（见包内 `package.json`）。说明：[`react-native/library/README.md`](react-native/library/README.md)。

### 7. Flutter（pub.dev）

当前 pub.dev latest：**0.2.0**。

```yaml
dependencies:
  tech_skill_planet_components: ^0.2.0
```

```bash
flutter pub get
```

```dart
import 'package:tech_skill_planet_components/tech_skill_planet_components.dart';

TspButton(
  text: 'Get Started',
  variant: TspButtonVariant.primary,
  theme: StarPlanetTheme.sky,
  onTap: () {},
);
```

- pub：https://pub.dev/packages/tech_skill_planet_components  
- 说明：[`flutter/library/README.md`](flutter/library/README.md)

### 8. 微信小程序（npm）

当前 npm latest：**0.2.0**。

```bash
npm install @techskillplanet/planet-components-miniprogram
```

小程序侧使用 `bc-*` 组件（语义对齐契约 `Tsp*`）。说明：[`miniprogram/library/README.md`](miniprogram/library/README.md)。

### 9. Kuikly（Maven Central 流水线已就绪）

坐标：`io.github.techskillplanet:planet-components-kuikly:0.2.0`（上架后可用）。

```gradle
repositories {
    mavenCentral()
    maven { url = uri("https://mirrors.tencent.com/repository/maven-tencent/") }
}

dependencies {
    implementation("io.github.techskillplanet:planet-components-kuikly:0.2.0")
}
```

维护者发布（对齐 Android）：

```bash
kuikly/scripts/publish-maven-central.sh --dry-run
kuikly/scripts/publish-maven-central.sh
# → https://central.sonatype.com/publishing/deployments 点 Publish
```

上架前业务侧仍可：

1. 将本仓库 `kuikly/library/shared` 以 Gradle 源码依赖 / include 进 Kuikly 工程；或  
2. 直接使用 `phonics/controls/Tsp*.kt` 公开控件。

```text
Gradle: io.github.techskillplanet:planet-components-kuikly:0.2.0
iOS framework 名: PlanetComponentsKuiklyShared
```

说明：[`kuikly/library/README.md`](kuikly/library/README.md)。本仓可跑 samples：

```bash
cd kuikly
./gradlew :androidApp:assembleDebug
```

---

## Platform Layout

| Platform | Library | Runnable samples | Package target |
| --- | --- | --- | --- |
| Android View | `android/library` | `android/samples` | Maven Central |
| React Native | `react-native/library` | `react-native/samples` | npm |
| React Web | `react-web/library` | `react-web/samples` | npm |
| Vue Web | `vue-web/library` | `vue-web/samples` | npm |
| Flutter | `flutter/library` | `flutter/samples` | pub.dev |
| iOS SwiftUI | `ios-swiftui/library` | `ios-swiftui/samples` | SPM / CocoaPods |
| WeChat Mini Program | `miniprogram/library` | `miniprogram/samples` | npm / miniprogram package |
| Kuikly | `kuikly/library` | `kuikly/samples` | Maven Central |

## License

[MIT License](LICENSE)

## Reusable Agent Context

- `AGENTS.md`：仓库级规则  
- `.agents/skills/build-planet-components/SKILL.md`：扩展本仓组件库  
- `.agents/skills/build-android-view-ui/SKILL.md`：Android View  
- `.agents/skills/integrate-react-components/SKILL.md`：React Web 接入  
- `docs/AI_PLUGIN.md`：安装 Cursor / Claude / Codex 插件  
- `docs/PLATFORM_STRUCTURE.md`：一组件一文件 / 一页一文件等结构规则  
- `docs/COMPONENT_CONTRACT.md` + `component_contract.json`：跨端契约  

## Rules

- 一组件一源文件（已拆分的平台）  
- 一示例页一文件  
- samples 必须依赖本地 library，不复制实现  
- barrel/index 只导出公开 API  
- 构建产物与 IDE/cache 不进开源包表面  

## Local Checks

```bash
node tools/check-structure.cjs
```

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
