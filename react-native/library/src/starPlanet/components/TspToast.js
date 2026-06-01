import React from 'react';
import { Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspToast({ message, variant = 'info', theme }) {
  const t = withTheme(theme);
  const fill = variant === 'success' ? t.success : variant === 'warning' ? t.warning : variant === 'error' ? t.danger : t.brandDark;
  return <View style={[styles.toast, { backgroundColor: fill }]}><Text style={{ color: variant === 'warning' ? t.textPrimary : '#FFFFFF', fontWeight: '800' }}>{message}</Text></View>;
}

