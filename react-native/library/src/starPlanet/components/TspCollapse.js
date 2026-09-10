import React from 'react';
import { Pressable, Text, View, StyleSheet } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

/** Expandable collapse / accordion panel. */
export function TspCollapse({
  title = '',
  message = '',
  expanded = false,
  disabled = false,
  variant = 'default',
  theme,
  onChange,
}) {
  const t = withTheme(theme);
  return (
    <View
      style={[
        local.root,
        {
          borderColor: expanded ? t.brandPrimary : t.borderDefault,
          backgroundColor: t.surfaceRaised,
        },
        variant === 'subtle' && { backgroundColor: t.surfaceSubtle },
        disabled && shared.disabled,
      ]}
    >
      <Pressable
        accessibilityRole="button"
        accessibilityState={{ expanded, disabled }}
        disabled={disabled}
        onPress={() => onChange?.(!expanded)}
        style={local.header}
      >
        <Text style={[local.title, { color: t.textPrimary }]} numberOfLines={2}>
          {title}
        </Text>
        <View style={[local.mark, { borderColor: t.borderDefault }]}>
          <Text style={{ color: t.brandPrimary, fontWeight: '800' }}>{expanded ? '−' : '+'}</Text>
        </View>
      </Pressable>
      {expanded ? (
        <Text style={[local.body, { color: t.textSecondary }]}>{message}</Text>
      ) : null}
    </View>
  );
}

const local = StyleSheet.create({
  root: { borderWidth: 1, borderRadius: 16, overflow: 'hidden' },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    gap: 12,
    paddingHorizontal: 16,
    paddingVertical: 14,
  },
  title: { flex: 1, fontSize: 15, fontWeight: '800' },
  mark: {
    width: 28,
    height: 28,
    borderRadius: 14,
    borderWidth: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  body: { paddingHorizontal: 16, paddingBottom: 14, fontSize: 14, fontWeight: '600' },
});
