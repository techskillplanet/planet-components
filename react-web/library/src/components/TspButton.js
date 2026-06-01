import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspButton({
  text,
  variant = 'default',
  disabled = false,
  fullWidth = true,
  theme = starPlanetTheme,
  onTap,
  children
}) {
  return h(
    'button',
    {
      type: 'button',
      disabled,
      className: cx('bc-button', `bc-button--${variant}`, fullWidth && 'bc-full', disabled && 'bc-disabled'),
      style: themed(theme),
      onClick: disabled ? undefined : onTap
    },
    h('span', { className: 'bc-button__shadow' }),
    h('span', { className: 'bc-button__face' }, children ?? text)
  );
}
