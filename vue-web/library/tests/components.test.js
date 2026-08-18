/**
 * Unit tests for TechSkillPlanet Vue Web components.
 * Uses vitest + @vue/test-utils + jsdom.
 */
import { describe, it, expect, vi } from 'vitest';
import { mount } from '@vue/test-utils';
import {
  TspButton, TspCard, TspAlert, TspBadge, TspChip, TspInput, TspSelect,
  TspOptionSheet, TspSwitch, TspProgress, TspTopBar, TspBottomTab, TspTabs,
  TspAmount, TspIconButton, TspKeyValueLabel, TspNotification, TspTextLink,
  TspStepper, TspStickyFooter, TspPinInput, TspListItem, TspEmpty, TspToast, TspModal,
  TspLoadingDialog, TspRefreshLayout,
  starPlanetTheme, starPlanetThemes, themeVars, resolveTheme, cx, clamp, optionText
} from '../src/index.js';

describe('cx (class name utility)', () => {
  it('joins truthy values', () => {
    expect(cx('a', 'b', 'c')).toBe('a b c');
  });
  it('filters falsy values', () => {
    expect(cx('a', false, null, undefined, '', 'b')).toBe('a b');
  });
});

describe('clamp', () => {
  it('clamps below min', () => { expect(clamp(-5, 0, 100)).toBe(0); });
  it('clamps above max', () => { expect(clamp(150, 0, 100)).toBe(100); });
  it('passes through valid value', () => { expect(clamp(50, 0, 100)).toBe(50); });
  it('handles NaN input', () => { expect(clamp('abc', 0, 100)).toBe(0); });
  it('handles string numbers', () => { expect(clamp('42', 0, 100)).toBe(42); });
});

describe('optionText', () => {
  it('returns string directly', () => { expect(optionText('hello')).toBe('hello'); });
  it('extracts title from object', () => { expect(optionText({ title: 'A', value: 'B' })).toBe('A'); });
  it('falls back to text', () => { expect(optionText({ text: 'X' })).toBe('X'); });
  it('falls back to label', () => { expect(optionText({ label: 'L' })).toBe('L'); });
  it('falls back to value', () => { expect(optionText({ value: 'V' })).toBe('V'); });
  it('handles null', () => { expect(optionText(null)).toBe(null); });
});

describe('themeVars', () => {
  it('returns CSS variable object', () => {
    const vars = themeVars(starPlanetTheme);
    expect(vars['--bc-brand-primary']).toBe('#31A8FF');
    expect(vars['--bc-page-start']).toBe('#DDF4FF');
  });
});

describe('resolveTheme', () => {
  it('returns merged color + style profile', () => {
    const t = resolveTheme('night', 'island_flat');
    expect(t.pageStart).toBe('#0F1A2E');
    expect(t.buttonRaisedShadowEnabled).toBe(false);
    expect(t.buttonHeight).toBe(46);
  });
  it('defaults to sky + island_raised', () => {
    const t = resolveTheme();
    expect(t.pageStart).toBe('#DDF4FF');
    expect(t.buttonRaisedShadowEnabled).toBe(true);
  });
});

describe('TspButton', () => {
  it('renders with text', () => {
    const wrapper = mount(TspButton, { props: { text: 'Click Me' } });
    expect(wrapper.find('button').text()).toContain('Click Me');
  });
  it('applies variant class', () => {
    const wrapper = mount(TspButton, { props: { text: 'X', variant: 'primary' } });
    expect(wrapper.find('.bc-button--primary').exists()).toBe(true);
  });
  it('applies disabled state', () => {
    const wrapper = mount(TspButton, { props: { text: 'X', disabled: true } });
    expect(wrapper.find('button').attributes('disabled')).toBeDefined();
  });
  it('fires tap event', async () => {
    const wrapper = mount(TspButton, { props: { text: 'Go' } });
    await wrapper.find('button').trigger('click');
    expect(wrapper.emitted('tap')).toHaveLength(1);
  });
  it('does not fire tap when disabled', async () => {
    const wrapper = mount(TspButton, { props: { text: 'Go', disabled: true } });
    await wrapper.find('button').trigger('click');
    expect(wrapper.emitted('tap')).toBeUndefined();
  });
  it('applies flat class when shadow disabled', () => {
    const theme = { ...starPlanetTheme, buttonRaisedShadowEnabled: false };
    const wrapper = mount(TspButton, { props: { text: 'X', theme } });
    expect(wrapper.find('.bc-button--flat').exists()).toBe(true);
  });
  it('renders slot over text', () => {
    const wrapper = mount(TspButton, { props: { text: 'No' }, slots: { default: 'Yes' } });
    expect(wrapper.find('button').text()).toContain('Yes');
  });
});

