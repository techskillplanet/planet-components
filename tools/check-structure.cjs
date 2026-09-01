const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');

function exists(p) {
  return fs.existsSync(path.join(root, p));
}

function read(p) {
  return fs.readFileSync(path.join(root, p), 'utf8');
}

function files(dir, matcher) {
  const full = path.join(root, dir);
  if (!fs.existsSync(full)) return [];
  return fs.readdirSync(full).filter(name => fs.statSync(path.join(full, name)).isFile()).filter(matcher);
}

function dirs(dir, matcher) {
  const full = path.join(root, dir);
  if (!fs.existsSync(full)) return [];
  return fs.readdirSync(full).filter(name => fs.statSync(path.join(full, name)).isDirectory()).filter(matcher);
}

function usesLocalLibrary(relPath, needles) {
  if (!exists(relPath)) return false;
  const text = read(relPath);
  return needles.some(needle => text.includes(needle));
}

const checks = [
  ['android', exists('android/library/build.gradle') && exists('android/samples/build.gradle') && exists('android/samples/src/main/java/com/techskillplanet/planetcomponents/samples/navigation/SampleRouter.java')],
  ['android-sample-local', usesLocalLibrary('android/samples/build.gradle', ["project(\":library\")", "project(':library')"])],
  ['react-native', files('react-native/library/src/starPlanet/components', f => /^Tsp.+\.js$/.test(f)).length >= 25 && exists('react-native/samples/src/navigation/AppRouter.js')],
  ['react-native-sample-local', usesLocalLibrary('react-native/samples/package.json', ['file:../library'])],
  ['react-web', exists('react-web/library/package.json') && exists('react-web/samples/index.html')],
  ['react-web-sample-local', usesLocalLibrary('react-web/samples/package.json', ['file:../library'])],
  ['vue-web', exists('vue-web/library/package.json') && exists('vue-web/samples/index.html')],
  ['vue-web-sample-local', usesLocalLibrary('vue-web/samples/package.json', ['file:../library'])],
  ['flutter', exists('flutter/library/pubspec.yaml') && exists('flutter/samples/pubspec.yaml') && read('flutter/samples/pubspec.yaml').includes('path: ../library')],
  ['flutter-one-file', files('flutter/library/lib/src', f => /^tsp_.+\.dart$/.test(f)).length >= 25 && read('flutter/library/lib/tech_skill_planet_components.dart').includes("export 'src/")],
  ['ios-swiftui', exists('ios-swiftui/library/Package.swift') && exists('ios-swiftui/samples/Package.swift') && exists('ios-swiftui/library/README.md') && exists('ios-swiftui/library/LICENSE') && exists('ios-swiftui/library/PUBLISHING.md') && read('ios-swiftui/library/Package.swift').includes('PlanetComponents')],
  ['ios-publish-spm', exists('Package.swift') && read('Package.swift').includes('PlanetComponents') && read('Package.swift').includes('ios-swiftui/library/Sources/PlanetComponents')],
  ['ios-publish-cocoapods', exists('PlanetComponents.podspec') && read('PlanetComponents.podspec').includes("s.name             = 'PlanetComponents'") && read('PlanetComponents.podspec').includes('ios-swiftui/library/Sources/PlanetComponents')],
  ['miniprogram', exists('miniprogram/library/package.json') && exists('miniprogram/samples/pages/basic-samples/index.js')],
  ['miniprogram-coverage', dirs('miniprogram/library/components', name => name.startsWith('bc-')).length >= 25],
  ['miniprogram-sample-local', exists('miniprogram/samples/planet-components') && usesLocalLibrary('miniprogram/samples/package.json', ['file:../library'])],
  ['kuikly', exists('kuikly/library/shared/build.gradle.kts') && exists('kuikly/samples/miniApp/build.gradle.kts') && exists('kuikly/samples/androidApp/build.gradle.kts') && exists('kuikly/README.md')],
  ['kuikly-publish-maven', exists('kuikly/scripts/publish-maven-central.sh') && read('kuikly/library/shared/build.gradle.kts').includes('maven-publish') && read('kuikly/library/shared/build.gradle.kts').includes('planet-components-kuikly') && exists('kuikly/gradle.properties.example')],
  ['kuikly-one-file', files('kuikly/library/shared/src/commonMain/kotlin/com/techskillplanet/phonics/controls', f => /^Tsp.+\.kt$/.test(f)).length >= 27],
];

let failed = 0;
for (const [name, ok] of checks) {
  console.log(`${ok ? 'PASS' : 'FAIL'} ${name}`);
  if (!ok) failed += 1;
}

if (failed) process.exit(1);
