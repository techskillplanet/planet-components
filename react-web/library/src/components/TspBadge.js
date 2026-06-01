import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspBadge({ text, variant = 'default', disabled = false, theme = starPlanetTheme }) {
  return h('span', { className: cx('bc-badge', `bc-badge--${variant}`, disabled && 'bc-disabled'), style: themed(theme) }, text);
}
