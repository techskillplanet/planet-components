const fs = require('fs');
const path = require('path');

const repoRoot = path.resolve(__dirname, '../../..');
const rnRoot = path.join(repoRoot, 'react-native');
const componentDir = path.join(rnRoot, 'library/src/starPlanet/components');
const pageDir = path.join(rnRoot, 'samples/src/pages');
const routerFile = path.join(rnRoot, 'samples/src/navigation/AppRouter.js');
const indexFile = path.join(rnRoot, 'library/src/starPlanet/index.js');

function fail(message) {
  console.error(message);
  process.exit(1);
}

const componentFiles = fs.readdirSync(componentDir).filter(file => /^Tsp.+\.js$/.test(file));
const pageFiles = fs.readdirSync(pageDir).filter(file => file.endsWith('.js'));
const indexSource = fs.readFileSync(indexFile, 'utf8');

if (componentFiles.length < 25) {
  fail(`Expected at least 25 component files, found ${componentFiles.length}`);
}

if (!indexSource.includes("export * from './components';")) {
  fail('starPlanet/index.js should only re-export component APIs from components/.');
}

['HomePage.js', 'ComponentDetailPage.js', 'SettingsPage.js'].forEach(file => {
  if (!pageFiles.includes(file)) fail(`Missing sample page file: ${file}`);
});

if (!fs.existsSync(routerFile)) {
  fail('Missing sample router: samples/src/navigation/AppRouter.js');
}

console.log(`React Native structure OK: ${componentFiles.length} component files, ${pageFiles.length} page files.`);
