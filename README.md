# Planet Components

<p align="center">
  <strong>TechSkillPlanet · 技趣星球</strong><br/>
  <em>用技术创造乐趣 · Create Joy with Technology</em>
</p>

<p align="center">
  Cross-platform UI component libraries sharing one <strong>Sky Planet</strong> design system:<br/>
  blue-sky brand, cloud surfaces, island-style controls, soft borders, playful motion.
</p>

<p align="center">
  <a href="README.md">English</a> ·
  <a href="README.zh-CN.md">简体中文</a> ·
  <a href="README.ja.md">日本語</a> ·
  <a href="README.ko.md">한국어</a>
  · <a href="docs/AI_FOR_EVERYONE.md">非技术 · 用 AI 做页面</a>
</p>

<p align="center">
  <img alt="version" src="https://img.shields.io/badge/version-0.2.1-31A8FF?style=flat-square" />
  <img alt="license" src="https://img.shields.io/badge/license-MIT-2BB8E6?style=flat-square" />
  <img alt="platforms" src="https://img.shields.io/badge/platforms-8_stacks-1479D6?style=flat-square" />
  <img alt="tokens" src="https://img.shields.io/badge/design-token--driven-8BD5FF?style=flat-square" />
</p>

---

## Why Planet Components?

| | |
| --- | --- |
| **One visual language** | Shared semantic tokens in `design/tokens/` — same names across Android, iOS, Web, Flutter, Mini Program, and Kuikly |
| **Eight publishable stacks** | Each stack ships a real `library` + runnable `samples` (samples always depend on the local library) |
| **Small, consistent APIs** | Common props: `variant`, `disabled`, `selected` / `checked`, `text` / `title` / `message`, runtime theme refresh |
| **Open & packaged** | MIT · public registries at **0.2.0** (npm / Maven Central / pub.dev / SPM / CocoaPods) |

---

## Supported platforms (v0.2.0)

| Stack | Package | Registry |
| --- | --- | --- |
| Android View | [`io.github.techskillplanet:planet-components-android`](https://central.sonatype.com/artifact/io.github.techskillplanet/planet-components-android) | Maven Central |
| iOS SwiftUI | [`PlanetComponents`](https://github.com/techskillplanet/planet-components) | [SPM](https://github.com/techskillplanet/planet-components) · [CocoaPods](https://cocoapods.org/pods/PlanetComponents) |
| React Web | [`@techskillplanet/planet-components-react`](https://www.npmjs.com/package/@techskillplanet/planet-components-react) | npm |
| Vue Web | [`@techskillplanet/planet-components-vue`](https://www.npmjs.com/package/@techskillplanet/planet-components-vue) | npm |
| React Native | [`@techskillplanet/planet-components-react-native`](https://www.npmjs.com/package/@techskillplanet/planet-components-react-native) | npm |
| Flutter | [`tech_skill_planet_components`](https://pub.dev/packages/tech_skill_planet_components) | pub.dev |
| WeChat Mini Program | [`@techskillplanet/planet-components-miniprogram`](https://www.npmjs.com/package/@techskillplanet/planet-components-miniprogram) | npm |
| Kuikly (KMP) | [`io.github.techskillplanet:planet-components-kuikly`](https://central.sonatype.com/artifact/io.github.techskillplanet/planet-components-kuikly) | Maven Central |

Full install snippets: **[简体中文（完整）](README.zh-CN.md#三方接入按技术栈)** · [日本語](README.ja.md) · [한국어](README.ko.md).

---

## Quick start

Pick your stack — all examples use the public **0.2.0** packages.

<details>
<summary><strong>React Web</strong></summary>

```bash
npm install @techskillplanet/planet-components-react
```

```jsx
import { TspButton, starPlanetTheme } from '@techskillplanet/planet-components-react';
import '@techskillplanet/planet-components-react/styles.css';

<TspButton text="Get Started" variant="primary" theme={starPlanetTheme} onTap={() => {}} />
```
</details>

<details>
<summary><strong>Android View</strong></summary>

```gradle
implementation "io.github.techskillplanet:planet-components-android:0.2.1"
```

```java
BasicThemeManager.init(context, "sky_planet_day", "island_raised");
```
</details>

<details>
<summary><strong>iOS SwiftUI (SPM)</strong></summary>

```swift
.package(url: "https://github.com/techskillplanet/planet-components", from: "0.2.1")
// product: PlanetComponents
```
</details>

<details>
<summary><strong>Flutter</strong></summary>

```yaml
dependencies:
  tech_skill_planet_components: ^0.2.1
```
</details>

<details>
<summary><strong>Kuikly</strong></summary>

```gradle
repositories {
    mavenCentral()
    maven { url = uri("https://mirrors.tencent.com/repository/maven-tencent/") }
}
dependencies {
    implementation("io.github.techskillplanet:planet-components-kuikly:0.2.1")
}
```
</details>

---

## Design system

- **Brand**: 技趣星球 / TechSkillPlanet  
- **Direction**: Sky Planet — sky-blue primary (`#31A8FF`), cloud surfaces, island-raised controls  
- **Tokens**: [`design/tokens/color_token.json`](design/tokens/color_token.json) · [`design/tokens/style_token.json`](design/tokens/style_token.json)  
- **Contract**: [`docs/COMPONENT_CONTRACT.md`](docs/COMPONENT_CONTRACT.md) · [`component_contract.json`](component_contract.json)

Themes (where supported): Sky / Night / Mint / Sunrise, with style profiles such as `island_raised`.

---

## Repository layout

```text
planet-components/
├── design/tokens/          # shared color + style tokens
├── android/                # View (Java) library + samples
├── ios-swiftui/            # SwiftUI library + samples
├── react-web/ · vue-web/   # Web libraries + samples
├── react-native/           # RN library + samples
├── flutter/                # Dart library + samples
├── miniprogram/            # WeChat Mini Program
├── kuikly/                 # Tencent Kuikly / KMP
├── docs/                   # structure, contract, AI plugin
└── tools/                  # structure checks, publish helpers
```

Each stack: **`library/`** (publishable) + **`samples/`** (local dependency only).

---

## Documentation

| Doc | Description |
| --- | --- |
| [README.zh-CN.md](README.zh-CN.md) | Full Chinese guide (install per stack) |
| [PUBLISHING.md](PUBLISHING.md) | Release playbooks (npm / Maven / pub / SPM / CocoaPods) |
| [docs/PLATFORM_STRUCTURE.md](docs/PLATFORM_STRUCTURE.md) | One-component-one-file rules |
| [docs/COMPONENT_CONTRACT.md](docs/COMPONENT_CONTRACT.md) | Cross-platform component contract |
| [docs/AI_FOR_EVERYONE.md](docs/AI_FOR_EVERYONE.md) | Non-engineers: build pages with AI (Chinese) |
| [docs/prompts/](docs/prompts/) | Copy-paste prompts (zh / en) |
| [docs/AI_PLUGIN.md](docs/AI_PLUGIN.md) | Agent Skills: `npx skills add techskillplanet/planet-components` |
| [.agents/README.md](.agents/README.md) | Built-in skill catalog (v0.2.0) |
| [AGENTS.md](AGENTS.md) | Repo rules for coding agents |

---

## Local development

```bash
node tools/check-structure.cjs
```

See [README.zh-CN.md](README.zh-CN.md#local-checks) for per-stack build / pack commands. Publishing scripts live under `android/scripts/`, `kuikly/scripts/`, and `tools/publish-npm-web.sh`.

---

## License

[MIT](LICENSE) © TechSkillPlanet
