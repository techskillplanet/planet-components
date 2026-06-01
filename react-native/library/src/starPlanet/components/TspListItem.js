import React from 'react';
import { Pressable, Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspListItem({ title, message, trailing, selected = false, disabled = false, theme, onPress }) {
  const t = withTheme(theme);
  return <Pressable disabled={disabled} onPress={onPress} style={[styles.listItem, { backgroundColor: selected ? t.selectedFill : t.surfaceRaised, borderColor: selected ? t.success : t.borderDefault }, disabled && styles.disabled]}><View style={{ flex: 1 }}><Text style={[styles.strong, { color: t.textPrimary }]}>{title}</Text>{!!message && <Text style={{ color: t.textSecondary, marginTop: 4 }}>{message}</Text>}</View>{!!trailing && <Text style={{ color: t.textTertiary, fontSize: 20 }}>{trailing}</Text>}</Pressable>;
}

