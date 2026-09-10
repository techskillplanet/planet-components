#!/usr/bin/env node
/**
 * Build runtime theme artifacts from design/tokens.
 *
 * Inputs:
 *   design/tokens/color_token.json
 *   design/tokens/style_token.json
 *
 * Outputs:
 *   design/tokens/generated/runtime-themes.json
 *   design/tokens/generated/tokens.css
 *   react-web|vue-web|react-native theme.generated.js
 *   android assets color/style token copies
 *
 * Style Dictionary is available under tools/token-build for future DTCG exports;
 * this script owns Planet's nested theme → flat runtime mapping today.
 */
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');

function readJson(rel) {
  return JSON.parse(fs.readFileSync(path.join(root, rel), 'utf8'));
}

function write(rel, text) {
  const full = path.join(root, rel);
  fs.mkdirSync(path.dirname(full), { recursive: true });
  fs.writeFileSync(full, text);
  console.log(`wrote ${rel}`);
}

function getPath(obj, dotted) {
  return dotted.split('.').reduce((acc, key) => (acc == null ? undefined : acc[key]), obj);
}

function resolveRefs(themeRoot, value, depth = 0) {
  if (typeof value !== 'string') return value;
  const match = value.match(/^\{(.+)\}$/);
  if (!match) return value;
  if (depth > 20) throw new Error(`token cycle: ${value}`);
  const next = getPath(themeRoot, match[1]);
  if (next == null) throw new Error(`unresolved token ${value}`);
  return resolveRefs(themeRoot, next, depth + 1);
}

function deepResolve(themeRoot, node) {
  if (Array.isArray(node)) return node.map((n) => deepResolve(themeRoot, n));
  if (node && typeof node === 'object') {
    const out = {};
    for (const [k, v] of Object.entries(node)) out[k] = deepResolve(themeRoot, v);
    return out;
  }
  return resolveRefs(themeRoot, node);
}

const THEME_ALIAS = {
  sky_planet_day: 'sky',
  star_planet_night: 'night',
  mint_planet_day: 'mint',
  sunrise_planet_day: 'sunrise',
};

/** Map nested semantic tokens → flat runtime keys used by Tsp* / Basic* themes. */
function toFlatRuntime(semantic) {
  const s = (p) => getPath(semantic, p);
  return {
    pageStart: s('background.page'),
    pageEnd: s('background.pageGradientEnd'),
    textPrimary: s('text.primary'),
    textSecondary: s('text.secondary'),
    textTertiary: s('text.tertiary'),
    surfaceRaised: s('background.surfaceRaised'),
    surfaceSubtle: s('background.surfaceSubtle'),
    borderDefault: s('border.default'),
    brandPrimary: s('brand.primary'),
    brandDark: s('brand.dark'),
    brandSubtle: s('brand.primarySubtle'),
    success: s('status.success'),
    successSubtle: s('status.successSubtle'),
    warning: s('status.warning'),
    selectedFill: s('background.selectedFill'),
    selectedBorder: s('border.selected'),
    emphasisFill: s('background.emphasisFill'),
    activeFill: s('background.activeFill'),
    danger: s('status.danger'),
    switchOffBg: s('control.switch.offBackground'),
    switchOffBorder: s('control.switch.offBorder'),
    switchOffText: s('control.switch.offText'),
    switchOnBg: s('control.switch.onBackground'),
    switchOnBorder: s('control.switch.onBorder'),
    switchOnText: s('control.switch.onText'),
    switchHandleBg: s('control.switch.handleBackground'),
    switchHandleBorder: s('control.switch.handleBorder'),
    switchHandleCheckedBorder: s('control.switch.handleCheckedBorder'),
    switchSpinner: s('control.switch.onText'),
  };
}

