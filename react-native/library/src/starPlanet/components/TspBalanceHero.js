import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { withTheme } from '../utils/shared';

const BREAKDOWN_LABELS = {
  balance: '余额',
  ruleScore: '规则分',
  streakBonus: '连续奖励',
  redeemTotal: '已兑换',
};

/**
 * TspBalanceHero – Available points hero.
 */
export function TspBalanceHero({
  total = 0,
  breakdown,
  suffix = '分',
  variant = 'default',
  theme,
}) {
  const t = withTheme(theme);
  const rows = breakdown
    ? Object.entries(breakdown).map(([key, value]) => ({
        key,
        label: BREAKDOWN_LABELS[key] || key,
        value,
      }))
    : [];
  const compact = variant === 'compact';

  return (
    <View
      style={[
        styles.root,
        {
          backgroundColor: t.brandSubtle ?? t.selectedFill,
          borderColor: t.borderDefault,
        },
      ]}
    >
      <Text style={[styles.label, { color: t.textSecondary }]}>可用积分</Text>
      <View style={styles.totalRow}>
        <Text
          style={[
            styles.total,
            compact && styles.totalCompact,
            { color: t.textPrimary },
          ]}
        >
          {String(total)}
        </Text>
        {suffix ? (
          <Text style={[styles.suffix, { color: t.textSecondary }]}>{suffix}</Text>
        ) : null}
      </View>
      {rows.length ? (
        <View style={styles.breakdown}>
          {rows.map((row) => (
            <View key={row.key} style={styles.row}>
              <Text style={{ color: t.textSecondary }}>{row.label}</Text>
              <Text style={[styles.rowValue, { color: t.textPrimary }]}>{String(row.value)}</Text>
            </View>
          ))}
        </View>
      ) : null}
    </View>
  );
}

const styles = StyleSheet.create({
  root: {
    width: '100%',
    borderWidth: 1,
    borderRadius: 24,
    padding: 20,
  },
  label: { fontWeight: '700', fontSize: 14 },
  totalRow: {
    flexDirection: 'row',
    alignItems: 'flex-end',
    gap: 6,
    marginTop: 4,
  },
  total: { fontSize: 40, fontWeight: '900', lineHeight: 46 },
  totalCompact: { fontSize: 28, lineHeight: 34 },
  suffix: { fontSize: 16, fontWeight: '700', marginBottom: 6 },
  breakdown: { marginTop: 12, gap: 6 },
  row: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  rowValue: { fontWeight: '800' },
});
