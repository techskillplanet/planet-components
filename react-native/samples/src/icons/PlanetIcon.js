import React from 'react';
import { Ionicons } from '@expo/vector-icons';

/** Semantic demo icons backed by Ionicons (Expo vector iconfont). */
export const planetIcons = {
  music: props => <Ionicons name="musical-notes" {...props} />,
  check: props => <Ionicons name="checkmark" {...props} />,
  close: props => <Ionicons name="close" {...props} />,
  home: props => <Ionicons name="home-outline" {...props} />,
  settings: props => <Ionicons name="settings-outline" {...props} />,
  back: props => <Ionicons name="chevron-back" {...props} />,
};

export function PlanetIcon({ name, size = 22, color, ...rest }) {
  const Renderer = planetIcons[name];
  if (!Renderer) return null;
  return <Renderer size={size} color={color} {...rest} />;
}
