import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

export function TspAmount({ symbol = '¥', value, cycle = '', symbolAfter = false, strikeThrough = false, theme = starPlanetTheme }) {
  return h(
    'span',
    { className: cx('bc-amount', strikeThrough && 'bc-strike'), style: themed(theme) },
    !symbolAfter && h('span', { className: 'bc-amount__symbol' }, symbol),
    h('span', { className: 'bc-amount__value' }, value),
    symbolAfter && h('span', { className: 'bc-amount__symbol' }, symbol),
    cycle && h('span', { className: 'bc-amount__cycle' }, `/${cycle}`)
  );
}
