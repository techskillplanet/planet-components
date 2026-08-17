import { vi } from 'vitest';
import React from 'react';

vi.mock('react-native', () => {
  const React = require('react');
  const flatten = styles => {
    if (!styles) return {};
    if (Array.isArray(styles)) return Object.assign({}, ...styles.filter(Boolean).map(flatten));
    return styles;
  };
  const StyleSheet = {
    create: styles => styles,
    flatten,
  };
  const View = ({ children, ...props }) => React.createElement('View', props, children);
  const Text = ({ children, ...props }) => React.createElement('Text', props, children);
  const Pressable = ({ children, ...props }) => React.createElement('Pressable', props, children);

  class AnimatedValue {
    constructor(value) {
      this._value = value;
    }
    setValue(value) {
      this._value = value;
    }
    stopAnimation() {}
    interpolate() {
      return '0deg';
    }
  }

  const timing = (value, config) => ({
    start(cb) {
      if (value && typeof value.setValue === 'function' && config && 'toValue' in config) {
        value.setValue(config.toValue);
      }
      cb?.({ finished: true });
    },
    stop() {},
  });

  const Animated = {
    View,
    Value: AnimatedValue,
    timing,
    loop: anim => ({
      start() {
        anim?.start?.();
      },
      stop() {
        anim?.stop?.();
      },
    }),
  };

  return {
    StyleSheet,
    Platform: { OS: 'android', select: specs => (specs && (specs.android ?? specs.default)) },
    View,
    Text,
    Pressable,
    Modal: View,
    ScrollView: View,
    ActivityIndicator: View,
    RefreshControl: View,
    Switch: View,
    Animated,
    Easing: { linear: x => x },
  };
});
