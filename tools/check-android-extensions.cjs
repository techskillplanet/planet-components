#!/usr/bin/env node
/**
 * Ensure every Android Basic* widget is either:
 *   - covered by component_contract.json (public 8-stack contract), or
 *   - listed in android/library/extensions.json (android-private / candidate).
 *
 * Prevents silent Android supersets without documentation.
 */
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');
const androidDir = path.join(
  root,
  'android/library/src/main/java/com/techskillplanet/planetcomponents/widget'
);
const extensionsPath = path.join(root, 'android/library/extensions.json');
const contractPath = path.join(root, 'component_contract.json');

function fail(msg) {
  console.log(`FAIL ${msg}`);
  return 1;
}

function pass(msg) {
  console.log(`PASS ${msg}`);
  return 0;
}

function androidCandidates(name) {
  const base = `Basic${name}`;
  return [
    `${base}.java`,
    `${base}View.java`,
    `${base}Dialog.java`,
    `${base}Sheet.java`,
    'BasicModalDialog.java',
    'BasicOptionSheet.java',
    'BasicRefreshLayout.java',
    'BasicLoadingDialog.java',
    'BasicToast.java',
  ];
}

function resolveContractAndroid(name) {
  for (const f of androidCandidates(name)) {
    const full = path.join(androidDir, f);
    if (fs.existsSync(full)) return path.basename(full, '.java');
  }
  return null;
}

let failed = 0;

if (!fs.existsSync(androidDir)) {
  process.exit(fail('missing Android widget directory'));
}
if (!fs.existsSync(extensionsPath)) {
  process.exit(fail('missing android/library/extensions.json'));
}
if (!fs.existsSync(contractPath)) {
  process.exit(fail('missing component_contract.json'));
}

const contract = JSON.parse(fs.readFileSync(contractPath, 'utf8'));
const components = contract.components.map((c) => c.name);
const covered = new Set();
for (const name of components) {
  const cls = resolveContractAndroid(name);
  if (cls) covered.add(cls);
}

const extDoc = JSON.parse(fs.readFileSync(extensionsPath, 'utf8'));
const listed = new Map();
for (const item of extDoc.extensions || []) {
  if (!item.class) {
    failed += fail('extensions.json entry missing class');
    continue;
  }
  if (listed.has(item.class)) {
    failed += fail(`duplicate extension entry: ${item.class}`);
  }
  listed.set(item.class, item.status || 'android-private');
  const javaPath = path.join(androidDir, `${item.class}.java`);
  if (!fs.existsSync(javaPath)) {
    failed += fail(`extensions.json lists missing class: ${item.class}`);
  }
  if (covered.has(item.class)) {
    failed += fail(
      `${item.class} is in the public contract — remove from extensions.json`
    );
  }
}

const widgets = fs
  .readdirSync(androidDir)
  .filter((f) => f.startsWith('Basic') && f.endsWith('.java'))
  .map((f) => path.basename(f, '.java'))
  .sort();

const orphans = [];
for (const cls of widgets) {
  if (covered.has(cls)) continue;
  if (listed.has(cls)) continue;
  orphans.push(cls);
}

if (orphans.length) {
  failed += fail(
    `Android widgets not in contract or extensions.json: ${orphans.join(', ')}`
  );
} else {
  failed += pass(
    `Android catalog: ${covered.size} contract + ${listed.size} extension = ${widgets.length} widgets`
  );
}

const candidates = [...listed.entries()].filter(([, s]) => s === 'candidate-for-contract');
console.log(
  `INFO candidate-for-contract (${candidates.length}): ${candidates
    .map(([c]) => c)
    .join(', ') || '(none)'}`
);

if (failed) {
  console.error(`\n${failed} android extension check(s) failed`);
  process.exit(1);
}
console.log('\nALL ANDROID EXTENSION CHECKS PASSED');
