import React from 'react';
import { Pressable, Text, View, StyleSheet } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

function tagColors(t, variant) {
  switch (variant) {
    case 'primary':
      return { bg: t.brandSubtle, color: t.brandPrimary, border: 'transparent' };
    case 'success':
      return { bg: t.successSubtle, color: t.success, border: 'transparent' };
    case 'warning':
      return { bg: t.activeFill, color: t.textPrimary, border: 'transparent' };
    case 'danger':
      return { bg: '#FFE8EB', color: t.danger, border: 'transparent' };
    default:
      return { bg: t.surfaceRaised, color: t.textPrimary, border: t.borderDefault };
  }
}

/** Closable / selectable tag (Chip remains for filter chips). */
export function TspTag({
  text = '',
  closable = false,
  selected = false,
  disabled = false,
  variant = 'default',
  theme,
  onClose,
  onTap,
}) {
  const t = withTheme(theme);
  const colors = tagColors(t, variant);

  const Root = onTap ? Pressable : View;
  const rootProps = onTap
    ? {
        accessibilityRole: 'button',
        accessibilityState: { selected, disabled },
        disabled,
        onPress: () => {
          if (!disabled) onTap?.();
        },
      }
    : { accessibilityRole: 'text' };

  return (
    <Root
      accessibilityLabel={text || 'Tag'}
      {...rootProps}
      style={[
        local.root,
        {
          backgroundColor: colors.bg,
          borderColor: selected ? t.selectedBorder : colors.border,
          borderWidth: selected ? 2 : 1,
        },
        disabled && shared.disabled,
      ]}
    >
      <Text style={[local.text, { color: colors.color }]} numberOfLines={1}>
        {text}
      </Text>
      {closable ? (
        <Pressable
          accessibilityRole="button"
          accessibilityLabel="Remove"
          disabled={disabled}
          hitSlop={6}
          onPress={(e) => {
            e?.stopPropagation?.();
            if (!disabled) onClose?.();
          }}
          style={local.close}
        >
          <Text style={[local.closeGlyph, { color: colors.color }]}>×</Text>
        </Pressable>
      ) : null}
    </Root>
  );
}

const local = StyleSheet.create({
  root: {
    flexDirection: 'row',
    alignItems: 'center',
    alignSelf: 'flex-start',
    gap: 4,
    maxWidth: '100%',
    borderRadius: 999,
    paddingHorizontal: 12,
    paddingVertical: 6,
  },
  text: { fontSize: 13, fontWeight: '700', flexShrink: 1 },
  close: {
    width: 20,
    height: 20,
    borderRadius: 10,
    alignItems: 'center',
    justifyContent: 'center',
  },
  closeGlyph: { fontSize: 16, fontWeight: '700', lineHeight: 18 },
});
