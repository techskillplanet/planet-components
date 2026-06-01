import React from 'react';
import { TextInput } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspInput({ value = '', placeholder = '', variant = 'default', disabled = false, theme, onChange }) {
  const t = withTheme(theme);
  return <TextInput editable={!disabled} value={value} placeholder={placeholder} placeholderTextColor={t.textTertiary} onChangeText={onChange} style={[styles.input, { color: t.textPrimary, backgroundColor: t.surfaceRaised, borderColor: variant === 'error' ? t.danger : t.borderDefault }, disabled && styles.disabled]} />;
}

