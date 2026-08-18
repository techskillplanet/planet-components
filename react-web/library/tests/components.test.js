/**
 * Unit tests for TechSkillPlanet React Web components.
 * Uses vitest + @testing-library/react + jsdom.
 */
import React from 'react';
import { describe, it, expect, vi } from 'vitest';
import { render, screen, fireEvent } from '@testing-library/react';
import {
  TspButton, TspCard, TspAlert, TspBadge, TspChip, TspInput, TspSelect,
  TspOptionSheet, TspSwitch, TspProgress, TspTopBar, TspBottomTab, TspTabs,
  TspAmount, TspIconButton, TspKeyValueLabel, TspNotification, TspTextLink,
  TspStepper, TspStickyFooter, TspPinInput, TspListItem, TspEmpty, TspToast, TspModal,
  TspLoadingDialog, TspRefreshLayout,
  starPlanetTheme, starPlanetThemes, themeVars, resolveTheme, cx, clamp, optionText
} from '../src/index.js';

// ─── Utility Tests ─────────────────────────────────────────────────────────────

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

// ─── Component Tests ───────────────────────────────────────────────────────────

describe('TspButton', () => {
  it('renders with text', () => {
    render(React.createElement(TspButton, { text: 'Click Me' }));
    expect(screen.getByRole('button')).toHaveTextContent('Click Me');
  });
  it('applies variant class', () => {
    const { container } = render(React.createElement(TspButton, { text: 'X', variant: 'primary' }));
    expect(container.querySelector('.bc-button--primary')).toBeTruthy();
  });
  it('applies disabled state', () => {
    render(React.createElement(TspButton, { text: 'X', disabled: true }));
    expect(screen.getByRole('button')).toBeDisabled();
  });
  it('fires onTap callback', () => {
    const fn = vi.fn();
    render(React.createElement(TspButton, { text: 'Go', onTap: fn }));
    fireEvent.click(screen.getByRole('button'));
    expect(fn).toHaveBeenCalledTimes(1);
  });
  it('does not fire onTap when disabled', () => {
    const fn = vi.fn();
    render(React.createElement(TspButton, { text: 'Go', disabled: true, onTap: fn }));
    fireEvent.click(screen.getByRole('button'));
    expect(fn).not.toHaveBeenCalled();
  });
  it('applies flat class when shadow disabled', () => {
    const theme = { ...starPlanetTheme, buttonRaisedShadowEnabled: false };
    const { container } = render(React.createElement(TspButton, { text: 'X', theme }));
    expect(container.querySelector('.bc-button--flat')).toBeTruthy();
  });
  it('renders children over text', () => {
    render(React.createElement(TspButton, { text: 'No' }, 'Yes'));
    expect(screen.getByRole('button')).toHaveTextContent('Yes');
  });
});

describe('TspCard', () => {
  it('renders children', () => {
    const { container } = render(React.createElement(TspCard, null, 'Card content'));
    expect(container.querySelector('.bc-card')).toHaveTextContent('Card content');
  });
  it('applies selected class', () => {
    const { container } = render(React.createElement(TspCard, { selected: true }, 'X'));
    expect(container.querySelector('.bc-selected')).toBeTruthy();
  });
  it('applies disabled class', () => {
    const { container } = render(React.createElement(TspCard, { disabled: true }, 'X'));
    expect(container.querySelector('.bc-disabled')).toBeTruthy();
  });
});

describe('TspAlert', () => {
  it('renders title and message', () => {
    render(React.createElement(TspAlert, { title: 'Error', message: 'Something failed' }));
    const el = screen.getByRole('status');
    expect(el).toHaveTextContent('Error');
    expect(el).toHaveTextContent('Something failed');
  });
  it('applies variant class', () => {
    const { container } = render(React.createElement(TspAlert, { title: 'W', variant: 'warning' }));
    expect(container.querySelector('.bc-alert--warning')).toBeTruthy();
  });
  it('renders without title', () => {
    render(React.createElement(TspAlert, { message: 'Only message' }));
    expect(screen.getByRole('status')).toHaveTextContent('Only message');
  });
});

describe('TspBadge', () => {
  it('renders text', () => {
    const { container } = render(React.createElement(TspBadge, { text: '99+' }));
    expect(container.querySelector('.bc-badge')).toHaveTextContent('99+');
  });
  it('applies variant class', () => {
    const { container } = render(React.createElement(TspBadge, { text: 'X', variant: 'danger' }));
    expect(container.querySelector('.bc-badge--danger')).toBeTruthy();
  });
});

