import React from 'react';
import { View } from 'react-native';
import { clamp, styles, withTheme } from '../utils/shared';

export function TspProgress({ progress = 0, variant = 'primary', theme }) {
  const t = withTheme(theme);
  const fill = variant === 'success' ? t.success : variant === 'warning' ? t.warning : variant === 'danger' ? t.danger : t.brandPrimary;
  return <View style={[styles.progress, { backgroundColor: t.borderDefault }]}><View style={[styles.progressFill, { width: clamp(progress, 0, 100) + '%', backgroundColor: fill }]} /></View>;
}

