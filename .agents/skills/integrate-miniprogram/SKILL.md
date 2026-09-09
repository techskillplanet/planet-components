---
name: integrate-miniprogram
description: >-
  Integrate @techskillplanet/planet-components-miniprogram into a WeChat Mini Program.
  Use when installing bc-* components aligned with Tsp* contract.
---

# Integrate WeChat Mini Program

## Install

```bash
npm install @techskillplanet/planet-components-miniprogram
```

In the mini program tooling, build npm and reference components from the package `miniprogram` field (`components/`).

## Usage

- Component folders are `bc-*` (e.g. `bc-button`), semantically aligned with `Tsp*` in `docs/COMPONENT_CONTRACT.md`.
- Theme helpers live under package `theme/` (`sky` / `night` / `mint` / `sunrise`; page class `theme-sunrise`).

## Coverage

≈ **35** `bc-*` components. Includes **DatePicker** (`bc-date-picker`) and **Domain-7**: `bc-child-switcher`, `bc-score-rule-grid`, `bc-redeem-card-grid`, `bc-calendar-heatmap`, `bc-print-sheet`, `bc-balance-hero`, `bc-check-in-streak-card`.

## Notes

- `bc-button` property `loading` for spinner / blocked tap.
- npm: https://www.npmjs.com/package/@techskillplanet/planet-components-miniprogram
- Version **0.2.1** (publish after headless checks).
- Monorepo: `miniprogram/library` (samples use local symlink, not published coord).
