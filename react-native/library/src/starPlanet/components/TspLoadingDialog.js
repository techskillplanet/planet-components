import React from 'react';
import { ActivityIndicator, Modal, Pressable, Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

export function TspLoadingDialog({
  visible = false,
  message = '加载中...',
  variant = 'default',
  dismissible = false,
  theme,
  onDismiss,
}) {
  const t = withTheme(theme);
  const compact = variant === 'compact';

  return (
    <Modal transparent animationType="fade" visible={!!visible} onRequestClose={dismissible ? onDismiss : undefined}>
      <Pressable
        style={styles.modalMask}
        onPress={dismissible ? onDismiss : undefined}
        disabled={!dismissible}
      >
        <Pressable
          style={[
            styles.loadingPanel,
            compact && styles.loadingPanelCompact,
            { backgroundColor: t.surfaceRaised, borderColor: t.borderDefault },
          ]}
          onPress={event => event.stopPropagation?.()}
        >
          <ActivityIndicator size={compact ? 'small' : 'large'} color={t.brandPrimary} />
          {!!message && (
            <Text style={[styles.strong, { color: t.textPrimary, textAlign: 'center', marginTop: compact ? 8 : 12 }]}>
              {message}
            </Text>
          )}
        </Pressable>
      </Pressable>
    </Modal>
  );
}
