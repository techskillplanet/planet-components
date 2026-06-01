import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspTextLink({ text, inverse = false, theme = starPlanetTheme, onTap }) {
  return h('button', { type: 'button', className: cx('bc-text-link', inverse && 'bc-text-link--inverse'), style: themed(theme), onClick: onTap }, text);
}
