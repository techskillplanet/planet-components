# RN 升级与契约补齐实施计划

**Spec 引用：** `docs/prds/rn-upgrade-parity/specs/00-spec-index.md`
**目标：** Expo 57/RN 0.86 + RefreshLayout/LoadingDialog + 模拟器跑通
**验收：** AC-01..AC-04
**执行环境：** 本仓库工作树；Android 模拟器 emulator-5554

## 全局约束

- 仅改 `react-native/`（及本 Spec/Plan 文档）
- Sample 依赖 `file:../library`
- 契约 API：RefreshLayout / LoadingDialog

## 任务

### 1. 升级 Sample 依赖
- 在 `react-native/samples` 执行 `npx expo install expo@^57.0.0 --fix`
- 对齐 react / react-native / safe-area 等
- 更新 library peer：`react-native >= 0.86`、`react >= 19`
- 验证：`node -e` 打印 installed versions → AC-01

### 2. 刷新原生工程
- `npx expo prebuild --clean`（保留 package id）
- 验证：android 工程可 gradle 配置

### 3. 新增组件
- `TspRefreshLayout.js`、`TspLoadingDialog.js`
- 更新 barrel / Sample docs / detail demo
- 验证：结构检查 → AC-02/AC-03

### 4. 模拟器运行
- `npm run android` 对 emulator-5554
- 验证：安装成功 → AC-04
