import React from 'react';
import { Image, Text, View, StyleSheet } from 'react-native';
import { withTheme } from '../utils/shared';

const SIZE_MAP = { sm: 32, md: 40, lg: 56 };

/** Circular avatar — initials or remote/local Image `src`. */
export function TspAvatar({
  text = '',
  src,
  size = 'md',
  variant = 'default',
  theme,
}) {
  const t = withTheme(theme);
  const dim = SIZE_MAP[size] || SIZE_MAP.md;
  const initial = String(text || '?').trim().slice(0, 2).toUpperCase();
  const bg =
    variant === 'primary' ? t.brandPrimary : variant === 'subtle' ? t.brandSubtle : t.surfaceSubtle;
  const fg = variant === 'primary' ? '#FFFFFF' : t.textPrimary;

  return (
    <View
      accessibilityRole="image"
      accessibilityLabel={text || 'Avatar'}
      style={[
        local.root,
        {
          width: dim,
          height: dim,
          borderRadius: dim / 2,
          backgroundColor: bg,
          borderColor: t.borderDefault,
        },
      ]}
    >
      {src ? (
        <Image
          source={typeof src === 'string' ? { uri: src } : src}
          style={{ width: dim, height: dim, borderRadius: dim / 2 }}
          accessibilityIgnoresInvertColors
        />
      ) : (
        <Text style={[local.text, { color: fg, fontSize: dim * 0.38 }]}>{initial}</Text>
      )}
    </View>
  );
}

const local = StyleSheet.create({
  root: {
    borderWidth: 1,
    alignItems: 'center',
    justifyContent: 'center',
    overflow: 'hidden',
  },
  text: { fontWeight: '800', textAlign: 'center' },
});
