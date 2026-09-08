---
name: integrate-react-native-components
description: >-
  Integrate @techskillplanet/planet-components-react-native into a React Native app.
  Use when installing or theming Tsp* controls on RN (Expo or bare) with resolveTheme.
---

# Integrate React Native Components

## Install

```bash
npm install @techskillplanet/planet-components-react-native
```

Peers: `react` / `react-native` per package.json. Version: **0.2.0**.

## Usage

```js
import { TspButton, resolveTheme } from '@techskillplanet/planet-components-react-native';

const theme = resolveTheme('sky', 'island_raised');

export function Screen() {
  return (
    <TspButton text="Get Started" variant="primary" theme={theme} onTap={() => {}} />
  );
}
```

## Notes

- Prefer `resolveTheme(palette, profile)` over hard-coded colors.
- No separate CSS import (StyleSheet inside the library).
- Monorepo library: `react-native/library`.
