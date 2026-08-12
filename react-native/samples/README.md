# Basic Controls React Native Sample

Runnable Expo sample project for the React Native Basic Controls package.

## Runtime

- Expo SDK 57
- React Native 0.86
- React 19.2

## Structure

- `App.js`: application entry.
- `src/navigation/AppRouter.js`: route state and page switching.
- `src/pages/HomePage.js`: component list page.
- `src/pages/ComponentDetailPage.js`: one detail/demo page component for component previews.
- `src/pages/SettingsPage.js`: theme and language settings page.
- `src/data/componentDocs.js`: grouped component metadata.
- `android/`: native Android project for `expo run:android`.
- `ios/`: native iOS project for `expo run:ios`.

## Run

```bash
npm install --legacy-peer-deps
npm test
npm run start
```

> Metro must resolve a **single** `react` copy for `../library`.
> The sample `metro.config.js` forces `extraNodeModules` + `disableHierarchicalLookup`.
> If `../library/node_modules/react` exists (from library unit-test installs) and Metro is not configured,
> opening component detail pages crashes with `Cannot read property 'useState' of null`.

### Android

Requires Android SDK and a device/emulator.

```bash
npm run android
```

Or build the native project directly:

```bash
cd android && ./gradlew :app:assembleDebug
```

### iOS

Requires Xcode and CocoaPods on macOS.

```bash
cd ios && pod install
npm run ios
```

## Theme Switch

Settings tab exposes 6 built-in presets aligned with Android View:

- Sky / Star / Mint color themes
- `island_raised` (button raised shadow) or `island_flat` (flat, no shadow)

Library API:

```javascript
import { resolveTheme, builtInThemePresets } from '@techskillplanet/planet-components-react-native';

const theme = resolveTheme('sky', 'island_flat');
```

## Structure Check

```bash
npm run check
```
