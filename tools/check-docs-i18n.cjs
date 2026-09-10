#!/usr/bin/env node
/**
 * Ensures component doc descriptions + sample UI locale keys stay fully covered.
 */
const fs = require('fs');
const path = require('path');
const { pathToFileURL } = require('url');

const root = path.resolve(__dirname, '..');

async function load(rel) {
  return import(pathToFileURL(path.join(root, rel)).href);
}

async function main() {
  const { componentDocs, componentCategories } = await load('react-web/shared/componentDocs.js');
  const { docDescriptions, categoryLabels, localizeDocs } = await load('react-web/shared/componentDocs.i18n.js');
  const { sampleLocales, SAMPLE_LOCALE_KEYS } = await load('react-web/shared/sampleLocales.js');

  const names = componentDocs.map((d) => d.name);
  const errors = [];

  for (const [locale, map] of Object.entries(docDescriptions)) {
    for (const name of names) {
      if (!map[name]) errors.push(`missing description ${locale}/${name}`);
    }
  }

  for (const [locale, map] of Object.entries(categoryLabels)) {
    for (const cat of componentCategories) {
      if (!map[cat]) errors.push(`missing category ${locale}/${cat}`);
    }
  }

  for (const [locale, map] of Object.entries(sampleLocales)) {
    for (const key of SAMPLE_LOCALE_KEYS) {
      if (map[key] == null || map[key] === '') errors.push(`missing sample key ${locale}/${key}`);
    }
  }

  // Smoke: zh-CN StickyFooter must not fall back to English source
  const zh = localizeDocs(componentDocs, componentCategories, 'zh-CN');
  const sticky = zh.localizedDocs.find((d) => d.name === 'StickyFooter');
  if (!sticky?.categoryLabel || sticky.categoryLabel === 'Navigation') {
    errors.push('zh-CN StickyFooter categoryLabel not localized');
  }
  if (!sticky?.description || sticky.description.startsWith('Fixed bottom')) {
    errors.push('zh-CN StickyFooter description not localized');
  }

  if (errors.length) {
    console.error(`check-docs-i18n: ${errors.length} issue(s)`);
    for (const e of errors.slice(0, 40)) console.error(' -', e);
    if (errors.length > 40) console.error(` ... and ${errors.length - 40} more`);
    process.exit(1);
  }

  console.log(`check-docs-i18n: ok (${names.length} components, ${Object.keys(sampleLocales).length} locales, ${SAMPLE_LOCALE_KEYS.length} sample keys)`);
}

main().catch((err) => {
  console.error(err);
  process.exit(1);
});
