#!/usr/bin/env node
/**
 * Emit design/tokens/generated/figma-variables.manifest.json from canonical tokens.
 * Used as the source map for Figma Variables ↔ repo sync (see docs/FIGMA.md).
 */
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');
const color = JSON.parse(
  fs.readFileSync(path.join(root, 'design/tokens/color_token.json'), 'utf8')
);
const style = JSON.parse(
  fs.readFileSync(path.join(root, 'design/tokens/style_token.json'), 'utf8')
);
const contract = JSON.parse(
  fs.readFileSync(path.join(root, 'component_contract.json'), 'utf8')
);

function flatten(obj, prefix = '') {
  const out = {};
  for (const [k, v] of Object.entries(obj || {})) {
    if (k.startsWith('$')) continue;
    const key = prefix ? `${prefix}.${k}` : k;
    if (v && typeof v === 'object' && !Array.isArray(v)) {
      Object.assign(out, flatten(v, key));
    } else {
      out[key] = v;
    }
  }
  return out;
}

const themeName = 'sky_planet_day';
const semantic = flatten(color.themes[themeName].semantic);
const colorVars = Object.entries(semantic).map(([tokenPath, value]) => ({
  collection: `Planet / Colors / ${themeName}`,
  figmaName: tokenPath.replace(/\./g, '/'),
  tokenPath: `themes.${themeName}.semantic.${tokenPath}`,
  type: 'COLOR',
  value: String(value),
}));

const styleFlat = flatten({
  font: style.font,
  space: style.space,
  spacing: style.spacing,
  radius: style.radius,
  border: style.border,
  size: style.size,
  shadow: style.shadow,
  motion: style.motion,
  opacity: style.opacity,
});
const floatVars = Object.entries(styleFlat).map(([tokenPath, value]) => ({
  collection: 'Planet / Style',
  figmaName: tokenPath.replace(/\./g, '/'),
  tokenPath,
  type: typeof value === 'number' ? 'FLOAT' : 'STRING',
  value,
}));

const island = style.themes || {};
const islandVars = [];
for (const [profile, body] of Object.entries(island)) {
  const flat = flatten(body);
  for (const [tokenPath, value] of Object.entries(flat)) {
    islandVars.push({
      collection: `Planet / Island / ${profile}`,
      figmaName: tokenPath.replace(/\./g, '/'),
      tokenPath: `themes.${profile}.${tokenPath}`,
      type: typeof value === 'number' ? 'FLOAT' : 'STRING',
      value,
    });
  }
}

const manifest = {
  meta: {
    generatedBy: 'tools/build-figma-manifest.cjs',
    version: color.meta?.version || '0.2.1',
    contractComponents: contract.components.length,
    note: 'Import into Figma as Variables; keep names stable for Code Connect.',
  },
  themes: Object.keys(color.themes || {}),
  islandProfiles: Object.keys(island),
  contractThemeTokens: contract.theme?.tokens || [],
  variables: [...colorVars, ...floatVars, ...islandVars],
};

const outDir = path.join(root, 'design/tokens/generated');
fs.mkdirSync(outDir, { recursive: true });
const outFile = path.join(outDir, 'figma-variables.manifest.json');
fs.writeFileSync(outFile, JSON.stringify(manifest, null, 2) + '\n');
console.log(
  `Wrote ${path.relative(root, outFile)} (${manifest.variables.length} variables)`
);
