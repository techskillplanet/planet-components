# Spec Index — Cross-platform UI parity

Status: **confirmed**；W1–W6 已在工作区落地（2026-08-17）

## Scope

全端一致化（RN / Flutter / Android / React Web / Vue Web / iOS SwiftUI）：缺失补齐、不一致统一；iOS = SwiftUI。

## Source of truth

1. 规范层最高（token + COMPONENT_CONTRACT + 已确认组件契约）  
2. 未写死 → **RN starPlanet**  
3. RN 与已确认契约冲突 → 改 RN  

## Confirmed decisions

| ID | 决策 |
| --- | --- |
| D-01 | iOS = SwiftUI |
| D-02 | 全端一致化：缺补齐、不一致统一 |
| D-03 | 冲突跟 RN（规范优先） |
| D-04 | AC-01～AC-07 为通过门槛 |
| D-05 | LoadingDialog / RefreshLayout 六端必选 |
| D-06 | iOS sample 禁用系统 Toggle / alert 顶替 |
| D-07 | iOS 一组件一文件 |
| D-08 | Switch 契约增补 iOS |
| D-09 | 既有 iOS 组件按 RN 对齐，不「有类型即合格」 |

## Docs

| 文档 | 状态 |
| --- | --- |
| `01-background-and-goal.md` | confirmed |
| `02-acceptance-criteria.md` | confirmed |
| `03-as-is.md` | confirmed（矩阵已按实现更新） |
| `04-to-be.md` | confirmed |
| `05-switch-contract.md` | confirmed（含 iOS） |
| Plan | `../plans/01-parity-plan.md`（W1–W6 done） |

## Next（工程）

- macOS：`cd ios-swiftui/library && swift build`  
- 可选：Web samples 重新 link 本地 library 后再跑 sample.smoke  
- 可选：逐组件像素对照 RN 深化视觉细调  
