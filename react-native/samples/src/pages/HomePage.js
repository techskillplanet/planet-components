import React from 'react';
import { ScrollView, Text, View } from 'react-native';
import { TspListItem } from '@techskillplanet/planet-components-react-native';
import { componentGroups, descriptionKeyOf } from '../data/componentDocs';

export function HomePage({ theme, onOpenComponent, t }) {
  const translate = t || (key => key);
  return (
    <ScrollView contentContainerStyle={{ padding: 18, gap: 14, paddingBottom: 96 }}>
      {componentGroups.map(([category, names]) => (
        <View key={category} style={{ gap: 8 }}>
          <Text style={{ color: theme.textSecondary, fontWeight: '900' }}>
            {translate(`sample/group/${category.toLowerCase()}`)}
          </Text>
          {names.map(name => (
            <TspListItem
              key={name}
              title={`Tsp${name}`}
              message={translate(descriptionKeyOf(name))}
              trailing="›"
              theme={theme}
              onPress={() => onOpenComponent(name)}
            />
          ))}
        </View>
      ))}
    </ScrollView>
  );
}
