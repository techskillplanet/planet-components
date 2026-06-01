import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspToast({ message, variant = 'info', duration = 1800, theme = starPlanetTheme }) {
  return h('div', { className: cx('bc-toast', `bc-toast--${variant}`), style: themed(theme), role: 'status', 'data-duration': duration }, message);
}
