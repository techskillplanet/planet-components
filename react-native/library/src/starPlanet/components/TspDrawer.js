import React from 'react';
import { Modal, Pressable, Text, View, StyleSheet, useWindowDimensions } from 'react-native';
import { styles as shared, withTheme } from '../utils/shared';

/** Overlay drawer — bottom / left / right panel with mask `onClose`. */
export function TspDrawer({
  visible = false,
  title = '',
  placement = 'bottom',
  theme,
  onClose,
  children,
}) {
  const t = withTheme(theme);
  const { width } = useWindowDimensions();
  const sideW = Math.min(320, width * 0.82);

  let rootJustify = { justifyContent: 'flex-end' };
  let panelStyle = {
    alignSelf: 'stretch',
    maxHeight: '78%',
    borderTopLeftRadius: 24,
    borderTopRightRadius: 24,
  };

  if (placement === 'left') {
    rootJustify = { flexDirection: 'row', justifyContent: 'flex-start' };
    panelStyle = {
      width: sideW,
      height: '100%',
      borderTopRightRadius: 24,
      borderBottomRightRadius: 24,
    };
  } else if (placement === 'right') {
    rootJustify = { flexDirection: 'row', justifyContent: 'flex-end' };
    panelStyle = {
      width: sideW,
      height: '100%',
      borderTopLeftRadius: 24,
      borderBottomLeftRadius: 24,
    };
  }

  return (
    <Modal transparent animationType="fade" visible={visible} onRequestClose={onClose}>
      <View style={[local.maskRoot, rootJustify]}>
        <Pressable
          accessibilityLabel="Close"
          accessibilityRole="button"
          style={local.maskFill}
          onPress={() => onClose?.()}
        />
        <View
          accessibilityRole="dialog"
          accessibilityLabel={title || 'Drawer'}
          style={[
            local.panel,
            panelStyle,
            {
              backgroundColor: t.surfaceRaised,
              borderColor: t.borderDefault,
            },
          ]}
        >
          {title ? (
            <View style={local.header}>
              <Text style={[shared.strong, { color: t.textPrimary, flex: 1 }]} numberOfLines={1}>
                {title}
              </Text>
              <Pressable
                accessibilityLabel="Close"
                accessibilityRole="button"
                onPress={() => onClose?.()}
                hitSlop={8}
                style={local.closeBtn}
              >
                <Text style={{ color: t.textSecondary, fontSize: 22, fontWeight: '700' }}>×</Text>
              </Pressable>
            </View>
          ) : null}
          <View style={local.body}>{children}</View>
        </View>
      </View>
    </Modal>
  );
}

const local = StyleSheet.create({
  maskRoot: { flex: 1, backgroundColor: 'rgba(23,58,98,0.32)' },
  maskFill: { ...StyleSheet.absoluteFillObject },
  panel: { borderWidth: 1, zIndex: 1, overflow: 'hidden' },
  header: {
    minHeight: 52,
    flexDirection: 'row',
    alignItems: 'center',
    paddingHorizontal: 16,
    gap: 8,
  },
  closeBtn: { width: 36, height: 36, alignItems: 'center', justifyContent: 'center' },
  body: { paddingHorizontal: 16, paddingBottom: 20, gap: 12 },
});
