# TechSkillPlanet Basic Controls · 微信小程序版

Package name: `@techskillplanet/basic-controls-miniprogram`.

本目录是可独立分发的小程序组件库；业务示例在外层 `pages/basic-samples`，不要把音标业务页面放进组件库目录。

与 Android `basiccontrols` 模块对应的 Sky Planet 组件库（精简 MVP）。

## 组件

- `bc-button` — 岛屿风格按钮（default / primary / danger）
- `bc-card` — 卡片容器
- `bc-top-bar` — 顶栏（标题 + 返回）
- `bc-bottom-tab` — 底部 Tab
- `bc-progress` — 进度条
- `bc-alert` — 提示条
- `bc-chip` — 标签

## 主题 / 国际化

- `theme/theme.js` — `sky` / `night` 两套 token
- `i18n/i18n.js` — 读取 `data/local.json`

页面通过 `getApp().globalData.theme` 传入各组件。
