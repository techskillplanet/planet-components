const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');

function exists(p) {
  return fs.existsSync(path.join(root, p));
}

function files(dir, matcher) {
  const full = path.join(root, dir);
  if (!fs.existsSync(full)) return [];
  return fs.readdirSync(full).filter(name => fs.statSync(path.join(full, name)).isFile()).filter(matcher);
}

const checks = [
  ['android-view', exists('android-view/library/build.gradle') && exists('android-view/samples/build.gradle')],
  ['react-native', files('react-native/library/src/starPlanet/components', f => /^Tsp.+\.js$/.test(f)).length >= 25 && exists('react-native/samples/src/navigation/AppRouter.js')],
  ['react-web', exists('react-web/library/package.json') && exists('react-web/samples/index.html')],
  ['vue-web', exists('vue-web/library/package.json') && exists('vue-web/samples/index.html')],
  ['flutter', exists('flutter/library/pubspec.yaml') && fs.readFileSync(path.join(root, 'flutter/samples/pubspec.yaml'), 'utf8').includes('path: ../library')],
  ['ios-swiftui', exists('ios-swiftui/library/Package.swift') && exists('ios-swiftui/samples/Package.swift')],
  ['miniprogram', exists('miniprogram/library/package.json') && exists('miniprogram/samples/pages/basic-samples/index.js')],
  ['kuikly', exists('kuikly/library/shared/build.gradle.kts') && exists('kuikly/samples/miniApp/build.gradle.kts')],
];

let failed = 0;
for (const [name, ok] of checks) {
  console.log(`${ok ? 'PASS' : 'FAIL'} ${name}`);
  if (!ok) failed += 1;
}

if (failed) process.exit(1);