describe('TspCard', () => {
  it('renders children', () => {
    const wrapper = mount(TspCard, { slots: { default: 'Card content' } });
    expect(wrapper.find('.bc-card').text()).toContain('Card content');
  });
  it('applies selected class', () => {
    const wrapper = mount(TspCard, { props: { selected: true }, slots: { default: 'X' } });
    expect(wrapper.find('.bc-selected').exists()).toBe(true);
  });
  it('applies disabled class', () => {
    const wrapper = mount(TspCard, { props: { disabled: true }, slots: { default: 'X' } });
    expect(wrapper.find('.bc-disabled').exists()).toBe(true);
  });
});

describe('TspAlert', () => {
  it('renders title and message', () => {
    const wrapper = mount(TspAlert, { props: { title: 'Error', message: 'Something failed' } });
    const el = wrapper.find('[role="status"]');
    expect(el.text()).toContain('Error');
    expect(el.text()).toContain('Something failed');
  });
  it('applies variant class', () => {
    const wrapper = mount(TspAlert, { props: { title: 'W', variant: 'warning' } });
    expect(wrapper.find('.bc-alert--warning').exists()).toBe(true);
  });
  it('renders without title', () => {
    const wrapper = mount(TspAlert, { props: { message: 'Only message' } });
    expect(wrapper.find('[role="status"]').text()).toContain('Only message');
  });
});

describe('TspBadge', () => {
  it('renders text', () => {
    const wrapper = mount(TspBadge, { props: { text: '99+' } });
    expect(wrapper.find('.bc-badge').text()).toContain('99+');
  });
  it('applies variant class', () => {
    const wrapper = mount(TspBadge, { props: { text: 'X', variant: 'danger' } });
    expect(wrapper.find('.bc-badge--danger').exists()).toBe(true);
  });
});

describe('TspChip', () => {
  it('renders text and fires tap', async () => {
    const wrapper = mount(TspChip, { props: { text: 'Tag' } });
    await wrapper.find('button').trigger('click');
    expect(wrapper.emitted('tap')).toHaveLength(1);
  });
  it('applies selected class', () => {
    const wrapper = mount(TspChip, { props: { text: 'X', selected: true } });
    expect(wrapper.find('.bc-selected').exists()).toBe(true);
  });
  it('disabled chip does not fire', async () => {
    const wrapper = mount(TspChip, { props: { text: 'X', disabled: true } });
    await wrapper.find('button').trigger('click');
    expect(wrapper.emitted('tap')).toBeUndefined();
  });
});

describe('TspInput', () => {
  it('renders with value and placeholder', () => {
    const wrapper = mount(TspInput, { props: { value: 'hello', placeholder: 'Type...' } });
    const input = wrapper.find('input');
    expect(input.element.value).toBe('hello');
    expect(input.attributes('placeholder')).toBe('Type...');
  });
  it('emits change with new value', async () => {
    const wrapper = mount(TspInput, { props: { value: '', placeholder: 'X' } });
    await wrapper.find('input').setValue('test');
    expect(wrapper.emitted('change')[0]).toEqual(['test']);
  });
  it('applies error variant', () => {
    const wrapper = mount(TspInput, { props: { value: '', variant: 'error' } });
    expect(wrapper.find('.bc-input--error').exists()).toBe(true);
  });
});

describe('TspSwitch', () => {
  it('renders and toggles', async () => {
    const wrapper = mount(TspSwitch, { props: { checked: false } });
    await wrapper.find('button').trigger('click');
    expect(wrapper.emitted('change')[0]).toEqual([true]);
  });
  it('applies checked class', () => {
    const wrapper = mount(TspSwitch, { props: { checked: true } });
    expect(wrapper.find('.bc-checked').exists()).toBe(true);
  });
  it('renders flat hierarchy without inner ON/OFF text', () => {
    const wrapper = mount(TspSwitch, { props: { text: 'Label', checked: false } });
    expect(wrapper.find('.bc-switch__label').text()).toBe('Label');
    expect(wrapper.find('.bc-switch__control').exists()).toBe(true);
    expect(wrapper.find('.bc-switch__track').exists()).toBe(true);
    expect(wrapper.find('.bc-switch__inner-text').exists()).toBe(false);
    expect(wrapper.find('.bc-switch__thumb').exists()).toBe(true);
    expect(wrapper.find('.bc-switch--md').exists()).toBe(true);
  });
  it('applies sm flat sizes class', () => {
    const wrapper = mount(TspSwitch, { props: { checked: false, variant: 'sm' } });
    expect(wrapper.find('.bc-switch--sm').exists()).toBe(true);
  });
  it('shows spinner when loading', () => {
    const wrapper = mount(TspSwitch, { props: { checked: true, loading: true } });
    expect(wrapper.find('.bc-switch__spinner').exists()).toBe(true);
    expect(wrapper.find('.bc-loading').exists()).toBe(true);
  });
  it('disabled does not toggle', async () => {
    const wrapper = mount(TspSwitch, { props: { checked: false, disabled: true } });
    await wrapper.find('button').trigger('click');
    expect(wrapper.emitted('change')).toBeUndefined();
  });
  it('loading disables interaction', async () => {
    const wrapper = mount(TspSwitch, { props: { checked: true, loading: true } });
    await wrapper.find('button').trigger('click');
    expect(wrapper.emitted('change')).toBeUndefined();
  });
});

