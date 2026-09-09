# Planet Components

<p align="center">
  <strong>技趣星球 · TechSkillPlanet</strong><br/>
  <em>用技术创造乐趣</em>
</p>

<p align="center">
  跨端基础组件库，共享同一套 <strong>Sky Planet</strong> 设计语言：<br/>
  蓝天主色、云感表面、岛屿式控件、柔和描边、克制动效。
</p>

<p align="center">
  <a href="README.md">English</a> ·
  <a href="README.zh-CN.md">简体中文</a> ·
  <a href="README.ja.md">日本語</a> ·
  <a href="README.ko.md">한국어</a>
  · <a href="docs/AI_FOR_EVERYONE.md">非技术同学 · 用 AI 做页面</a>
</p>

<p align="center">
  <img alt="version" src="https://img.shields.io/badge/version-0.2.1-31A8FF?style=flat-square" />
  <img alt="license" src="https://img.shields.io/badge/license-MIT-2BB8E6?style=flat-square" />
  <img alt="platforms" src="https://img.shields.io/badge/platforms-8_stacks-1479D6?style=flat-square" />
  <img alt="tokens" src="https://img.shields.io/badge/design-token--driven-8BD5FF?style=flat-square" />
</p>

---

## 为什么选 Planet Components？

| | |
| --- | --- |
| **一套视觉语言** | `design/tokens/` 语义 Token，Android / iOS / Web / Flutter / 小程序 / Kuikly 同名同义 |
| **八栈可独立发版** | 每栈 `library` + 可运行 `samples`（samples 只依赖本地 library） |
| **小而一致的 API** | 常用：`variant`、`disabled`、`selected` / `checked`、`text` / `title` / `message`、运行时换肤 |
| **已公开上架** | MIT · 全栈 **0.2.0**（npm / Maven Central / pub.dev / SPM / CocoaPods） |

---

## 发布状态（公开渠道）

当前公开版本统一为 **0.2.0**（2026-09-08 实查）：

| 技术栈 | 包坐标 / 渠道 | 状态 |
| --- | --- | --- |
| Android View | Maven Central `io.github.techskillplanet:planet-components-android` | 已发布 |
| iOS SwiftUI | SPM（GitHub tag）+ CocoaPods `PlanetComponents` | 已发布 |
| React Web | npm `@techskillplanet/planet-components-react` | 已发布 |
| Vue Web | npm `@techskillplanet/planet-components-vue` | 已发布 |
| React Native | npm `@techskillplanet/planet-components-react-native` | 已发布 |
| Flutter | pub.dev `tech_skill_planet_components` | 已发布 |
| 微信小程序 | npm `@techskillplanet/planet-components-miniprogram` | 已发布 |
| Kuikly | Maven Central `io.github.techskillplanet:planet-components-kuikly` | 已发布 |

发版流程：[`PUBLISHING.md`](PUBLISHING.md)。iOS 双渠道：[`ios-swiftui/library/PUBLISHING.md`](ios-swiftui/library/PUBLISHING.md)。

---

## 三方接入（按技术栈）

面向 **业务工程消费已发布包**。本仓库 `samples/` 请继续用本地 `library`。

### 1. Android View（Maven Central）

```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation "io.github.techskillplanet:planet-components-android:0.2.1"
}
```

```java
BasicThemeManager.init(context, "sky_planet_day", "island_raised");
```

- Central：https://central.sonatype.com/artifact/io.github.techskillplanet/planet-components-android  
- 说明：[`android/library/README.md`](android/library/README.md)

### 2. iOS SwiftUI · SPM

```swift
.package(url: "https://github.com/techskillplanet/planet-components", from: "0.2.1")
```

Product：**`PlanetComponents`**

```swift
import PlanetComponents

TspButton("Get Started", variant: .primary) { }
```

说明：[`ios-swiftui/library/README.md`](ios-swiftui/library/README.md)

### 3. iOS SwiftUI · CocoaPods

```ruby
pod 'PlanetComponents', '~> 0.2.1'
```

- 包页：https://cocoapods.org/pods/PlanetComponents

### 4. React Web（npm）

```bash
npm install @techskillplanet/planet-components-react
```

```jsx
import { TspButton, starPlanetTheme } from '@techskillplanet/planet-components-react';
import '@techskillplanet/planet-components-react/styles.css';

<TspButton text="Get Started" variant="primary" theme={starPlanetTheme} onTap={() => {}} />
```

- 说明：[`react-web/library/README.md`](react-web/library/README.md)  
- Agent skill：[`.agents/skills/integrate-react-components/SKILL.md`](.agents/skills/integrate-react-components/SKILL.md)

### 5. Vue Web（npm）

```bash
npm install @techskillplanet/planet-components-vue
```

```js
import { TspButton } from '@techskillplanet/planet-components-vue';
import '@techskillplanet/planet-components-vue/styles.css';
```

