/**
 * Sample smoke tests + responsive layout contract for Vue Web.
 */
import { describe, it, expect, beforeEach, afterEach } from 'vitest';
import { mount, flushPromises } from '@vue/test-utils';
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
    const wrapper = mount(BasicControlsSample);
    expect(wrapper.find('.bc-sample').exists()).toBe(true);
    expect(wrapper.text()).toContain('基础组件');
    expect(wrapper.find('[data-platform="mobile"]').exists()).toBe(true);
  });

  it('opens every component detail page from catalog', async () => {
    const wrapper = mount(BasicControlsSample);
    for (const doc of componentDocs) {
      const item = wrapper.findAll('.bc-list-item').find((el) => el.text().includes(doc.component));
      expect(item, `missing list item for ${doc.component}`).toBeTruthy();
      await item.trigger('click');
      await flushPromises();
      expect(wrapper.find('.bc-doc-page').exists()).toBe(true);
      expect(wrapper.find('.bc-doc-title').text()).toBe(doc.component);
      await wrapper.find('[aria-label="Back"]').trigger('click');
      await flushPromises();
    }
  });

  it('can force desktop layout from settings', async () => {
    const wrapper = mount(BasicControlsSample);
    const settingsTab = wrapper.findAll('.bc-bottom-tab__item').find((el) => el.text().includes('设置'));
    await settingsTab.trigger('click');
    await wrapper.find('.bc-select').trigger('click');
    const desktopOption = wrapper.findAll('.bc-option-sheet__option').find((el) => el.text().includes('桌面'));
    await desktopOption.trigger('click');
    expect(wrapper.find('.bc-sample--force-desktop').exists()).toBe(true);
    expect(wrapper.find('[data-platform="desktop"]').exists()).toBe(true);
  });

  it('auto detects desktop viewport', () => {
    mockMatchMedia(true);
    const wrapper = mount(BasicControlsSample);
    expect(wrapper.find('.bc-sample--force-desktop').exists()).toBe(true);
  });
});
