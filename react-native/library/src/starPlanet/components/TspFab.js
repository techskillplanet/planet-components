import React from 'react';
import { Pressable, Text, StyleSheet } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

/** Floating action button — circular icon or extended with text. */
export function TspFab({
  icon = '+',
  text = '',
  variant = 'primary',
  disabled = false,
  theme,
  onTap,
}) {
  const t = withTheme(theme);
  const primary = variant !== 'default';
  const extended = !!text;

  return (
    <Pressable
      accessibilityRole="button"
      accessibilityLabel={text || String(icon)}
      accessibilityState={{ disabled }}
      disabled={disabled}
      onPress={() => onTap?.()}
      style={[
        local.root,
        extended ? local.extended : local.circle,
        {
          backgroundColor: primary ? t.brandPrimary : t.surfaceRaised,
          borderColor: primary ? t.brandPrimary : t.borderDefault,
        },
        disabled && shared.disabled,
      ]}
    >
      <Text
        style={[local.icon, { color: primary ? '#FFFFFF' : t.textPrimary }]}
        accessibilityElementsHidden
        importantForAccessibility="no"
      >
        {icon}
      </Text>
      {extended ? (
        <Text style={[local.label, { color: primary ? '#FFFFFF' : t.textPrimary }]} numberOfLines={1}>
          {text}
        </Text>
      ) : null}
    </Pressable>
  );
}

const local = StyleSheet.create({
  root: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    borderWidth: 1,
    borderRadius: 999,
    gap: 8,
    elevation: 4,
    shadowColor: '#173A62',
    shadowOpacity: 0.18,
    shadowRadius: 8,
    shadowOffset: { width: 0, height: 4 },
  },
  circle: { width: 56, height: 56 },
  extended: { minHeight: 56, paddingHorizontal: 20 },
  icon: { fontSize: 22, fontWeight: '800', lineHeight: 26 },
  label: { fontSize: 15, fontWeight: '800' },
});
