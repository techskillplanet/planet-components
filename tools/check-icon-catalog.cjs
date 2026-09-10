#!/usr/bin/env node
/**
 * Ensure design/icons SVGs + planet-icons.json stay aligned with platform catalogs.
 */
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');
const catalog = JSON.parse(
  fs.readFileSync(path.join(root, 'design/icons/planet-icons.json'), 'utf8')
);
const names = Object.keys(catalog.icons);

function fail(msg) {
  console.log(`FAIL ${msg}`);
  return 1;
}
function pass(msg) {
  console.log(`PASS ${msg}`);
  return 0;
}

let failed = 0;

for (const name of names) {
  const svg = path.join(root, 'design/icons', `${name}.svg`);
  if (!fs.existsSync(svg)) {
    failed += fail(`missing design/icons/${name}.svg`);
  } else {
    const text = fs.readFileSync(svg, 'utf8');
    const pathAttr = catalog.icons[name].path.split(/(?=M)/)[0].trim();
    if (!text.includes(pathAttr.slice(0, 12))) {
      failed += fail(`SVG ${name}.svg does not contain catalog path prefix`);
    }
  }
}
failed += pass(`catalog icons: ${names.join(', ')}`);

const mirrors = [
  'react-web/library/src/icons/planetIcons.js',
  'vue-web/library/src/icons/planetIcons.js',
  'react-native/library/src/starPlanet/icons/planetIcons.js',
  'flutter/library/lib/src/planet_icons.dart',
  'ios-swiftui/library/Sources/PlanetComponents/PlanetIcons.swift',
  'miniprogram/library/theme/planet-icons.json',
  'android/library/src/main/java/com/techskillplanet/planetcomponents/icon/PlanetIcons.java',
];

for (const rel of mirrors) {
  const full = path.join(root, rel);
  if (!fs.existsSync(full)) {
    failed += fail(`missing ${rel} (run: node tools/sync-icons.cjs)`);
    continue;
  }
  const text = fs.readFileSync(full, 'utf8');
  const missing = names.filter((n) => !text.includes(n));
  if (missing.length) {
    failed += fail(`${rel} missing names: ${missing.join(', ')}`);
  } else {
    failed += pass(rel);
  }
}

const components = [
  'react-web/library/src/components/TspIcon.js',
  'vue-web/library/src/components/TspIcon.js',
  'react-native/library/src/starPlanet/components/TspIcon.js',
  'flutter/library/lib/src/tsp_icon.dart',
  'ios-swiftui/library/Sources/PlanetComponents/TspIcon.swift',
  'miniprogram/library/components/bc-icon/bc-icon.js',
  'android/library/src/main/java/com/techskillplanet/planetcomponents/widget/BasicIconView.java',
];
for (const rel of components) {
  if (!fs.existsSync(path.join(root, rel))) {
    failed += fail(`missing icon component ${rel}`);
  } else {
    failed += pass(rel);
  }
}

if (failed) {
  console.error(`\n${failed} icon catalog check(s) failed`);
  process.exit(1);
}
console.log('\nALL ICON CATALOG CHECKS PASSED');