describe('TspChip', () => {
  it('renders text and fires onTap', () => {
    const fn = vi.fn();
    render(React.createElement(TspChip, { text: 'Tag', onTap: fn }));
    fireEvent.click(screen.getByRole('button'));
    expect(fn).toHaveBeenCalledTimes(1);
  });
  it('applies selected class', () => {
    const { container } = render(React.createElement(TspChip, { text: 'X', selected: true }));
    expect(container.querySelector('.bc-selected')).toBeTruthy();
  });
  it('disabled chip does not fire', () => {
    const fn = vi.fn();
    render(React.createElement(TspChip, { text: 'X', disabled: true, onTap: fn }));
    fireEvent.click(screen.getByRole('button'));
    expect(fn).not.toHaveBeenCalled();
  });
});

describe('TspInput', () => {
  it('renders with value and placeholder', () => {
    render(React.createElement(TspInput, { value: 'hello', placeholder: 'Type...' }));
    const input = screen.getByPlaceholderText('Type...');
    expect(input.value).toBe('hello');
  });
  it('calls onChange with new value', () => {
    const fn = vi.fn();
    render(React.createElement(TspInput, { value: '', placeholder: 'X', onChange: fn }));
    fireEvent.change(screen.getByPlaceholderText('X'), { target: { value: 'test' } });
    expect(fn).toHaveBeenCalledWith('test');
  });
  it('applies error variant', () => {
    const { container } = render(React.createElement(TspInput, { value: '', variant: 'error' }));
    expect(container.querySelector('.bc-input--error')).toBeTruthy();
  });
});

describe('TspSwitch', () => {
  it('renders and toggles', () => {
    const fn = vi.fn();
    render(React.createElement(TspSwitch, { checked: false, onChange: fn }));
    fireEvent.click(screen.getByRole('switch'));
    expect(fn).toHaveBeenCalledWith(true);
  });
  it('applies checked class', () => {
    const { container } = render(React.createElement(TspSwitch, { checked: true }));
    expect(container.querySelector('.bc-checked')).toBeTruthy();
  });
  it('renders flat hierarchy without inner ON/OFF text', () => {
    const { container } = render(React.createElement(TspSwitch, { text: 'Label', checked: false }));
    expect(container.querySelector('.bc-switch__label')?.textContent).toBe('Label');
    expect(container.querySelector('.bc-switch__control')).toBeTruthy();
    expect(container.querySelector('.bc-switch__track')).toBeTruthy();
    expect(container.querySelector('.bc-switch__inner-text')).toBeNull();
    expect(container.querySelector('.bc-switch__thumb')).toBeTruthy();
    expect(container.querySelector('.bc-switch--md')).toBeTruthy();
  });
  it('applies sm flat sizes class', () => {
    const { container } = render(React.createElement(TspSwitch, { checked: false, variant: 'sm' }));
    expect(container.querySelector('.bc-switch--sm')).toBeTruthy();
  });
  it('shows spinner when loading', () => {
    const { container } = render(React.createElement(TspSwitch, { checked: true, loading: true }));
    expect(container.querySelector('.bc-switch__spinner')).toBeTruthy();
    expect(container.querySelector('.bc-loading')).toBeTruthy();
  });
  it('disabled does not toggle', () => {
    const fn = vi.fn();
    render(React.createElement(TspSwitch, { checked: false, disabled: true, onChange: fn }));
    fireEvent.click(screen.getByRole('switch'));
    expect(fn).not.toHaveBeenCalled();
  });
  it('loading disables interaction', () => {
    const fn = vi.fn();
    render(React.createElement(TspSwitch, { checked: true, loading: true, onChange: fn }));
    fireEvent.click(screen.getByRole('switch'));
    expect(fn).not.toHaveBeenCalled();
  });
});

describe('TspProgress', () => {
  it('renders with correct aria values', () => {
    render(React.createElement(TspProgress, { progress: 65 }));
    const bar = screen.getByRole('progressbar');
    expect(bar.getAttribute('aria-valuenow')).toBe('65');
    expect(bar.getAttribute('aria-valuemin')).toBe('0');
    expect(bar.getAttribute('aria-valuemax')).toBe('100');
  });
  it('clamps values', () => {
    render(React.createElement(TspProgress, { progress: 150 }));
    expect(screen.getByRole('progressbar').getAttribute('aria-valuenow')).toBe('100');
  });
});

