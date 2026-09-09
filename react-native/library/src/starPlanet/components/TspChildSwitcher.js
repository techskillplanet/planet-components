import React from 'react';
import { Image, Pressable, ScrollView, StyleSheet, Text, View } from 'react-native';
import { withTheme } from '../utils/shared';

/**
 * TspChildSwitcher – Single-select child chips/tabs.
 * Item label resolves from label | name | text.
 */
export function TspChildSwitcher({
  items = [],
  selectedId,
  variant = 'chip',
  disabled = false,
  theme,
  onChange,
}) {
  const t = withTheme(theme);
  const useTabs = variant === 'tabs';

  const row = items.map((item) => {
    const id = item?.id;
    const label = item?.label ?? item?.name ?? item?.text ?? '';
    const selected = String(id) === String(selectedId);
    const leading = item?.leading
      ? item.leading
      : item?.iconSrc
        ? (
          <Image
            source={{ uri: item.iconSrc }}
            style={styles.avatar}
            accessibilityIgnoresInvertColors
          />
        )
        : item?.emoji
          ? <Text style={styles.emoji}>{item.emoji}</Text>
          : null;

    return (
      <Pressable
        key={String(id)}
        disabled={disabled}
        onPress={disabled ? undefined : () => onChange?.(id)}
        style={[
          useTabs ? styles.tab : styles.chip,
          useTabs && styles.tabFlex,
          {
            backgroundColor: selected ? t.brandPrimary : t.surfaceRaised,
            borderColor: selected ? t.brandPrimary : t.borderDefault,
          },
          disabled && styles.disabled,
        ]}
        accessibilityRole="tab"
        accessibilityState={{ selected, disabled }}
      >
        {leading}
        <Text
          style={[styles.label, { color: selected ? '#FFFFFF' : t.textSecondary }]}
          numberOfLines={1}
        >
          {label}
        </Text>
      </Pressable>
    );
  });

  if (useTabs) {
    return <View style={styles.row}>{row}</View>;
  }

  return (
    <ScrollView horizontal showsHorizontalScrollIndicator={false} contentContainerStyle={styles.row}>
      {row}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  row: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
    paddingVertical: 4,
  },
  chip: {
    minHeight: 44,
    paddingHorizontal: 14,
    borderRadius: 999,
    borderWidth: 1,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    gap: 8,
  },
  tab: {
    minHeight: 44,
    paddingHorizontal: 14,
    borderRadius: 14,
    borderWidth: 1,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    gap: 8,
  },
  tabFlex: { flex: 1 },
  avatar: {
    width: 22,
    height: 22,
    borderRadius: 8,
    backgroundColor: '#FFFFFF',
  },
  emoji: { fontSize: 16 },
  label: { fontWeight: '800', fontSize: 14 },
  disabled: { opacity: 0.45 },
});
