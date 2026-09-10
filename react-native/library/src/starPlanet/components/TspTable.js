import React from 'react';
import { ScrollView, Text, View, StyleSheet } from 'react-native';
import { withTheme } from '../utils/shared';

function cellText(row, col, index) {
  if (row == null) return '';
  if (typeof row === 'string' || typeof row === 'number') return String(row);
  if (Array.isArray(row)) return String(row[index] ?? '');
  const key = col?.key ?? col?.id ?? index;
  return String(row[key] ?? '');
}

/** Lightweight data table inside horizontal ScrollView. */
export function TspTable({
  columns = [],
  rows = [],
  variant = 'default',
  emptyText = '暂无数据',
  theme,
}) {
  const t = withTheme(theme);
  const cols = Array.isArray(columns) ? columns : [];
  const data = Array.isArray(rows) ? rows : [];
  const headers = cols.map((c) => (typeof c === 'string' ? c : c.title ?? c.label ?? c.key ?? ''));
  const striped = variant === 'striped';
  const colCount = Math.max(headers.length, 1);

  return (
    <View
      style={[
        local.wrap,
        { backgroundColor: t.surfaceRaised, borderColor: t.borderDefault },
      ]}
      accessibilityRole="summary"
      accessibilityLabel="Table"
    >
      <ScrollView horizontal showsHorizontalScrollIndicator>
        <View style={local.table}>
          <View style={[local.row, local.headerRow, { borderBottomColor: t.borderDefault }]}>
            {(headers.length ? headers : ['']).map((title, i) => (
              <Text
                key={i}
                style={[
                  local.cell,
                  local.headerCell,
                  { color: t.textSecondary, width: Math.max(96, 280 / colCount) },
                ]}
                numberOfLines={2}
              >
                {title}
              </Text>
            ))}
          </View>

          {data.length ? (
            data.map((row, ri) => (
              <View
                key={ri}
                style={[
                  local.row,
                  {
                    borderBottomColor: t.borderDefault,
                    backgroundColor: striped && ri % 2 === 1 ? t.surfaceSubtle : 'transparent',
                  },
                ]}
              >
                {cols.map((col, ci) => (
                  <Text
                    key={ci}
                    style={[
                      local.cell,
                      { color: t.textPrimary, width: Math.max(96, 280 / colCount) },
                    ]}
                    numberOfLines={3}
                  >
                    {cellText(row, col, ci)}
                  </Text>
                ))}
              </View>
            ))
          ) : (
            <View style={local.emptyRow}>
              <Text style={{ color: t.textTertiary, fontWeight: '600' }}>{emptyText}</Text>
            </View>
          )}
        </View>
      </ScrollView>
    </View>
  );
}

const local = StyleSheet.create({
  wrap: {
    width: '100%',
    borderWidth: 1,
    borderRadius: 16,
    overflow: 'hidden',
  },
  table: { minWidth: '100%' },
  row: {
    flexDirection: 'row',
    borderBottomWidth: StyleSheet.hairlineWidth,
    alignItems: 'stretch',
  },
  headerRow: { backgroundColor: 'transparent' },
  cell: {
    paddingHorizontal: 12,
    paddingVertical: 10,
    fontSize: 13,
    fontWeight: '600',
  },
  headerCell: { fontWeight: '800', fontSize: 12 },
  emptyRow: {
    minHeight: 56,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 16,
  },
});
