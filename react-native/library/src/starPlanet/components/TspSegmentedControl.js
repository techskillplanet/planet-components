import React from 'react';
import { Pressable, Text, View, StyleSheet } from 'react-native';
import { optionText, styles as shared, withTheme } from '../utils/shared';

/**
 * Segmented control.
 * @param {Array<string|{label:string,value?:string}>} [props.options]
 */
export function TspSegmentedControl({
  options = [],
  selectedIndex = 0,
  disabled = false,
  variant = 'default',
  theme,
  onSelect,
}) {
  const t = withTheme(theme);
  const items = options.map((opt, index) => {
    if (typeof opt === 'string') return { label: opt, value: opt, index };
    return {
      label: optionText(opt) || String(opt?.label ?? ''),
      value: opt?.value ?? String(index),
      index,
    };
  });

  return (
    <View
      accessibilityRole="tablist"
      style={[
        local.root,
        {
          backgroundColor: t.surfaceSubtle || t.pageEnd,
          borderColor: t.borderDefault,
        },
        variant === 'raised' && { backgroundColor: t.surfaceRaised },
        disabled && shared.disabled,
      ]}
    >
      {items.map((item, index) => {
        const selected = index === selectedIndex;
        return (
          <Pressable
            key={`${item.value}-${index}`}
            accessibilityRole="tab"
            accessibilityState={{ selected, disabled }}
            disabled={disabled}
            onPress={() => onSelect?.(index, item.label, item.value)}
            style={[
              local.item,
              selected && { backgroundColor: t.brandPrimary },
            ]}
          >
            <Text
              style={[
                local.label,
                { color: selected ? '#fff' : t.textSecondary },
              ]}
              numberOfLines={1}
            >
              {item.label}
            </Text>
          </Pressable>
        );
      })}
    </View>
  );
}

const local = StyleSheet.create({
  root: {
    flexDirection: 'row',
    width: '100%',
    gap: 4,
    padding: 4,
    borderRadius: 999,
    borderWidth: 1,
  },
  item: {
    flex: 1,
    minHeight: 36,
    borderRadius: 999,
    paddingHorizontal: 10,
    paddingVertical: 8,
    alignItems: 'center',
    justifyContent: 'center',
  },
  label: { fontSize: 14, fontWeight: '800' },
});
