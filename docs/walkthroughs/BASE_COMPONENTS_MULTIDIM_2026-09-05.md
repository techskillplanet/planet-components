# 基础组件多维度全栈走查

| 字段 | 值 |
|------|------|
| 日期 | 2026-09-05 |
| 类型 | `optimize`（组件能力与跨栈对齐） |
| 契约 | [`component_contract.json`](../../component_contract.json) · [`docs/COMPONENT_CONTRACT.md`](../COMPONENT_CONTRACT.md) |
| 结构检查 | `node tools/check-structure.cjs` → **全栈 PASS**（2026-09-05） |
| 关联 | 学习管家 UI uplift 优先修基础组件；基线 tag（study）`pre-ui-uplift` |

---

## 0. 走查范围与维度

**组件**：契约内全部 **35** 个（含 Study 域扩展 7 个）。  
**技术栈**：`react-web` · `vue-web` · `react-native` · `flutter` · `ios-swiftui` · `miniprogram` · `android` · `kuikly`（共 8）。

| 维度 ID | 维度 | 检查内容 |
|---------|------|----------|
| D1 | 存在性覆盖 | 契约组件在各栈是否有实现 |
| D2 | 命名映射 | Tsp* / Basic* / bc-* 是否可追溯到同一契约名 |
| D3 | API 契约 | `variant` / `disabled` / `loading` / `onTap` 等是否对齐 |
| D4 | 视觉 Token | Sky Planet · `island_raised` / `island_flat` · 色板 |
| D5 | 交互状态 | default / hover·press / focus / disabled / loading / error |
| D6 | 触控与无障碍 | ≥44px、焦点环、reduced-motion |
| D7 | Samples | 各栈是否有可跑 sample 覆盖组件 |
| D8 | 自动化测试 | 单测 / 结构检查 / 冒烟 |

---

## 1. D1 · 存在性覆盖矩阵（契约 35 × 8 栈）

> `Y` = 有实现；`—` = 缺失。命名已归一到契约名（Android `BasicButton`→Button，小程序 `bc-button`→Button）。

| Component | react-web | vue-web | react-native | flutter | ios-swiftui | miniprogram | android | kuikly | Gaps |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Button | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Card | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Alert | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Badge | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Chip | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Input | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Select | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| OptionSheet | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Switch | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Progress | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| TopBar | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| BottomTab | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Tabs | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Amount | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| IconButton | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| KeyValueLabel | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Notification | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| TextLink | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Stepper | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| StickyFooter | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| PinInput | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| ListItem | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Empty | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Toast | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| Modal | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| RefreshLayout | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| LoadingDialog | Y | Y | Y | Y | Y | Y | Y | Y | 0 |
| DatePicker | Y | Y | — | — | — | — | — | — | 6 |
| ChildSwitcher | Y | — | — | — | — | — | — | — | 7 |
| ScoreRuleGrid | Y | — | — | — | — | — | — | — | 7 |
| RedeemCardGrid | Y | — | — | — | — | — | — | — | 7 |
| CalendarHeatmap | Y | — | — | — | — | — | — | — | 7 |
| PrintSheet | Y | — | — | — | — | — | — | — | 7 |
| BalanceHero | Y | — | — | — | — | — | — | — | 7 |
| CheckInStreakCard | Y | — | — | — | — | — | — | — | 7 |

### 覆盖率摘要

| 栈 | 契约覆盖 | 缺什么 |
|----|----------|--------|
| react-web | **35/35** | — |
| vue-web | **28/35** | 7 个 Study 域组件 |
| react-native / flutter / ios / miniprogram / android / kuikly | **27/35** | DatePicker + 7 个 Study 域 |
| 结构检查 | 8 栈 PASS | — |

**分层结论**

1. **核心基础控件（前 27）**：八栈齐备。  
2. **DatePicker**：仅 Web（react/vue）。  
3. **Study 域扩展（ChildSwitcher…CheckInStreakCard）**：目前几乎只在 **react-web**，不应要求小程序/Android 立即 1:1，但契约已写进 `component_contract.json`，需在文档中标为 **P1 域组件 / 可选跨栈**。

---

## 2. D2 · 命名映射

| 契约名 | Web/RN/Kuikly/iOS/Flutter | Android | 小程序 |
|--------|---------------------------|---------|--------|
| Button | `TspButton` / `tsp_button` | `BasicButton` | `bc-button` |
| Card | `TspCard` | `BasicCardView` | `bc-card` |
| … | `Tsp*` | `Basic*View/Dialog` | `bc-*` |

**问题**

- Android 另有一批 **契约外** 控件（SearchBar、Table、StarRating、Checkbox、Radio、Collapse…）——能力更强，但未进统一契约，跨栈不可发现。  
- Flutter/iOS/Kuikly 有 `TabItem` / `ModalButton` 等实现细节文件，属内部拆分，OK。  
- Kuikly 包名仍见 `phonics.controls` 历史路径，可读性/品牌一致性弱。

---

## 3. D3 · API 契约抽检（Button）

契约 props：`text, variant, disabled, fullWidth, onTap`（**未列 loading**）。

| 栈 | loading | 备注 |
|----|---------|------|
| react-web | **有**（2026-09-04 提升） | 需回写契约 |
| vue / RN / Flutter / iOS / MP / Android / Kuikly | **无** | 能力不齐 |

