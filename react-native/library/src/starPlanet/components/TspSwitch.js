import React, { useEffect, useRef } from 'react';
import { Animated, Easing, Pressable, Text, View, StyleSheet } from 'react-native';
import { withTheme } from '../utils/shared';
import { useReduceMotion } from '../utils/useReduceMotion';

const SIZES = {
  md: { width: 52, height: 28, handle: 24, inset: 2, travel: 24 },
  sm: { width: 40, height: 22, handle: 18, inset: 2, travel: 18 },
};

/**
 * Flat TspSwitch — custom Pressable track (no RN Switch).
 * Hierarchy: Label? + Control > Track > Thumb > Spinner?
 * checkedText / uncheckedText kept for API compat; not rendered.
 */
export function TspSwitch({
  text = '',
  checked = false,
  checkedText: _checkedText = 'ON',
  uncheckedText: _uncheckedText = 'OFF',
  loading = false,
  disabled = false,
  variant = 'md',
  theme,
  onChange,
}) {
  const t = withTheme(theme);
  const size = variant === 'sm' ? SIZES.sm : SIZES.md;
  const blocked = disabled || loading;
  const reduceMotion = useReduceMotion();
  const thumbX = useRef(new Animated.Value(checked ? size.travel : 0)).current;
  const spin = useRef(new Animated.Value(0)).current;

  useEffect(() => {
    Animated.timing(thumbX, {
      toValue: checked ? size.travel : 0,
      duration: reduceMotion ? 0 : 180,
      useNativeDriver: true,
    }).start();
  }, [checked, size.travel, thumbX, reduceMotion]);

  useEffect(() => {
    if (!loading || reduceMotion) {
      spin.stopAnimation?.();
      spin.setValue(0);
      return undefined;
    }
    const loop = Animated.loop(
      Animated.timing(spin, {
        toValue: 1,
        duration: 700,
        easing: Easing.linear,
        useNativeDriver: true,
      })
    );
    loop.start();
    return () => loop.stop();
  }, [loading, spin, reduceMotion]);

  const trackBg = checked ? t.switchOnBg : t.switchOffBg;
  const spinnerColor = checked ? '#FFFFFF' : t.brandDark;
  const controlOpacity = disabled ? 0.5 : loading ? 0.7 : 1;
  const spinRotate = spin.interpolate({
    inputRange: [0, 1],
    outputRange: ['0deg', '360deg'],
  });

  return (
    <Pressable
      accessibilityRole="switch"
      accessibilityLabel={text || 'Switch'}
      accessibilityState={{ checked, disabled: blocked, busy: loading }}
      disabled={blocked}
      onPress={() => onChange?.(!checked)}
      style={styles.switchRoot}
    >
      {!!text && (
        <Text style={[styles.switchLabel, { color: t.textPrimary }]} numberOfLines={1}>
          {text}
        </Text>
      )}
      <View style={[styles.switchControl, { opacity: controlOpacity }]}>
        <View
          style={[
            styles.switchTrack,
            {
              width: size.width,
              height: size.height,
              backgroundColor: trackBg,
            },
          ]}
        >
          <Animated.View
            style={[
              styles.switchThumb,
              {
                width: size.handle,
                height: size.handle,
                top: size.inset,
                left: size.inset,
                backgroundColor: t.switchHandleBg ?? '#FFFFFF',
                transform: [{ translateX: thumbX }],
              },
            ]}
          >
            {loading ? (
              <Animated.View
                style={[
                  styles.switchSpinner,
                  {
                    borderTopColor: spinnerColor,
                    width: Math.max(8, size.handle * 0.55),
                    height: Math.max(8, size.handle * 0.55),
                    transform: [{ rotate: spinRotate }],
                  },
                ]}
              />
            ) : null}
          </Animated.View>
        </View>
      </View>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  switchRoot: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 12,
  },
  switchLabel: {
    flex: 1,
    minWidth: 0,
    fontSize: 14,
    fontWeight: '800',
  },
  switchControl: {
    flexShrink: 0,
  },
  switchTrack: {
    borderRadius: 999,
    borderWidth: 0,
  },
  switchThumb: {
    position: 'absolute',
    borderRadius: 999,
    borderWidth: 0,
    alignItems: 'center',
    justifyContent: 'center',
  },
  switchSpinner: {
    borderRadius: 999,
    borderWidth: 2,
    borderColor: 'transparent',
  },
});
