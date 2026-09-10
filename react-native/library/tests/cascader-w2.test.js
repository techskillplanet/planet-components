/**
 * W2 Cascader + Tag / InputNumber smoke — react-test-renderer.
 */
import { describe, expect, it, vi } from 'vitest';
import React from 'react';
import renderer, { act } from 'react-test-renderer';
import { TspCascader } from '../src/starPlanet/components/TspCascader.js';
import { TspTag } from '../src/starPlanet/components/TspTag.js';
import { TspInputNumber } from '../src/starPlanet/components/TspInputNumber.js';

const cascaderOptions = [
  { value: 'asia', label: '亚洲', children: [{ value: 'cn', label: '中国' }, { value: 'jp', label: '日本' }] },
  { value: 'eu', label: '欧洲', children: [{ value: 'fr', label: '法国' }] },
];

function textsOf(root) {
  return root
    .findAllByType('Text')
    .map((node) => node.props.children)
    .flat()
    .filter((t) => typeof t === 'string');
}

function pressByLabel(root, label) {
  const nodes = root.findAll(
    (n) =>
      n.props?.onPress &&
      (n.props.accessibilityLabel === label ||
        (Array.isArray(n.children) === false &&
          n.findAllByType?.('Text')?.some?.((t) => {
            const c = t.props.children;
            return c === label || (Array.isArray(c) && c.includes(label));
          })))
  );
  // Prefer Pressable whose Text child equals label
  const byText = root.findAll((n) => {
    if (!n.props?.onPress) return false;
    try {
      const texts = n
        .findAllByType('Text')
        .map((t) => t.props.children)
        .flat();
      return texts.includes(label);
    } catch {
      return false;
    }
  });
  const target = byText[0] || nodes[0];
  expect(target).toBeTruthy();
  act(() => {
    target.props.onPress();
  });
}

describe('TspCascader W2', () => {
  it('opens and lists asia / europe', () => {
    let tree;
    act(() => {
      tree = renderer.create(React.createElement(TspCascader, { options: cascaderOptions }));
    });
    pressByLabel(tree.root, '请选择');
    const texts = textsOf(tree.root);
    expect(texts).toContain('亚洲');
    expect(texts).toContain('欧洲');
  });

  it('expands france after picking europe and emits leaf path', () => {
    const onChange = vi.fn();
    let tree;
    act(() => {
      tree = renderer.create(
        React.createElement(TspCascader, { options: cascaderOptions, onChange })
      );
    });
    pressByLabel(tree.root, '请选择');
    pressByLabel(tree.root, '欧洲');
    expect(textsOf(tree.root)).toContain('法国');
    pressByLabel(tree.root, '法国');
    expect(onChange).toHaveBeenCalledWith(['eu', 'fr'], ['欧洲', '法国']);
  });
});

describe('TspTag / TspInputNumber smoke', () => {
  it('Tag closable fires onClose', () => {
    const onClose = vi.fn();
    let tree;
    act(() => {
      tree = renderer.create(
        React.createElement(TspTag, { text: 'tag', closable: true, onClose })
      );
    });
    const close = tree.root.findAll(
      (n) => n.props?.onPress && n.props.accessibilityLabel === 'Remove'
    )[0];
    expect(close).toBeTruthy();
    act(() => {
      close.props.onPress();
    });
    expect(onClose).toHaveBeenCalledTimes(1);
  });

  it('InputNumber increments', () => {
    const onChange = vi.fn();
    let tree;
    act(() => {
      tree = renderer.create(
        React.createElement(TspInputNumber, {
          value: 1,
          min: 0,
          max: 2,
          step: 1,
          onChange,
        })
      );
    });
    const inc = tree.root.findAll(
      (n) => n.props?.onPress && n.props.accessibilityLabel === 'Increase'
    )[0];
    act(() => {
      inc.props.onPress();
    });
    expect(onChange).toHaveBeenCalledWith(2);
  });
});
