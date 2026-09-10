import React from 'react';
import { Pressable, Text, View, StyleSheet } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

/** Single radio option (group exclusivity is app-owned). */
export function TspRadio({
  text = '',
  checked = false,
  disabled = false,
  theme,
  onChange,
}) {
  const t = withTheme(theme);
  return (
    <Pressable
      accessibilityRole="radio"
      accessibilityState={{ checked, disabled }}
      disabled={disabled}
      onPress={() => onChange?.(true)}
      style={[local.root, disabled && shared.disabled]}
    >
      <View
        style={[
          local.dot,
          {
            borderColor: checked ? t.brandPrimary : t.borderDefault,
            backgroundColor: t.surfaceRaised,
          },
        ]}
      >
        {checked ? <View style={[local.inner, { backgroundColor: t.brandPrimary }]} /> : null}
      </View>
      {!!text && (
        <Text style={[local.label, { color: t.textPrimary }]} numberOfLines={1}>
          {text}
        </Text>
      )}
    </Pressable>
  );
}

const local = StyleSheet.create({
  root: { flexDirection: 'row', alignItems: 'center', gap: 10, paddingVertical: 6 },
  dot: {
    width: 22,
    height: 22,
    borderRadius: 11,
    borderWidth: 2,
    alignItems: 'center',
    justifyContent: 'center',
  },
  inner: { width: 10, height: 10, borderRadius: 5 },
  label: { fontSize: 14, fontWeight: '700', flexShrink: 1 },
});
