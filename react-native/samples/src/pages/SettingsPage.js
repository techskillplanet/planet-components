import React from 'react';
import { ScrollView, Text, View } from 'react-native';
import { TspButton, TspCard, builtInThemePresets } from '@techskillplanet/planet-components-react-native';

const themeLabelKeys = {
  'sky:island_raised': 'sample/theme/sky_raised',
  'sky:island_flat': 'sample/theme/sky_flat',
  'night:island_raised': 'sample/theme/star_raised',
  'night:island_flat': 'sample/theme/star_flat',
  'mint:island_raised': 'sample/theme/mint_raised',
  'mint:island_flat': 'sample/theme/mint_flat',
};

export function SettingsPage({ theme, themePresetKey, setThemePresetKey, languageKey, setLanguageKey, t }) {
  const translate = t || (key => key);
  const activeLabel = translate(themeLabelKeys[themePresetKey] || themePresetKey);

  return (
    <ScrollView contentContainerStyle={{ padding: 18, gap: 14, paddingBottom: 96 }}>
      <TspCard theme={theme}>
        <Text style={{ color: theme.textPrimary, fontWeight: '800' }}>{translate('sample/settings/theme/title')}</Text>
        <Text style={{ marginTop: 6, color: theme.textSecondary, fontSize: 13 }}>
          {translate('sample/settings/theme/current', activeLabel)}
        </Text>
        <Text style={{ marginTop: 4, color: theme.textTertiary, fontSize: 12 }}>
          {translate('sample/settings/theme/hint')}
        </Text>
        <View style={{ marginTop: 10 }}>
          <TspButton text={translate('sample/settings/theme/preview_button')} variant="primary" theme={theme} />
        </View>
        {builtInThemePresets.map(preset => {
          const key = `${preset.colorKey}:${preset.styleProfile}`;
          return (
            <View key={key} style={{ marginTop: 8 }}>
              <TspButton
                text={translate(themeLabelKeys[key] || preset.label)}
                variant={key === themePresetKey ? 'primary' : 'default'}
                theme={theme}
                onPress={() => setThemePresetKey(key)}
              />
            </View>
          );
        })}
      </TspCard>
      <TspCard theme={theme}>
        <Text style={{ color: theme.textPrimary, fontWeight: '800' }}>{translate('sample/settings/language/title')}</Text>
        <Text style={{ marginTop: 6, color: theme.textSecondary, fontSize: 13 }}>
          {translate('sample/settings/language/current', translate(`sample/lang/${languageKey}`))}
        </Text>
        <Text style={{ marginTop: 4, color: theme.textTertiary, fontSize: 12 }}>
          {translate('sample/settings/language/hint')}
        </Text>
        {[['zh-CN', 'sample/lang/zh-CN'], ['en', 'sample/lang/en'], ['ja', 'sample/lang/ja']].map(([key, labelKey]) => (
          <View key={key} style={{ marginTop: 8 }}>
            <TspButton
              text={translate(labelKey)}
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
