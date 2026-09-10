/**
 * Visual / DOM contract smoke — className + structure snapshots for core controls.
 * Catches accidental class or markup regressions without a pixel screenshot service.
 */
import React from 'react';
import { describe, it, expect } from 'vitest';
import { render } from '@testing-library/react';
import {
  TspButton,
  TspSwitch,
  TspProgress,
  TspTabs,
  TspTopBar,
  TspAlert,
  TspCard,
  TspIcon,
  starPlanetTheme,
} from '../src/index.js';

function snapshotTree(node) {
  if (!node || node.nodeType !== 1) return null;
  const kids = [...node.children].map(snapshotTree).filter(Boolean);
  return {
    tag: node.tagName.toLowerCase(),
    className: node.className || undefined,
    role: node.getAttribute('role') || undefined,
    aria: {
      label: node.getAttribute('aria-label') || undefined,
      busy: node.getAttribute('aria-busy') || undefined,
      checked: node.getAttribute('aria-checked') || undefined,
    },
    kids: kids.length ? kids : undefined,
  };
}

describe('visual DOM smoke', () => {
  it('TspButton primary loading structure', () => {
    const { container } = render(
      React.createElement(TspButton, {
        text: 'Go',
        variant: 'primary',
        loading: true,
        theme: starPlanetTheme,
      })
    );
    expect(snapshotTree(container.firstElementChild)).toMatchSnapshot();
  });

  it('TspSwitch checked structure', () => {
    const { container } = render(
      React.createElement(TspSwitch, {
        text: 'On',
        checked: true,
        onChange: () => {},
        theme: starPlanetTheme,
      })
    );
    expect(snapshotTree(container.firstElementChild)).toMatchSnapshot();
  });

  it('TspProgress labeled structure', () => {
    const { container } = render(
      React.createElement(TspProgress, {
        progress: 40,
        theme: starPlanetTheme,
      })
    );
    expect(snapshotTree(container.firstElementChild)).toMatchSnapshot();
  });

  it('TspTabs selected structure', () => {
    const { container } = render(
      React.createElement(TspTabs, {
        tabs: ['A', 'B'],
        selectedIndex: 1,
        onSelect: () => {},
        theme: starPlanetTheme,
      })
    );
    expect(snapshotTree(container.firstElementChild)).toMatchSnapshot();
  });

  it('TspTopBar structure', () => {
    const { container } = render(
      React.createElement(TspTopBar, {
        title: 'Planet',
        showBack: true,
        theme: starPlanetTheme,
      })
    );
    expect(snapshotTree(container.firstElementChild)).toMatchSnapshot();
  });

  it('TspAlert + TspCard nested structure', () => {
    const { container } = render(
      React.createElement(
        TspCard,
        { theme: starPlanetTheme },
        React.createElement(TspAlert, {
          title: 'Heads up',
          message: 'Sky clear',
          variant: 'info',
          theme: starPlanetTheme,
        })
      )
    );
    expect(snapshotTree(container.firstElementChild)).toMatchSnapshot();
  });

  it('TspIcon check structure', () => {
    const { container } = render(
      React.createElement(TspIcon, {
        name: 'check',
        label: 'Done',
        theme: starPlanetTheme,
      })
    );
    expect(snapshotTree(container.firstElementChild)).toMatchSnapshot();
  });
});
