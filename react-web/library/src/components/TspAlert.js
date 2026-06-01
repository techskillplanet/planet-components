import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspAlert({ title, message, variant = 'info', theme = starPlanetTheme }) {
  return h(
    'section',
    { className: cx('bc-alert', `bc-alert--${variant}`), style: themed(theme), role: 'status' },
    title && h('div', { className: 'bc-alert__title' }, title),
    message && h('div', { className: 'bc-alert__message' }, message)
  );
}
