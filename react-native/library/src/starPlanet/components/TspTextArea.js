import React from 'react';
import { TextInput, StyleSheet } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

/** Multi-line TextInput. */
export function TspTextArea({
  value = '',
  placeholder = '',
  rows = 3,
  maxLength,
  disabled = false,
  variant = 'default',
  theme,
  onChange,
}) {
  const t = withTheme(theme);
  const lineCount = Math.max(2, Math.min(12, Number(rows) || 3));
  const minHeight = 22 * lineCount + 24;

  return (
    <TextInput
      accessibilityLabel={placeholder || 'Text area'}
      value={value}
      placeholder={placeholder}
      placeholderTextColor={t.textTertiary}
      editable={!disabled}
      multiline
      numberOfLines={lineCount}
      maxLength={maxLength != null ? Number(maxLength) : undefined}
      textAlignVertical="top"
      onChangeText={text => onChange?.(text)}
      style={[
        local.input,
        {
          minHeight,
          color: t.textPrimary,
          backgroundColor: t.surfaceRaised,
          borderColor: variant === 'error' ? t.danger : t.borderDefault,
        },
        disabled && shared.disabled,
      ]}
    />
  );
}

const local = StyleSheet.create({
  input: {
    width: '100%',
    borderWidth: 1,
    borderRadius: 16,
    paddingHorizontal: 14,
    paddingVertical: 12,
    fontSize: 15,
    fontWeight: '600',
    lineHeight: 22,
  },
});
