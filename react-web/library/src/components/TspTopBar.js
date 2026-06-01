import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspTopBar({ title, showBack = false, backgroundColor, theme = starPlanetTheme, onBack }) {
  return h(
    'header',
    { className: 'bc-top-bar', style: themed(theme, backgroundColor ? { backgroundColor } : undefined) },
    showBack && h('button', { type: 'button', className: 'bc-top-bar__back', onClick: onBack, 'aria-label': 'Back' }, '‹'),
    h('div', { className: 'bc-top-bar__title' }, title)
  );
}
