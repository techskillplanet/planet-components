import React from 'react';
import { Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspAmount({ symbol = '¥', value, cycle = '', symbolAfter = false, strikeThrough = false, theme }) {
  const t = withTheme(theme);
  const line = strikeThrough ? 'line-through' : 'none';
  return <View style={styles.amount}>{!symbolAfter && <Text style={[styles.amountSymbol, { color: t.textPrimary, textDecorationLine: line }]}>{symbol}</Text>}<Text style={[styles.amountValue, { color: t.textPrimary, textDecorationLine: line }]}>{value}</Text>{symbolAfter && <Text style={[styles.amountSymbol, { color: t.textPrimary, textDecorationLine: line }]}>{symbol}</Text>}{!!cycle && <Text style={[styles.amountCycle, { color: t.textSecondary }]}>/{cycle}</Text>}</View>;
}

