import React from 'react';
import { View, StyleSheet } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

/** Horizontal hairline divider. */
export function TspDivider({
  text = '',
  disabled = false,
  variant = 'default',
  theme,
}) {
  const t = withTheme(theme);
  return (
    <View
      accessibilityRole="none"
      accessibilityLabel={text || undefined}
      style={[
        local.line,
        {
          borderTopColor: variant === 'strong' ? t.textTertiary : t.borderDefault,
          opacity: disabled ? 0.4 : 1,
        },
        disabled && shared.disabled,
      ]}
    />
  );
}

const local = StyleSheet.create({
  line: { width: '100%', borderTopWidth: StyleSheet.hairlineWidth * 2, marginVertical: 12 },
});
