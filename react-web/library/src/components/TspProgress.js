import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspProgress({ progress = 0, variant = 'primary', theme = starPlanetTheme }) {
  const percent = clamp(progress, 0, 100);
  return h('div', { className: cx('bc-progress', `bc-progress--${variant}`), style: themed(theme), role: 'progressbar', 'aria-valuenow': percent, 'aria-valuemin': 0, 'aria-valuemax': 100 }, h('span', { style: { width: `${percent}%` } }));
}
