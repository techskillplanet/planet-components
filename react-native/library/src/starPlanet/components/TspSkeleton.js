import React, { useEffect, useRef } from 'react';
import { Animated, View, StyleSheet } from 'react-native';
import { withTheme } from '../utils/shared';
import { useReduceMotion } from '../utils/useReduceMotion';

/** Multi-line grey placeholder blocks with optional circular avatar. */
export function TspSkeleton({
  rows = 3,
  animated = true,
  avatar = false,
  variant = 'default',
  theme,
}) {
  const t = withTheme(theme);
  const reduceMotion = useReduceMotion();
  const count = Math.max(1, Math.min(12, Number(rows) || 3));
  const opacity = useRef(new Animated.Value(1)).current;
  const runPulse = Boolean(animated) && !reduceMotion;

  useEffect(() => {
    if (!runPulse) {
      opacity.setValue(1);
      return undefined;
    }
    const loop = Animated.loop(
      Animated.sequence([
        Animated.timing(opacity, { toValue: 0.45, duration: 700, useNativeDriver: true }),
        Animated.timing(opacity, { toValue: 1, duration: 700, useNativeDriver: true }),
      ])
    );
    loop.start();
    return () => loop.stop();
  }, [runPulse, opacity]);

  const blockColor = variant === 'pulse' ? t.brandSubtle : t.borderDefault;

  return (
    <Animated.View
      accessibilityElementsHidden
      importantForAccessibility="no-hide-descendants"
      style={[local.root, runPulse && { opacity }]}
    >
      {avatar ? (
        <View
          style={[
            local.avatar,
            { backgroundColor: blockColor, borderColor: t.borderDefault },
          ]}
        />
      ) : null}
      <View style={local.body}>
        {Array.from({ length: count }, (_, i) => (
          <View
            key={i}
            style={[
              local.line,
              {
                backgroundColor: blockColor,
                width: i === count - 1 ? '62%' : '100%',
              },
            ]}
          />
        ))}
      </View>
    </Animated.View>
  );
}

const local = StyleSheet.create({
  root: { flexDirection: 'row', alignItems: 'flex-start', gap: 12, width: '100%' },
  avatar: { width: 40, height: 40, borderRadius: 20, borderWidth: 1 },
  body: { flex: 1, gap: 10, paddingTop: 4 },
  line: { height: 12, borderRadius: 8 },
});
