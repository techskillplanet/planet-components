import React, { useState } from 'react';
import { Pressable, Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspButton({ text, variant = 'default', disabled = false, fullWidth = true, theme, onPress }) {
  const t = withTheme(theme);
  const [pressed, setPressed] = useState(false);
  const isFlatVariant = variant === 'text' || variant === 'link';
  const showRaisedShadow = t.buttonRaisedShadowEnabled !== false && !isFlatVariant;
  const shadowLift = showRaisedShadow ? (t.shadowControlIslandLiftY || 0) : 0;
  const faceHeight = t.buttonFaceHeight || 46;
  const buttonHeight = faceHeight + shadowLift;
  const pressedDrop = showRaisedShadow ? (t.pressedDropY || 0) : 0;
  const faceColor = variant === 'primary' ? t.brandPrimary : variant === 'danger' ? t.danger : 'transparent';
  const textColor = variant === 'primary' || variant === 'danger' ? '#FFFFFF' : isFlatVariant ? t.brandPrimary : t.textPrimary;

  return (
    <Pressable
      disabled={disabled}
      onPress={onPress}
      onPressIn={() => setPressed(true)}
      onPressOut={() => setPressed(false)}
      style={[styles.button, fullWidth && styles.full, { height: buttonHeight }, disabled && styles.disabled]}
    >
      {showRaisedShadow && (
        <View style={[styles.buttonShadow, { top: shadowLift, height: faceHeight, backgroundColor: t.borderDefault }]} />
      )}
      <View
        style={[
          styles.buttonFace,
          {
            height: faceHeight,
            backgroundColor: faceColor,
            borderColor: isFlatVariant ? 'transparent' : t.borderDefault,
            transform: [{ translateY: pressed ? pressedDrop : 0 }],
          },
        ]}
      >
        <Text style={[styles.buttonText, { color: textColor }]}>{text}</Text>
      </View>
    </Pressable>
  );
}
