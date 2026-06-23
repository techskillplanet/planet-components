import React from 'react';
import { ScrollView, Text, View } from 'react-native';
import { TspButton, TspCard, builtInThemePresets } from '@techskillplanet/planet-components-react-native';

export function SettingsPage({ theme, themePresetKey, setThemePresetKey, languageKey, setLanguageKey }) {
  const activePreset = builtInThemePresets.find(preset => `${preset.colorKey}:${preset.styleProfile}` === themePresetKey);

  return (
    <ScrollView contentContainerStyle={{ padding: 18, gap: 14, paddingBottom: 96 }}>
      <TspCard theme={theme}>
        <Text style={{ color: theme.textPrimary, fontWeight: '800' }}>Theme Switch</Text>
        <Text style={{ marginTop: 6, color: theme.textSecondary, fontSize: 13 }}>
          当前：{activePreset?.label || themePresetKey}
        </Text>
        <Text style={{ marginTop: 4, color: theme.textTertiary, fontSize: 12 }}>
          6 套内置主题：Sky / Star / Mint × 岛屿阴影 / 扁平无影
        </Text>
        <TspButton text="Primary island preview" variant="primary" theme={theme} />
        {builtInThemePresets.map(preset => {
          const key = `${preset.colorKey}:${preset.styleProfile}`;
          return (
            <View key={key} style={{ marginTop: 8 }}>
              <TspButton
                text={preset.label}
                variant={key === themePresetKey ? 'primary' : 'default'}
                theme={theme}
                onPress={() => setThemePresetKey(key)}
              />
            </View>
          );
        })}
      </TspCard>
      <TspCard theme={theme}>
        <Text style={{ color: theme.textPrimary, fontWeight: '800' }}>Language Switch</Text>
        {[['zh-CN', '简体中文'], ['en', 'English'], ['ja', '日本語']].map(([key, label]) => (
          <View key={key} style={{ marginTop: 8 }}>
            <TspButton
              text={label}
              variant={key === languageKey ? 'primary' : 'default'}
              theme={theme}
              onPress={() => setLanguageKey(key)}
            />
          </View>
        ))}
      </TspCard>
    </ScrollView>
  );
}
