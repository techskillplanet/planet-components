import React from 'react';
import { Text } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspTextLink({ text, inverse = false, theme, onPress }) {
  const t = withTheme(theme);
  return <Text onPress={onPress} style={[styles.link, { color: inverse ? '#FFFFFF' : t.brandPrimary }]}>{text}</Text>;
}

