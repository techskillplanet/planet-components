import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { withTheme } from '../utils/shared';

/**
 * TspPrintSheet – A4-style dictation / fill-in print layout.
 */
export function TspPrintSheet({
  title = '',
  items = [],
  columns = 5,
  footerFields = ['姓名', '日期', '得分'],
  variant = 'pinyin',
  theme,
}) {
  const t = withTheme(theme);
  const cols = Math.max(1, Number(columns) || 5);
  const cellWidth = `${100 / cols}%`;

  return (
    <View
      style={[
        styles.root,
        {
          backgroundColor: t.surfaceRaised,
          borderColor: t.borderDefault,
        },
      ]}
      accessibilityLabel={`print-sheet-${variant}`}
    >
      {title ? (
        <Text style={[styles.title, { color: t.textPrimary }]}>{title}</Text>
      ) : null}
      <View style={styles.grid}>
        {items.map((item, index) => {
          const prompt = typeof item === 'string' ? item : item?.prompt ?? '';
          return (
            <View key={index} style={[styles.cell, { width: cellWidth }]}>
              <Text style={[styles.prompt, { color: t.textSecondary }]}>{prompt}</Text>
              <View style={styles.blank} />
            </View>
          );
        })}
      </View>
      {footerFields?.length ? (
        <View style={styles.footer}>
          {footerFields.map((field) => (
            <Text key={field} style={[styles.footerField, { color: t.textPrimary }]}>
              {`${field}：________`}
            </Text>
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
    borderRadius: 12,
    padding: 16,
  },
  title: {
    textAlign: 'center',
    marginBottom: 12,
    fontSize: 16,
    fontWeight: '900',
  },
  grid: {
    flexDirection: 'row',
    flexWrap: 'wrap',
  },
  cell: {
    paddingVertical: 8,
    paddingHorizontal: 4,
  },
  prompt: {
    fontSize: 11,
    textAlign: 'center',
    minHeight: 16,
  },
  blank: {
    minHeight: 22,
    borderTopWidth: StyleSheet.hairlineWidth,
    borderTopColor: '#999999',
    marginTop: 6,
  },
  footer: {
    marginTop: 16,
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'space-between',
    gap: 8,
  },
  footerField: {
    fontSize: 13,
    fontWeight: '700',
  },
});