describe('TspProgress', () => {
  it('renders with correct aria values', () => {
    const wrapper = mount(TspProgress, { props: { progress: 65 } });
    const bar = wrapper.find('[role="progressbar"]');
    expect(bar.attributes('aria-valuenow')).toBe('65');
    expect(bar.attributes('aria-valuemin')).toBe('0');
    expect(bar.attributes('aria-valuemax')).toBe('100');
  });
  it('clamps values', () => {
    const wrapper = mount(TspProgress, { props: { progress: 150 } });
    expect(wrapper.find('[role="progressbar"]').attributes('aria-valuenow')).toBe('100');
  });
});

describe('TspTopBar', () => {
  it('renders title', () => {
    const wrapper = mount(TspTopBar, { props: { title: 'Home' } });
    expect(wrapper.find('.bc-top-bar__title').text()).toBe('Home');
  });
  it('shows back button when showBack=true', () => {
    const wrapper = mount(TspTopBar, { props: { title: 'X', showBack: true } });
    expect(wrapper.find('[aria-label="Back"]').exists()).toBe(true);
  });
  it('fires back event', async () => {
    const wrapper = mount(TspTopBar, { props: { title: 'X', showBack: true } });
    await wrapper.find('[aria-label="Back"]').trigger('click');
    expect(wrapper.emitted('back')).toHaveLength(1);
  });
});

describe('TspBottomTab', () => {
  const tabs = [{ key: 'a', title: 'Tab A', icon: '★' }, { key: 'b', title: 'Tab B' }];
  it('renders tabs', () => {
    const wrapper = mount(TspBottomTab, { props: { tabs, selectedKey: 'a' } });
    const buttons = wrapper.findAll('.bc-bottom-tab__item');
    expect(buttons).toHaveLength(2);
    expect(buttons[0].classes()).toContain('bc-selected');
  });
  it('fires select with key', async () => {
    const wrapper = mount(TspBottomTab, { props: { tabs, selectedKey: 'a' } });
    await wrapper.findAll('.bc-bottom-tab__item')[1].trigger('click');
    expect(wrapper.emitted('select')[0]).toEqual(['b', tabs[1]]);
  });
});

describe('TspTabs', () => {
  it('renders tab items', () => {
    const wrapper = mount(TspTabs, { props: { tabs: ['All', 'Done', 'Todo'], selectedIndex: 1 } });
    const tabEls = wrapper.findAll('[role="tab"]');
    expect(tabEls).toHaveLength(3);
    expect(tabEls[1].attributes('aria-selected')).toBe('true');
  });
  it('fires select', async () => {
    const wrapper = mount(TspTabs, { props: { tabs: ['A', 'B'], selectedIndex: 0 } });
    await wrapper.findAll('[role="tab"]')[1].trigger('click');
    expect(wrapper.emitted('select')[0]).toEqual([1, 'B']);
  });
});

describe('TspAmount', () => {
  it('renders symbol and value', () => {
    const wrapper = mount(TspAmount, { props: { symbol: '$', value: '99.50' } });
    expect(wrapper.find('.bc-amount__symbol').text()).toBe('$');
    expect(wrapper.find('.bc-amount__value').text()).toBe('99.50');
  });
  it('renders cycle suffix', () => {
    const wrapper = mount(TspAmount, { props: { value: '10', cycle: 'month' } });
    expect(wrapper.find('.bc-amount__cycle').text()).toBe('/month');
  });
  it('applies strikethrough', () => {
    const wrapper = mount(TspAmount, { props: { value: '10', strikeThrough: true } });
    expect(wrapper.find('.bc-strike').exists()).toBe(true);
  });
  it('supports symbolAfter', () => {
    const wrapper = mount(TspAmount, { props: { symbol: '€', value: '5', symbolAfter: true } });
    const spans = wrapper.findAll('.bc-amount > span');
    expect(spans[0].classes()).toContain('bc-amount__value');
  });
});

