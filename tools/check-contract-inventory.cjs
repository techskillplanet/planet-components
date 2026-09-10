#!/usr/bin/env node
/**
 * Verify every component in component_contract.json exists on all eight stacks.
 *
 * Naming map (docs/COMPONENT_CONTRACT.md):
 *   Contract name → Tsp{Name} (RN/Web/Flutter/iOS/Kuikly)
 *                → Basic{Name}View | Basic{Name} | Basic{Name}Dialog (Android heuristics)
 *                → bc-{kebab} (Mini Program folder)
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

function kebab(name) {
  return name
    .replace(/([a-z0-9])([A-Z])/g, '$1-$2')
    .replace(/([A-Z])([A-Z][a-z])/g, '$1-$2')
    .toLowerCase();
}

function androidCandidates(name) {
  const base = `Basic${name}`;
  return [
    `${base}.java`,
    `${base}View.java`,
    `${base}Dialog.java`,
    // Toast / OptionSheet / RefreshLayout / LoadingDialog / Modal specials
    `${base}Sheet.java`,
    `BasicModalDialog.java`,
    `BasicOptionSheet.java`,
    `BasicRefreshLayout.java`,
    `BasicLoadingDialog.java`,
    `BasicToast.java`,
  ];
}

function flutterFile(name) {
  // TspFooBar → tsp_foo_bar.dart
  const snake = name
    .replace(/([a-z0-9])([A-Z])/g, '$1_$2')
    .replace(/([A-Z])([A-Z][a-z])/g, '$1_$2')
    .toLowerCase();
  return `tsp_${snake}.dart`;
}

const contract = JSON.parse(read('component_contract.json'));
const components = contract.components.map((c) => c.name);

const androidDir = 'android/library/src/main/java/com/techskillplanet/planetcomponents/widget';
const stacks = [
  {
    name: 'react-web',
    resolve: (n) => `react-web/library/src/components/Tsp${n}.js`,
  },
  {
    name: 'vue-web',
    resolve: (n) => `vue-web/library/src/components/Tsp${n}.js`,
  },
  {
    name: 'react-native',
    resolve: (n) => `react-native/library/src/starPlanet/components/Tsp${n}.js`,
  },
  {
    name: 'flutter',
    resolve: (n) => `flutter/library/lib/src/${flutterFile(n)}`,
  },
  {
    name: 'ios-swiftui',
    resolve: (n) => `ios-swiftui/library/Sources/PlanetComponents/Tsp${n}.swift`,
  },
  {
    name: 'miniprogram',
    resolve: (n) => {
      const dir = `miniprogram/library/components/bc-${kebab(n)}`;
      return exists(`${dir}/bc-${kebab(n)}.js`) || exists(`${dir}/bc-${kebab(n)}.wxml`)
        ? `${dir}/bc-${kebab(n)}.js`
        : `${dir}/MISSING`;
    },
  },
  {
    name: 'kuikly',
    resolve: (n) =>
      `kuikly/library/shared/src/commonMain/kotlin/com/techskillplanet/phonics/controls/Tsp${n}.kt`,
  },
  {
    name: 'android',
    resolve: (n) => {
      const files = androidCandidates(n);
      for (const f of files) {
        const rel = `${androidDir}/${f}`;
        if (exists(rel)) return rel;
      }
      // Modal → BasicModalDialog already in candidates; special case TopBar etc.
      if (n === 'Modal' && exists(`${androidDir}/BasicModalDialog.java`)) {
        return `${androidDir}/BasicModalDialog.java`;
      }
      if (n === 'Toast' && exists(`${androidDir}/BasicToast.java`)) {
        return `${androidDir}/BasicToast.java`;
      }
      if (n === 'OptionSheet' && exists(`${androidDir}/BasicOptionSheet.java`)) {
        return `${androidDir}/BasicOptionSheet.java`;
      }
      if (n === 'RefreshLayout' && exists(`${androidDir}/BasicRefreshLayout.java`)) {
        return `${androidDir}/BasicRefreshLayout.java`;
      }
      if (n === 'LoadingDialog' && exists(`${androidDir}/BasicLoadingDialog.java`)) {
        return `${androidDir}/BasicLoadingDialog.java`;
      }
      return `${androidDir}/Basic${n}View.java`;
    },
  },
];

let failed = 0;
console.log(`Contract components: ${components.length}`);

for (const stack of stacks) {
  const missing = [];
  for (const name of components) {
    const rel = stack.resolve(name);
    if (!exists(rel)) missing.push(name);
  }
  if (missing.length) {
    console.log(`FAIL ${stack.name}: missing ${missing.join(', ')}`);
    failed += 1;
  } else {
    console.log(`PASS ${stack.name}: ${components.length}/${components.length}`);
  }
}

// Barrel export spot-check for JS stacks
for (const [stack, barrel, prefix] of [
  ['react-web', 'react-web/library/src/components/index.js', 'Tsp'],
  ['vue-web', 'vue-web/library/src/components/index.js', 'Tsp'],
  ['react-native', 'react-native/library/src/starPlanet/components/index.js', 'Tsp'],
]) {
  if (!exists(barrel)) {
    console.log(`FAIL ${stack}-barrel: missing ${barrel}`);
    failed += 1;
    continue;
  }
  const text = read(barrel);
  const missing = components.filter((n) => !text.includes(`${prefix}${n}`));
  if (missing.length) {
    console.log(`FAIL ${stack}-barrel: not exported ${missing.join(', ')}`);
    failed += 1;
  } else {
    console.log(`PASS ${stack}-barrel exports`);
  }
}

if (failed) {
  console.error(`\n${failed} contract inventory check(s) failed`);
  process.exit(1);
}
console.log('\nALL CONTRACT INVENTORY CHECKS PASSED');
