#!/usr/bin/env node
/**
 * Generate docs/API_REFERENCE.md from component_contract.json.
 */
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');
const contract = JSON.parse(
  fs.readFileSync(path.join(root, 'component_contract.json'), 'utf8')
);

function kebab(name) {
  return name
    .replace(/([a-z0-9])([A-Z])/g, '$1-$2')
    .replace(/([A-Z])([A-Z][a-z])/g, '$1-$2')
    .toLowerCase();
}

const lines = [];
lines.push('# API Reference (contract-driven)');
lines.push('');
lines.push(
  `Generated from \`component_contract.json\` (${contract.components.length} components). Do not hand-edit; run \`node tools/build-api-docs.cjs\`.`
);
lines.push('');
lines.push('## Theme tokens');
lines.push('');
lines.push((contract.theme?.tokens || []).map((t) => `\`${t}\``).join(', '));
lines.push('');
lines.push('## Naming');
lines.push('');
lines.push('| Contract | Web / RN / Flutter / iOS / Kuikly | Android | Mini Program |');
lines.push('| --- | --- | --- | --- |');
for (const c of contract.components) {
  const n = c.name;
  lines.push(
    `| ${n} | \`Tsp${n}\` | \`Basic${n}\` / \`Basic${n}View\` | \`bc-${kebab(n)}\` |`
  );
}
lines.push('');
lines.push('## Components');
lines.push('');
for (const c of contract.components) {
  lines.push(`### ${c.name}`);
  lines.push('');
  lines.push(`- **Variants:** ${(c.variants || []).map((v) => `\`${v}\``).join(', ') || '—'}`);
  lines.push(`- **Props:** ${(c.props || []).map((p) => `\`${p}\``).join(', ') || '—'}`);
  lines.push('');
}

const out = path.join(root, 'docs/API_REFERENCE.md');
fs.writeFileSync(out, lines.join('\n') + '\n');
console.log(`wrote docs/API_REFERENCE.md (${contract.components.length} components)`);
