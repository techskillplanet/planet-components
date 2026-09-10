#!/usr/bin/env node
/**
 * Verify each stack's test sources mention the shared contract TC ids.
 *
 * Usage: node tools/check-test-matrix.cjs
 */
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');
const matrix = JSON.parse(fs.readFileSync(path.join(root, 'tools/contract-test-matrix.json'), 'utf8'));
const contract = JSON.parse(fs.readFileSync(path.join(root, 'component_contract.json'), 'utf8'));
const components = contract.components.map((c) => c.name);

const STACK_GLOBS = {
  'react-web': ['react-web/library/tests'],
  'vue-web': ['vue-web/library/tests'],
  'react-native': ['react-native/library/tests'],
  flutter: ['flutter/library/test'],
  android: ['android/library/src/test'],
  'ios-swiftui': ['ios-swiftui/library/Tests', 'ios-swiftui/Tests'],
  miniprogram: ['miniprogram/library/__tests__', 'miniprogram/tests'],
  kuikly: ['kuikly/library/shared/src/commonTest', 'kuikly/library/shared/src/androidUnitTest'],
};

function walk(dir, acc = []) {
  if (!fs.existsSync(dir)) return acc;
  for (const ent of fs.readdirSync(dir, { withFileTypes: true })) {
    const p = path.join(dir, ent.name);
    if (ent.isDirectory()) walk(p, acc);
    else if (/\.(js|ts|dart|swift|kt|java)$/.test(ent.name)) acc.push(p);
  }
  return acc;
}

function loadStackText(stack) {
  const dirs = STACK_GLOBS[stack] || [];
  const files = dirs.flatMap((d) => walk(path.join(root, d)));
  return {
    files,
    text: files.map((f) => fs.readFileSync(f, 'utf8')).join('\n'),
  };
}

const required = [];
for (const name of components) {
  required.push(`TC-CONTRACT-${name}-01`);
}
for (const item of matrix.interactions) {
  required.push(item.id);
}

let failed = 0;
const summary = [];

for (const stack of matrix.stacks) {
  const { files, text } = loadStackText(stack);
  const missing = required.filter((id) => !text.includes(id));
  const present = required.length - missing.length;
  summary.push({ stack, files: files.length, present, total: required.length, missing });
  if (files.length === 0) {
    console.error(`FAIL ${stack}: no test directory found`);
    failed += 1;
    continue;
  }
  if (missing.length) {
    console.error(`FAIL ${stack}: ${present}/${required.length} matrix ids (missing ${missing.length})`);
    console.error(`  e.g. ${missing.slice(0, 8).join(', ')}${missing.length > 8 ? '…' : ''}`);
    failed += 1;
  } else {
    console.log(`PASS ${stack}: ${present}/${required.length} matrix ids (${files.length} test files)`);
  }
}

console.log('\nShared matrix size:', required.length, `(${components.length} smoke + ${matrix.interactions.length} interactions)`);
if (failed) {
  console.error(`\n${failed} stack(s) below matrix parity`);
  process.exit(1);
}
console.log('\nALL TEST MATRIX CHECKS PASSED');
