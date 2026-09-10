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
  const TextInput = (props) => React.createElement('TextInput', props);
  const Image = (props) => React.createElement('Image', props);

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
    sequence: anims => ({
      start(cb) {
        (anims || []).forEach(a => a?.start?.());
        cb?.({ finished: true });
      },
      stop() {},
    }),
    parallel: anims => ({
      start(cb) {
        (anims || []).forEach(a => a?.start?.());
        cb?.({ finished: true });
      },
      stop() {},
    }),
    loop: anim => ({
      start() {
        anim?.start?.();
      },
      stop() {
        anim?.stop?.();
      },
    }),
  };

  const PanResponder = {
    create: () => ({
      panHandlers: {},
    }),
  };

  const useWindowDimensions = () => ({ width: 390, height: 844, scale: 2, fontScale: 1 });

  return {
    StyleSheet,
    Platform: { OS: 'android', select: specs => (specs && (specs.android ?? specs.default)) },
    View,
    Text,
    TextInput,
    Image,
    Pressable,
    Modal: View,
    ScrollView: View,
    ActivityIndicator: View,
    RefreshControl: View,
    Switch: View,
    Animated,
    PanResponder,
    useWindowDimensions,
    Easing: { linear: x => x },
    AccessibilityInfo: {
      isReduceMotionEnabled: async () => false,
      addEventListener: () => ({ remove() {} }),
    },
  };
});