**裁决建议**：契约增加 `loading`；P0 先对齐 **vue-web + miniprogram + android**（与学习管家/小程序/安卓最相关），再推 RN/Flutter/iOS/Kuikly。

其它 API 风险（需专项扫）：

- Switch：契约禁止系统 Switch / 岛屿轨内字（见 cross-platform-ui-parity）。  
- Card：`subtle` / `selected` 是否各栈都支持。  
- Input：`error` variant 与 `disabled` 是否统一。

---

## 4. D4 · 视觉 Token / 岛屿

| 栈 | island_raised 默认 | 近期提升 |
|----|--------------------|----------|
| react-web | 默认 raised；阴影 y=6、Card 浮岛 | **已做** hover/focus/press/loading |
| vue-web | theme 已跟 y=6 | CSS 已从 react 同步一批；**需人工目视** |
| android | `BasicThemeManager` 默认 `island_raised` | 未做与 Web 同级的 hover（触控平台不同，应对齐 press/disabled） |
| 其它栈 | 有 profile 概念 | 未系统对照 Web 最新阴影/焦点环 |

**风险**：Web 已领先，其它栈视觉「偏平/少反馈」会再次被业务侧嫌「组件一般」。

---

## 5. D5 · 交互状态矩阵（目标态）

每个可交互组件应具备：

| 状态 | Button | Chip | Input | Select | Switch | ListItem | IconButton |
|------|--------|------|-------|--------|--------|----------|------------|
| default | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| hover（指针） | Web 必 | Web | Web | Web | — | Web | Web |
| press/active | 全栈 | 全栈 | — | 全栈 | 全栈 | 全栈 | 全栈 |
| focus-visible | Web/桌面 | Web | Web | Web | Web | Web | Web |
| disabled | 全栈 | 全栈 | 全栈 | 全栈 | 全栈 | 全栈 | 全栈 |
| loading | 目标全栈 | — | — | — | 契约已有 | — | — |
| error | — | — | 契约 | — | — | — | — |

**现状**：react-web 最接近目标；其它栈多为 default/disabled/press，**缺统一 loading / focus 规范落地**。

---

## 6. D6 · 触控与无障碍

| 检查项 | 目标 | 现状线索 |
|--------|------|----------|
| 可点高度 ≥44 | Chip/TextButton/IconButton | react-web Chip/Text 已抬到 44；其它栈待扫 |
| 焦点可见 | Web focus-visible | 仅 react-web 系统加强 |
| prefers-reduced-motion | Web | react-web 已加；vue 已同步块 |
| 对比度 | 主色/白字 | Sky `#31A8FF` 需抽检 primary on white |

---

## 7. D7 · Samples

| 栈 | Samples 目录 | 备注 |
|----|--------------|------|
| react-web / vue-web / RN / flutter / ios / mp / android / kuikly | 结构检查 PASS | 需确认 **是否展示全部契约组件 + 全状态**，而非仅 Happy Path |

**建议**：每个栈 sample 增加「States Gallery」页：同一 Button 展示 primary/default/danger/text × default/disabled/loading。

---

## 8. D8 · 自动化

| 检查 | 结果 |
|------|------|
| `node tools/check-structure.cjs` | 全栈 PASS |
| react-web `npm test` | 96 passed（含 Button loading） |
| 其它栈单测 | 未在本走查日全跑；提升后按栈补跑 |

---

## 9. 分层与优先级（给后续开发）

### P0 · 核心基础控件能力对齐（八栈）

对象：Button / Card / Input / Chip / Select / Switch / IconButton / ListItem / TextLink  

| 任务 | 说明 |
|------|------|
| P0-1 | 契约回写 Button.`loading` |
| P0-2 | 将 react-web 交互能力 **移植清单**（press、disabled 视觉、loading）到 vue / mp / android |
| P0-3 | 各栈 States Gallery sample |
| P0-4 | 触控 ≥44 抽检脚本或清单 |

### P1 · DatePicker 跨栈

Web 已有 → RN / Flutter / iOS / MP / Android / Kuikly 择学习管家需要的栈先补。

### P2 · Study 域组件策略

ChildSwitcher / ScoreRuleGrid / RedeemCardGrid / CalendarHeatmap / PrintSheet / BalanceHero / CheckInStreakCard  

- 短期：**契约标注 `domain: study`，跨栈可选**  
- 中期：小程序 / Android 按产品优先级移植（学习管家真要用才做）

### P3 · Android 契约外组件

SearchBar / Table / … 进扩展契约或文档附录，避免「有实现但不可发现」。

---

## 10. 与学习管家的关系

| 产品动作 | 依赖本走查 |
|----------|------------|
| 登录/首页抛光 | 依赖 P0（尤其 Web Button/Card/Input） |
| 小程序 | 依赖 P0-2 mp + 可选 P2 |
| 安卓壳 | 依赖 P0-2 android |

---

## 11. 下一步（请确认）

1. **先做 P0**：八栈核心控件 loading/press/disabled/触控对齐（从 vue-web + miniprogram + android 开始）  
2. **先做 P0 但只 Web**：vue 对齐 react，其它栈只出 checklist  
3. **先改契约文档**：把 7 个 Study 组件标为 domain-optional，再排期  

回复 1 / 2 / 3 后按该选项拆 `tasks.md` 开工。
