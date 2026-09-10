#!/usr/bin/env node
/**
 * Scaffold a new contract component stub across stacks.
 *
 * Usage:
 *   node tools/scaffold-component.cjs ComponentName
 *
 * Creates empty/stub files where missing. Does not overwrite existing files.
 * After scaffolding: implement each stub, add samples, update component_contract.json.
 */
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');
const name = process.argv[2];
if (!name || !/^[A-Z][A-Za-z0-9]+$/.test(name)) {
  console.error('Usage: node tools/scaffold-component.cjs ComponentName');
  process.exit(1);
}

function kebab(n) {
  return n.replace(/([a-z0-9])([A-Z])/g, '$1-$2').replace(/([A-Z])([A-Z][a-z])/g, '$1-$2').toLowerCase();
}
function snake(n) {
  return n.replace(/([a-z0-9])([A-Z])/g, '$1_$2').replace(/([A-Z])([A-Z][a-z])/g, '$1_$2').toLowerCase();
}

function writeIfMissing(rel, contents) {
  const full = path.join(root, rel);
  if (fs.existsSync(full)) {
    console.log(`skip ${rel}`);
    return;
  }
  fs.mkdirSync(path.dirname(full), { recursive: true });
  fs.writeFileSync(full, contents);
  console.log(`create ${rel}`);
}

const kebabName = kebab(name);
const snakeName = snake(name);

writeIfMissing(
  `react-web/library/src/components/Tsp${name}.js`,
  `import { React, h, themed, starPlanetTheme } from './_shared.js';\n\nexport function Tsp${name}({ theme = starPlanetTheme, ...props }) {\n  return h('div', { className: 'bc-${kebabName}', style: themed(theme), ...props }, '${name}');\n}\n`
);

writeIfMissing(
  `vue-web/library/src/components/Tsp${name}.js`,
  `import { defineComponent } from 'vue';\nimport { h, themed, starPlanetTheme } from './_shared.js';\n\nexport const Tsp${name} = defineComponent({\n  name: 'Tsp${name}',\n  props: { theme: { type: Object, default: () => starPlanetTheme } },\n  setup(props) {\n    return () => h('div', { class: 'bc-${kebabName}', style: themed(props.theme) }, '${name}');\n  }\n});\n`
);

writeIfMissing(
  `react-native/library/src/starPlanet/components/Tsp${name}.js`,
  `import React from 'react';\nimport { Text, View } from 'react-native';\nimport { starPlanetTheme } from '../theme.js';\n\nexport function Tsp${name}({ theme = starPlanetTheme, text = '${name}' }) {\n  return (\n    <View style={{ backgroundColor: theme.surfaceRaised, borderColor: theme.borderDefault, borderWidth: 1, borderRadius: 12, padding: 12 }}>\n      <Text style={{ color: theme.textPrimary }}>{text}</Text>\n    </View>\n  );\n}\n`
);

writeIfMissing(
  `flutter/library/lib/src/tsp_${snakeName}.dart`,
  `import 'package:flutter/material.dart';\nimport 'star_planet_theme.dart';\n\nclass Tsp${name} extends StatelessWidget {\n  const Tsp${name}({super.key, this.theme = StarPlanetTheme.sky, this.text = '${name}'});\n\n  final StarPlanetTheme theme;\n  final String text;\n\n  @override\n  Widget build(BuildContext context) {\n    return Text(text, style: TextStyle(color: theme.textPrimary));\n  }\n}\n`
);

writeIfMissing(
  `ios-swiftui/library/Sources/PlanetComponents/Tsp${name}.swift`,
  `import SwiftUI\n\npublic struct Tsp${name}: View {\n    public var text: String = "${name}"\n    public var theme: StarPlanetTheme = .sky\n\n    public init(text: String = "${name}", theme: StarPlanetTheme = .sky) {\n        self.text = text\n        self.theme = theme\n    }\n\n    public var body: some View {\n        Text(text).foregroundStyle(theme.textPrimary)\n    }\n}\n`
);

writeIfMissing(
  `android/library/src/main/java/com/techskillplanet/planetcomponents/widget/Basic${name}View.java`,
  `package com.techskillplanet.planetcomponents.widget;\n\nimport android.content.Context;\nimport android.util.AttributeSet;\nimport android.widget.FrameLayout;\nimport android.widget.TextView;\n\npublic class Basic${name}View extends FrameLayout {\n    public Basic${name}View(Context context) {\n        this(context, null);\n    }\n\n    public Basic${name}View(Context context, AttributeSet attrs) {\n        super(context, attrs);\n        TextView label = new TextView(context);\n        label.setText("${name}");\n        addView(label);\n    }\n}\n`
);

const miniDir = `miniprogram/library/components/bc-${kebabName}`;
writeIfMissing(
  `${miniDir}/bc-${kebabName}.json`,
  `{\n  "component": true\n}\n`
);
writeIfMissing(
  `${miniDir}/bc-${kebabName}.wxml`,
  `<view class="bc-${kebabName}">{{text}}</view>\n`
);
writeIfMissing(
  `${miniDir}/bc-${kebabName}.wxss`,
  `.bc-${kebabName} { padding: 12px; }\n`
);
writeIfMissing(
  `${miniDir}/bc-${kebabName}.js`,
  `Component({\n  properties: {\n    text: { type: String, value: '${name}' },\n    theme: { type: Object, value: {} }\n  }\n});\n`
);

writeIfMissing(
  `kuikly/library/shared/src/commonMain/kotlin/com/techskillplanet/phonics/controls/Tsp${name}.kt`,
  `package com.techskillplanet.phonics.controls\n\nimport androidx.compose.runtime.Composable\nimport com.techskillplanet.phonics.theme.PhonicsTheme\n\n@Composable\nfun Tsp${name}(\n    text: String = "${name}",\n    theme: com.techskillplanet.phonics.theme.PhonicsColors = PhonicsTheme.Sky,\n) {\n    // TODO: implement ${name}\n}\n`
);

console.log(`\nScaffolded ${name}. Next:`);
console.log(`  1. Add to component_contract.json`);
console.log(`  2. Export from barrel/index files`);
console.log(`  3. Implement stubs + samples`);
console.log(`  4. node tools/check-contract-inventory.cjs`);