function buildStyleProfiles(styleRoot) {
  const sizeButton = getPath(styleRoot, 'size.controlHeight.buttonMedium') ?? 46;
  const raised = styleRoot.themes?.island_raised || {};
  const flat = styleRoot.themes?.island_flat || {};
  const cardShadow =
    getPath(styleRoot, 'shadow.cardIsland') ||
    '0 14px 34px rgba(49, 168, 255, 0.20), 0 2px 8px rgba(23, 58, 98, 0.06)';
  const cardShadowStr =
    typeof cardShadow === 'object'
      ? `0 ${cardShadow.y ?? 14}px ${cardShadow.blur ?? 34}px ${cardShadow.color || 'rgba(49, 168, 255, 0.20)'}`
      : String(cardShadow);

  return {
    island_raised: {
      buttonRaisedShadowEnabled: getPath(raised, 'islandStyle.rules.buttonRaisedShadowEnabled') !== false,
      shadowControlIslandLiftY: getPath(raised, 'shadow.controlIslandLift.y') ?? 6,
      shadowControlPressedY: getPath(raised, 'shadow.controlPressed.y') ?? 2,
      pressedDropY: getPath(raised, 'motion.interaction.pressedDropY') ?? 2,
      hoverLiftY: getPath(raised, 'motion.interaction.hoverLiftY') ?? -1,
      buttonFaceHeight: sizeButton,
      cardIslandShadow: cardShadowStr,
    },
    island_flat: {
      buttonRaisedShadowEnabled: false,
      shadowControlIslandLiftY: getPath(flat, 'shadow.controlIslandLift.y') ?? 0,
      shadowControlPressedY: getPath(flat, 'shadow.controlPressed.y') ?? 0,
      pressedDropY: getPath(flat, 'motion.interaction.pressedDropY') ?? 0,
      hoverLiftY: getPath(flat, 'motion.interaction.hoverLiftY') ?? 0,
      buttonFaceHeight: sizeButton,
      cardIslandShadow: 'none',
    },
  };
}

function buildMotion(styleRoot) {
  return {
    durationFast: getPath(styleRoot, 'motion.duration.fast') ?? 150,
    durationBase: getPath(styleRoot, 'motion.duration.base') ?? 220,
    durationSlow: getPath(styleRoot, 'motion.duration.slow') ?? 320,
    easeStandard: getPath(styleRoot, 'motion.easing.standard') ?? 'cubic-bezier(0.4,0,0.2,1)',
    easeOut: getPath(styleRoot, 'motion.easing.easeOut') ?? 'cubic-bezier(0,0,0.2,1)',
    respectPrefersReducedMotion: getPath(styleRoot, 'motion.rules.respectPrefersReducedMotion') !== false,
  };
}

function toCss(themes, profiles, motion) {
  const lines = ['/* GENERATED by tools/build-tokens.cjs — do not edit */', ''];
  for (const [name, theme] of Object.entries(themes)) {
    lines.push(`[data-planet-theme="${name}"] {`);
    for (const [k, v] of Object.entries(theme)) {
      const cssKey = k.replace(/[A-Z]/g, (m) => `-${m.toLowerCase()}`);
      lines.push(`  --bc-${cssKey}: ${v};`);
    }
    lines.push('}');
    lines.push('');
  }
  for (const [name, profile] of Object.entries(profiles)) {
    lines.push(`[data-planet-style="${name}"] {`);
    lines.push(`  --bc-button-raised-shadow-display: ${profile.buttonRaisedShadowEnabled ? 'block' : 'none'};`);
    lines.push(`  --bc-shadow-control-island-lift-y: ${profile.shadowControlIslandLiftY}px;`);
    lines.push(`  --bc-pressed-drop-y: ${profile.pressedDropY}px;`);
    lines.push(`  --bc-hover-lift-y: ${profile.hoverLiftY}px;`);
    lines.push(`  --bc-button-face-height: ${profile.buttonFaceHeight}px;`);
    lines.push(`  --bc-card-island-shadow: ${profile.cardIslandShadow};`);
    lines.push('}');
    lines.push('');
  }
  lines.push(':root {');
  lines.push(`  --bc-motion-fast: ${motion.durationFast}ms;`);
  lines.push(`  --bc-motion-base: ${motion.durationBase}ms;`);
  lines.push(`  --bc-motion-slow: ${motion.durationSlow}ms;`);
  lines.push(`  --bc-ease-std: ${motion.easeStandard};`);
  lines.push(`  --bc-ease-out: ${motion.easeOut};`);
  lines.push('}');
  lines.push('');
  if (motion.respectPrefersReducedMotion) {
    lines.push('@media (prefers-reduced-motion: reduce) {');
    lines.push('  :root {');
    lines.push('    --bc-motion-fast: 0ms;');
    lines.push('    --bc-motion-base: 0ms;');
    lines.push('    --bc-motion-slow: 0ms;');
    lines.push('  }');
    lines.push('}');
    lines.push('');
  }
  return lines.join('\n');
}

