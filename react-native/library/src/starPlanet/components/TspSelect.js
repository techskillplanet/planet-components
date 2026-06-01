import React from 'react';
import { Pressable, Text } from 'react-native';
import { TspOptionSheet } from './TspOptionSheet';
import { optionText, styles, withTheme } from '../utils/shared';

export function TspSelect({ options = [], selectedIndex = 0, disabled = false, theme, onSelect }) {
  const [open, setOpen] = React.useState(false);
  const t = withTheme(theme);
  return <><Pressable disabled={disabled} onPress={() => setOpen(true)} style={[styles.select, { backgroundColor: t.surfaceRaised, borderColor: t.borderDefault }, disabled && styles.disabled]}><Text style={{ color: t.textPrimary }}>{optionText(options[selectedIndex])}</Text><Text style={{ color: t.textTertiary }}>⌄</Text></Pressable><TspOptionSheet visible={open} options={options} selectedIndex={selectedIndex} theme={t} onCancel={() => setOpen(false)} onSelect={(index, option) => { setOpen(false); onSelect && onSelect(index, option); }} /></>;
}

