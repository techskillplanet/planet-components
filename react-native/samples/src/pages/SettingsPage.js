import React from 'react';
import { ScrollView, Text, View } from 'react-native';
import { TspButton, TspCard } from '/basic-controls-react-native';

export function SettingsPage({ theme, themeKey, setThemeKey, languageKey, setLanguageKey }) {
  return (
    <ScrollView contentContainerStyle={{ padding: 18, gap: 14, paddingBottom: 96 }}>
      <TspCard theme={theme}><Text style={{ color: theme.textPrimary, fontWeight: '800' }}>Theme Switch</Text>{['sky', 'night', 'mint'].map(key => <View key={key} style={{ marginTop: 8 }}><TspButton text={key} variant={key === themeKey ? 'primary' : 'default'} theme={theme} onPress={() => setThemeKey(key)} /></View>)}</TspCard>
      <TspCard theme={theme}><Text style={{ color: theme.textPrimary, fontWeight: '800' }}>Language Switch</Text>{[['zh-CN', '简体中文'], ['en', 'English'], ['ja', '日本語']].map(([key, label]) => <View key={key} style={{ marginTop: 8 }}><TspButton text={label} variant={key === languageKey ? 'primary' : 'default'} theme={theme} onPress={() => setLanguageKey(key)} /></View>)}</TspCard>
    </ScrollView>
  );
}
