import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspCard({ variant = 'default', selected = false, disabled = false, content, theme = starPlanetTheme, children }) {
  return h(
    'section',
    {
      className: cx('bc-card', `bc-card--${variant}`, selected && 'bc-selected', disabled && 'bc-disabled'),
      style: themed(theme)
    },
    children ?? content
  );
}
