import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspIconButton({ icon, selected = false, disabled = false, variant = 'default', theme = starPlanetTheme, onTap }) {
  return h(
    'button',
    {
      type: 'button',
      disabled,
      className: cx('bc-icon-button', `bc-icon-button--${variant}`, selected && 'bc-selected', disabled && 'bc-disabled'),
      style: themed(theme),
      onClick: disabled ? undefined : onTap
    },
    icon
  );
}
