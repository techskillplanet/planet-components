import { React, h, cx, themed, starPlanetTheme } from './_shared.js';

/** Horizontal hairline divider. */
export function TspDivider({
  text = '',
  disabled = false,
  variant = 'default',
  theme = starPlanetTheme,
}) {
  return h('hr', {
    className: cx('bc-divider', `bc-divider--${variant}`, disabled && 'bc-disabled'),
    style: themed(theme),
    role: 'separator',
    'aria-label': text || undefined,
  });
}
