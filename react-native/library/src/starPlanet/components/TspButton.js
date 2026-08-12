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

  // Align with Web/Android: default uses opaque surfaceRaised so shadow only peeks below,
  // matching primary's raised-island look (transparent face would show the whole shadow through).
  let faceColor = t.surfaceRaised;
  let borderColor = t.borderDefault;
  let textColor = t.textPrimary;
  if (variant === 'primary') {
    faceColor = t.brandPrimary;
    textColor = '#FFFFFF';
  } else if (variant === 'danger') {
    faceColor = t.danger;
    textColor = '#FFFFFF';
  } else if (isFlatVariant) {
    faceColor = 'transparent';
    borderColor = 'transparent';
    textColor = t.brandPrimary;
  }

  return (
    <Pressable
      disabled={disabled}
      onPress={onPress}
      onPressIn={() => setPressed(true)}
      onPressOut={() => setPressed(false)}
      style={[styles.button, fullWidth && styles.full, { height: buttonHeight }, disabled && styles.disabled]}
    >
      {showRaisedShadow && (
        <View
          style={[
            styles.buttonShadow,
            { top: shadowLift, height: faceHeight, backgroundColor: t.borderDefault },
          ]}
        />
      )}
      <View
        style={[
          styles.buttonFace,
          {
            height: faceHeight,
            backgroundColor: faceColor,
            borderColor,
            transform: [{ translateY: pressed ? pressedDrop : 0 }],
          },
        ]}
      >
        <Text style={[styles.buttonText, { color: textColor }]}>{text}</Text>
      </View>
    </Pressable>
  );
}
