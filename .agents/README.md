# Planet Components · Agent Skills

内置 AI Agent Skills（与 Cursor / Claude / Codex 日常 skill 同格式）。清单版本见 [`plugin-manifest.json`](plugin-manifest.json)。

## 公共安装

```bash
npx skills add techskillplanet/planet-components
npx skills add techskillplanet/planet-components -l   # 仅列出
```

## Skill 目录

| Skill | 用途 |
| --- | --- |
| `ai-build-with-planet` | **非技术友好**：用人话让 AI 用 React Web 搭技趣星球页面 |
| `use-planet-components` | 业务侧消费已发布包（路由到各 integrate-*） |
| `build-planet-components` | 在本仓扩展 / 对齐跨端组件库 |
| `build-android-view-ui` | 仅 Android View（Java）实现 |
| `publish-planet-components` | 各 registry 发版流程与鉴权 |
| `integrate-react-components` | React Web 接入 |
| `integrate-vue-components` | Vue Web 接入 |
| `integrate-react-native-components` | React Native 接入 |
| `integrate-flutter-components` | Flutter 接入 |
| `integrate-android-view` | Android Maven 消费 |
| `integrate-ios-swiftui` | iOS SPM / CocoaPods |
| `integrate-miniprogram` | 微信小程序 npm |
| `integrate-kuikly` | Kuikly Maven 消费 |

零基础文档：[`docs/AI_FOR_EVERYONE.md`](../docs/AI_FOR_EVERYONE.md) · 提示词：[`docs/prompts/`](../docs/prompts/)

## 本仓维护者：符号链接

```bash
./tools/install-ai-plugin.sh
./tools/install-ai-plugin.sh --target cursor
```

完整说明：[`docs/AI_PLUGIN.md`](../docs/AI_PLUGIN.md)。
