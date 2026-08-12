import React from 'react';
import { Pressable, Text, View } from 'react-native';
import { styles, withTheme } from '../utils/shared';

const TAB_ICON_SIZE = 22;

function renderTabIcon(icon, color) {
  if (icon == null || icon === false) return null;
  if (typeof icon === 'string' || typeof icon === 'number') {
    return <Text style={{ color, fontSize: 18, lineHeight: TAB_ICON_SIZE, includeFontPadding: false }}>{icon}</Text>;
  }
  if (React.isValidElement(icon)) {
    return React.cloneElement(icon, {
      color: icon.props.color ?? color,
      size: icon.props.size ?? TAB_ICON_SIZE,
    });
  }
  return icon;
}

export function TspBottomTab({ tabs = [], selectedKey, theme, onSelect, bottomInset = 0 }) {
  const t = withTheme(theme);
  return (
    <View
      testID="tsp-bottom-tab"
      style={[
        styles.bottomTab,
        {
          backgroundColor: t.surfaceRaised,
          borderTopColor: t.borderDefault,
          // Safe-area adds below the content row — do not shrink the 52dp item area.
          paddingBottom: bottomInset,
        },
      ]}
    >
      <View testID="tsp-bottom-tab-content" style={styles.bottomTabContent}>
        {tabs.map(tab => {
          const active = tab.key === selectedKey;
          const color = active ? t.brandPrimary : t.textTertiary;
          return (
            <Pressable
              key={tab.key}
              onPress={() => onSelect && onSelect(tab.key, tab)}
              style={styles.bottomItem}
              accessibilityRole="tab"
              accessibilityState={{ selected: active }}
            >
              {renderTabIcon(tab.icon, color)}
              <Text style={[styles.bottomTabLabel, { color }]}>
                {tab.title || tab.text || tab.key}
              </Text>
            </Pressable>
          );
        })}
      </View>
    </View>
  );
}