describe('TspTopBar', () => {
  it('renders title', () => {
    const { container } = render(React.createElement(TspTopBar, { title: 'Home' }));
    expect(container.querySelector('.bc-top-bar__title')).toHaveTextContent('Home');
  });
  it('shows back button when showBack=true', () => {
    render(React.createElement(TspTopBar, { title: 'X', showBack: true }));
    expect(screen.getByLabelText('Back')).toBeTruthy();
  });
  it('fires onBack', () => {
    const fn = vi.fn();
    render(React.createElement(TspTopBar, { title: 'X', showBack: true, onBack: fn }));
    fireEvent.click(screen.getByLabelText('Back'));
    expect(fn).toHaveBeenCalledTimes(1);
  });
});

describe('TspBottomTab', () => {
  const tabs = [{ key: 'a', title: 'Tab A', icon: '★' }, { key: 'b', title: 'Tab B' }];
  it('renders tabs', () => {
    const { container } = render(React.createElement(TspBottomTab, { tabs, selectedKey: 'a' }));
    const buttons = container.querySelectorAll('.bc-bottom-tab__item');
    expect(buttons).toHaveLength(2);
    expect(buttons[0]).toHaveClass('bc-selected');
  });
  it('fires onSelect with key', () => {
    const fn = vi.fn();
    const { container } = render(React.createElement(TspBottomTab, { tabs, selectedKey: 'a', onSelect: fn }));
    fireEvent.click(container.querySelectorAll('.bc-bottom-tab__item')[1]);
    expect(fn).toHaveBeenCalledWith('b', tabs[1]);
  });
});

describe('TspTabs', () => {
  it('renders tab items', () => {
    render(React.createElement(TspTabs, { tabs: ['All', 'Done', 'Todo'], selectedIndex: 1 }));
    const tabEls = screen.getAllByRole('tab');
    expect(tabEls).toHaveLength(3);
    expect(tabEls[1].getAttribute('aria-selected')).toBe('true');
  });
  it('fires onSelect', () => {
    const fn = vi.fn();
    render(React.createElement(TspTabs, { tabs: ['A', 'B'], selectedIndex: 0, onSelect: fn }));
    fireEvent.click(screen.getAllByRole('tab')[1]);
    expect(fn).toHaveBeenCalledWith(1, 'B');
  });
});

describe('TspAmount', () => {
  it('renders symbol and value', () => {
    const { container } = render(React.createElement(TspAmount, { symbol: '$', value: '99.50' }));
    expect(container.querySelector('.bc-amount__symbol')).toHaveTextContent('$');
    expect(container.querySelector('.bc-amount__value')).toHaveTextContent('99.50');
  });
  it('renders cycle suffix', () => {
    const { container } = render(React.createElement(TspAmount, { value: '10', cycle: 'month' }));
    expect(container.querySelector('.bc-amount__cycle')).toHaveTextContent('/month');
  });
  it('applies strikethrough', () => {
    const { container } = render(React.createElement(TspAmount, { value: '10', strikeThrough: true }));
    expect(container.querySelector('.bc-strike')).toBeTruthy();
  });
  it('supports symbolAfter', () => {
    const { container } = render(React.createElement(TspAmount, { symbol: '€', value: '5', symbolAfter: true }));
    const spans = container.querySelectorAll('.bc-amount > span');
    // value should come before symbol when symbolAfter=true
    expect(spans[0]).toHaveClass('bc-amount__value');
  });
});

describe('TspIconButton', () => {
  it('renders icon content', () => {
    render(React.createElement(TspIconButton, { icon: '★' }));
    expect(screen.getByRole('button')).toHaveTextContent('★');
  });
  it('fires onTap', () => {
    const fn = vi.fn();
    render(React.createElement(TspIconButton, { icon: 'X', onTap: fn }));
    fireEvent.click(screen.getByRole('button'));
    expect(fn).toHaveBeenCalledTimes(1);
  });
});

describe('TspKeyValueLabel', () => {
  it('renders label and value', () => {
    const { container } = render(React.createElement(TspKeyValueLabel, { label: 'Score', value: '100' }));
    expect(container.querySelector('.bc-key-value')).toHaveTextContent('Score');
    expect(container.querySelector('.bc-key-value strong')).toHaveTextContent('100');
  });
});

