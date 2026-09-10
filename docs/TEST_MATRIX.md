# Cross-stack contract test matrix

## Why counts differed

| Stack | Approx cases before | Nature |
| --- | --- | --- |
| React Web | ~136 | Full unit + a11y + visual + sample smoke (written first) |
| Vue Web | ~107 | Mirrors React components suite, fewer extras |
| React Native | ~24 | Spot checks only |
| Flutter | ~15 | Spot checks only |
| Android | ~15 | Scenario / Robolectric spot checks |
| iOS / Mini / Kuikly | 0–few | Build gates only |

**Absolute counts do not need to be identical** (axe/jsdom/utils are Web-only).  
**Shared behavior coverage must be identical** via the matrix below.

## Shared baseline

Source of truth: [`tools/contract-test-matrix.json`](../tools/contract-test-matrix.json)

1. **Smoke** — every contract component: `TC-CONTRACT-{Name}-01`
2. **Interactions** — fixed list (Cascader leaf path, Tag close, InputNumber step, …)

Gate:

```bash
node tools/check-test-matrix.cjs
```

Stacks may add extra tests on top; they must not drop matrix ids.

## Current inventory target

- Contract components: **57**
- Shared matrix size: **57 smoke + 20 interactions = 77** required TC ids per stack
