# 验收标准（RN Sample 交互缺陷修复）

Status: confirmed  
Prerequisites: `docs/prds/rn-upgrade-parity/specs/02-acceptance-criteria.md`

| ID | 可观察结果 | 验证 |
| --- | --- | --- |
| AC-B01 | `TspTopBar` 返回按钮贴左（含 safe-area 后内容区左侧），标题水平居中；`showBack=false` 时不显示返回符号但仍保持标题居中 | `library` 单测 + Sample 详情页目视 |
| AC-B02 | 设置页切换 `zh-CN` / `en` / `ja` 后，首页标题、Tab、设置文案、组件描述随语言表更新 | `i18n` 单测 + Sample 交互 |
| AC-B03 | `TspBottomTab` 支持 `bottomInset`，底部不被系统手势条遮挡 | 单测 style 字段 + Sample |
| AC-B04 | 详情页返回可回到列表（`onBack`） | 单测回调 |
| AC-B05 | `TspBadge` 文案在徽标内水平+垂直居中（Android 无额外 font padding 下沉/上浮） | `library` 样式契约单测 + Sample Badge 页目视 |
| AC-B06 | `TspModal` 打开后取消/确认双按钮同排等分，均完整落在面板内；按钮文案在按钮面内居中；无控件溢出到屏幕边缘 | `library` 布局契约单测 + Sample Modal 页目视 |

## 跨平台同源缺陷结论（本次复核）

| 问题 | RN | React Web | Vue Web | Android View | Flutter / iOS |
| --- | --- | --- | --- | --- | --- |
| Badge 文案不居中 | 有（本次修） | 无（`inline-flex`+`align-items:center`） | 无 | 无（`Gravity.CENTER`） | 未见同症 |
| Modal 双按钮溢出 / 文案偏 | 有（本次修） | 无（`grid 1fr 1fr`） | 无 | 无（`weight=1`） | Sample 用系统 Alert，非同实现 |

## 护栏

- Sample 文案走 `src/i18n/sample_strings.json`（与 Android 同源）
- 仅在跨平台复核确认「同症」时才改其他栈；本次仅 RN 需修
