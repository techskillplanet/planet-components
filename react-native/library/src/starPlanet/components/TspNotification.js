import React from 'react';
import { Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspNotification({ title, message, variant = 'info', theme }) {
  const t = withTheme(theme);
  return <View style={[styles.alert, { backgroundColor: variant === 'alert' ? t.activeFill : t.pageEnd, borderColor: variant === 'alert' ? t.warning : t.borderDefault }]}><Text style={[styles.strong, { color: t.textPrimary }]}>{title}</Text><Text style={[styles.secondary, { color: t.textSecondary }]}>{message}</Text></View>;
}

