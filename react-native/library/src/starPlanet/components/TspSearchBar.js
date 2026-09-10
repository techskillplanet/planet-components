import React from 'react';
import { Text, TextInput, View, StyleSheet } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

/** Pill search field. */
export function TspSearchBar({
  value = '',
  placeholder = 'Search…',
  disabled = false,
  variant = 'default',
  theme,
  onChange,
}) {
  const t = withTheme(theme);
  return (
    <View
      style={[
        local.root,
        {
          borderColor: variant === 'error' ? t.danger : t.borderDefault,
          backgroundColor: t.surfaceRaised,
        },
        disabled && shared.disabled,
      ]}
    >
      <Text style={[local.icon, { color: t.textTertiary }]} accessibilityElementsHidden>
        ⌕
      </Text>
      <TextInput
        value={value}
        placeholder={placeholder}
        placeholderTextColor={t.textTertiary}
        editable={!disabled}
        onChangeText={text => onChange?.(text)}
        style={[local.input, { color: t.textPrimary }]}
      />
    </View>
  );
}

const local = StyleSheet.create({
  root: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
    minHeight: 44,
    paddingHorizontal: 14,
    borderRadius: 999,
    borderWidth: 1,
  },
  icon: { fontSize: 16, fontWeight: '700' },
  input: { flex: 1, minWidth: 0, fontSize: 15, fontWeight: '600', paddingVertical: 8 },
});