describe('TspNotification', () => {
  it('renders title and message', () => {
    const { container } = render(React.createElement(TspNotification, { title: 'Heads up', message: 'Task due' }));
    expect(container.querySelector('.bc-notification')).toHaveTextContent('Heads up');
    expect(container.querySelector('.bc-notification')).toHaveTextContent('Task due');
  });
  it('applies alert variant', () => {
    const { container } = render(React.createElement(TspNotification, { title: 'X', variant: 'alert' }));
    expect(container.querySelector('.bc-notification--alert')).toBeTruthy();
  });
});

describe('TspTextLink', () => {
  it('renders text and fires onTap', () => {
    const fn = vi.fn();
    render(React.createElement(TspTextLink, { text: 'Learn more', onTap: fn }));
    fireEvent.click(screen.getByRole('button'));
    expect(fn).toHaveBeenCalledTimes(1);
  });
  it('applies inverse class', () => {
    const { container } = render(React.createElement(TspTextLink, { text: 'X', inverse: true }));
    expect(container.querySelector('.bc-text-link--inverse')).toBeTruthy();
  });
});

describe('TspStepper', () => {
  it('renders correct number of steps', () => {
    const { container } = render(React.createElement(TspStepper, { stepCount: 4, currentStep: 2 }));
    expect(container.querySelectorAll('.bc-stepper__dot')).toHaveLength(4);
  });
  it('marks completed steps', () => {
    const { container } = render(React.createElement(TspStepper, { stepCount: 3, currentStep: 3 }));
    const done = container.querySelectorAll('.bc-done');
    expect(done.length).toBeGreaterThan(0);
  });
  it('clamps stepCount between 3-5', () => {
    const { container } = render(React.createElement(TspStepper, { stepCount: 10, currentStep: 1 }));
    expect(container.querySelectorAll('.bc-stepper__dot')).toHaveLength(5);
  });
});

describe('TspStickyFooter', () => {
  it('renders children', () => {
    const { container } = render(React.createElement(TspStickyFooter, null, 'Footer'));
    expect(container.querySelector('.bc-sticky-footer')).toHaveTextContent('Footer');
  });
});

describe('TspPinInput', () => {
  it('renders correct number of cells', () => {
    const { container } = render(React.createElement(TspPinInput, { cellCount: 6 }));
    expect(container.querySelectorAll('.bc-pin-input__cell')).toHaveLength(6);
  });
  it('masks value in secure mode', () => {
    const { container } = render(React.createElement(TspPinInput, { value: '12', secure: true }));
    const cells = container.querySelectorAll('.bc-pin-input__cell');
    expect(cells[0]).toHaveTextContent('•');
    expect(cells[1]).toHaveTextContent('•');
    expect(cells[2]).toHaveTextContent('');
  });
  it('shows digits when not secure', () => {
    const { container } = render(React.createElement(TspPinInput, { value: '34' }));
    const cells = container.querySelectorAll('.bc-pin-input__cell');
    expect(cells[0]).toHaveTextContent('3');
    expect(cells[1]).toHaveTextContent('4');
  });
});

describe('TspListItem', () => {
  it('renders title and message', () => {
    render(React.createElement(TspListItem, { title: 'Item', message: 'Desc' }));
    expect(screen.getByRole('button')).toHaveTextContent('Item');
    expect(screen.getByRole('button')).toHaveTextContent('Desc');
  });
  it('renders trailing', () => {
    const { container } = render(React.createElement(TspListItem, { title: 'X', trailing: '›' }));
    expect(container.querySelector('.bc-list-item__trailing')).toHaveTextContent('›');
  });
  it('fires onTap', () => {
    const fn = vi.fn();
    render(React.createElement(TspListItem, { title: 'X', onTap: fn }));
    fireEvent.click(screen.getByRole('button'));
    expect(fn).toHaveBeenCalledTimes(1);
  });
});

describe('TspEmpty', () => {
  it('renders title, message, and action', () => {
    const fn = vi.fn();
    const { container } = render(React.createElement(TspEmpty, { title: 'No data', message: 'Empty', actionText: 'Retry', onAction: fn }));
    expect(container.querySelector('.bc-empty')).toHaveTextContent('No data');
    expect(container.querySelector('.bc-empty')).toHaveTextContent('Empty');
    fireEvent.click(screen.getByRole('button'));
    expect(fn).toHaveBeenCalledTimes(1);
  });
});

