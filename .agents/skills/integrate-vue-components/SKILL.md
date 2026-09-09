---
name: integrate-vue-components
description: >-
  Integrate @techskillplanet/planet-components-vue into a Vue 3 web app.
  Use when installing, theming, or composing Tsp* controls on Vue Web
  (Vite, Nuxt) with Sky Planet themes.
---

# Integrate Vue Components

## Install

```bash
npm install @techskillplanet/planet-components-vue
```

Peer: `vue >= 3`. Version: **0.2.1**.

## Setup

```js
import { createApp } from 'vue';
import App from './App.vue';
import '@techskillplanet/planet-components-vue/styles.css';

createApp(App).mount('#app');
```

```vue
<script setup>
import { TspButton, starPlanetTheme } from '@techskillplanet/planet-components-vue';
</script>

<template>
  <TspButton text="Get Started" variant="primary" :theme="starPlanetTheme" />
</template>
```

## Coverage

≈ **35** `Tsp*` controls aligned with React Web. Includes **DatePicker** (`TspDatePicker`) and **Domain-7**: `TspChildSwitcher`, `TspScoreRuleGrid`, `TspRedeemCardGrid`, `TspCalendarHeatmap`, `TspPrintSheet`, `TspBalanceHero`, `TspCheckInStreakCard`.

## Notes

- Pass `theme` (or use built-in `starPlanetThemes.sky|night|mint|sunrise`) on controls.
- `TspButton` supports `loading`.
- Library version **0.2.1** (publish after `./tools/headless-check.sh` is green).
- Library path: `vue-web/library` in the monorepo (for contributors only).
- Full Chinese install table: repo `README.zh-CN.md`.
