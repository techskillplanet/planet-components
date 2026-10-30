# 基础控件状态矩阵

> 专业组件库最低完整态。实现以 Kuikly + React Web 为准。  
> 关联：`COMPONENT_CONTRACT.md` · Study `20261024-foundation-components-uplift`

## 图例

| 符号 | 含义 |
|------|------|
| ✅ | 已实现 |
| 🟡 | 部分 / 平台差 |
| ❌ | 未做 |
| — | 不适用 |

## 核心控件

| 组件 | default | hover | focus | disabled | loading | error |
|------|---------|-------|-------|----------|---------|-------|
| Button | ✅ | 🟡 Web | 🟡 Web | ✅ | ✅ | — |
| Input | ✅ | 🟡 Web | 🟡 Web | ✅ | — | ✅ |
| TextArea | ✅ | 🟡 | 🟡 | ✅ | — | ✅ size |
| FormField | ✅ | — | — | — | — | ✅ |
| Checkbox | ✅ | 🟡 | — | ✅ | — | — |
| Radio / RadioGroup | ✅ | 🟡 | — | ✅ | — | — |
| Chip | ✅ solid/compact | 🟡 | — | ✅ | — | — |
| Badge | ✅ Soft* | — | — | ✅ | — | — |
| InlineNotice | ✅ | — | — | — | — | — |
| ActionSheet | ✅ | — | — | item | — | — |
| SheetActions | ✅ | — | — | ✅ | ✅ | — |
| Switch | ✅ | 🟡 | — | ✅ | ✅ | — |
| Modal | ✅ | — | — | — | confirmLoading | — |
| Toast | ✅ | — | — | — | — | — |
| Empty | ✅ | — | — | — | — | — |
| OptionSheet | ✅ | — | — | — | — | — |

## 尺寸阶梯

| size | Button 面高 | Input 高 | Checkbox | TextArea 行高基数 |
|------|-------------|----------|----------|-------------------|
| sm | 36 | 40 | 14 | 14+6 |
| md | 46 | 48 | 20 | 16+6 |
| lg | 52 | 56 | 22 | 17+6 |

## Study 委托

| Study | 库 |
|-------|-----|
| `SpCheckBox` | 权威 SVG（结构对齐 TspCheckbox） |
| `SpBadge` | `TspBadge` Soft* |
| `SpInlineNotice` | `TspInlineNotice` |
| `SpSheetActions` | `TspSheetActions` |
| `SpActionSheet` | Hoist + **`TspActionSheetPanel`**（盖底栏）；Dialog 版 `TspActionSheet` |
| `SpPillChip` | 仍 Study（elder scale）；库 `TspChip` 已支持 solid/compact |

## 已知防线

| ID | 规则 |
|----|------|
| C1/L18 | 勾选兄弟层 |
| L5 | 无 label 不撑满（Checkbox / Switch / Radio） |
| N-13 | InlineNotice 外层铺底 + lineHeight=字号 |
| N-14 | **定高面内单行标签**：父级 Center + `lineHeight==fontSize` + `TextAlign.Center` + `maxLines=1`；共用 `TspFaceLabel` / Web `line-height: 1` |
| N-15 | 符号热区（× / ✓）：固定方盒 + Center，禁止把 `size` 直接打在裸 Text 上 |
