import React from 'react';
import { View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspCard({ children, selected = false, disabled = false, theme }) {
  const t = withTheme(theme);
  return <View style={[styles.card, { backgroundColor: selected ? t.selectedFill : t.surfaceRaised, borderColor: selected ? t.success : t.borderDefault }, disabled && styles.disabled]}>{children}</View>;
}

