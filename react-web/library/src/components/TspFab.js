import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Floating action button. */
export function TspFab({
  icon = '+',
  text = '',
  variant = 'primary',
  disabled = false,
  theme = starPlanetTheme,
  onTap,
}) {
  return h(
    'button',
    {
      type: 'button',
      className: cx('bc-fab', `bc-fab--${variant}`, text && 'bc-fab--extended', disabled && 'bc-disabled'),
      style: themed(theme),
      disabled,
      'aria-label': text || String(icon),
      onClick: disabled ? undefined : onTap,
    },
    h('span', { className: 'bc-fab__icon', 'aria-hidden': true }, icon),
    text ? h('span', { className: 'bc-fab__text' }, text) : null
  );
}
