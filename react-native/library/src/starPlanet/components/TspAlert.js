import React from 'react';
import { Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspAlert({ title, message, variant = 'info', theme }) {
  const t = withTheme(theme);
  const fill = variant === 'success' ? t.selectedFill : variant === 'warning' ? t.activeFill : variant === 'error' ? '#FF6B7A1F' : t.pageEnd;
  const border = variant === 'success' ? t.success : variant === 'warning' ? t.warning : variant === 'error' ? t.danger : t.borderDefault;
  return <View style={[styles.alert, { backgroundColor: fill, borderColor: border }]}>{!!title && <Text style={[styles.strong, { color: t.textPrimary }]}>{title}</Text>}{!!message && <Text style={[styles.secondary, { color: t.textSecondary }]}>{message}</Text>}</View>;
}

