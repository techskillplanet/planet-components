# As-Is — Cross-platform UI parity（含 iOS SwiftUI）

Status: confirmed  
Prerequisites: `02-acceptance-criteria.md`（confirmed）  
Confirmed at: 2026-08-17（用户确认）  
Evidence date: 2026-08-17  

Evidence sources:

- RN 导出：`react-native/library/src/starPlanet/components/index.js`（27 个 `Tsp*`）
- Flutter：`flutter/library/lib/src/*.dart`（barrel：`tech_skill_planet_components.dart`）
- iOS：`ios-swiftui/library/Sources/PlanetComponents/Tsp*.swift`
- Android：`android/library/.../widget/Basic*.java` 文件列表
- Web：`react-web|vue-web/library/src/components/`
- 契约：`docs/COMPONENT_CONTRACT.md`、`05-switch-contract.md`
- RN Switch 参照：`TspSwitch.js`（自定义 Pressable，非系统 Switch）

## 1. 裁决与规范现状

| 层 | 状态 |
| --- | --- |
| 规范 | token + COMPONENT_CONTRACT + Switch 契约（五端，**无 iOS 行**） |
| 参照实现 | RN `starPlanet`（D-03） |
| iOS 技术栈决策 | SwiftUI（D-01） |

## 2. 组件完备性矩阵（相对契约 / RN）

图例：✅ 有公开实现 · ❌ 缺失 · △ 有实现但未按最新契约核验 / sample 提及但库无类型

| 组件 | RN | Flutter | Android | React Web | Vue Web | iOS SwiftUI |
| --- | --- | --- | --- | --- | --- | --- |
| Button | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Card | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Alert | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Badge | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Chip | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Input | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Select | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| OptionSheet | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Switch | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Progress | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| TopBar | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| BottomTab | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Tabs | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Amount | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| IconButton | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| KeyValueLabel | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Notification | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| TextLink | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Stepper | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| StickyFooter | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| PinInput | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| ListItem | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Empty | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Toast | ✅ | ✅ | ✅ | ✅ | ✅ | ✅（视图在；全局弹出/时长待对齐） |
| Modal | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| RefreshLayout | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| LoadingDialog | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |

**结论（AC-01）**：

- iOS 已补齐：Switch、Modal、OptionSheet、LoadingDialog、RefreshLayout（2026-08-17 实现）  
- Flutter / React Web / Vue Web 已补齐 LoadingDialog、RefreshLayout  
- Sample：iOS 已去除系统 Toggle / `.alert` 顶替  

## 3. iOS 结构与工程痛点

| 痛点 | 证据 | 影响 AC |
| --- | --- | --- |
| 巨石文件 | 已拆为 `Tsp*.swift` 一组件一文件 | AC-06 已满足 |
| Sample 与库脱节 | sample 目录含 Modal/Switch/OptionSheet 说明，库无类型 | AC-01 / AC-04 |
| 未发版 | 相对 Flutter 已上 pub.dev；iOS 仍 SPM 本地 | AC-07 对 iOS 压力较低 |
| UIKit 局部桥接 | TopBar 读 `UIApplication` safeArea | 可接受，需控制范围 |
| Toast | 仅有静态 `TspToast` View，未见 RN 级 auto-dismiss / 全局宿主 API | AC-05 |
| Sample 用系统 Toggle | `BasicControlsSampleView` 在 `"Switch"` 分支使用 `Toggle(...)`，非 `TspSwitch` | AC-03 / AC-05（禁止系统 Switch） |
| Sample Modal | 使用 SwiftUI `.alert`，非 `TspModal` | AC-01 / AC-05 |

## 4. 已知一致化锚点（已完成 / 部分完成）

- **Switch 扁平契约**：RN / Flutter / Android / React Web / Vue Web 已按 `05-switch-contract.md`；**iOS 未纳入**  
- Toast 方向（solid、~1600ms、info=brandDark）：多端 parity 草案有记录；**iOS 需对照 RN 核验**  
- Flutter 曾按 RN 做过一轮 layout/token 对齐（plan 记载）；**不能假设已与 RN 完全无差**

## 5. 不一致风险区（需 As-Is→To-Be 后逐项对照，非本文件臆测像素）

以下在进入实现前应用 RN 参照做 diff（标为「待核验」，不是已证实 bug）：

- Button variant 命名：`default` vs Flutter `standard` 等别名是否行为等价  
- iOS 已有组件的圆角/高度/选中填充是否跟 RN token 同档  
- Select ↔ OptionSheet 联动在 iOS 是否缺失导致 Select 行为不完整  
- LoadingDialog / RefreshLayout：多端缺失是否改为「契约必选」或「分阶段」

## 6. 角色与边界

| 角色 | 当前职责 |
| --- | --- |
| 规范 | token + COMPONENT_CONTRACT + 组件契约 |
| RN | 冲突时参照实现（D-03） |
| iOS | SwiftUI 落地；补缺失 + 对齐 + 拆文件 |
| 其它端 | 对共同缺失与已证实偏离做回改 |

## 7. 基线度量（便于验收对照）

- RN 公开 `Tsp*`：27  
- iOS 公开 `Tsp*` struct：约 23（含 TabItem；**无** Switch/Modal/OptionSheet/LoadingDialog/RefreshLayout）  
- Switch 契约覆盖端：5；目标 6（+iOS）

## 8. 事实缺口（不阻塞 As-Is 确认，但影响实现排序）

- 尚未做逐组件像素/截图对比表（AC-05 实施阶段补）  
- LoadingDialog / RefreshLayout 是否坚持六端必选，待 To-Be 明确（建议默认：跟 RN 必选，其它端补齐）

## 请确认

本 As-Is 是否准确描述当前差距？回复 **「确认」** 或指出要修正的矩阵项。确认后进入 To-Be。
