import React from 'react';
import { Pressable, Text, View, StyleSheet } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

/** Interactive star rating. */
export function TspStarRating({
  value = 0,
  max = 5,
  disabled = false,
  variant = 'default',
  theme,
  onChange,
}) {
  const t = withTheme(theme);
  const count = Math.max(1, Math.min(10, Number(max) || 5));
  const readonly = variant === 'readonly' || disabled;
  const stars = [];
  for (let i = 1; i <= count; i += 1) {
    const filled = i <= value;
    stars.push(
      <Pressable
        key={i}
        accessibilityRole="button"
        accessibilityLabel={`${i}`}
        disabled={readonly}
        onPress={() => onChange?.(i)}
        style={local.starHit}
      >
        <Text style={[local.star, { color: filled ? t.brandPrimary : t.borderDefault }]}>★</Text>
      </Pressable>
    );
  }
  return (
    <View
      accessibilityRole="adjustable"
      accessibilityValue={{ min: 0, max: count, now: value }}
      style={[local.root, disabled && shared.disabled]}
    >
      {stars}
    </View>
  );
}

const local = StyleSheet.create({
  root: { flexDirection: 'row', alignItems: 'center', gap: 4 },
  starHit: { padding: 2 },
  star: { fontSize: 22, lineHeight: 24 },
});
