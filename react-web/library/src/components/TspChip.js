import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspChip({ text, variant = 'default', selected = false, disabled = false, theme = starPlanetTheme, onTap }) {
  return h(
    'button',
    {
      type: 'button',
      disabled,
      className: cx('bc-chip', `bc-chip--${variant}`, selected && 'bc-selected', disabled && 'bc-disabled'),
      style: themed(theme),
      onClick: disabled ? undefined : onTap
    },
    text
  );
}
