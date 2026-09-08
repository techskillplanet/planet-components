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
- Theme helpers live under package `theme/`.

## Notes

- npm: https://www.npmjs.com/package/@techskillplanet/planet-components-miniprogram
- Version **0.2.0**.
- Monorepo: `miniprogram/library` (samples use local symlink, not published coord).
