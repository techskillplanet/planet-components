import React from 'react';
import { ScrollView, Text, View } from 'react-native';
import { TspListItem } from '/basic-controls-react-native';
import { componentDescriptions, componentGroups } from '../data/componentDocs';

export function HomePage({ theme, onOpenComponent }) {
  return (
    <ScrollView contentContainerStyle={{ padding: 18, gap: 14, paddingBottom: 96 }}>
      {componentGroups.map(([category, names]) => <View key={category} style={{ gap: 8 }}><Text style={{ color: theme.textSecondary, fontWeight: '900' }}>{category}</Text>{names.map(name => <TspListItem key={name} title={'Tsp' + name} message={componentDescriptions[name]} trailing="›" theme={theme} onPress={() => onOpenComponent(name)} />)}</View>)}
    </ScrollView>
  );
}
