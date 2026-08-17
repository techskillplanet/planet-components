# Switch 跨端强约束契约

Status: confirmed（扁平常规 UI，五端完全一致）

## 视觉方向

**扁平常规 Switch**：无轨内 ON/OFF、无厚边框岛屿、无阴影。仅外侧 `text` + 轨道 + 滑块。

## 公开组件名

| 栈 | 公开导出名 | 文件路径（强制） |
| --- | --- | --- |
| React Web | `TspSwitch` | `react-web/library/src/components/TspSwitch.js` |
| Vue Web | `TspSwitch` | `vue-web/library/src/components/TspSwitch.js` |
| React Native | `TspSwitch` | `react-native/library/src/starPlanet/components/TspSwitch.js` |
| Flutter | `TspSwitch` | `flutter/library/lib/tech_skill_planet_components.dart` |
| Android View | `BasicSwitchView`（语义等价 `TspSwitch`） | `android/library/.../widget/BasicSwitchView.java` |

Web CSS 根类名强制：`bc-switch`。

## 属性（语义名强制一致）

| 语义 | 类型 | 默认 | 说明 |
| --- | --- | --- | --- |
| `text` | string | `''` | 外侧说明文案（左） |
| `checked` | boolean | `false` | 开关值 |
| `checkedText` / `uncheckedText` | string | `'ON'`/`'OFF'` | **保留 API 兼容，扁平 UI 不渲染** |
| `loading` | boolean | `false` | thumb 内 spinner + 禁交互 |
| `disabled` | boolean | `false` | opacity `0.5` |
| `variant` | `'md' \| 'sm'` | `'md'` | Android 兼容 `default`/`small` |
| `theme` | object | sky | 运行时主题 |
| `onChange` | `(checked: boolean) => void` | — | Flutter=`onChanged`；Vue=`change`；Android=listener |

## DOM / 视图层级（强制同构）

```
SwitchRoot                    // bc-switch
├── Label?                    // bc-switch__label
└── Control                   // bc-switch__control
    └── Track                 // bc-switch__track
        └── Thumb             // bc-switch__thumb
            └── Spinner?      // bc-switch__spinner (loading)
```

禁止渲染 `bc-switch__inner-text`。

修饰态：`bc-checked`、`bc-loading`、`bc-disabled`、`bc-switch--md`、`bc-switch--sm`。

## 视觉规格（扁平）

| 项 | md | sm |
| --- | --- | --- |
| Track | **52 × 28** | **40 × 22** |
| Thumb | **24** | **18** |
| Thumb inset | **2** | **2** |
| Travel | 24 | 18 |
| Border | **0** | **0** |
| Radius | 999 | 999 |
| Motion | 180ms | 180ms |
| Opacity loading / disabled | 0.7 / 0.5 | 同 |

### Day 颜色

| 状态 | Track | Thumb |
| --- | --- | --- |
| off | `#C8EAFF`（`borderDefault` / off track） | `#FFFFFF` |
| on | `#31A8FF`（`brandPrimary`） | `#FFFFFF` |
| spinner | on 时 `brandPrimary` 反差用白环；off 时 `brandDark` | — |

## 分包

- 一组件一文件；Barrel 仅导出公开 API
- Sample 覆盖：md、sm、loading、disabled、带 `text`
