const { getDefaultConfig } = require('expo/metro-config');
const path = require('path');

const projectRoot = __dirname;
const sampleModules = path.resolve(projectRoot, 'node_modules');

const config = getDefaultConfig(projectRoot);

// Keep a single React / RN copy even when the published package pulls peers in.
config.resolver.nodeModulesPaths = [sampleModules];
config.resolver.disableHierarchicalLookup = true;
config.resolver.extraNodeModules = {
  react: path.resolve(sampleModules, 'react'),
  'react-native': path.resolve(sampleModules, 'react-native'),
  'react/jsx-runtime': path.resolve(sampleModules, 'react/jsx-runtime'),
  'react/jsx-dev-runtime': path.resolve(sampleModules, 'react/jsx-dev-runtime'),
};

module.exports = config;
