import { StyleSheet } from 'react-native';

/** Flatten RN style arrays/objects for assertions. */
export function flattenStyle(style) {
  return StyleSheet.flatten(style) || {};
}

/** True when style uses absolute left docking (TopBar back). */
export function isLeftDocked(style) {
  const flat = flattenStyle(style);
  return flat.position === 'absolute' && (flat.left === 0 || flat.left === '0');
}
