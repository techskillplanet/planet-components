import React from 'react';
import { Switch, Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspSwitch({ text, checked = false, disabled = false, loading = false, theme, onChange }) {
  const t = withTheme(theme);
  return <View style={styles.inline}><Switch disabled={disabled || loading} value={checked} onValueChange={onChange} trackColor={{ false: t.borderDefault, true: t.brandPrimary }} />{!!text && <Text style={[styles.strong, { color: t.textPrimary }]}>{text}</Text>}</View>;
}

