import React from 'react';
import { View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspStickyFooter({ children, content, theme }) {
  const t = withTheme(theme);
  return <View style={[styles.stickyDemo, { backgroundColor: t.surfaceRaised, borderColor: t.borderDefault }]}>{children || content}</View>;
}

