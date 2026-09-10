#!/usr/bin/env node
/**
 * Sync design/icons/planet-icons.json → platform icon catalogs.
 * Does not add Icon to component_contract (still extension/helper API).
 */
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');
const catalog = JSON.parse(
  fs.readFileSync(path.join(root, 'design/icons/planet-icons.json'), 'utf8')
);
const icons = catalog.icons;

function write(rel, text) {
  const full = path.join(root, rel);
  fs.mkdirSync(path.dirname(full), { recursive: true });
  fs.writeFileSync(full, text);
  console.log(`wrote ${rel}`);
}

const pathEntries = Object.entries(icons)
  .map(([name, spec]) => `  ${name}: '${spec.path.replace(/'/g, "\\'")}',`)
  .join('\n');

const glyphEntries = Object.entries(icons)
  .map(([name, spec]) => `  ${name}: '${spec.glyph.replace(/'/g, "\\'")}',`)
  .join('\n');

write(
  'react-web/library/src/icons/planetIcons.js',
  `/**
 * Named SVG path catalog (24×24, currentColor stroke).
 * Generated from design/icons/planet-icons.json — run: node tools/sync-icons.cjs
 */
export const PLANET_ICONS = {
${pathEntries}
};

export const PLANET_ICON_GLYPHS = {
${glyphEntries}
};

export const PLANET_ICON_NAMES = Object.keys(PLANET_ICONS);
`
);

write(
  'vue-web/library/src/icons/planetIcons.js',
  `/**
 * Named SVG path catalog (24×24, currentColor stroke).
 * Generated from design/icons/planet-icons.json — run: node tools/sync-icons.cjs
 */
export const PLANET_ICONS = {
${pathEntries}
};

export const PLANET_ICON_GLYPHS = {
${glyphEntries}
};

export const PLANET_ICON_NAMES = Object.keys(PLANET_ICONS);
`
);

write(
  'react-native/library/src/starPlanet/icons/planetIcons.js',
  `/**
 * Icon catalog for React Native (glyph fallback — no react-native-svg peer required).
 * Generated from design/icons/planet-icons.json — run: node tools/sync-icons.cjs
 */
export const PLANET_ICONS = {
${pathEntries}
};

export const PLANET_ICON_GLYPHS = {
${glyphEntries}
};

export const PLANET_ICON_NAMES = Object.keys(PLANET_ICON_GLYPHS);
`
);

const dartMap = Object.entries(icons)
  .map(
    ([name, spec]) =>
      `  '${name}': PlanetIconSpec(path: '${spec.path.replace(/'/g, "\\'")}', glyph: '${spec.glyph.replace(/'/g, "\\'")}'),`
  )
  .join('\n');

write(
  'flutter/library/lib/src/planet_icons.dart',
  `/// Planet icon catalog. Generated from design/icons/planet-icons.json.
class PlanetIconSpec {
  const PlanetIconSpec({required this.path, required this.glyph});
  final String path;
  final String glyph;
}

const Map<String, PlanetIconSpec> planetIcons = {
${dartMap}
};

List<String> get planetIconNames => planetIcons.keys.toList(growable: false);
`
);

const swiftCases = Object.entries(icons)
  .map(([name, spec]) => {
    const pathEsc = spec.path.replace(/\\/g, '\\\\').replace(/"/g, '\\"');
    const glyphEsc = spec.glyph.replace(/\\/g, '\\\\').replace(/"/g, '\\"');
    return `        case "${name}": return PlanetIconSpec(path: "${pathEsc}", glyph: "${glyphEsc}")`;
  })
  .join('\n');

write(
  'ios-swiftui/library/Sources/PlanetComponents/PlanetIcons.swift',
  `import Foundation

public struct PlanetIconSpec: Sendable {
    public let path: String
    public let glyph: String
    public init(path: String, glyph: String) {
        self.path = path
        self.glyph = glyph
    }
}

public enum PlanetIcons {
    public static let names: [String] = ["back", "check", "close", "chevron", "warning"]

    public static func spec(named name: String) -> PlanetIconSpec {
        switch name {
${swiftCases}
        default:
            return spec(named: "check")
        }
    }
}
`
);

const mpIcons = JSON.stringify(
  Object.fromEntries(
    Object.entries(icons).map(([k, v]) => [k, { path: v.path, glyph: v.glyph }])
  ),
  null,
  2
);

write(
  'miniprogram/library/theme/planet-icons.json',
  mpIcons + '\n'
);

const javaEntries = Object.entries(icons)
  .map(
    ([name, spec]) =>
      `        map.put("${name}", new Spec("${spec.path.replace(/\\/g, '\\\\').replace(/"/g, '\\"')}", "${spec.glyph.replace(/\\/g, '\\\\').replace(/"/g, '\\"')}"));`
  )
  .join('\n');

write(
  'android/library/src/main/java/com/techskillplanet/planetcomponents/icon/PlanetIcons.java',
  `package com.techskillplanet.planetcomponents.icon;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Planet icon catalog (glyph + SVG path). Synced from design/icons/planet-icons.json.
 */
public final class PlanetIcons {
    public static final class Spec {
        public final String path;
        public final String glyph;

        public Spec(String path, String glyph) {
            this.path = path;
            this.glyph = glyph;
        }
    }

    private static final Map<String, Spec> ICONS;

    static {
        Map<String, Spec> map = new HashMap<>();
${javaEntries}
        ICONS = Collections.unmodifiableMap(map);
    }

    private PlanetIcons() {
    }

    public static Spec get(String name) {
        Spec spec = ICONS.get(name);
        return spec != null ? spec : ICONS.get("check");
    }

    public static Set<String> names() {
        return ICONS.keySet();
    }
}
`
);

console.log(`Synced ${Object.keys(icons).length} icons to platforms`);
