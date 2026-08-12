# To-Be

Status: confirmed  
Prerequisites: `03-as-is.md`

## 目标行为

1. Sample 运行在 Expo 57 / RN 0.86；必要时 `prebuild` 刷新 `android/`、`ios/`
2. Library 新增：
   - `TspRefreshLayout`：`refreshing`、`loadingMore`、`disabled`、`onRefresh`、`onLoadMore` + children
   - `TspLoadingDialog`：`visible`、`message`、`dismissible`（compact 变体可选）
3. Sample `componentDocs` / 详情页可演示上述两组件
4. 模拟器可安装并打开 Sample App

## 推荐方案

Expo 托管升级（非裸 RN 0.87），保证 `expo run:android` 可验收。
