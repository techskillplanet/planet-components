import { React, h, cx, themed, starPlanetTheme } from './_shared.js';
import { useState } from 'react';

/** Hover/focus tooltip. */
export function TspTooltip({
  text = '',
  placement = 'top',
  visible,
  theme = starPlanetTheme,
  children,
}) {
  const [open, setOpen] = useState(false);
  const shown = visible != null ? visible : open;
  return h(
    'span',
    {
      className: cx('bc-tooltip', `bc-tooltip--${placement}`, shown && 'bc-open'),
      style: themed(theme),
      onMouseEnter: () => setOpen(true),
      onMouseLeave: () => setOpen(false),
      onFocus: () => setOpen(true),
      onBlur: () => setOpen(false),
    },
    children,
    shown
      ? h('span', { className: 'bc-tooltip__bubble', role: 'tooltip' }, text)
      : null
  );
}
