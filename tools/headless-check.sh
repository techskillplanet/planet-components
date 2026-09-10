#!/usr/bin/env bash
# Headless verification for all Planet Components stacks (no GUI).
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"
FAIL=0
run() {
  local name="$1"; shift
  echo ""
  echo "==> $name"
  if "$@"; then
    echo "PASS $name"
  else
    echo "FAIL $name"
    FAIL=1
  fi
}

run structure node tools/check-structure.cjs
run build-tokens node tools/build-tokens.cjs
run sync-icons node tools/sync-icons.cjs
run build-api-docs node tools/build-api-docs.cjs
run build-figma-manifest node tools/build-figma-manifest.cjs
run token-drift node tools/check-token-drift.cjs
run contract-inventory node tools/check-contract-inventory.cjs
run android-extensions node tools/check-android-extensions.cjs
run skills-contract node tools/check-skills-contract.cjs
run icon-catalog node tools/check-icon-catalog.cjs
run test-matrix node tools/check-test-matrix.cjs
run docs-i18n node tools/check-docs-i18n.cjs

run react-web-lib bash -lc 'cd react-web/library && npm run check && npm test && npm run pack:dry'
run react-web-sample bash -lc 'cd react-web/samples && npx --yes esbuild@0.25.0 preview-entry.js --bundle --format=iife --outfile=dist/preview.js --platform=browser --alias:react=./node_modules/react/index.js --alias:react-dom=./node_modules/react-dom/index.js --alias:react-dom/client=./node_modules/react-dom/client.js && cp node_modules/@techskillplanet/planet-components-react/src/styles.css dist/styles.css'

run vue-web-lib bash -lc 'cd vue-web/library && npm run check && npm test && npm run pack:dry'
run vue-web-sample bash -lc 'cd vue-web/samples && npx --yes esbuild@0.25.0 preview-entry.js --bundle --format=iife --outfile=dist/preview.js --alias:vue=./vendor/vue.esm-browser.js --platform=browser && cp node_modules/@techskillplanet/planet-components-vue/src/styles.css dist/styles.css'

run react-native-lib bash -lc 'cd react-native/library && npm test && npm run pack:dry'
run react-native-sample bash -lc 'cd react-native/samples && npx vitest run'

run miniprogram bash -lc 'cd miniprogram/library && npm run pack:dry'

run flutter bash -lc 'cd flutter/library && flutter analyze && flutter test && cd ../samples && flutter analyze'

run ios bash -lc 'cd ios-swiftui/library && swift build'

run android bash -lc 'cd android && ./gradlew :library:testDebugUnitTest :library:assembleRelease :samples:assembleDebug --console=plain'

run kuikly bash -lc 'cd kuikly && ./gradlew :shared:compileDebugKotlinAndroid :androidApp:assembleDebug --console=plain'

echo ""
if [[ "$FAIL" -eq 0 ]]; then
  echo "ALL HEADLESS CHECKS PASSED"
  exit 0
fi
echo "SOME HEADLESS CHECKS FAILED"
exit 1
