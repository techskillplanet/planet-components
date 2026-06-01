import React from 'react';
import { Pressable, Text } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspChip({ text, selected = false, disabled = false, theme, onPress }) {
  const t = withTheme(theme);
  return <Pressable disabled={disabled} onPress={onPress} style={[styles.chip, { backgroundColor: selected ? t.selectedFill : t.surfaceRaised, borderColor: selected ? t.success : t.borderDefault }, disabled && styles.disabled]}><Text style={[styles.strong, { color: t.textPrimary }]}>{text}</Text></Pressable>;
}

