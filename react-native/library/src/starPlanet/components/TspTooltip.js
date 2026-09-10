import React, { useState } from 'react';
import { Pressable, Text, View, StyleSheet } from 'react-native';
import { withTheme } from '../utils/shared';

/** Press / long-press text bubble (placement: top | bottom). */
export function TspTooltip({
  text = '',
  placement = 'top',
  visible,
  theme,
  children,
}) {
  const t = withTheme(theme);
  const [open, setOpen] = useState(false);
  const shown = visible != null ? Boolean(visible) : open;
  const isTop = placement !== 'bottom';

  return (
    <View style={local.wrap}>
      {shown && isTop ? (
        <View
          accessibilityRole="text"
          style={[
            local.bubble,
            local.bubbleTop,
            { backgroundColor: t.textPrimary, borderColor: t.borderDefault },
          ]}
        >
          <Text style={local.bubbleText}>{text}</Text>
        </View>
      ) : null}
      <Pressable
        accessibilityRole="button"
        accessibilityHint={text ? `Shows tip: ${text}` : undefined}
        onPress={() => {
          if (visible != null) return;
          setOpen(v => !v);
        }}
        onLongPress={() => {
          if (visible != null) return;
          setOpen(true);
        }}
        delayLongPress={280}
        style={local.trigger}
      >
        {children}
      </Pressable>
      {shown && !isTop ? (
        <View
          accessibilityRole="text"
          style={[
            local.bubble,
            local.bubbleBottom,
            { backgroundColor: t.textPrimary, borderColor: t.borderDefault },
          ]}
        >
          <Text style={local.bubbleText}>{text}</Text>
        </View>
      ) : null}
    </View>
  );
}

const local = StyleSheet.create({
  wrap: { alignSelf: 'flex-start', alignItems: 'center', maxWidth: '100%' },
  trigger: { alignSelf: 'flex-start' },
  bubble: {
    maxWidth: 240,
    borderRadius: 12,
    borderWidth: 1,
    paddingHorizontal: 12,
    paddingVertical: 8,
    zIndex: 2,
  },
  bubbleTop: { marginBottom: 6 },
  bubbleBottom: { marginTop: 6 },
  bubbleText: { color: '#FFFFFF', fontSize: 13, fontWeight: '700' },
});
