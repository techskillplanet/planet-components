import React from 'react';
import { Pressable, Text } from 'react-native';
import { styles, withTheme } from '../utils/shared';

function renderIcon(icon, color) {
  if (icon == null || icon === false) return null;
  if (typeof icon === 'string' || typeof icon === 'number') {
    return <Text style={[styles.strong, { color, fontSize: 20 }]}>{icon}</Text>;
  }
  if (React.isValidElement(icon)) {
    return React.cloneElement(icon, {
      color: icon.props.color ?? color,
      size: icon.props.size ?? 22,
    });
  }
  return icon;
}

export function TspIconButton({
  icon,
  selected = false,
  disabled = false,
  theme,
  onPress,
}) {
  const t = withTheme(theme);
  const color = selected ? '#FFFFFF' : t.textPrimary;
  return (
    <Pressable
      disabled={disabled}
      onPress={onPress}
      accessibilityRole="button"
      style={[
        styles.iconButton,
        {
          backgroundColor: selected ? t.brandPrimary : t.surfaceRaised,
          borderColor: t.borderDefault,
        },
        disabled && styles.disabled,
      ]}
    >
      {renderIcon(icon, color)}
    </Pressable>
  );
}
