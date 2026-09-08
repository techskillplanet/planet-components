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

Peer: `vue >= 3`. Version: **0.2.0**.

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

## Notes

- Pass `theme` (or use built-in `starPlanetThemes.sky|night|mint|sunrise`) on controls.
- Library path: `vue-web/library` in the monorepo (for contributors only).
- Full Chinese install table: repo `README.zh-CN.md`.
