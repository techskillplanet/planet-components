import React from 'react';
import { Text } from 'react-native';
import { PLANET_ICON_GLYPHS } from '../icons/planetIcons';
import { starPlanetTheme } from '../theme';

/**
 * Named Planet icon (glyph fallback — no react-native-svg peer required).
 * Path data remains available via PLANET_ICONS for apps that add SVG.
 */
export function TspIcon({
  name = 'check',
  size = 20,
  color,
  theme = starPlanetTheme,
  label,
  style,
  ...rest
}) {
  const glyph = PLANET_ICON_GLYPHS[name] || PLANET_ICON_GLYPHS.check;
  return (
    <Text
      accessibilityRole={label ? 'image' : undefined}
      accessibilityLabel={label}
      accessible={Boolean(label)}
      importantForAccessibility={label ? 'yes' : 'no-hide-descendants'}
      style={[
        {
          fontSize: size,
          lineHeight: size + 2,
          color: color || theme.textPrimary,
          includeFontPadding: false,
          textAlign: 'center',
        },
        style,
      ]}
      {...rest}
    >
      {glyph}
    </Text>
  );
}
