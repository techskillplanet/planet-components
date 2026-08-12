import React, { useMemo, useState } from 'react';
import { View } from 'react-native';
import { useSafeAreaInsets } from 'react-native-safe-area-context';
import { TspBottomTab, TspTopBar, TspToast, builtInThemePresets, resolveTheme } from '@techskillplanet/planet-components-react-native';
import { ComponentDetailPage } from '../pages/ComponentDetailPage';
import { HomePage } from '../pages/HomePage';
import { SettingsPage } from '../pages/SettingsPage';
import dictionary from '../i18n/sample_strings.json';
import { createTranslator } from '../i18n/createTranslator';
import { PlanetIcon } from '../icons/PlanetIcon';

const themeLabelKeys = {
  'sky:island_raised': 'sample/theme/sky_raised',
  'sky:island_flat': 'sample/theme/sky_flat',
  'night:island_raised': 'sample/theme/star_raised',
  'night:island_flat': 'sample/theme/star_flat',
  'mint:island_raised': 'sample/theme/mint_raised',
  'mint:island_flat': 'sample/theme/mint_flat',
};

export function AppRouter() {
  const [route, setRoute] = useState({ name: 'home' });
  const [themePresetKey, setThemePresetKey] = useState('sky:island_raised');
  const [languageKey, setLanguageKey] = useState('zh-CN');
  const [toast, setToast] = useState('');
  const i18n = useMemo(() => createTranslator(dictionary, languageKey), [languageKey]);
  const activePreset = builtInThemePresets.find(preset => `${preset.colorKey}:${preset.styleProfile}` === themePresetKey)
    || builtInThemePresets[0];
  const theme = resolveTheme(activePreset.colorKey, activePreset.styleProfile);
  const insets = useSafeAreaInsets();
  const tabs = [
    { key: 'home', title: i18n.t('sample/tab/home'), icon: <PlanetIcon name="home" /> },
    { key: 'settings', title: i18n.t('sample/tab/settings'), icon: <PlanetIcon name="settings" /> },
  ];
  const inDetail = route.name === 'detail';
  const title = inDetail ? `Tsp${route.component}` : i18n.t('sample/app/title');

  const showToast = message => {
    setToast(message);
    setTimeout(() => setToast(''), 1600);
  };

  const onLanguageChange = next => {
    setLanguageKey(next);
    const nextI18n = createTranslator(dictionary, next);
    showToast(nextI18n.t('sample/toast/language_changed', nextI18n.t(`sample/lang/${next}`)));
  };

  const onThemeChange = key => {
    setThemePresetKey(key);
    showToast(i18n.t('sample/toast/theme_changed', i18n.t(themeLabelKeys[key] || key)));
  };

  return (
    <View style={{ flex: 1, backgroundColor: theme.pageStart }}>
      <TspTopBar
        title={title}
        showBack={inDetail}
        backIcon={<PlanetIcon name="back" />}
        theme={theme}
        topInset={insets.top}
        onBack={() => setRoute({ name: 'home' })}
      />
      {route.name === 'settings'
        ? (
          <SettingsPage
            theme={theme}
            themePresetKey={themePresetKey}
            setThemePresetKey={onThemeChange}
            languageKey={languageKey}
            setLanguageKey={onLanguageChange}
            t={i18n.t}
          />
        )
        : inDetail
          ? <ComponentDetailPage name={route.component} theme={theme} t={i18n.t} />
          : <HomePage theme={theme} t={i18n.t} onOpenComponent={component => setRoute({ name: 'detail', component })} />}
      {!inDetail && (
        <TspBottomTab
          tabs={tabs}
          selectedKey={route.name}
          theme={theme}
          bottomInset={insets.bottom}
          onSelect={key => setRoute({ name: key })}
        />
      )}
      {!!toast && (
        <View style={{ position: 'absolute', left: 18, right: 18, bottom: 24 + insets.bottom }}>
          <TspToast message={toast} variant="success" theme={theme} />
        </View>
      )}
    </View>
  );
}
