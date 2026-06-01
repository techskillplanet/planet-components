import React from 'react';
import { Modal, Text, View } from 'react-native';
import { TspButton } from './TspButton';
import { styles, withTheme } from '../utils/shared';

export function TspModal({ title, message, confirmText = 'OK', cancelText = 'Cancel', visible = true, theme, onConfirm, onCancel }) {
  const t = withTheme(theme);
  return <Modal transparent animationType="fade" visible={visible} onRequestClose={onCancel}><View style={styles.modalMask}><View style={[styles.modalPanel, { backgroundColor: t.surfaceRaised, borderColor: t.borderDefault }]}><Text style={[styles.title, { color: t.textPrimary }]}>{title}</Text><Text style={[styles.secondary, { color: t.textSecondary }]}>{message}</Text><View style={styles.modalActions}><TspButton text={cancelText} theme={t} onPress={onCancel} /><TspButton text={confirmText} variant="primary" theme={t} onPress={onConfirm} /></View></View></View></Modal>;
}

