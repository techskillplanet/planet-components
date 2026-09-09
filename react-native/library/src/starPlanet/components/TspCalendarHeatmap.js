import React from 'react';
import { Pressable, StyleSheet, Text, View } from 'react-native';
import { withTheme } from '../utils/shared';

const WEEKDAYS = ['一', '二', '三', '四', '五', '六', '日'];
const LEVELS = [
  { level: 'full', label: '全勤' },
  { level: 'partial', label: '部分' },
  { level: 'none', label: '未打' },
  { level: 'exempt', label: '豁免' },
];

function parseYearMonth(yearMonth) {
  const [y, m] = String(yearMonth || '').split('-').map(Number);
  if (!y || !m) {
    const now = new Date();
    return { year: now.getFullYear(), month: now.getMonth() + 1 };
  }
  return { year: y, month: m };
}

function cellColors(level, t) {
  if (level === 'full') {
    return { backgroundColor: t.success, borderColor: t.success, color: '#FFFFFF' };
  }
  if (level === 'partial') {
    return { backgroundColor: t.warning, borderColor: t.warning, color: t.textPrimary };
  }
  if (level === 'exempt') {
    return { backgroundColor: t.brandPrimary, borderColor: t.brandPrimary, color: '#FFFFFF' };
  }
  return {
    backgroundColor: t.pageEnd,
    borderColor: t.borderDefault,
    color: t.textTertiary,
  };
}

/**
 * TspCalendarHeatmap – Month attendance heatmap.
 */
export function TspCalendarHeatmap({
  yearMonth,
  cells = [],
  showLegend = true,
  theme,
  onSelectDay,
}) {
  const t = withTheme(theme);
  const { year, month } = parseYearMonth(yearMonth);
  const levelMap = new Map(cells.map((c) => [c.date, c.level || 'none']));
  const first = new Date(year, month - 1, 1);
  const daysInMonth = new Date(year, month, 0).getDate();
  const startPad = (first.getDay() + 6) % 7;
  const slots = [];
  for (let i = 0; i < startPad; i += 1) slots.push(null);
  for (let d = 1; d <= daysInMonth; d += 1) {
    const date = `${year}-${String(month).padStart(2, '0')}-${String(d).padStart(2, '0')}`;
    slots.push({ day: d, date, level: levelMap.get(date) || 'none' });
  }

  return (
    <View style={styles.root}>
      {showLegend ? (
        <View style={styles.legend}>
          {LEVELS.map((item) => {
            const colors = cellColors(item.level, t);
            return (
              <View
                key={item.level}
                style={[
                  styles.legendItem,
                  {
                    backgroundColor: colors.backgroundColor,
                    borderColor: colors.borderColor,
                  },
                ]}
              >
                <Text style={[styles.legendText, { color: colors.color }]}>{item.label}</Text>
              </View>
            );
          })}
        </View>
      ) : null}
      <View style={styles.weekdays}>
        {WEEKDAYS.map((w) => (
          <Text key={w} style={[styles.weekday, { color: t.textTertiary }]}>
            {w}
          </Text>
        ))}
      </View>
      <View style={styles.grid}>
        {slots.map((slot, index) => {
          if (!slot) {
            return <View key={`empty-${index}`} style={[styles.cell, styles.cellEmpty]} />;
          }
          const colors = cellColors(slot.level, t);
          return (
            <Pressable
              key={slot.date}
              onPress={() => onSelectDay?.(slot.date, slot.level)}
              style={[
                styles.cell,
                {
                  backgroundColor: colors.backgroundColor,
                  borderColor: colors.borderColor,
                },
              ]}
            >
              <Text style={[styles.cellText, { color: colors.color }]}>{slot.day}</Text>
            </Pressable>
          );
        })}
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  root: { gap: 10 },
  legend: { flexDirection: 'row', flexWrap: 'wrap', gap: 8 },
  legendItem: {
    borderWidth: 1,
    borderRadius: 999,
    paddingHorizontal: 10,
    paddingVertical: 4,
  },
  legendText: { fontSize: 11, fontWeight: '800' },
  weekdays: { flexDirection: 'row' },
  weekday: {
    width: `${100 / 7}%`,
    textAlign: 'center',
    fontSize: 12,
    fontWeight: '700',
  },
  grid: { flexDirection: 'row', flexWrap: 'wrap' },
  cell: {
    width: `${100 / 7}%`,
    aspectRatio: 1,
    borderWidth: 1,
    borderRadius: 10,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 4,
  },
  cellEmpty: { borderWidth: 0, backgroundColor: 'transparent' },
  cellText: { fontSize: 13, fontWeight: '800' },
});
