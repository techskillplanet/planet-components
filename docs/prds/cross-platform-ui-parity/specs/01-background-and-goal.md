# 背景与目标 — Cross-platform UI parity（含 iOS SwiftUI）

Status: confirmed  
Prerequisites: `00-spec-index.md`  
Confirmed at: 2026-08-17（用户确认）  
Evidence:

- 用户确认 iOS 实现栈 = SwiftUI（2026-08-17）
- 用户澄清：全端一致化——缺失补齐、不一致统一；可回改任一端（2026-08-17）
- 用户确认冲突裁决参照 = **RN `starPlanet`**（选项 1，2026-08-17）
- 既有：`docs/COMPONENT_CONTRACT.md`、`design/tokens/*`、`05-switch-contract.md`
- iOS 缺失：`TspSwitch` / `TspModal` / `TspOptionSheet` / `TspLoadingDialog` / `TspRefreshLayout`

## 问题背景

Planet Components 多端并行实现后，出现：

1. **缺失**：某端没有契约组件（如 iOS 缺 Switch/Modal 等）
2. **不一致**：两端都有，但视觉/交互/API 语义不同

用户要求以 SwiftUI 做 iOS，并对齐其它平台：**1:1 还原 = 全端一致化**。

## A/B 澄清（已确认理解）

- A/B 不是「只跟某一端、其它端可以不一致」。
- A/B 只是：两端已打架且契约未写死时，以谁为正确目标。
- 最终各端必须一致；偏离端（含 RN 以外的端，以及必要时回写文档）一律统一。

## 受影响用户

- 多端业务接入方
- 库维护者

## 约束

- iOS：**SwiftUI**
- 规范层（最高）：`design/tokens/*` + `docs/COMPONENT_CONTRACT.md` + 已 confirmed 组件契约
- 参照实现层：规范未写死时，以 **RN `starPlanet`**（`shared.js` + components）为准
- 一组件一文件；samples 覆盖契约状态
- Android 公开名可保留 `Basic*`，语义对齐 `Tsp*`

## 目标

1. 契约组件在 RN / Flutter / Android / React Web / Vue Web / **iOS SwiftUI** 均有语义等价实现
2. 视觉与交互一致化；冲突时按「规范 → RN 参照」裁决并回改偏离端
3. iOS 补齐缺失组件并纳入既有契约（至少 Switch）

## 成功信号

- iOS 无契约级缺失；Switch 等已确认契约含 iOS 行
- 跨端不一致项有清单并按裁决收敛
- Sample + 栈级测试覆盖关键状态

## 非目标

- 不重做 UIKit 平行库
- 不强制本轮上线像素级自动截图 diff（验收阶段可另定）
- 不强制各端源码结构同构，只强制对外语义与可观察 UI 一致

## Confirmed decisions

| ID | 决策 |
| --- | --- |
| D-01 | iOS = SwiftUI |
| D-02 | 全端一致化：缺补齐、不一致统一 |
| D-03 | 冲突裁决：规范优先；未写死则跟 **RN starPlanet** |
