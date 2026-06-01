import React from 'react';
import { Pressable, Text } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspIconButton({ icon, selected = false, disabled = false, theme, onPress }) {
  const t = withTheme(theme);
  return <Pressable disabled={disabled} onPress={onPress} style={[styles.iconButton, { backgroundColor: selected ? t.brandPrimary : t.surfaceRaised, borderColor: t.borderDefault }, disabled && styles.disabled]}><Text style={[styles.strong, { color: selected ? '#FFFFFF' : t.textPrimary }]}>{icon}</Text></Pressable>;
}

