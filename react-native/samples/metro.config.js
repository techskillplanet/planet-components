const { getDefaultConfig } = require('expo/metro-config');
const path = require('path');

const projectRoot = __dirname;
const libraryRoot = path.resolve(projectRoot, '../library');
const sampleModules = path.resolve(projectRoot, 'node_modules');

const config = getDefaultConfig(projectRoot);

// Local library is watched for live edits, but React must stay a singleton.
// Without this, Metro can resolve `react` from `../library/node_modules`
// (test install) and crash with: Cannot read property 'useState' of null.
config.watchFolders = [libraryRoot];
config.resolver.nodeModulesPaths = [sampleModules];
config.resolver.disableHierarchicalLookup = true;
config.resolver.extraNodeModules = {
  react: path.resolve(sampleModules, 'react'),
  'react-native': path.resolve(sampleModules, 'react-native'),
  'react/jsx-runtime': path.resolve(sampleModules, 'react/jsx-runtime'),
  'react/jsx-dev-runtime': path.resolve(sampleModules, 'react/jsx-dev-runtime'),
};

module.exports = config;
