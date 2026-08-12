import React from 'react';
import { Pressable, Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspTopBar({
  title,
  showBack = false,
  theme,
  onBack,
  topInset = 0,
  immersive = true,
  backIcon,
}) {
  const t = withTheme(theme);
  const inset = immersive ? topInset : 0;
  const backContent = (() => {
    if (!showBack) return null;
    if (backIcon != null) {
      if (React.isValidElement(backIcon)) {
        return React.cloneElement(backIcon, {
          color: backIcon.props.color ?? t.brandPrimary,
          size: backIcon.props.size ?? 26,
        });
      }
      return backIcon;
    }
    return <Text style={{ color: t.brandPrimary, fontSize: 28, lineHeight: 32 }}>‹</Text>;
  })();

  return (
    <View
      testID="tsp-top-bar"
      style={[
        styles.topBarShell,
        {
          paddingTop: inset,
          backgroundColor: t.surfaceRaised,
          borderBottomColor: t.borderDefault,
        },
      ]}
    >
      <View style={styles.topBar}>
        {showBack ? (
          <Pressable
            testID="tsp-top-bar-back"
            accessibilityRole="button"
            accessibilityLabel="Back"
            onPress={onBack}
            style={styles.topBack}
          >
            {backContent}
          </Pressable>
        ) : (
          <View pointerEvents="none" style={styles.topBack} />
        )}
        <Text style={[styles.title, { color: t.textPrimary, textAlign: 'center', paddingHorizontal: 48 }]} numberOfLines={1}>
          {title}
        </Text>
      </View>
    </View>
  );
}
