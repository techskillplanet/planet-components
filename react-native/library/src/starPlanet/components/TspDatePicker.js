import React from 'react';
import { StyleSheet, TextInput } from 'react-native';
import { withTheme } from '../utils/shared';

/**
 * TspDatePicker – Themed YYYY-MM-DD date field (text input; native picker optional upstream).
 * `min` / `max` kept for API parity with React Web; validate in onChange if needed.
 */
export function TspDatePicker({
  value = '',
  min: _min,
  max: _max,
  placeholder = 'YYYY-MM-DD',
  disabled = false,
  theme,
  onChange,
}) {
  const t = withTheme(theme);
  return (
    <TextInput
      editable={!disabled}
      value={value || ''}
      placeholder={placeholder}
      placeholderTextColor={t.textTertiary}
      keyboardType="numbers-and-punctuation"
      autoCapitalize="none"
      autoCorrect={false}
      onChangeText={(next) => onChange?.(next)}
      style={[
        styles.field,
        {
          color: t.textPrimary,
          backgroundColor: t.surfaceRaised,
          borderColor: t.borderDefault,
        },
        disabled && styles.disabled,
      ]}
      accessibilityLabel="date"
    />
  );
}

const styles = StyleSheet.create({
  field: {
    minHeight: 48,
    borderWidth: 1,
    borderRadius: 16,
    paddingHorizontal: 14,
    fontSize: 15,
    fontWeight: '700',
  },
  disabled: { opacity: 0.45 },
});
