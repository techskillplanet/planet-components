/**
 * Sample smoke tests + responsive layout contract for React Web.
 */
import React from 'react';
import { describe, it, expect, beforeEach, afterEach } from 'vitest';
import { render, screen, fireEvent, act } from '@testing-library/react';
import { readFileSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import { dirname, join } from 'node:path';
import { BasicControlsSample } from '../../samples/BasicControlsSample.js';
import { componentDocs } from '../../shared/componentDocs.js';

const __dirname = dirname(fileURLToPath(import.meta.url));
const styles = readFileSync(join(__dirname, '../src/styles.css'), 'utf8');

function mockMatchMedia(matchesDesktop) {
  window.matchMedia = (query) => ({
    matches: query.includes('min-width: 1024px') ? matchesDesktop : false,
    media: query,
    onchange: null,
    addListener: () => {},
    removeListener: () => {},
    addEventListener: () => {},
    removeEventListener: () => {},
    dispatchEvent: () => false
  });
}

describe('Responsive stylesheet contract', () => {
  it('covers phone, tablet and desktop breakpoints', () => {
    expect(styles).toContain('@media (max-width: 560px)');
    expect(styles).toContain('@media (min-width: 561px) and (max-width: 1023px)');
    expect(styles).toContain('@media (min-width: 1024px)');
    expect(styles).toContain('.bc-sample--force-mobile');
    expect(styles).toContain('.bc-sample--force-desktop');
  });
});

describe('BasicControlsSample smoke', () => {
  beforeEach(() => {
    window.location.hash = '';
    mockMatchMedia(false);
  });

  afterEach(() => {
    window.location.hash = '';
  });

  it('mounts home catalog without crashing', () => {
    const { container } = render(React.createElement(BasicControlsSample));
    expect(container.querySelector('.bc-sample')).toBeTruthy();
    expect(screen.getByText('基础组件')).toBeTruthy();
    expect(container.querySelector('[data-platform="mobile"]')).toBeTruthy();
  });

  it('opens every component detail page from catalog', () => {
    const { container } = render(React.createElement(BasicControlsSample));
    for (const doc of componentDocs) {
      const item = Array.from(container.querySelectorAll('.bc-list-item')).find((el) => el.textContent.includes(doc.component));
      expect(item, `missing list item for ${doc.component}`).toBeTruthy();
      fireEvent.click(item);
      expect(container.querySelector('.bc-doc-page')).toBeTruthy();
      expect(container.querySelector('.bc-doc-title').textContent).toBe(doc.component);
      // Doc page TopBar is first; some previews (e.g. TopBar) also render a Back control.
      fireEvent.click(container.querySelector('.bc-top-bar [aria-label="Back"]'));
    }
  });

  it('can force desktop layout from settings', () => {
    const { container } = render(React.createElement(BasicControlsSample));
    const settingsTab = Array.from(container.querySelectorAll('.bc-bottom-tab__item')).find((el) => el.textContent.includes('设置'));
    fireEvent.click(settingsTab);
    const platformSelect = container.querySelector('.bc-select');
    fireEvent.click(platformSelect);
    const desktopOption = Array.from(container.querySelectorAll('.bc-option-sheet__option')).find((el) => el.textContent.includes('桌面'));
    fireEvent.click(desktopOption);
    expect(container.querySelector('.bc-sample--force-desktop')).toBeTruthy();
    expect(container.querySelector('[data-platform="desktop"]')).toBeTruthy();
  });

  it('auto detects desktop viewport', () => {
    mockMatchMedia(true);
    const { container } = render(React.createElement(BasicControlsSample));
    expect(container.querySelector('.bc-sample--force-desktop')).toBeTruthy();
  });
});
