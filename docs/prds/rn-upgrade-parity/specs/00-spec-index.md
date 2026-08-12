# Spec Index：RN 升级与功能补全

Status: confirmed

## 确认记录

- 2026-08-12：用户回复「执行」，确认推荐方案：
  1. 运行时：Expo SDK 57 + React Native 0.86
  2. 补齐范围：契约缺口 `TspRefreshLayout` + `TspLoadingDialog`
  3. 验收：Android 模拟器跑通 Sample；不做 iOS；不移植 Android 超集控件

## 范围

- 栈：`react-native/`（library + samples）

## 文档地图

| 文档 | 状态 | 依赖 |
| --- | --- | --- |
| `00-spec-index.md` | confirmed | — |
| `01-background-and-goal.md` | confirmed | 本索引 |
| `02-acceptance-criteria.md` | confirmed | 01 |
| `03-as-is.md` | confirmed | 02 |
| `04-to-be.md` | confirmed | 03 |
| `05-bugfix-acceptance.md` | confirmed | 用户确认修交互缺陷；2026-08-12 增补 Badge/Modal（AC-B05/B06） |
| `06-bugfix-test-cases.md` | confirmed | 05 |
