import React from 'react';
import { Pressable, ScrollView, Text } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspTabs({ tabs = [], selectedIndex = 0, theme, onSelect }) {
  const t = withTheme(theme);
  return <ScrollView horizontal showsHorizontalScrollIndicator={false} style={[styles.tabs, { borderColor: t.borderDefault, backgroundColor: t.pageEnd }]}>{tabs.map((tab, index) => <Pressable key={String(tab) + '-' + index} onPress={() => onSelect && onSelect(index, tab)} style={[styles.tabItem, index === selectedIndex && { backgroundColor: t.brandPrimary }]}><Text style={[styles.strong, { color: index === selectedIndex ? '#FFFFFF' : t.textSecondary }]}>{tab}</Text></Pressable>)}</ScrollView>;
}

