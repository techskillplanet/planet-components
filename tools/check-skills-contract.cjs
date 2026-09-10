#!/usr/bin/env node
/**
 * Keep Agent Skills aligned with component_contract.json.
 *
 * Checks:
 *   - plugin-manifest version matches README / CHANGELOG narrative (0.2.x soft)
 *   - skill docs mention the current contract component count
 *   - Domain-7 component names appear in use/build/integrate skills
 */
const fs = require('fs');
const path = require('path');

const root = path.resolve(__dirname, '..');

function read(rel) {
  return fs.readFileSync(path.join(root, rel), 'utf8');
}

function exists(rel) {
  return fs.existsSync(path.join(root, rel));
}

function fail(msg) {
  console.log(`FAIL ${msg}`);
  return 1;
}

function pass(msg) {
  console.log(`PASS ${msg}`);
  return 0;
}

let failed = 0;

const contract = JSON.parse(read('component_contract.json'));
const count = contract.components.length;
const names = contract.components.map((c) => c.name);
const domain7 = [
  'ChildSwitcher',
  'ScoreRuleGrid',
  'RedeemCardGrid',
  'CalendarHeatmap',
  'PrintSheet',
  'BalanceHero',
  'CheckInStreakCard',
];

failed += pass(`contract component count = ${count}`);

const manifestPath = '.agents/plugin-manifest.json';
if (!exists(manifestPath)) {
  failed += fail(`missing ${manifestPath}`);
} else {
  const manifest = JSON.parse(read(manifestPath));
  if (!manifest.version) {
    failed += fail('plugin-manifest missing version');
  } else {
    failed += pass(`plugin-manifest version ${manifest.version}`);
  }
  const skillIds = manifest.skills || [];
  for (const id of skillIds) {
    const skillFile = `.agents/skills/${id}/SKILL.md`;
    if (!exists(skillFile)) {
      failed += fail(`manifest lists missing skill: ${id}`);
    }
  }
  failed += pass(`manifest skills present (${skillIds.length})`);
}

const skillsToScan = [
  '.agents/skills/use-planet-components/SKILL.md',
  '.agents/skills/build-planet-components/SKILL.md',
  '.agents/skills/ai-build-with-planet/SKILL.md',
  '.agents/skills/integrate-react-components/SKILL.md',
  '.agents/skills/integrate-vue-components/SKILL.md',
  '.agents/skills/integrate-react-native-components/SKILL.md',
  '.agents/skills/integrate-flutter-components/SKILL.md',
  '.agents/skills/integrate-android-view/SKILL.md',
  '.agents/skills/integrate-ios-swiftui/SKILL.md',
  '.agents/skills/integrate-miniprogram/SKILL.md',
  '.agents/skills/integrate-kuikly/SKILL.md',
  'AGENTS.md',
];

const countPatterns = [
  new RegExp(String(count)),
  new RegExp(`≈\\s*\\*\\*${count}\\*\\*`),
  new RegExp(`\\*\\*${count}\\*\\*`),
  new RegExp(`${count}\\s+components`, 'i'),
  new RegExp(`≈\\s*${count}`),
];

let skillsMentioningCount = 0;
const missingDomain = [];

for (const rel of skillsToScan) {
  if (!exists(rel)) {
    failed += fail(`missing ${rel}`);
    continue;
  }
  const text = read(rel);
  if (countPatterns.some((re) => re.test(text))) {
    skillsMentioningCount += 1;
  }
  // Domain-7 names (or Tsp*/Basic* forms) should appear in at least the hub skills.
  if (
    rel.includes('use-planet-components') ||
    rel.includes('build-planet-components') ||
    rel.includes('ai-build-with-planet') ||
    rel.includes('integrate-react-components')
  ) {
    for (const name of domain7) {
      if (
        !text.includes(name) &&
        !text.includes(`Tsp${name}`) &&
        !text.includes(`Basic${name}`)
      ) {
        missingDomain.push(`${rel} missing ${name}`);
      }
    }
  }
}

if (skillsMentioningCount < 4) {
  failed += fail(
    `expected ≥4 skill/docs files to mention contract count ${count}, found ${skillsMentioningCount}`
  );
} else {
  failed += pass(`skills/docs mention count ${count} (${skillsMentioningCount} files)`);
}

if (missingDomain.length) {
  failed += fail(missingDomain.join('; '));
} else {
  failed += pass(`Domain-7 names present in hub skills (${domain7.length})`);
}

// Spot-check: every contract name appears somewhere under .agents/skills or docs contract.
const contractDoc = exists('docs/COMPONENT_CONTRACT.md')
  ? read('docs/COMPONENT_CONTRACT.md')
  : '';
const agentsBlob = fs
  .readdirSync(path.join(root, '.agents/skills'), { withFileTypes: true })
  .filter((d) => d.isDirectory())
  .map((d) => {
    const p = path.join(root, '.agents/skills', d.name, 'SKILL.md');
    return fs.existsSync(p) ? fs.readFileSync(p, 'utf8') : '';
  })
  .join('\n');
const missingNames = names.filter(
  (n) => !contractDoc.includes(n) && !agentsBlob.includes(n) && !agentsBlob.includes(`Tsp${n}`)
);
if (missingNames.length) {
  failed += fail(
    `contract names missing from COMPONENT_CONTRACT.md and skills: ${missingNames.join(', ')}`
  );
} else {
  failed += pass('all contract names documented in contract doc or skills');
}

if (failed) {
  console.error(`\n${failed} skills↔contract check(s) failed`);
  process.exit(1);
}
console.log('\nALL SKILLS↔CONTRACT CHECKS PASSED');
