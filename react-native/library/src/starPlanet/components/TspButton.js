import React from 'react';
import { Pressable, Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspButton({ text, variant = 'default', disabled = false, fullWidth = true, theme, onPress }) {
  const t = withTheme(theme);
  const faceColor = variant === 'primary' ? t.brandPrimary : variant === 'danger' ? t.danger : 'transparent';
  const textColor = variant === 'primary' || variant === 'danger' ? '#FFFFFF' : variant === 'text' || variant === 'link' ? t.brandPrimary : t.textPrimary;
  return <Pressable disabled={disabled} onPress={onPress} style={[styles.button, fullWidth && styles.full, disabled && styles.disabled]}>{variant !== 'text' && variant !== 'link' && <View style={[styles.buttonShadow, { backgroundColor: t.borderDefault }]} />}<View style={[styles.buttonFace, { backgroundColor: faceColor, borderColor: variant === 'text' || variant === 'link' ? 'transparent' : t.borderDefault }]}><Text style={[styles.buttonText, { color: textColor }]}>{text}</Text></View></Pressable>;
}

