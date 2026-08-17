import { readFileSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';
import { describe, expect, it } from 'vitest';
import React from 'react';
import renderer, { act } from 'react-test-renderer';
import { TspSwitch } from '../src/starPlanet/components/TspSwitch.js';

const sourcePath = join(dirname(fileURLToPath(import.meta.url)), '../src/starPlanet/components/TspSwitch.js');

describe('TC-SWITCH flat structure', () => {
  it('TC-SWITCH-01 does not import RN Switch', () => {
    const source = readFileSync(sourcePath, 'utf8');
    expect(source).not.toMatch(/import\s*\{[^}]*\bSwitch\b[^}]*\}\s*from\s*['"]react-native['"]/);
  });

  it('TC-SWITCH-02 renders label and track without inner ON/OFF', () => {
    let tree;
    act(() => {
      tree = renderer.create(
        React.createElement(TspSwitch, { text: 'Switch', checked: false })
      );
    });
    const root = tree.root;
    const texts = root
      .findAllByType('Text')
      .map(node => node.props.children)
      .flat();
    expect(texts).toContain('Switch');
    expect(texts).not.toContain('OFF');
    expect(texts).not.toContain('ON');
    expect(root.findAllByType('Pressable').length).toBeGreaterThan(0);
    expect(root.findAllByType('View').length).toBeGreaterThan(0);
  });

  it('TC-SWITCH-03 uses flat md sizes and shows spinner when loading', () => {
    const source = readFileSync(sourcePath, 'utf8');
    expect(source).toMatch(/width:\s*52/);
    expect(source).toMatch(/height:\s*28/);
    expect(source).toMatch(/handle:\s*24/);
    expect(source).toMatch(/travel:\s*24/);
    expect(source).toMatch(/width:\s*40/);
    expect(source).toMatch(/height:\s*22/);
    expect(source).toMatch(/borderWidth:\s*0/);

    let tree;
    act(() => {
      tree = renderer.create(
        React.createElement(TspSwitch, { text: 'Loading', checked: true, loading: true })
      );
    });
    const texts = tree.root
      .findAllByType('Text')
      .map(node => node.props.children)
      .flat();
    expect(texts).toContain('Loading');
    expect(texts).not.toContain('ON');
    expect(tree.root.findAllByType('View').length).toBeGreaterThan(0);
  });
});
