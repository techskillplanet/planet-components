import React from 'react';
import { Pressable, Text, View, StyleSheet } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

/** Checkbox with brand checkmark box + label. */
export function TspCheckbox({
  text = '',
  checked = false,
  disabled = false,
  variant = 'default',
  theme,
  onChange,
}) {
  const t = withTheme(theme);
  return (
    <Pressable
      accessibilityRole="checkbox"
      accessibilityLabel={text || 'Checkbox'}
      accessibilityState={{ checked, disabled }}
      disabled={disabled}
      onPress={() => onChange?.(!checked)}
      style={[local.root, disabled && shared.disabled]}
    >
      <View
        style={[
          local.box,
          {
            borderColor: checked ? t.brandPrimary : t.borderDefault,
            backgroundColor: checked ? t.brandPrimary : t.surfaceRaised,
          },
          variant === 'subtle' && { backgroundColor: checked ? t.brandSubtle : t.surfaceRaised },
        ]}
      >
        {checked ? <Text style={local.check}>✓</Text> : null}
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
  box: {
    width: 22,
    height: 22,
    borderRadius: 6,
    borderWidth: 2,
    alignItems: 'center',
    justifyContent: 'center',
  },
  check: { color: '#fff', fontSize: 14, fontWeight: '800', lineHeight: 16 },
  label: { fontSize: 14, fontWeight: '700', flexShrink: 1 },
});
