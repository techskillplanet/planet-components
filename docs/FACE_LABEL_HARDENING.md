# 定高面标签硬化（N-14 / N-15）· 2026-10-30

**类型**: optimize（夹带 fix）  
**范围**: Kuikly `Tsp*` + React Web `.bc-*` 对等；不扩对外 API 契约。

## 硬规则

| ID | 规则 |
|----|------|
| N-14 | 定高面内单行：父级 Center + `lineHeight == fontSize`（Web `line-height: 1`）+ 水平居中 |
| N-15 | `×` / `✓` 等符号：固定方盒 + Center，禁止 `Modifier.size` 直接打在裸 `Text` |
| L5 | 无 label 不 `fillMaxWidth`（Checkbox / Switch / Radio） |

## 共用

- Kuikly：`TspFaceLabel` / `TspGlyphMark`
- Web：Button face / Chip / Badge / Tabs / Segmented / Tag close / Search clear → flex + `line-height: 1`

## 本轮触及

Button · Chip · Badge · Tag · Tabs · Segmented(Solid/Soft) · SearchBar clear · Switch(L5) · Checkbox ✓ · Stepper · PinInput · ActionSheet 行 · TopBar 标题 · Sample SearchBar 尾随 lambda 误绑

## 自检

```bash
cd kuikly && ./gradlew :shared:compileKotlinJs
```
