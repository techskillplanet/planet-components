#!/usr/bin/env node
/**
 * Fail when canonical design tokens drift from platform copies / semantic consumers.
 *
 * Canonical sources:
 *   design/tokens/color_token.json
 *   design/tokens/style_token.json
 *
 * Exact copies that must match byte-for-byte (after normalizing JSON):
 *   android/library/src/main/assets/theme/color_token.json
 *   android/library/src/main/assets/theme/style_token.json
 *
 * Semantic key presence (theme.name tokens from component_contract.json):
 *   react-web/library/src/theme.js
 *   vue-web/library/src/theme.js
 *   react-native/library/src/starPlanet/theme.js
 */
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');

function read(rel) {
  return fs.readFileSync(path.join(root, rel), 'utf8');
}

function exists(rel) {
  return fs.existsSync(path.join(root, rel));
}

function normalizeJson(text) {
  return JSON.stringify(JSON.parse(text), null, 2) + '\n';
}

function fail(msg) {
  console.log(`FAIL ${msg}`);
  return 1;
}

function pass(msg) {
  console.log(`PASS ${msg}`);
  return 0;
}

let failed = 0;

const colorSrc = 'design/tokens/color_token.json';
const styleSrc = 'design/tokens/style_token.json';
const androidColor = 'android/library/src/main/assets/theme/color_token.json';
const androidStyle = 'android/library/src/main/assets/theme/style_token.json';

for (const [src, copy] of [
  [colorSrc, androidColor],
  [styleSrc, androidStyle],
]) {
  if (!exists(src)) {
    failed += fail(`missing canonical ${src}`);
    continue;
  }
  if (!exists(copy)) {
    failed += fail(`missing copy ${copy}`);
    continue;
  }
  const a = normalizeJson(read(src));
  const b = normalizeJson(read(copy));
  if (a !== b) {
    failed += fail(`token drift: ${copy} != ${src} (run: cp ${src} ${copy})`);
  } else {
    failed += pass(`sync ${path.basename(src)} → android assets`);
  }
}

const contractPath = 'component_contract.json';
if (!exists(contractPath)) {
  failed += fail('missing component_contract.json');
} else {
  const contract = JSON.parse(read(contractPath));
  const semanticKeys = contract.theme && Array.isArray(contract.theme.tokens)
    ? contract.theme.tokens
    : [];
  const themeFiles = [
    'react-web/library/src/theme.generated.js',
    'vue-web/library/src/theme.generated.js',
    'react-native/library/src/starPlanet/theme.generated.js',
  ];
  for (const themeFile of themeFiles) {
    if (!exists(themeFile)) {
      failed += fail(`missing ${themeFile} (run: node tools/build-tokens.cjs)`);
      continue;
    }
    const text = read(themeFile);
    const missing = semanticKeys.filter((key) => !text.includes(key));
    if (missing.length) {
      failed += fail(`${themeFile} missing semantic keys: ${missing.join(', ')}`);
    } else {
      failed += pass(`semantic keys present in ${themeFile}`);
    }
  }

  // Hand-written theme.js must re-export generated themes.
  for (const wrapper of [
    'react-web/library/src/theme.js',
    'vue-web/library/src/theme.js',
    'react-native/library/src/starPlanet/theme.js',
  ]) {
    if (!exists(wrapper)) {
      failed += fail(`missing ${wrapper}`);
      continue;
    }
    const text = read(wrapper);
    if (!text.includes('theme.generated.js')) {
      failed += fail(`${wrapper} must import theme.generated.js`);
    } else {
      failed += pass(`${wrapper} wires generated themes`);
    }
  }
}

if (failed) {
  console.error(`\n${failed} token drift check(s) failed`);
  process.exit(1);
}
console.log('\nALL TOKEN DRIFT CHECKS PASSED');
