const { getDefaultConfig } = require('expo/metro-config');
const path = require('path');

const projectRoot = __dirname;
const sampleModules = path.resolve(projectRoot, 'node_modules');
const libraryRoot = path.resolve(projectRoot, '../library');

const config = getDefaultConfig(projectRoot);

config.watchFolders = [projectRoot, libraryRoot];
// Keep a single React / RN copy even when the local library pulls peers in.
config.resolver.nodeModulesPaths = [sampleModules];
config.resolver.disableHierarchicalLookup = true;
config.resolver.extraNodeModules = {
  react: path.resolve(sampleModules, 'react'),
  'react-native': path.resolve(sampleModules, 'react-native'),
  'react/jsx-runtime': path.resolve(sampleModules, 'react/jsx-runtime'),
  'react/jsx-dev-runtime': path.resolve(sampleModules, 'react/jsx-dev-runtime'),
  '@techskillplanet/planet-components-react-native': libraryRoot,
};

module.exports = config;
