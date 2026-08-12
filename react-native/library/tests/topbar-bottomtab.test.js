import { describe, expect, it, vi } from 'vitest';
import React from 'react';
import renderer, { act } from 'react-test-renderer';
import { TspTopBar } from '../src/starPlanet/components/TspTopBar.js';
import { TspBottomTab } from '../src/starPlanet/components/TspBottomTab.js';
import { styles } from '../src/starPlanet/utils/shared.js';
import { flattenStyle, isLeftDocked } from '../src/starPlanet/utils/layout.js';

describe('TC-TOPBAR layout contract', () => {
  it('TC-TOPBAR-01 docks back control to the left', () => {
    expect(isLeftDocked(styles.topBack)).toBe(true);
    const flat = flattenStyle(styles.topBack);
    expect(flat.justifyContent).toBe('center');
    expect(flat.alignItems).toBe('center');
  });

  it('TC-TOPBAR-02 keeps title centered container', () => {
    const flat = flattenStyle(styles.topBar);
    expect(flat.justifyContent).toBe('center');
    expect(flat.position === 'relative' || flat.position == null || flat.position === 'relative').toBe(true);
  });

  it('TC-TOPBAR-03 invokes onBack when back is pressed', () => {
    const onBack = vi.fn();
    let tree;
    act(() => {
      tree = renderer.create(
        React.createElement(TspTopBar, { title: '详情', showBack: true, onBack, immersive: false })
      );
    });
    const pressable = tree.root.findByProps({ testID: 'tsp-top-bar-back' });
    act(() => {
      pressable.props.onPress();
    });
    expect(onBack).toHaveBeenCalledTimes(1);
    const texts = tree.root.findAllByType('Text').map(node => node.props.children).flat();
    expect(texts).toContain('‹');
    expect(texts).toContain('详情');
  });

  it('TC-TOPBAR-02b hides chevron when showBack is false', () => {
    let tree;
    act(() => {
      tree = renderer.create(
        React.createElement(TspTopBar, { title: '基础组件', showBack: false, immersive: false })
      );
    });
    const texts = tree.root.findAllByType('Text').map(node => node.props.children).flat();
    expect(texts).toContain('基础组件');
    expect(texts).not.toContain('‹');
  });

  it('TC-TOPBAR-04 renders custom backIcon node', () => {
    let tree;
    act(() => {
      tree = renderer.create(
        React.createElement(TspTopBar, {
          title: '详情',
          showBack: true,
          immersive: false,
          backIcon: React.createElement('Text', { testID: 'custom-back' }, '←'),
        })
      );
    });
    expect(tree.root.findByProps({ testID: 'custom-back' }).props.children).toBe('←');
    const texts = tree.root.findAllByType('Text').map(node => node.props.children).flat();
    expect(texts).not.toContain('‹');
  });
});

describe('TC-TAB bottom inset', () => {
  it('TC-TAB-01 applies bottomInset padding', () => {
    let tree;
    act(() => {
      tree = renderer.create(
        React.createElement(TspBottomTab, {
          tabs: [{ key: 'home', title: '学习', icon: '⌂' }],
          selectedKey: 'home',
          bottomInset: 20,
        })
      );
    });
    const root = tree.root.findByProps({ testID: 'tsp-bottom-tab' });
    const flat = flattenStyle(root.props.style);
    expect(flat.paddingBottom).toBe(20);
  });

  it('TC-TAB-02 accepts ReactNode icons', () => {
    let tree;
    act(() => {
      tree = renderer.create(
        React.createElement(TspBottomTab, {
          tabs: [
            {
              key: 'home',
              title: '学习',
              icon: React.createElement('Text', { testID: 'tab-icon-home' }, 'H'),
            },
          ],
          selectedKey: 'home',
        })
      );
    });
    expect(tree.root.findByProps({ testID: 'tab-icon-home' }).props.children).toBe('H');
  });

  it('TC-TAB-03 keeps top content padding above safe-area', () => {
    const content = flattenStyle(styles.bottomTabContent);
    const shell = flattenStyle(styles.bottomTab);
    expect(content.paddingTop).toBeGreaterThanOrEqual(8);
    expect(content.paddingBottom).toBeGreaterThanOrEqual(8);
    expect(content.minHeight).toBeGreaterThanOrEqual(52);
    expect(shell.borderTopWidth).toBe(1);
    expect(shell.borderWidth == null || shell.borderWidth === 0).toBe(true);

    let tree;
    act(() => {
      tree = renderer.create(
        React.createElement(TspBottomTab, {
          tabs: [{ key: 'home', title: '学习', icon: '⌂' }],
          selectedKey: 'home',
          bottomInset: 34,
        })
      );
    });
    const contentNode = tree.root.findByProps({ testID: 'tsp-bottom-tab-content' });
    expect(flattenStyle(contentNode.props.style).paddingTop).toBeGreaterThanOrEqual(8);
    expect(flattenStyle(tree.root.findByProps({ testID: 'tsp-bottom-tab' }).props.style).paddingBottom).toBe(34);
  });
});
