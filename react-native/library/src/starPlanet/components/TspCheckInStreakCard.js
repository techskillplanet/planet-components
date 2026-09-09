import React from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { withTheme } from '../utils/shared';

/**
 * TspCheckInStreakCard – Continuous check-in summary.
 */
export function TspCheckInStreakCard({
  streakDays = 0,
  totalDays = 0,
  weekProgress = 0,
  disabled = false,
  theme,
  onOpen,
}) {
  const t = withTheme(theme);
  const pct = Math.max(0, Math.min(100, Math.round(Number(weekProgress) * 100)));

  return (
    <Pressable
      disabled={disabled}
      onPress={disabled ? undefined : () => onOpen?.()}
      style={[
        styles.root,
        {
          backgroundColor: t.surfaceRaised,
          borderColor: t.borderDefault,
        },
        disabled && styles.disabled,
      ]}
    >
      <Text style={[styles.title, { color: t.textPrimary }]}>连续打卡</Text>
      <View style={styles.stats}>
        <Text style={[styles.stat, { color: t.textSecondary }]}>{`连续 ${streakDays} 天`}</Text>
        <Text style={[styles.stat, { color: t.textSecondary }]}>{`累计 ${totalDays} 天`}</Text>
        <Text style={[styles.stat, { color: t.textSecondary }]}>{`本周 ${pct}%`}</Text>
      </View>
    </Pressable>
  );
}

const styles = StyleSheet.create({
  root: {
    width: '100%',
    borderWidth: 1,
    borderRadius: 20,
    padding: 16,
    gap: 10,
  },
  title: { fontWeight: '900', fontSize: 16 },
  stats: { flexDirection: 'row', flexWrap: 'wrap', gap: 12 },
  stat: { fontSize: 13, fontWeight: '700' },
  disabled: { opacity: 0.45 },
});
