import React from 'react';
import { Pressable, Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspBottomTab({ tabs = [], selectedKey, theme, onSelect }) {
  const t = withTheme(theme);
  return <View style={[styles.bottomTab, { backgroundColor: t.surfaceRaised, borderColor: t.borderDefault }]}>{tabs.map(tab => { const active = tab.key === selectedKey; return <Pressable key={tab.key} onPress={() => onSelect && onSelect(tab.key, tab)} style={styles.bottomItem}><Text style={{ color: active ? t.brandPrimary : t.textTertiary }}>{tab.icon}</Text><Text style={[styles.strong, { color: active ? t.brandPrimary : t.textTertiary }]}>{tab.title || tab.text || tab.key}</Text></Pressable>; })}</View>;
}

