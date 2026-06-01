import React from 'react';
import { Modal, Pressable, Text, View } from 'react-native';
import { optionText, styles, withTheme } from '../utils/shared';

export function TspOptionSheet({ title = '请选择', options = [], selectedIndex = 0, visible = false, theme, onSelect, onCancel }) {
  const t = withTheme(theme);
  return <Modal transparent animationType="slide" visible={visible} onRequestClose={onCancel}><Pressable style={styles.sheetMask} onPress={onCancel} /><View style={[styles.sheetPanel, { backgroundColor: t.surfaceRaised, borderColor: t.borderDefault }]}><View style={styles.sheetHeader}><Text onPress={onCancel} style={[styles.link, { color: t.brandPrimary }]}>取消</Text><Text style={[styles.strong, { color: t.textPrimary }]}>{title}</Text><View style={{ width: 44 }} /></View>{options.map((option, index) => <Pressable key={String(optionText(option)) + '-' + index} onPress={() => onSelect && onSelect(index, option)} style={[styles.sheetOption, index === selectedIndex && { backgroundColor: t.brandPrimary }]}><Text style={{ width: 28, color: index === selectedIndex ? '#FFFFFF' : t.textPrimary }}>{index === selectedIndex ? '✓' : ''}</Text><Text style={[styles.strong, { color: index === selectedIndex ? '#FFFFFF' : t.textPrimary }]}>{optionText(option)}</Text></Pressable>)}</View></Modal>;
}

