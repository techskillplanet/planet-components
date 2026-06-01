import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspNotification({ title, message, variant = 'info', theme = starPlanetTheme }) {
  return h(
    'section',
    { className: cx('bc-notification', `bc-notification--${variant}`), style: themed(theme) },
    title && h('strong', null, title),
    message && h('span', null, message)
  );
}
