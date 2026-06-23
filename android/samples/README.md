# Basic Controls Android View Sample

可运行的 Android View 示例工程，依赖本地 `../library`，用于预览 TechSkillPlanet 基础组件。

## 导航架构（Shell + Router + Page）

与 React Native Sample 的 `AppRouter.js` + `pages/` 对齐，详见 `docs/PLATFORM_STRUCTURE.md`。

```text
MainActivity.java                 ← Shell：TopBar + RefreshLayout + BottomTab
├── navigation/SampleRouter.java  ← 路由（home / settings Tab + detail 栈）
├── navigation/SampleRoute.java
├── SamplePageHost.java
├── HomeSamplePage.java           ← 学习 Tab：组件列表
├── SettingsSamplePage.java       ← 设置 Tab：主题 / 语言
└── ComponentDetailSamplePage.java← 组件详情（隐藏 BottomTab）
```

换页流程：

1. Page 调用 `host.navigateXxx()` 或 TopBar 返回。
2. `SampleRouter` 更新 `SampleRoute`。
3. `MainActivity.renderCurrentPage()` 清空 `content` 并 dispatch 到对应 Page。
4. Shell（TopBar、下拉刷新）不重建，只更新标题与返回键。

Sample 文案在 `src/main/assets/i18n/sample_strings.json`，通过 `BasicI18nManager` 读取，不在 Java 中硬编码。

## 运行

```bash
cd android
./gradlew :samples:assembleDebug
./gradlew :samples:installDebug
```

或在 Android Studio 中打开 `android` 目录，运行 `samples` 模块。

## 结构检查

```bash
# 仓库根目录
node tools/check-structure.cjs

# Android 构建
cd android && ./gradlew :library:assembleRelease :samples:assembleDebug
```
