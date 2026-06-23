import { React, h, cx, clamp, optionText, themed, starPlanetTheme } from './_shared.js';

/**
 * TspAmount – Monetary/numeric amount display.
 *
 * Shows currency symbol, value, optional cycle suffix and strikethrough.
 *
 * @param {Object} props
 * @param {string} [props.symbol='¥'] - Currency symbol.
 * @param {string} props.value - Numeric value string.
 * @param {string} [props.cycle=''] - Billing cycle (e.g. 'month').
 * @param {boolean} [props.symbolAfter=false] - Place symbol after value.
 * @param {boolean} [props.strikeThrough=false] - Apply strikethrough styling.
 * @param {Object} [props.theme] - Theme object.
 * @returns {React.ReactElement} A <span> with formatted amount.
 */

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
