import React, { useState } from 'react';
import { View } from 'react-native';
import { useSafeAreaInsets } from 'react-native-safe-area-context';
import { TspBottomTab, TspTopBar, builtInThemePresets, resolveTheme } from '@techskillplanet/planet-components-react-native';
import { ComponentDetailPage } from '../pages/ComponentDetailPage';
import { HomePage } from '../pages/HomePage';
import { SettingsPage } from '../pages/SettingsPage';

export function AppRouter() {
  const [route, setRoute] = useState({ name: 'home' });
  const [themePresetKey, setThemePresetKey] = useState('sky:island_raised');
  const [languageKey, setLanguageKey] = useState('zh-CN');
  const activePreset = builtInThemePresets.find(preset => `${preset.colorKey}:${preset.styleProfile}` === themePresetKey)
    || builtInThemePresets[0];
  const theme = resolveTheme(activePreset.colorKey, activePreset.styleProfile);
  const insets = useSafeAreaInsets();
  const tabs = [{ key: 'home', title: '学习', icon: '⌂' }, { key: 'settings', title: '设置', icon: '⚙' }];
  const inDetail = route.name === 'detail';

  return (
    <View style={{ flex: 1, backgroundColor: theme.pageStart }}>
      <TspTopBar
        title={inDetail ? 'Tsp' + route.component : '基础组件'}
        showBack={inDetail}
        theme={theme}
        topInset={insets.top}
        onBack={() => setRoute({ name: 'home' })}
      />
      {route.name === 'settings'
        ? <SettingsPage theme={theme} themePresetKey={themePresetKey} setThemePresetKey={setThemePresetKey} languageKey={languageKey} setLanguageKey={setLanguageKey} />
        : inDetail
          ? <ComponentDetailPage name={route.component} theme={theme} />
          : <HomePage theme={theme} onOpenComponent={component => setRoute({ name: 'detail', component })} />}
      {!inDetail && <TspBottomTab tabs={tabs} selectedKey={route.name} theme={theme} onSelect={key => setRoute({ name: key })} />}
    </View>
  );
}