describe('TspIconButton', () => {
  it('renders icon content', () => {
    const wrapper = mount(TspIconButton, { props: { icon: '★' } });
    expect(wrapper.find('button').text()).toContain('★');
  });
  it('fires tap', async () => {
    const wrapper = mount(TspIconButton, { props: { icon: 'X' } });
    await wrapper.find('button').trigger('click');
    expect(wrapper.emitted('tap')).toHaveLength(1);
  });
});

describe('TspKeyValueLabel', () => {
  it('renders label and value', () => {
    const wrapper = mount(TspKeyValueLabel, { props: { label: 'Score', value: '100' } });
    expect(wrapper.find('.bc-key-value').text()).toContain('Score');
    expect(wrapper.find('.bc-key-value strong').text()).toBe('100');
  });
});

describe('TspNotification', () => {
  it('renders title and message', () => {
    const wrapper = mount(TspNotification, { props: { title: 'Heads up', message: 'Task due' } });
    expect(wrapper.find('.bc-notification').text()).toContain('Heads up');
    expect(wrapper.find('.bc-notification').text()).toContain('Task due');
  });
  it('applies alert variant', () => {
    const wrapper = mount(TspNotification, { props: { title: 'X', variant: 'alert' } });
    expect(wrapper.find('.bc-notification--alert').exists()).toBe(true);
  });
});

describe('TspTextLink', () => {
  it('renders text and fires tap', async () => {
    const wrapper = mount(TspTextLink, { props: { text: 'Learn more' } });
    await wrapper.find('button').trigger('click');
    expect(wrapper.emitted('tap')).toHaveLength(1);
  });
  it('applies inverse class', () => {
    const wrapper = mount(TspTextLink, { props: { text: 'X', inverse: true } });
    expect(wrapper.find('.bc-text-link--inverse').exists()).toBe(true);
  });
});

describe('TspStepper', () => {
  it('renders correct number of steps', () => {
    const wrapper = mount(TspStepper, { props: { stepCount: 4, currentStep: 2 } });
    expect(wrapper.findAll('.bc-stepper__dot')).toHaveLength(4);
  });
  it('marks completed steps', () => {
    const wrapper = mount(TspStepper, { props: { stepCount: 3, currentStep: 3 } });
    expect(wrapper.findAll('.bc-done').length).toBeGreaterThan(0);
  });
  it('clamps stepCount between 3-5', () => {
    const wrapper = mount(TspStepper, { props: { stepCount: 10, currentStep: 1 } });
    expect(wrapper.findAll('.bc-stepper__dot')).toHaveLength(5);
  });
});

describe('TspStickyFooter', () => {
  it('renders children', () => {
    const wrapper = mount(TspStickyFooter, { slots: { default: 'Footer' } });
    expect(wrapper.find('.bc-sticky-footer').text()).toContain('Footer');
  });
});

describe('TspPinInput', () => {
  it('renders correct number of cells', () => {
    const wrapper = mount(TspPinInput, { props: { cellCount: 6 } });
    expect(wrapper.findAll('.bc-pin-input__cell')).toHaveLength(6);
  });
  it('masks value in secure mode', () => {
    const wrapper = mount(TspPinInput, { props: { value: '12', secure: true } });
    const cells = wrapper.findAll('.bc-pin-input__cell');
    expect(cells[0].text()).toBe('•');
    expect(cells[1].text()).toBe('•');
    expect(cells[2].text()).toBe('');
  });
  it('shows digits when not secure', () => {
    const wrapper = mount(TspPinInput, { props: { value: '34' } });
    const cells = wrapper.findAll('.bc-pin-input__cell');
    expect(cells[0].text()).toBe('3');
    expect(cells[1].text()).toBe('4');
  });
});

describe('TspListItem', () => {
  it('renders title and message', () => {
    const wrapper = mount(TspListItem, { props: { title: 'Item', message: 'Desc' } });
    expect(wrapper.find('button').text()).toContain('Item');
    expect(wrapper.find('button').text()).toContain('Desc');
  });
  it('renders trailing', () => {
    const wrapper = mount(TspListItem, { props: { title: 'X', trailing: '›' } });
    expect(wrapper.find('.bc-list-item__trailing').text()).toBe('›');
  });
  it('fires tap', async () => {
    const wrapper = mount(TspListItem, { props: { title: 'X' } });
    await wrapper.find('button').trigger('click');
    expect(wrapper.emitted('tap')).toHaveLength(1);
  });
});

