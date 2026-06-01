import React from 'react';
import { Pressable, Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspTopBar({ title, showBack = false, theme, onBack }) {
  const t = withTheme(theme);
  return <View style={[styles.topBar, { backgroundColor: t.pageStart, borderBottomColor: t.borderDefault }]}><Pressable onPress={onBack} style={styles.topBack}><Text style={{ color: t.textPrimary, fontSize: 28 }}>{showBack ? '‹' : ''}</Text></Pressable><Text style={[styles.title, { color: t.textPrimary }]}>{title}</Text><View style={styles.topBack} /></View>;
}

