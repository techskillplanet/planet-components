import React from 'react';
import { Text, View } from 'react-native';
import { TspButton } from './TspButton';
import { styles, withTheme } from '../utils/shared';

export function TspEmpty({ title, message, actionText, theme, onAction }) {
  const t = withTheme(theme);
  return <View style={[styles.empty, { backgroundColor: t.surfaceRaised, borderColor: t.borderDefault }]}><View style={[styles.emptyMark, { backgroundColor: t.brandPrimary }]}><Text style={{ color: '#FFFFFF' }}>○</Text></View><Text style={[styles.strong, { color: t.textPrimary }]}>{title}</Text><Text style={{ color: t.textSecondary }}>{message}</Text>{!!actionText && <TspButton text={actionText} variant="primary" fullWidth={false} theme={t} onPress={onAction} />}</View>;
}

