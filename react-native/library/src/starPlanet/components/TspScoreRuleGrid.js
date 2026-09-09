import React from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { withTheme } from '../utils/shared';

/**
 * TspScoreRuleGrid – Score rule cards with optional +1.
 */
export function TspScoreRuleGrid({
  rules = [],
  columns = 'auto',
  variant = 'default',
  disabled = false,
  theme,
  onIncrement,
}) {
  const t = withTheme(theme);
  const readOnly = variant === 'readOnly' || disabled;
  const colCount = columns === 1 || columns === '1' ? 1 : 2;

  return (
    <View style={styles.grid}>
      {rules.map((rule) => {
        const value = Number(rule.value) || 0;
        const count = Number(rule.count) || 0;
        const limit = rule.dailyLimit == null ? null : Number(rule.dailyLimit);
        const atLimit = limit != null && count >= limit;
        return (
          <View
            key={String(rule.id ?? rule.name)}
            style={[
              styles.card,
              {
                width: colCount === 1 ? '100%' : '48%',
                backgroundColor: t.surfaceRaised,
                borderColor: t.borderDefault,
              },
            ]}
          >
            <View style={styles.head}>
              {rule.icon ? <Text>{rule.icon}</Text> : null}
              <Text style={[styles.name, { color: t.textPrimary }]} numberOfLines={1}>
                {rule.name || ''}
              </Text>
            </View>
            <Text
              style={[
                styles.value,
                { color: value >= 0 ? t.success : t.danger },
              ]}
            >
              {value > 0 ? `+${value}` : String(value)}
            </Text>
            <Text style={[styles.meta, { color: t.textTertiary }]}>
              {limit == null ? `已记 ${count}` : `已记 ${count}/${limit}`}
            </Text>
            {readOnly ? null : (
              <Pressable
                disabled={atLimit || disabled}
                onPress={() => onIncrement?.(rule)}
                style={[
                  styles.add,
                  {
                    backgroundColor: t.brandSubtle ?? t.selectedFill,
                    borderColor: t.borderDefault,
                  },
                  (atLimit || disabled) && styles.disabled,
                ]}
              >
                <Text style={[styles.addText, { color: t.brandPrimary }]}>+1</Text>
              </Pressable>
            )}
          </View>
        );
      })}
    </View>
  );
}

const styles = StyleSheet.create({
  grid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    gap: 12,
    justifyContent: 'space-between',
  },
  card: {
    borderWidth: 1,
    borderRadius: 18,
    padding: 14,
    gap: 6,
  },
  head: { flexDirection: 'row', alignItems: 'center', gap: 6 },
  name: { flex: 1, fontWeight: '800', fontSize: 14 },
  value: { fontSize: 22, fontWeight: '900' },
  meta: { fontSize: 12 },
  add: {
    marginTop: 4,
    alignSelf: 'flex-start',
    borderWidth: 1,
    borderRadius: 999,
    paddingHorizontal: 12,
    paddingVertical: 6,
  },
  addText: { fontWeight: '900', fontSize: 13 },
  disabled: { opacity: 0.4 },
});
