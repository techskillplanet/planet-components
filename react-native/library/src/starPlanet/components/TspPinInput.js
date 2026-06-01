import React from 'react';
import { Text, View } from 'react-native';
import { clamp, styles, withTheme } from '../utils/shared';

export function TspPinInput({ value = '', cellCount = 4, secure = false, theme }) {
  const t = withTheme(theme);
  return <View style={styles.pinRow}>{Array.from({ length: clamp(cellCount, 4, 6) }, (_, index) => <View key={index} style={[styles.pinCell, { backgroundColor: t.surfaceRaised, borderColor: t.borderDefault }]}><Text style={[styles.strong, { color: t.textPrimary }]}>{secure && value[index] ? '•' : value[index] || ''}</Text></View>)}</View>;
}

