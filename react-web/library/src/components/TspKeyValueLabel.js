import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspKeyValueLabel({ label, value, theme = starPlanetTheme }) {
  return h('div', { className: 'bc-key-value', style: themed(theme) }, h('span', null, label), h('strong', null, value));
}
