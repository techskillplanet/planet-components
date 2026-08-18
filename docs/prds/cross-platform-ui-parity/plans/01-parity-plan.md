# Cross-platform UI parity plan（含 iOS SwiftUI）

Status: W1–W6 implemented in workspace（2026-08-17）；macOS `swift build` 仍需人工复核

## Source of truth

1. `design/tokens/*` + `docs/COMPONENT_CONTRACT.md` + confirmed 组件契约  
2. 未写死细节 → RN `starPlanet`  
3. iOS 实现 = SwiftUI  

## Waves

| Wave | Work | Status |
| --- | --- | --- |
| **W1** | iOS 一组件一文件；`TspSwitch`；sample 去 Toggle；契约加 iOS | **done** |
| **W2** | iOS `TspModal` / `TspOptionSheet`；Select 联动；sample 去系统 alert | **done** |
| **W3** | iOS Toast host ~1600ms；`TspLoadingDialog`；`TspRefreshLayout` | **done** |
| **W4** | Flutter + React Web + Vue Web：LoadingDialog、RefreshLayout | **done** |
| **W5** | iOS Card selected/disabled 对齐；Select→OptionSheet；关键态 sample 覆盖 | **done（首批）**；其余视觉细调可后续迭代 |
| **W6** | `check-structure`；刷新本 plan / as-is | **done** |

## Verify

```bash
node tools/check-structure.cjs
cd flutter/library && flutter test
cd react-web/library && npm test -- tests/components.test.js
cd vue-web/library && npm test -- tests/components.test.js
# macOS:
cd ios-swiftui/library && swift build
cd ios-swiftui/samples && swift build
```

## Residual

- Linux 无 Swift CLI；需 macOS 编译验证  
- Web `sample.smoke` 依赖 samples 内 npm 链接的已发布包，与本次 library 改动可能不同步（components 单测已覆盖新组件）  
- 全量像素级对照 RN 仍可按组件继续深化（W5 后续）  