describe('TspEmpty', () => {
  it('renders title, message, and action', async () => {
    const wrapper = mount(TspEmpty, {
      props: { title: 'No data', message: 'Empty', actionText: 'Retry' }
    });
    expect(wrapper.find('.bc-empty').text()).toContain('No data');
    expect(wrapper.find('.bc-empty').text()).toContain('Empty');
    await wrapper.find('button').trigger('click');
    expect(wrapper.emitted('action')).toHaveLength(1);
  });
});

describe('TspToast', () => {
  it('renders message with variant', () => {
    const wrapper = mount(TspToast, { props: { message: 'Saved!' } });
    expect(wrapper.find('[role="status"]').text()).toContain('Saved!');
  });
  it('applies variant class', () => {
    const wrapper = mount(TspToast, { props: { message: 'X', variant: 'success' } });
    expect(wrapper.find('.bc-toast--success').exists()).toBe(true);
  });
});

describe('TspModal', () => {
  it('renders title and message', () => {
    const wrapper = mount(TspModal, { props: { title: 'Confirm', message: 'Are you sure?' } });
    expect(wrapper.find('[role="dialog"]').text()).toContain('Confirm');
    expect(wrapper.find('[role="dialog"]').text()).toContain('Are you sure?');
  });
  it('fires confirm and cancel', async () => {
    const wrapper = mount(TspModal, {
      props: { title: 'X', confirmText: 'Yes', cancelText: 'No' }
    });
    const buttons = wrapper.findAll('button');
    await buttons[0].trigger('click');
    await buttons[1].trigger('click');
    expect(wrapper.emitted('cancel')).toHaveLength(1);
    expect(wrapper.emitted('confirm')).toHaveLength(1);
  });
});

describe('TspOptionSheet', () => {
  it('returns null when not visible', () => {
    const wrapper = mount(TspOptionSheet, { props: { visible: false } });
    expect(wrapper.find('.bc-option-sheet').exists()).toBe(false);
  });
  it('renders options when visible', () => {
    const wrapper = mount(TspOptionSheet, {
      props: { visible: true, options: ['A', 'B', 'C'], selectedIndex: 1 }
    });
    expect(wrapper.find('[role="dialog"]').exists()).toBe(true);
    expect(wrapper.find('.bc-selected').text()).toContain('B');
  });
  it('fires select', async () => {
    const wrapper = mount(TspOptionSheet, {
      props: { visible: true, options: ['X', 'Y'] }
    });
    await wrapper.findAll('.bc-option-sheet__option')[0].trigger('click');
    expect(wrapper.emitted('select')[0]).toEqual([0, 'X']);
  });
});

describe('TspSelect', () => {
  it('renders selected option text', () => {
    const wrapper = mount(TspSelect, { props: { options: ['Apple', 'Banana'], selectedIndex: 0 } });
    expect(wrapper.find('.bc-select').text()).toContain('Apple');
  });
  it('opens option sheet on click', async () => {
    const wrapper = mount(TspSelect, { props: { options: ['A', 'B'] } });
    await wrapper.find('.bc-select').trigger('click');
    expect(wrapper.find('.bc-option-sheet').exists()).toBe(true);
  });
});

describe('TspLoadingDialog', () => {
  it('hides when not visible', () => {
    const wrapper = mount(TspLoadingDialog, { props: { visible: false } });
    expect(wrapper.find('.bc-loading-dialog').exists()).toBe(false);
  });
  it('renders message when visible', () => {
    const wrapper = mount(TspLoadingDialog, { props: { visible: true, message: '加载中...' } });
    expect(wrapper.find('[role="dialog"]').text()).toContain('加载中...');
  });
});

describe('TspRefreshLayout', () => {
  it('renders slot and emits refresh', async () => {
    const wrapper = mount(TspRefreshLayout, { slots: { default: 'rows' } });
    expect(wrapper.text()).toContain('rows');
    await wrapper.find('.bc-refresh-layout__refresh').trigger('click');
    expect(wrapper.emitted('refresh')).toHaveLength(1);
  });
});

describe('Theme integration', () => {
  it('all theme presets provide required keys', () => {
    const requiredKeys = ['pageStart', 'pageEnd', 'textPrimary', 'surfaceRaised', 'brandPrimary'];
    for (const [name, theme] of Object.entries(starPlanetThemes)) {
      for (const key of requiredKeys) {
        expect(theme[key], `${name}.${key} should exist`).toBeDefined();
      }
    }
  });
  it('components accept all theme variants without error', () => {
    for (const [, theme] of Object.entries(starPlanetThemes)) {
      expect(() => mount(TspButton, { props: { text: 'Test', theme } })).not.toThrow();
    }
  });
});
