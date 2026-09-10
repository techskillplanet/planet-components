/**
 * W1/W2 common controls — Cascader regression + Tag/Fab/Slider/InputNumber/Avatar/Drawer.
 */
import React from 'react';
import { describe, it, expect, vi } from 'vitest';
import { render, screen, fireEvent } from '@testing-library/react';
import {
  TspCascader,
  TspTag,
  TspFab,
  TspSlider,
  TspInputNumber,
  TspAvatar,
  TspDrawer,
  TspSkeleton,
  TspTimePicker,
  TspUpload,
  TspTable,
  TspTree,
  starPlanetTheme,
} from '../src/index.js';

const cascaderOptions = [
  { value: 'asia', label: '亚洲', children: [{ value: 'cn', label: '中国' }, { value: 'jp', label: '日本' }] },
  { value: 'eu', label: '欧洲', children: [{ value: 'fr', label: '法国' }] },
];

describe('TspCascader', () => {
  it('opens in-flow panel (not absolute overlay class regression)', () => {
    const { container } = render(
      React.createElement(TspCascader, {
        options: cascaderOptions,
        theme: starPlanetTheme,
      })
    );
    fireEvent.click(screen.getByRole('button', { name: /请选择/ }));
    const panel = container.querySelector('.bc-cascader__panel');
    expect(panel).toBeTruthy();
    expect(screen.getByRole('button', { name: /亚洲/ })).toBeTruthy();
    expect(screen.getByRole('button', { name: /欧洲/ })).toBeTruthy();
  });

  it('expands children when parent branch is selected', () => {
    render(
      React.createElement(TspCascader, {
        options: cascaderOptions,
        theme: starPlanetTheme,
      })
    );
    fireEvent.click(screen.getByRole('button', { name: /请选择/ }));
    fireEvent.click(screen.getByRole('button', { name: /欧洲/ }));
    expect(screen.getByRole('button', { name: /法国/ })).toBeTruthy();
  });

  it('emits leaf path and closes', () => {
    const onChange = vi.fn();
    render(
      React.createElement(TspCascader, {
        options: cascaderOptions,
        onChange,
        theme: starPlanetTheme,
      })
    );
    fireEvent.click(screen.getByRole('button', { name: /请选择/ }));
    fireEvent.click(screen.getByRole('button', { name: /欧洲/ }));
    fireEvent.click(screen.getByRole('button', { name: /法国/ }));
    expect(onChange).toHaveBeenCalledWith(['eu', 'fr'], ['欧洲', '法国']);
    expect(screen.queryByRole('listbox')).toBeNull();
  });

  it('uses dedicated active class instead of global bc-selected', () => {
    const { container } = render(
      React.createElement(TspCascader, {
        options: cascaderOptions,
        theme: starPlanetTheme,
      })
    );
    fireEvent.click(screen.getByRole('button', { name: /请选择/ }));
    fireEvent.click(screen.getByRole('button', { name: /欧洲/ }));
    expect(container.querySelector('.bc-cascader__option--active')).toBeTruthy();
    expect(container.querySelector('.bc-cascader__option.bc-selected')).toBeNull();
  });

  it('does not fire when disabled', () => {
    render(
      React.createElement(TspCascader, {
        options: cascaderOptions,
        disabled: true,
        theme: starPlanetTheme,
      })
    );
    fireEvent.click(screen.getByRole('button', { name: /请选择/ }));
    expect(screen.queryByRole('listbox')).toBeNull();
  });
});

describe('TspTag', () => {
  it('fires onClose for closable tag without bubbling onTap', () => {
    const onClose = vi.fn();
    const onTap = vi.fn();
    const { container } = render(
      React.createElement(TspTag, {
        text: 'closable',
        closable: true,
        onClose,
        onTap,
        theme: starPlanetTheme,
      })
    );
    fireEvent.click(container.querySelector('.bc-tag__close'));
    expect(onClose).toHaveBeenCalledTimes(1);
    expect(onTap).not.toHaveBeenCalled();
  });
});

describe('TspFab', () => {
  it('fires onTap', () => {
    const onTap = vi.fn();
    render(React.createElement(TspFab, { icon: '+', text: '新建', onTap, theme: starPlanetTheme }));
    fireEvent.click(screen.getByRole('button', { name: '新建' }));
    expect(onTap).toHaveBeenCalledTimes(1);
  });
});

