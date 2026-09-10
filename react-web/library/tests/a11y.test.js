/**
 * Accessibility smoke tests (axe-core) for primary interactive controls.
 * Spec: docs/A11Y.md
 */
import React from 'react';
import { describe, it, expect } from 'vitest';
import { render } from '@testing-library/react';
import axe from 'axe-core';
import {
  TspButton,
  TspSwitch,
  TspProgress,
  TspTabs,
  TspTopBar,
  TspModal,
  TspAlert,
  TspCheckbox,
  TspSearchBar,
  starPlanetTheme,
} from '../src/index.js';

async function expectNoSeriousViolations(container) {
  const results = await axe.run(container, {
    runOnly: {
      type: 'tag',
      values: ['wcag2a', 'wcag2aa'],
    },
  });
  const serious = results.violations.filter((v) =>
    v.impact === 'critical' || v.impact === 'serious'
  );
  expect(serious, JSON.stringify(serious, null, 2)).toEqual([]);
}

describe('a11y baseline (axe)', () => {
  it('TspButton primary', async () => {
    const { container } = render(
      React.createElement(TspButton, { text: 'Continue', variant: 'primary', theme: starPlanetTheme })
    );
    await expectNoSeriousViolations(container);
  });

  it('TspSwitch checked', async () => {
    const { container } = render(
      React.createElement(TspSwitch, {
        text: 'Reminders',
        checked: true,
        onChange: () => {},
        theme: starPlanetTheme,
      })
    );
    await expectNoSeriousViolations(container);
  });

  it('TspProgress', async () => {
    const { container } = render(
      React.createElement(TspProgress, { progress: 40, theme: starPlanetTheme })
    );
    await expectNoSeriousViolations(container);
  });

  it('TspTabs', async () => {
    const { container } = render(
      React.createElement(TspTabs, {
        tabs: ['A', 'B'],
        selectedIndex: 0,
        onSelect: () => {},
        theme: starPlanetTheme,
      })
    );
    await expectNoSeriousViolations(container);
  });

  it('TspTopBar with back', async () => {
    const { container } = render(
      React.createElement(TspTopBar, {
        title: 'Detail',
        showBack: true,
        onBack: () => {},
        theme: starPlanetTheme,
      })
    );
    await expectNoSeriousViolations(container);
  });

  it('TspAlert', async () => {
    const { container } = render(
      React.createElement(TspAlert, {
        title: 'Info',
        message: 'All good.',
        variant: 'info',
        theme: starPlanetTheme,
      })
    );
    await expectNoSeriousViolations(container);
  });

  it('TspModal confirm', async () => {
    const { container } = render(
      React.createElement(TspModal, {
        title: 'Confirm',
        message: 'Proceed?',
        confirmText: 'OK',
        cancelText: 'Cancel',
        onConfirm: () => {},
        onCancel: () => {},
        theme: starPlanetTheme,
      })
    );
    await expectNoSeriousViolations(container);
  });

  it('TspCheckbox checked', async () => {
    const { container } = render(
      React.createElement(TspCheckbox, {
        text: 'Agree',
        checked: true,
        onChange: () => {},
        theme: starPlanetTheme,
      })
    );
    await expectNoSeriousViolations(container);
  });

  it('TspSearchBar', async () => {
    const { container } = render(
      React.createElement(TspSearchBar, {
        value: 'sky',
        onChange: () => {},
        theme: starPlanetTheme,
      })
    );
    await expectNoSeriousViolations(container);
  });
});
