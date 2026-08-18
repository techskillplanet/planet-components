# 验收标准 — Cross-platform UI parity（含 iOS SwiftUI）

Status: confirmed  
Prerequisites: `01-background-and-goal.md`（confirmed）  
Confirmed at: 2026-08-17（用户确认）  
Evidence: D-01～D-03；`docs/COMPONENT_CONTRACT.md`；`05-switch-contract.md`

## 范围说明

本验收针对「全端一致化 + iOS SwiftUI 1:1 还原」。验证对象为对外可观察的 API 语义、视觉关键态、交互状态机；不以源码结构同构为通过条件。

## 验收准则

### AC-01 契约完备（无缺失）

`COMPONENT_CONTRACT.md` 组件表中的每一项，在下列栈均存在语义等价公开组件（允许 Android `Basic*` 命名）：

- React Native、Flutter、Android View、React Web、Vue Web、**iOS SwiftUI**

**当前已知缺口（必须关闭）**：iOS 的 Switch、Modal、OptionSheet、LoadingDialog、RefreshLayout。

**证据**：各栈公开导出/公开类型清单与契约表对照；iOS `swift build` 可通过并导出上述类型。

### AC-02 冲突裁决一致

当两端实现细节冲突且规范未写死时：

- 以 RN `starPlanet` 为正确目标（D-03）
- 偏离端（含 iOS、Flutter、Android、Web，必要时含文档）改到与 RN 一致，或将已确认细节升格写入契约/token 后再全端对齐

**证据**：不一致项清单中每一项标注「跟 RN / 已升格契约」及改动栈。

### AC-03 规范层优先

token 与已 confirmed 契约（含 Switch）高于任何单端实现。若 RN 与已确认契约冲突，**改 RN（及所有端）以契约为准**，不得用 RN 覆盖已确认契约。

**证据**：Switch 等已确认契约在六端（含 iOS）均满足；契约文件含 iOS 行。

### AC-04 API 语义对齐

各端同一组件的核心 props/事件语义与契约一致（`variant`、`disabled`、`selected`/`checked`、`text`/`title`/`message`、主题注入等）；平台惯用命名允许别名（如 Flutter `onChanged`），但行为等价。

**证据**：契约表对照 + 各栈 sample/测试覆盖默认、禁用、选中/开关、加载/错误等适用状态。

### AC-05 视觉关键态对齐

下列可观察视觉与 RN 参照（或已升格 token）一致，允许系统字体/渲染抗锯齿等平台差，不允许「用了系统默认控件导致形态不同」（例：Switch 禁止系统 Switch）：

- 主色/边框/表面来自 token 语义色
- variant 配色映射一致
- 关键尺寸（高度、圆角量级、轨道/滑块比例等）与参照同档

**证据**：按组件对照 RN sample 与 iOS（及其它偏离端）sample；关键组件（至少 Button、Switch、Toast、Modal、Chip）有记录化对比结论。

### AC-06 iOS 工程约束

- 实现为 SwiftUI（可局部 UIKit 桥接，不对外提供平行 UIKit 组件库）
- 遵守一组件一文件（对齐过程中拆分 `BasicControls.swift` 巨石文件）
- `ios-swiftui/library` 可 `swift build`；samples 依赖本地 library 并展示契约组件

**证据**：目录结构检查 + `swift build` 日志。

### AC-07 回归不破坏已发布端

一致化改动不得无说明地破坏已发布 npm/Maven/pub 包的公开 API；若必须破坏，需升版说明。iOS 尚未正式发版前，以补齐与对齐优先。

**证据**：公开 API diff 审查；已发版栈的既有测试通过。

## 护栏

- 不引入 UIKit 平行组件集作为正式对外 API
- 不把「看起来差不多」当作通过；必须以契约/RN 参照可核对
- 不在未确认情况下用 Flutter 覆盖 RN 参照（除非先升格契约）

## 验证方式汇总

| AC | 主要证据 |
| --- | --- |
| AC-01 | 组件清单矩阵 + iOS 导出 |
| AC-02 | 不一致项闭环表 |
| AC-03 | 契约（含 iOS）+ 六端 Switch 等 |
| AC-04 | sample/测试状态覆盖 |
| AC-05 | 关键组件视觉对照记录 |
| AC-06 | 一文件一组件 + `swift build` |
| AC-07 | API diff + 已发版栈测试 |

## 待确认

请确认本验收标准是否可作为后续 As-Is / To-Be / 实施的通过门槛（回复「确认」或指出要改的 AC）。
