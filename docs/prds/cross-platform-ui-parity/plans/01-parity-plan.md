# Cross-platform UI parity (RN / Flutter / Android)

## Source of truth
React Native `starPlanet` polish (`shared.js` + components) + shared design tokens.

## Status
- Flutter library rewritten to RN layout/token numbers (P0+P1).
- Android View P0/P1 aligned (OptionSheet, Pin cells, Chip/Badge/Card selected, etc.).
- Residual: RN Switch remains system Switch (Flutter aligned); Android island Switch may still differ — prefer system Switch track colors on Android for full parity if product confirms.

## Verify
```bash
cd flutter/library && flutter test
cd flutter/samples && flutter test && flutter build apk --debug
cd android && ./gradlew :library:compileDebugJavaWithJavac :samples:assembleDebug
cd react-native/library && npm test
node tools/check-structure.cjs
```