describe('TspToast', () => {
  it('renders message with variant', () => {
    render(React.createElement(TspToast, { message: 'Saved!' }));
    expect(screen.getByRole('status')).toHaveTextContent('Saved!');
  });
  it('applies variant class', () => {
    const { container } = render(React.createElement(TspToast, { message: 'X', variant: 'success' }));
    expect(container.querySelector('.bc-toast--success')).toBeTruthy();
  });
});

describe('TspModal', () => {
  it('renders title and message', () => {
    render(React.createElement(TspModal, { title: 'Confirm', message: 'Are you sure?' }));
    expect(screen.getByRole('dialog')).toHaveTextContent('Confirm');
    expect(screen.getByRole('dialog')).toHaveTextContent('Are you sure?');
  });
  it('fires onConfirm and onCancel', () => {
    const onConfirm = vi.fn();
    const onCancel = vi.fn();
    render(React.createElement(TspModal, { title: 'X', confirmText: 'Yes', cancelText: 'No', onConfirm, onCancel }));
    const buttons = screen.getAllByRole('button');
    // Cancel button is first, Confirm button is second
    fireEvent.click(buttons[0]); // Cancel (default variant rendered first)
    fireEvent.click(buttons[1]); // Confirm (primary variant)
    expect(onCancel).toHaveBeenCalledTimes(1);
    expect(onConfirm).toHaveBeenCalledTimes(1);
  });
});

describe('TspOptionSheet', () => {
  it('returns null when not visible', () => {
    const { container } = render(React.createElement(TspOptionSheet, { visible: false }));
    expect(container.querySelector('.bc-option-sheet')).toBeNull();
  });
  it('renders options when visible', () => {
    render(React.createElement(TspOptionSheet, { visible: true, options: ['A', 'B', 'C'], selectedIndex: 1 }));
    expect(screen.getByRole('dialog')).toBeTruthy();
    const selected = screen.getByRole('dialog').querySelector('.bc-selected');
    expect(selected).toHaveTextContent('B');
  });
  it('fires onSelect', () => {
    const fn = vi.fn();
    render(React.createElement(TspOptionSheet, { visible: true, options: ['X', 'Y'], onSelect: fn }));
    const options = screen.getByRole('dialog').querySelectorAll('.bc-option-sheet__option');
    fireEvent.click(options[0]);
    expect(fn).toHaveBeenCalledWith(0, 'X');
  });
});

describe('TspSelect', () => {
  it('renders selected option text', () => {
    const { container } = render(React.createElement(TspSelect, { options: ['Apple', 'Banana'], selectedIndex: 0 }));
    expect(container.querySelector('.bc-select')).toHaveTextContent('Apple');
  });
  it('opens option sheet on click', () => {
    const { container } = render(React.createElement(TspSelect, { options: ['A', 'B'] }));
    fireEvent.click(container.querySelector('.bc-select'));
    expect(container.querySelector('.bc-option-sheet')).toBeTruthy();
  });
  it('uses a custom sheet title', () => {
    const { container } = render(React.createElement(TspSelect, { options: ['A', 'B'], title: 'Language' }));
    fireEvent.click(container.querySelector('.bc-select'));
    expect(container.querySelector('.bc-option-sheet')).toHaveTextContent('Language');
  });
});

describe('TspLoadingDialog', () => {
  it('returns null when not visible', () => {
    const { container } = render(React.createElement(TspLoadingDialog, { visible: false }));
    expect(container.querySelector('.bc-loading-dialog')).toBeNull();
  });
  it('renders message when visible', () => {
    render(React.createElement(TspLoadingDialog, { visible: true, message: '加载中...' }));
    expect(screen.getByRole('dialog')).toHaveTextContent('加载中...');
  });
});

describe('TspRefreshLayout', () => {
  it('renders children and refresh control', () => {
    const onRefresh = vi.fn();
    render(React.createElement(TspRefreshLayout, { onRefresh }, 'rows'));
    expect(screen.getByText('rows')).toBeTruthy();
    fireEvent.click(screen.getByText('刷新'));
    expect(onRefresh).toHaveBeenCalledTimes(1);
  });
});

// ─── Theme Integration Tests ───────────────────────────────────────────────────

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
      expect(() => render(React.createElement(TspButton, { text: 'Test', theme }))).not.toThrow();
    }
  });
});
