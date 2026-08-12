import React from 'react';
import { Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspBadge({ text, variant = 'default', disabled = false, theme }) {
  const t = withTheme(theme);
  const fill = variant === 'primary' ? t.brandPrimary : variant === 'success' ? t.success : variant === 'warning' ? t.warning : variant === 'danger' ? t.danger : t.pageEnd;
  const color = ['primary', 'success', 'danger'].includes(variant) ? '#FFFFFF' : t.textPrimary;
  return (
    <View style={[styles.badge, { backgroundColor: fill }, disabled && styles.disabled]}>
      <Text style={[styles.badgeText, { color }]}>{text}</Text>
    </View>
  );
}
