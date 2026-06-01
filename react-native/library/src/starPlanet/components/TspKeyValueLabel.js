import React from 'react';
import { Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspKeyValueLabel({ label, value, theme }) {
  const t = withTheme(theme);
  return <View style={styles.keyValue}><Text style={{ color: t.textSecondary }}>{label}</Text><Text style={[styles.strong, { color: t.textPrimary }]}>{value}</Text></View>;
}