function toJsModule(payload) {
  return `/* GENERATED by tools/build-tokens.cjs — do not edit */
export const starPlanetThemes = ${JSON.stringify(payload.themes, null, 2)};

export const starPlanetTheme = starPlanetThemes.sky;

export const starPlanetStyleProfiles = ${JSON.stringify(payload.styleProfiles, null, 2)};

export const planetMotion = ${JSON.stringify(payload.motion, null, 2)};
`;
}

function main() {
  const colorRoot = readJson('design/tokens/color_token.json');
  const styleRoot = readJson('design/tokens/style_token.json');
  const themes = {};

  for (const [id, alias] of Object.entries(THEME_ALIAS)) {
    const theme = colorRoot.themes?.[id];
    if (!theme) throw new Error(`missing color theme ${id}`);
    const resolved = deepResolve(theme, theme);
    themes[alias] = toFlatRuntime(resolved.semantic);
    for (const [k, v] of Object.entries(themes[alias])) {
      if (v == null || typeof v !== 'string') {
        throw new Error(`theme ${alias}.${k} unresolved (${v})`);
      }
    }
  }

  const styleProfiles = buildStyleProfiles(styleRoot);
  const motion = buildMotion(styleRoot);
  const payload = {
    meta: {
      generatedAt: new Date().toISOString(),
      source: ['design/tokens/color_token.json', 'design/tokens/style_token.json'],
    },
    themes,
    styleProfiles,
    motion,
  };

  write('design/tokens/generated/runtime-themes.json', `${JSON.stringify(payload, null, 2)}\n`);
  write('design/tokens/generated/tokens.css', toCss(themes, styleProfiles, motion));

  const js = toJsModule(payload);
  const targets = [
    'react-web/library/src/theme.generated.js',
    'vue-web/library/src/theme.generated.js',
    'react-native/library/src/starPlanet/theme.generated.js',
  ];
  for (const t of targets) write(t, js);

  // Android continues to consume full token JSON (reference resolver in Java).
  fs.copyFileSync(
    path.join(root, 'design/tokens/color_token.json'),
    path.join(root, 'android/library/src/main/assets/theme/color_token.json'),
  );
  fs.copyFileSync(
    path.join(root, 'design/tokens/style_token.json'),
    path.join(root, 'android/library/src/main/assets/theme/style_token.json'),
  );
  console.log('synced android assets theme/*.json');

  // Optional Style Dictionary export (v4 ESM/CJS shapes vary).
  try {
    const sdPath = require.resolve('style-dictionary', {
      paths: [path.join(root, 'tools/token-build')],
    });
    let StyleDictionary = require(sdPath);
    StyleDictionary = StyleDictionary.default || StyleDictionary.StyleDictionary || StyleDictionary;
    const sdTokens = {};
    for (const [name, theme] of Object.entries(themes)) {
      for (const [k, v] of Object.entries(theme)) {
        sdTokens[`color.${name}.${k}`] = { value: v, type: 'color' };
      }
    }
    const sdSource = path.join(root, 'design/tokens/generated/sd-flat.json');
    fs.writeFileSync(sdSource, `${JSON.stringify(sdTokens, null, 2)}\n`);
    if (typeof StyleDictionary === 'function') {
      const sd = new StyleDictionary({
        tokens: sdTokens,
        platforms: {
          css: {
            transformGroup: 'css',
            buildPath: `${path.join(root, 'design/tokens/generated/sd')}/`,
            files: [{ destination: 'variables.css', format: 'css/variables' }],
          },
        },
      });
      const ready = sd.hasInitialized ? Promise.resolve() : Promise.resolve(sd);
      ready
        .then(() => (typeof sd.buildAllPlatforms === 'function' ? sd.buildAllPlatforms() : null))
        .then(() => console.log('style-dictionary css platform built'))
        .catch((err) => console.warn('style-dictionary optional build skipped:', err.message));
    } else {
      console.warn('style-dictionary optional build skipped: unexpected export shape');
    }
  } catch (err) {
    console.warn('style-dictionary optional build skipped:', err.message);
  }

  console.log('token build complete');
  try {
    require('child_process').execFileSync(process.execPath, [path.join(__dirname, 'build-figma-manifest.cjs')], {
      cwd: root,
      stdio: 'inherit',
    });
  } catch (err) {
    console.warn('figma manifest build skipped:', err.message);
  }
}

main();
