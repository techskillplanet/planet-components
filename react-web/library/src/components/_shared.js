import React from 'react';
import { starPlanetTheme, themeVars } from '../theme.js';

export { React, starPlanetTheme };
export const h = React.createElement;
export const cx = (...names) => names.filter(Boolean).join(' ');
export const clamp = (value, min, max) => Math.max(min, Math.min(max, Number(value) || min));
export const optionText = (option) => typeof option === 'object' && option !== null ? option.title ?? option.text ?? option.label ?? option.value : option;

export function themed(theme, style) {
  return { ...themeVars(theme), ...style };
}
