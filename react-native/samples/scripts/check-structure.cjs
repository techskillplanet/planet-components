const fs = require('fs');
const path = require('path');

const repoRoot = path.resolve(__dirname, '../../..');
const rnRoot = path.join(repoRoot, 'react-native');
const componentDir = path.join(rnRoot, 'library/src/starPlanet/components');
const pageDir = path.join(rnRoot, 'samples/src/pages');
const routerFile = path.join(rnRoot, 'samples/src/navigation/AppRouter.js');
const indexFile = path.join(rnRoot, 'library/src/starPlanet/index.js');
const themeFile = path.join(rnRoot, 'library/src/starPlanet/theme.js');
const androidDir = path.join(rnRoot, 'samples/android');
const iosDir = path.join(rnRoot, 'samples/ios');

function fail(message) {
  console.error(message);
  process.exit(1);
}

const componentFiles = fs.readdirSync(componentDir).filter(file => /^Tsp.+\.js$/.test(file));
const pageFiles = fs.readdirSync(pageDir).filter(file => file.endsWith('.js'));
const indexSource = fs.readFileSync(indexFile, 'utf8');
const themeSource = fs.readFileSync(themeFile, 'utf8');

if (componentFiles.length < 25) {
  fail(`Expected at least 25 component files, found ${componentFiles.length}`);
}

if (!indexSource.includes("export * from './components';")) {
  fail('starPlanet/index.js should only re-export component APIs from components/.');
}

if (!themeSource.includes('resolveTheme') || !themeSource.includes('starPlanetStyleProfiles')) {
  fail('theme.js should export resolveTheme and starPlanetStyleProfiles for style_token linkage.');
}

['HomePage.js', 'ComponentDetailPage.js', 'SettingsPage.js'].forEach(file => {
  if (!pageFiles.includes(file)) fail(`Missing sample page file: ${file}`);
});

if (!fs.existsSync(routerFile)) {
  fail('Missing sample router: samples/src/navigation/AppRouter.js');
}

if (!fs.existsSync(path.join(androidDir, 'app/build.gradle'))) {
  fail('Missing Android native project: samples/android/app/build.gradle');
}

if (!fs.existsSync(path.join(iosDir, 'Podfile'))) {
  fail('Missing iOS native project: samples/ios/Podfile');
}

console.log(`React Native structure OK: ${componentFiles.length} component files, ${pageFiles.length} page files, android/ios native projects present.`);
