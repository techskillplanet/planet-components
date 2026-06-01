import React, { useState } from 'react';
import { View } from 'react-native';
import { TspBottomTab, TspTopBar, starPlanetThemes } from '/basic-controls-react-native';
import { ComponentDetailPage } from '../pages/ComponentDetailPage';
import { HomePage } from '../pages/HomePage';
import { SettingsPage } from '../pages/SettingsPage';

export function AppRouter() {
  const [route, setRoute] = useState({ name: 'home' });
  const [themeKey, setThemeKey] = useState('sky');
  const [languageKey, setLanguageKey] = useState('zh-CN');
  const theme = starPlanetThemes[themeKey];
  const tabs = [{ key: 'home', title: '学习', icon: '⌂' }, { key: 'settings', title: '设置', icon: '⚙' }];
  const inDetail = route.name === 'detail';

  return (
    <View style={{ flex: 1, backgroundColor: theme.pageStart }}>
      <TspTopBar title={inDetail ? 'Tsp' + route.component : '基础组件'} showBack={inDetail} theme={theme} onBack={() => setRoute({ name: 'home' })} />
      {route.name === 'settings' ? <SettingsPage theme={theme} themeKey={themeKey} setThemeKey={setThemeKey} languageKey={languageKey} setLanguageKey={setLanguageKey} /> : inDetail ? <ComponentDetailPage name={route.component} theme={theme} /> : <HomePage theme={theme} onOpenComponent={component => setRoute({ name: 'detail', component })} />}
      {!inDetail && <TspBottomTab tabs={tabs} selectedKey={route.name} theme={theme} onSelect={key => setRoute({ name: key })} />}
    </View>
  );
}
