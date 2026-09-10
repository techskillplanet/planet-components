import React from 'react';
import { Pressable, Text, View, StyleSheet } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

function clampNum(value, min, max) {
  const n = Number(value);
  const v = Number.isFinite(n) ? n : 0;
  return Math.max(min, Math.min(max, v));
}

/** Numeric stepper with − / + Pressables. */
export function TspInputNumber({
  value = 0,
  min = Number.NEGATIVE_INFINITY,
  max = Number.POSITIVE_INFINITY,
  step = 1,
  disabled = false,
  theme,
  onChange,
}) {
  const t = withTheme(theme);
  const current = Number.isFinite(Number(value)) ? Number(value) : 0;
  const s = Number(step) > 0 ? Number(step) : 1;
  const lo = Number(min);
  const hi = Number(max);
  const atMin = current <= lo;
  const atMax = current >= hi;

  const dec = () => onChange?.(clampNum(current - s, lo, hi));
  const inc = () => onChange?.(clampNum(current + s, lo, hi));

  return (
    <View
      style={[local.root, disabled && shared.disabled]}
      accessibilityRole="adjustable"
      accessibilityState={{ disabled }}
      accessibilityValue={{ min: Number.isFinite(lo) ? lo : undefined, max: Number.isFinite(hi) ? hi : undefined, now: current }}
    >
      <Pressable
        accessibilityLabel="Decrease"
        accessibilityRole="button"
        disabled={disabled || atMin}
        onPress={dec}
        style={[
          local.btn,
          {
            borderColor: t.borderDefault,
            backgroundColor: t.surfaceRaised,
            opacity: disabled || atMin ? 0.45 : 1,
          },
        ]}
      >
        <Text style={[local.btnText, { color: t.brandPrimary }]}>−</Text>
      </Pressable>
      <Text
        accessibilityLiveRegion="polite"
        style={[local.value, { color: t.textPrimary, borderColor: t.borderDefault, backgroundColor: t.surfaceSubtle }]}
      >
        {String(current)}
      </Text>
      <Pressable
        accessibilityLabel="Increase"
        accessibilityRole="button"
        disabled={disabled || atMax}
        onPress={inc}
        style={[
          local.btn,
          {
            borderColor: t.borderDefault,
            backgroundColor: t.surfaceRaised,
            opacity: disabled || atMax ? 0.45 : 1,
          },
        ]}
      >
        <Text style={[local.btnText, { color: t.brandPrimary }]}>+</Text>
      </Pressable>
    </View>
  );
}

const local = StyleSheet.create({
  root: { flexDirection: 'row', alignItems: 'center', alignSelf: 'flex-start', gap: 8 },
  btn: {
    width: 40,
    height: 40,
    borderRadius: 12,
    borderWidth: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  btnText: { fontSize: 20, fontWeight: '800', lineHeight: 22 },
  value: {
    minWidth: 56,
    minHeight: 40,
    paddingHorizontal: 12,
    borderRadius: 12,
    borderWidth: 1,
    textAlign: 'center',
    textAlignVertical: 'center',
    fontSize: 16,
    fontWeight: '800',
    lineHeight: 38,
  },
});
