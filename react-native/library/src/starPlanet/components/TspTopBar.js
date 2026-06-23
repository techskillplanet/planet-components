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
}) {
  const t = withTheme(theme);
  const inset = immersive ? topInset : 0;
  return (
    <View
      style={[
        styles.topBar,
        {
          paddingTop: inset,
          backgroundColor: t.surfaceRaised,
          borderBottomColor: t.borderDefault,
        },
      ]}
    >
      <Pressable onPress={onBack} style={styles.topBack}>
        <Text style={{ color: t.brandPrimary, fontSize: 28 }}>{showBack ? '‹' : ''}</Text>
      </Pressable>
      <Text style={[styles.title, { color: t.textPrimary }]}>{title}</Text>
      <View style={styles.topBack} />
    </View>
  );
}