Peer：`vue >= 3`。说明：[`vue-web/library/README.md`](vue-web/library/README.md)

### 6. React Native（npm）

```bash
npm install @techskillplanet/planet-components-react-native
```

```js
import { TspButton, resolveTheme } from '@techskillplanet/planet-components-react-native';

const theme = resolveTheme('sky', 'island_raised');
<TspButton text="Get Started" variant="primary" theme={theme} onTap={() => {}} />
```

说明：[`react-native/library/README.md`](react-native/library/README.md)

### 7. Flutter（pub.dev）

```yaml
dependencies:
  tech_skill_planet_components: ^0.2.1
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

```bash
npm install @techskillplanet/planet-components-miniprogram
```

小程序侧使用 `bc-*` 组件（语义对齐契约 `Tsp*`）。说明：[`miniprogram/library/README.md`](miniprogram/library/README.md)

### 9. Kuikly（Maven Central）

```gradle
repositories {
    mavenCentral()
    maven { url = uri("https://mirrors.tencent.com/repository/maven-tencent/") }
}

dependencies {
    implementation("io.github.techskillplanet:planet-components-kuikly:0.2.1")
}
```

- Central：https://central.sonatype.com/artifact/io.github.techskillplanet/planet-components-kuikly  
- 说明：[`kuikly/library/README.md`](kuikly/library/README.md)

---

## 设计系统

- **品牌**：技趣星球 / TechSkillPlanet  
- **方向**：Sky Planet — 主色 `#31A8FF`、云感表面、岛屿抬升控件  
- **Token**：[`design/tokens/color_token.json`](design/tokens/color_token.json) · [`design/tokens/style_token.json`](design/tokens/style_token.json)  
- **契约**：[`docs/COMPONENT_CONTRACT.md`](docs/COMPONENT_CONTRACT.md) · [`component_contract.json`](component_contract.json)

支持主题（视平台）：Sky / Night / Mint / Sunrise；风格如 `island_raised`。

---

## 仓库结构

| 平台 | Library | Samples | 发包渠道 |
| --- | --- | --- | --- |
| Android View | `android/library` | `android/samples` | Maven Central |
| React Native | `react-native/library` | `react-native/samples` | npm |
| React Web | `react-web/library` | `react-web/samples` | npm |
| Vue Web | `vue-web/library` | `vue-web/samples` | npm |
| Flutter | `flutter/library` | `flutter/samples` | pub.dev |
| iOS SwiftUI | `ios-swiftui/library` | `ios-swiftui/samples` | SPM / CocoaPods |
| 微信小程序 | `miniprogram/library` | `miniprogram/samples` | npm |
| Kuikly | `kuikly/library` | `kuikly/samples` | Maven Central |

---

## 文档与 Agent

- [`AGENTS.md`](AGENTS.md)：仓库级规则  
- [`.agents/skills/build-planet-components/SKILL.md`](.agents/skills/build-planet-components/SKILL.md)：扩展组件库  
- [`.agents/skills/integrate-react-components/SKILL.md`](.agents/skills/integrate-react-components/SKILL.md)：React Web 接入  
- [`docs/AI_FOR_EVERYONE.md`](docs/AI_FOR_EVERYONE.md)：**非技术同学**用人话 + AI 做技趣星球网页  
- [`docs/prompts/zh-quickstart.md`](docs/prompts/zh-quickstart.md)：复制即用的提示词  
- [`docs/AI_PLUGIN.md`](docs/AI_PLUGIN.md)：Agent Skills 公共安装与发布  
- [`.agents/README.md`](.agents/README.md)：内置 skill 目录（含 `ai-build-with-planet`）  
- 安装：`npx skills add techskillplanet/planet-components`（本仓维护也可用 `./tools/install-ai-plugin.sh`）
- [`docs/PLATFORM_STRUCTURE.md`](docs/PLATFORM_STRUCTURE.md)：一组件一文件等结构规则  

---

## 约定

- 一组件一源文件  
- 一示例页一文件  
- samples 必须依赖本地 library，不复制实现  
- barrel / index 只导出公开 API  
- 构建产物与 IDE cache 不进开源包表面  

## Local checks

```bash
node tools/check-structure.cjs
./tools/headless-check.sh   # 全栈无头：单测 / pack:dry / analyze / assemble
```

```bash
cd android && ./gradlew :library:assembleRelease :samples:assembleDebug
cd react-web/library && npm run check && npm run pack:dry
cd vue-web/library && npm run check && npm run pack:dry
cd react-native/library && npm run pack:dry
cd flutter/library && flutter analyze
cd ios-swiftui/library && swift build
pod lib lint PlanetComponents.podspec --allow-warnings
cd kuikly && ./gradlew :shared:compileDebugKotlinAndroid :androidApp:assembleDebug
```

---

## License

[MIT License](LICENSE) © TechSkillPlanet
