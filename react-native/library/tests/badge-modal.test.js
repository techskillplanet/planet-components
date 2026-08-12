import { describe, expect, it } from 'vitest';
import React from 'react';
import renderer, { act } from 'react-test-renderer';
import { TspBadge } from '../src/starPlanet/components/TspBadge.js';
import { TspButton } from '../src/starPlanet/components/TspButton.js';
import { TspModal } from '../src/starPlanet/components/TspModal.js';
import { styles } from '../src/starPlanet/utils/shared.js';
import { flattenStyle } from '../src/starPlanet/utils/layout.js';

describe('TC-BADGE text centering', () => {
  it('TC-BADGE-01 centers badge text via container + text contracts', () => {
    const badge = flattenStyle(styles.badge);
    const badgeText = flattenStyle(styles.badgeText);
    expect(badge.alignItems).toBe('center');
    expect(badge.justifyContent).toBe('center');
    expect(badgeText.textAlign).toBe('center');
    expect(badgeText.includeFontPadding).toBe(false);
  });

  it('TC-BADGE-02 renders text inside a centered View shell', () => {
    let tree;
    act(() => {
      tree = renderer.create(
        React.createElement(TspBadge, { text: 'primary', variant: 'primary' })
      );
    });
    const root = tree.root;
    expect(root.findByType('Text').props.children).toBe('primary');
    const textStyle = flattenStyle(root.findByType('Text').props.style);
    expect(textStyle.textAlign).toBe('center');
    expect(textStyle.includeFontPadding).toBe(false);
    const shellStyle = flattenStyle(root.findByType('View').props.style);
    expect(shellStyle.alignItems).toBe('center');
    expect(shellStyle.justifyContent).toBe('center');
  });
});

describe('TC-MODAL dual actions', () => {
  it('TC-MODAL-01 uses equal-flex action slots', () => {
    const actions = flattenStyle(styles.modalActions);
    const item = flattenStyle(styles.modalActionItem);
    expect(actions.flexDirection).toBe('row');
    expect(item.flex).toBe(1);
    expect(item.minWidth).toBe(0);
  });

  it('TC-MODAL-02 stretches button face and centers label', () => {
    const face = flattenStyle(styles.buttonFace);
    const label = flattenStyle(styles.buttonText);
    expect(face.width).toBe('100%');
    expect(face.alignItems).toBe('center');
    expect(face.justifyContent).toBe('center');
    expect(label.textAlign).toBe('center');
    expect(label.includeFontPadding).toBe(false);
  });

  it('TC-MODAL-03 renders both cancel and confirm labels', () => {
    let tree;
    act(() => {
      tree = renderer.create(
        React.createElement(TspModal, {
          visible: true,
          title: '确认',
          message: '组件弹窗完整显示。',
          cancelText: '取消',
          confirmText: '确定',
        })
      );
    });
    const texts = tree.root
      .findAllByType('Text')
      .map(node => node.props.children)
      .flat();
    expect(texts).toContain('取消');
    expect(texts).toContain('确定');
    expect(texts).toContain('确认');
  });
});

describe('TC-BUTTON raised shadow parity', () => {
  it('TC-BUTTON-01 default and primary both use opaque faces with shared shadow layer', () => {
    const theme = {
      surfaceRaised: '#FFFFFF',
      borderDefault: '#C8EAFF',
      brandPrimary: '#31A8FF',
      textPrimary: '#173A62',
      buttonRaisedShadowEnabled: true,
      shadowControlIslandLiftY: 5,
      pressedDropY: 2,
      buttonFaceHeight: 46,
    };
    let cancelTree;
    let confirmTree;
    act(() => {
      cancelTree = renderer.create(
        React.createElement(TspButton, { text: '取消', variant: 'default', theme, fullWidth: true })
      );
      confirmTree = renderer.create(
        React.createElement(TspButton, { text: '确定', variant: 'primary', theme, fullWidth: true })
      );
    });

    const cancelFaces = cancelTree.root.findAllByType('View').filter(node => {
      const flat = flattenStyle(node.props.style);
      return flat.height === 46 && flat.borderWidth === 1;
    });
    const confirmFaces = confirmTree.root.findAllByType('View').filter(node => {
      const flat = flattenStyle(node.props.style);
      return flat.height === 46 && flat.borderWidth === 1;
    });
    const cancelFace = flattenStyle(cancelFaces[0].props.style);
    const confirmFace = flattenStyle(confirmFaces[0].props.style);
    expect(cancelFace.backgroundColor).toBe('#FFFFFF');
    expect(cancelFace.backgroundColor).not.toBe('transparent');
    expect(confirmFace.backgroundColor).toBe('#31A8FF');
    expect(cancelFace.position).toBe('absolute');
    expect(confirmFace.position).toBe('absolute');

    const cancelShadow = cancelTree.root.findAllByType('View').find(node => {
      const flat = flattenStyle(node.props.style);
      return flat.position === 'absolute' && flat.top === 5;
    });
    const confirmShadow = confirmTree.root.findAllByType('View').find(node => {
      const flat = flattenStyle(node.props.style);
      return flat.position === 'absolute' && flat.top === 5;
    });
    expect(flattenStyle(cancelShadow.props.style).backgroundColor).toBe('#C8EAFF');
    expect(flattenStyle(confirmShadow.props.style).backgroundColor).toBe('#C8EAFF');
  });
});