describe('TspSlider', () => {
  it('emits numeric onChange', () => {
    const onChange = vi.fn();
    const { container } = render(
      React.createElement(TspSlider, { value: 10, min: 0, max: 100, onChange, theme: starPlanetTheme })
    );
    fireEvent.change(container.querySelector('input[type="range"]'), { target: { value: '42' } });
    expect(onChange).toHaveBeenCalledWith(42);
  });
});

describe('TspInputNumber', () => {
  it('increments and decrements with clamp', () => {
    const onChange = vi.fn();
    render(
      React.createElement(TspInputNumber, {
        value: 1,
        min: 0,
        max: 2,
        step: 1,
        onChange,
        theme: starPlanetTheme,
      })
    );
    fireEvent.click(screen.getByRole('button', { name: 'Increase' }));
    expect(onChange).toHaveBeenCalledWith(2);
    fireEvent.click(screen.getByRole('button', { name: 'Decrease' }));
    expect(onChange).toHaveBeenCalledWith(0);
  });
});

describe('TspAvatar / Skeleton / Drawer / TimePicker / Upload / Table / Tree', () => {
  it('Avatar shows initials', () => {
    const { container } = render(React.createElement(TspAvatar, { text: '技趣', theme: starPlanetTheme }));
    expect(container.textContent).toMatch(/技趣|技/);
  });

  it('Skeleton renders animated lines', () => {
    const { container } = render(
      React.createElement(TspSkeleton, { rows: 2, animated: true, avatar: true, theme: starPlanetTheme })
    );
    expect(container.querySelectorAll('.bc-skeleton__line').length).toBe(2);
    expect(container.querySelector('.bc-skeleton--animated')).toBeTruthy();
  });

  it('Drawer opens body and closes via mask', () => {
    const onClose = vi.fn();
    const { container, rerender } = render(
      React.createElement(TspDrawer, {
        visible: true,
        title: 'Drawer',
        onClose,
        theme: starPlanetTheme,
      }, 'body')
    );
    expect(screen.getByRole('dialog')).toHaveTextContent('body');
    fireEvent.click(container.querySelector('.bc-drawer__mask'));
    expect(onClose).toHaveBeenCalled();
    rerender(React.createElement(TspDrawer, { visible: false, theme: starPlanetTheme }, 'body'));
    expect(screen.queryByRole('dialog')).toBeNull();
  });

  it('TimePicker emits HH:mm', () => {
    const onChange = vi.fn();
    const { container } = render(
      React.createElement(TspTimePicker, { value: '09:30', onChange, theme: starPlanetTheme })
    );
    fireEvent.change(container.querySelector('input[type="time"]'), { target: { value: '18:00' } });
    expect(onChange).toHaveBeenCalledWith('18:00');
  });

  it('Upload lists and removes files', () => {
    const onChange = vi.fn();
    render(
      React.createElement(TspUpload, {
        files: [{ id: '1', name: 'a.md' }],
        onChange,
        theme: starPlanetTheme,
      })
    );
    expect(screen.getByText('a.md')).toBeTruthy();
    fireEvent.click(screen.getByLabelText('Remove a.md'));
    expect(onChange).toHaveBeenCalledWith([]);
  });

  it('Table renders headers and rows', () => {
    const { container } = render(
      React.createElement(TspTable, {
        columns: [{ key: 'name', title: '名称' }, { key: 'status', title: '状态' }],
        rows: [{ name: 'Avatar', status: '就绪' }],
        theme: starPlanetTheme,
      })
    );
    expect(container.querySelector('th')).toHaveTextContent('名称');
    expect(container.textContent).toContain('Avatar');
  });

  it('Tree expands and selects', () => {
    const onSelect = vi.fn();
    const onExpand = vi.fn();
    render(
      React.createElement(TspTree, {
        items: [{ id: 'a', label: '学习', children: [{ id: 'a1', label: '天空' }] }],
        expandedIds: [],
        onSelect,
        onExpand,
        theme: starPlanetTheme,
      })
    );
    fireEvent.click(screen.getByLabelText('Expand'));
    expect(onExpand).toHaveBeenCalled();
    fireEvent.click(screen.getByRole('button', { name: '学习' }));
    expect(onSelect).toHaveBeenCalledWith('a', expect.any(Object));
  });
});
