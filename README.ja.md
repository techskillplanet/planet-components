# Planet Components

<p align="center">
  <strong>TechSkillPlanet · 技趣星球</strong><br/>
  <em>技術で楽しさを創る · Create Joy with Technology</em>
</p>

<p align="center">
  共有デザインシステム <strong>Sky Planet</strong> に基づくクロスプラットフォーム UI コンポーネントライブラリです。<br/>
  青空のプライマリカラー、雲のようなサーフェス、アイランド型コントロール。
</p>

<p align="center">
  <a href="README.md">English</a> ·
  <a href="README.zh-CN.md">简体中文</a> ·
  <a href="README.ja.md">日本語</a> ·
  <a href="README.ko.md">한국어</a>
</p>

<p align="center">
  <img alt="version" src="https://img.shields.io/badge/version-0.2.1-31A8FF?style=flat-square" />
  <img alt="license" src="https://img.shields.io/badge/license-MIT-2BB8E6?style=flat-square" />
  <img alt="platforms" src="https://img.shields.io/badge/platforms-8_stacks-1479D6?style=flat-square" />
</p>

---

## 特徴

- **統一ビジュアル**：`design/tokens/` のセマンティックトークンを全スタックで共有
- **8 プラットフォーム**：各スタックに公開可能な `library` と実行可能な `samples`
- **一貫 API**：`variant` / `disabled` / `selected` / `text` など
- **公開済み v0.2.0**：npm · Maven Central · pub.dev · SPM · CocoaPods

---

## 対応プラットフォーム

| スタック | パッケージ | レジストリ |
| --- | --- | --- |
| Android View | `io.github.techskillplanet:planet-components-android` | Maven Central |
| iOS SwiftUI | `PlanetComponents` | SPM / CocoaPods |
| React Web | `@techskillplanet/planet-components-react` | npm |
| Vue Web | `@techskillplanet/planet-components-vue` | npm |
| React Native | `@techskillplanet/planet-components-react-native` | npm |
| Flutter | `tech_skill_planet_components` | pub.dev |
| 微信ミニプログラム | `@techskillplanet/planet-components-miniprogram` | npm |
| Kuikly | `io.github.techskillplanet:planet-components-kuikly` | Maven Central |

インストール手順の詳細は [English](README.md) / [简体中文](README.zh-CN.md) を参照してください。

### クイックスタート例

```bash
npm install @techskillplanet/planet-components-react
```

```gradle
implementation "io.github.techskillplanet:planet-components-android:0.2.1"
```

```yaml
# pubspec.yaml
dependencies:
  tech_skill_planet_components: ^0.2.1
```

---

## ドキュメント

| ドキュメント | 内容 |
| --- | --- |
| [README.md](README.md) | 英語トップページ |
| [README.zh-CN.md](README.zh-CN.md) | 中国語・完全インストールガイド |
| [PUBLISHING.md](PUBLISHING.md) | 公開手順 |
| [docs/COMPONENT_CONTRACT.md](docs/COMPONENT_CONTRACT.md) | コンポーネント契約 |

---

## ライセンス

[MIT](LICENSE) © TechSkillPlanet
