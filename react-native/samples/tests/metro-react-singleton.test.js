import fs from 'fs';
import path from 'path';
import { fileURLToPath } from 'url';
import { describe, expect, it } from 'vitest';

const __dirname = path.dirname(fileURLToPath(import.meta.url));

describe('TC-CRASH metro react singleton', () => {
  it('forces a single react resolution for sample node_modules', () => {
    const metroPath = path.resolve(__dirname, '../metro.config.js');
    const source = fs.readFileSync(metroPath, 'utf8');
    expect(source).toContain('disableHierarchicalLookup');
    expect(source).toContain('extraNodeModules');
    expect(source).toMatch(/react:\s*path\.resolve\(sampleModules,\s*'react'\)/);
    expect(source).toMatch(/'react-native':\s*path\.resolve\(sampleModules,\s*'react-native'\)/);
  });
});
