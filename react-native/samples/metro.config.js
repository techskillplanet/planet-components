const { getDefaultConfig } = require('expo/metro-config');
const path = require('path');

const projectRoot = __dirname;
const libraryRoot = path.resolve(projectRoot, '../library');

const config = getDefaultConfig(projectRoot);

config.watchFolders = [libraryRoot];
config.resolver.nodeModulesPaths = [
  path.resolve(projectRoot, 'node_modules'),
];

module.exports = config;
