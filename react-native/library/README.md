# Basic Controls React Native

React Native implementation for the Star Planet basic controls library.

## Library

- `src/starPlanet/index.js`
- `src/starPlanet/theme.js`
- `src/starPlanet/components/*.js`
- `src/starPlanet/utils/shared.js`

Package name: `@techskillplanet/basic-controls-react-native`.

## Component File Rule

React Native follows the same structure rule as the other platforms:

- one component = one JavaScript file under `src/starPlanet/components/`
- `index.js` only exports public APIs
- shared helpers and styles live under `utils/`
- no component implementation should be added directly to `index.js`

## Sample

- `../samples/App.js`
- `../samples/src/navigation/AppRouter.js`
- `../samples/src/pages/HomePage.js`
- `../samples/src/pages/ComponentDetailPage.js`
- `../samples/src/pages/SettingsPage.js`
- `../samples/src/data/componentDocs.js`

The sample is a runnable Expo React Native project. It depends on the local library exports and follows the same structure as Web, Android, iOS and Kuikly:

- Learning tab: grouped component list.
- One list item per component.
- One visual-only detail page per component.
- Settings tab: theme and language switching.
- Route state is centralized in `../samples/src/navigation/AppRouter.js`.

Run it from the sample directory:

```bash
cd ../samples
npm install
npm run start
```
